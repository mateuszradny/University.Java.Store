package Base.Logic;

import Base.Model.Discount;
import Base.Model.Product;
import java.math.BigDecimal;

/**
 * Created by Mateusz on 10/01/2016.
 * The logic used to manage discounts.
 */
public interface IDiscountLogic {

    /**
     * Adds a new discount for specified product.
     *
     * @param discount
     * @param product
     */
    void addDiscount(Discount discount, Product product);


    /**
     * Returns a price of product taking into account the current discount.
     * If actually the discount not exists for specified product the function returns a normal price.
     *
     * @param product
     * @return
     */
    BigDecimal getPriceAfterDiscount(Product product);
}
