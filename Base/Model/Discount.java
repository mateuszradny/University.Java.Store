package Base.Model;

import Base.Infrastructure.ShowInUI;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Mateusz on 10/01/2016.
 */
@ShowInUI(displayName = "Promocje")
@XmlRootElement
public class Discount extends EntityBase {
    private UUID productId;

    @ShowInUI(displayName = "Początek obniżki")
    private Date startDate;

    @ShowInUI(displayName = "Koniec obniżki")
    private Date endData;

    @ShowInUI(displayName = "Obniżka (%)")
    private double value;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndData() {
        return endData;
    }

    public void setEndData(Date endData) {
        this.endData = endData;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }
}