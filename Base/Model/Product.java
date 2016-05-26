package Base.Model;

import Base.Infrastructure.ColorAdapter;
import Base.Infrastructure.ShowInUI;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.awt.*;
import java.math.BigDecimal;

/**
 * Created by Mateusz on 06/01/2016.
 * It represents the base class for clothing and footwear.
 */
public abstract class Product extends EntityBase {
    @ShowInUI(displayName = "Nazwa")
    private String name;

    @ShowInUI(displayName = "Cena")
    private BigDecimal price;

    @ShowInUI(displayName = "Płeć")
    private Sex sex;

    @ShowInUI(displayName = "Kolor")
    private Color color;

    @ShowInUI(displayName = "Marka")
    private String brand;

    /**
     * @return Returns the name of product
     */
    public String getName(){
        return name;
    }

    /**
     * Sets the name of the product.
     *
     * @param name The new name of the product.
     */
    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @XmlJavaTypeAdapter(ColorAdapter.class)
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
