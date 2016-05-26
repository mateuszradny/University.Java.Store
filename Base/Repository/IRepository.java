package Base.Repository;

import Base.Model.EntityBase;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by Mateusz on 06/01/2016.
 */
public interface IRepository<T extends EntityBase>  {
    Collection<T> getAll();
    T get(UUID id);
    T add(T entity);
    T update(T entity);
    T delete(T entity);
}