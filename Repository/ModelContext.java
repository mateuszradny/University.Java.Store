package Repository;

import Base.IContext;
import Base.Model.*;
import Base.Repository.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by Mateusz on 07/01/2016.
 */
public class ModelContext implements IContext {
    private IBootsRepository bootsRepository = null;
    private IElegantShirtRepository elegantShirtRepository = null;
    private IJacketRepository jacketRepository = null;
    private IPantsRepository pantsRepository = null;
    private ITShirtRepository tShirtRepository = null;
    private IDiscountRepository discountRepository = null;
    private IImageRepository imageRepository = null;
    private ArrayList<Class<?>> modelTypes = null;

    @Override
    public IBootsRepository boots() {
        if (bootsRepository == null)
            bootsRepository = new BootsRepository("boots.xml");

        return bootsRepository;
    }

    @Override
    public IElegantShirtRepository elegantShirts() {
        if (elegantShirtRepository == null)
            elegantShirtRepository = new ElegantShirtRepository("elegantShirt.xml");

        return elegantShirtRepository;
    }

    @Override
    public IJacketRepository jackets() {
        if (jacketRepository == null)
            jacketRepository = new JacketRepository("jacket.xml");

        return jacketRepository;
    }

    @Override
    public IPantsRepository pants() {
        if (pantsRepository == null)
            pantsRepository = new PantsRepository("pants.xml");

        return pantsRepository;
    }

    @Override
    public ITShirtRepository tShirts() {
        if (tShirtRepository == null)
            tShirtRepository = new TShirtRepository("tshirt.xml");

        return tShirtRepository;
    }

    @Override
    public IDiscountRepository discounts() {
        if (discountRepository == null)
            discountRepository = new DiscountRepository("discount.xml");

        return discountRepository;
    }

    @Override
    public IImageRepository images() {
        if (imageRepository == null) {
            imageRepository = new ImageRepository("images", new String[]{"jpg", "jpeg", "png"});
        }

        return imageRepository;
    }

    @Override
    public List<Class<?>> getModelTypes() {
        if (modelTypes == null) {
            modelTypes = new ArrayList<>();
            modelTypes.add(Boots.class);
            modelTypes.add(ElegantShirt.class);
            modelTypes.add(Jacket.class);
            modelTypes.add(Pants.class);
            modelTypes.add(TShirt.class);
            modelTypes.add(Discount.class);
        }

        return modelTypes;
    }

    @Override
    public <T extends EntityBase> IRepository<T> getRepositoryFor(Class<?> type) {
        if (type == Boots.class)
            return (IRepository<T>) boots();

        if (type == ElegantShirt.class)
            return (IRepository<T>) elegantShirts();

        if (type == Jacket.class)
            return (IRepository<T>) jackets();

        if (type == Pants.class)
            return (IRepository<T>) pants();

        if (type == TShirt.class)
            return (IRepository<T>) tShirts();

        if (type == Discount.class)
            return (IRepository<T>) discounts();

        return null;
    }

    @Override
    public Product getProductById(UUID id) throws InvocationTargetException, IllegalAccessException {
        for (Class<?> type : getModelTypes()) {
            if (Product.class.isAssignableFrom(type)) {
                IRepository<?> repository = getRepositoryFor(type);
                if (repository != null) {
                    Product product = (Product) repository.get(id);
                    if (product != null)
                        return product;
                }
            }
        }

        return null;
    }
}