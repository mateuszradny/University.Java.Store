package Repository;

import Base.Model.Discount;
import Base.Repository.IDiscountRepository;

import java.util.*;

/**
 * Created by Mateusz on 10/01/2016.
 */
public class DiscountRepository extends RepositoryBase<Discount> implements IDiscountRepository {
    public DiscountRepository(String filePath) {
        super(Discount.class, filePath);
    }


    @Override
    public Collection<Discount> getAllByProductId(UUID productId) {
        List<Discount> discounts = new ArrayList<>();

        for (Discount discount : this.getAll()) {
            if (discount.getProductId().equals(productId))
                discounts.add(discount);
        }

        return discounts;
    }

    @Override
    public Discount getByProductIdForDate(UUID productId, Date date) {
        int count = 0;
        Discount result = null;

        for (Discount discount : getAllByProductId(productId)) {
            if (date.after(discount.getStartDate()) && date.before(discount.getEndData())) {
                count++;

                if (count > 1)
                    throw new IllegalStateException("In data source exists more than one 'Discount' object for specified product id and date.");

                result = discount;
            }
        }

        return  result;
    }
}