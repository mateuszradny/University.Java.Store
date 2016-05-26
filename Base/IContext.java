package Base;

import Base.Model.EntityBase;
import Base.Model.Product;
import Base.Repository.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Mateusz on 06/01/2016.
 * It represents the model of application. This interface contains all repositories used in application.
 */
public interface IContext {
    IBootsRepository boots();
    IElegantShirtRepository elegantShirts();
    IJacketRepository jackets();
    IPantsRepository pants();
    ITShirtRepository tShirts();
    IDiscountRepository discounts();
    IImageRepository images();

    /**
     * @return Returns all product types used in application.
     */
    List<Class<?>> getModelTypes();

    /**
     * @param type Type of Product
     * @param <T>
     * @return Returns repository associated with specified type1
     */
    <T extends EntityBase> IRepository<T> getRepositoryFor(Class<?> type);

    /**
     * @param id Product ID.
     * @param <T>
     * @return Returns product with a given id.
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    <T extends Product> T getProductById(UUID id) throws InvocationTargetException, IllegalAccessException;
}