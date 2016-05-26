package Logic;

import Base.Logic.IDiscountLogic;
import Base.Model.Boots;
import Base.Model.Discount;
import Base.Model.Pants;
import Base.Model.Product;
import Base.Repository.IDiscountRepository;
import javafx.scene.control.TableColumn;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Mateusz on 10/01/2016.
 */
public class DiscountLogic implements IDiscountLogic {
    private IDiscountRepository repository;

    public DiscountLogic(IDiscountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void addDiscount(Discount discount, Product product) {
        if (discount == null)
            throw new IllegalArgumentException("Argument discount can not be null.");

        if (product == null)
            throw new IllegalArgumentException("Argument product can not be null.");

        if (!(product instanceof Boots || product instanceof Pants))
            throw new IllegalArgumentException("Invalid type of product.");

        if (discount.getStartDate().after(discount.getEndData()))
            throw new IllegalArgumentException("The start date is after the end date.");

        if (discount.getValue() < 10.0 || discount.getValue() > 70.0)
            throw new IllegalArgumentException("The value of discount is invalid (accept range: <10; 70>).");

        if (existsInDateRange(discount, product))
            throw new IllegalArgumentException("The discount for product already exists for specified date.");

        discount.setProductId(product.getId());
        this.repository.add(discount);
    }

    @Override
    public BigDecimal getPriceAfterDiscount(Product product) {
        if (product == null)
            throw new IllegalArgumentException("Argument product can not be null.");

        if (!(product instanceof Boots || product instanceof Pants))
            throw new IllegalArgumentException("Invalid type of product.");

        Discount discount = this.repository.getByProductIdForDate(product.getId(), new Date());
        if (discount == null)
            return product.getPrice();

        return product.getPrice().multiply(new BigDecimal((100 - discount.getValue()) / 100));
    }

    // TODO: Test it!
    private boolean existsInDateRange(Discount discount, Product product) {
        for (Discount d : this.repository.getAllByProductId(product.getId())) {
            if (discount.getStartDate().after(d.getStartDate()) && discount.getStartDate().before(d.getEndData()))
                return true;

            if (discount.getEndData().after(d.getStartDate()) && discount.getEndData().before(d.getEndData()))
                return true;

            if (discount.getStartDate().before(d.getStartDate()) && discount.getEndData().after(d.getEndData()))
                return true;

            if (discount.getStartDate().after(d.getStartDate()) && discount.getEndData().before(d.getEndData()))
                return true;
        }

        return false;
    }
}