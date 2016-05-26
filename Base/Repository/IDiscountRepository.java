package Base.Repository;

import Base.Model.Discount;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Mateusz on 10/01/2016.
 */
public interface IDiscountRepository extends IRepository<Discount> {
    Collection<Discount> getAllByProductId(UUID productId);
    Discount getByProductIdForDate(UUID productId, Date date);
}