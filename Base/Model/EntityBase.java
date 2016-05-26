package Base.Model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Mateusz on 06/01/2016.
 * It represents the base class elements. This class is used in IRepository interface as type of generic parameter.
 */
public abstract class EntityBase {
    @XmlAttribute
    private UUID id;

    @XmlElement
    private Date dateCreated;

    /**
     * Creates a new item with a unique identifier.
     */
    public EntityBase() {
        id = UUID.randomUUID();
        dateCreated = new Date();
    }

    /**
     * @return Returns the unique global identifier.
     */
    public UUID getId() {
        return id;
    }

    /**
     * @return Returns the creation date of the element.
     */
    public Date getDateCreated() {
        return dateCreated;
    }
}