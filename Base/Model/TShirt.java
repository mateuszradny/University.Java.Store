package Base.Model;

import Base.Infrastructure.ShowInUI;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Mateusz on 06/01/2016.
 * This class represents t-shirts.
 */
@ShowInUI(displayName = "Koszulki")
@XmlRootElement
public class TShirt extends Shirt {
    @ShowInUI(displayName = "Rozmiar")
    private Size size;

    /**
     * @return Returns the size of t-shirt as Size enum (XS, S, M, L, XL, XLL).
     */
    public Size getSize() {
        return size;
    }

    /**
     * @param size Set the size of t-shirt.
     */
    public void setSize(Size size) {
        this.size = size;
    }
}