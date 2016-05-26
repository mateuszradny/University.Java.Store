package Repository;

import Base.Model.EntityBase;
import Base.Repository.IRepository;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.*;

/**
 * Created by Mateusz on 06/01/2016.
 * The base repository class. It provides CRUD operations. When any CRUD function is invoked for the first time the
 * objects are loaded from XML file. All operations after loading the data from file are saves to file and stores in
 * memory for performance.
 *
 * The class which inherits from RepositoryBase class has to set 'type' field to type of T generic parameter.
 */
public abstract class RepositoryBase<T extends EntityBase> implements IRepository<T> {
    private String filePath;
    private boolean isLoaded;

    // Do not use directly, instead invoke getEntities() function.
    private HashMap<UUID, T> entities = null;
    private HashMap<UUID, T> getEntities() {
        if (entities == null) {
            entities = new HashMap<>();
            load();
        }

        return entities;
    }

    final Class<T> type;

    public RepositoryBase(Class<T> type, String filePath) {
        this.filePath = filePath;
        this.isLoaded = false;
        this.type = type;
    }

    @Override
    public Collection<T> getAll() {
        return this.getEntities().values();
    }

    @Override
    public T get(UUID id) {
        return this.getEntities().getOrDefault(id, null);
    }

    @Override
    public T add(T entity) {
        this.getEntities().put(entity.getId(), entity);
        this.saveChanges();
        return entity;
    }

    @Override
    public T delete(T entity) {
        if (this.getEntities().remove(entity.getId(), entity)) {
            this.saveChanges();
            return entity;
        }

        return null;
    }

    @Override
    public T update(T entity) {
        if (this.getEntities().containsKey(entity.getId())) {
            this.getEntities().put(entity.getId(), entity);
            this.saveChanges();
            return entity;
        }

        return null;
    }

    private void saveChanges() {
        try {
            JAXBContext context = JAXBContext.newInstance(Wrapper.class, type);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(new Wrapper<T>(new ArrayList<T>(this.entities.values())), new File(filePath));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void load() {
        try {
            if (!isLoaded) {
                StreamSource stream = new StreamSource(filePath);
                JAXBContext context = JAXBContext.newInstance(Wrapper.class, type);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                Wrapper<T> wrapper = (Wrapper<T>) unmarshaller.unmarshal(stream, Wrapper.class).getValue();

                this.entities.clear();
                for (T entity : wrapper.getEntities()) {
                    this.entities.put(entity.getId(), entity);
                }

                isLoaded = true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

@XmlRootElement(name = "Entities")
class Wrapper<T extends EntityBase> {
    private List<T> entities;

    public Wrapper() {
        entities = new ArrayList<T>();
    }

    public Wrapper(List<T> entities) {
        this.entities = entities;
    }

    @XmlAnyElement(lax = true)
    public List<T> getEntities() {
        return entities;
    }
}