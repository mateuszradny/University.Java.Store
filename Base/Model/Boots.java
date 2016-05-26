package Base.Model;

import Base.Infrastructure.ShowInUI;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Mateusz on 06/01/2016.
 * This class represents boots.
 */
@ShowInUI(displayName = "Buty")
@XmlRootElement
public class Boots extends Product {
    @ShowInUI(displayName = "Korek")
    private boolean heels;

    @ShowInUI(displayName = "Rozmiar")
    private double size;

    /**
     * Determines if the boots have high heels.
     *
     * @return Returns true if the boots have high hells, otherwise returns false.
     */
    public boolean isHeels() {
        return heels;
    }

    /**
     * Sets type of boots.
     *
     * @param heels True for boots with high hells.
     */
    public void setHeels(boolean heels) {
        this.heels = heels;
    }

    /**
     * @return Returns the size of boots.
     */
    public double getSize() {
        return size;
    }

    /**
     * Sets new size of boots.
     *
     * @param size The new size of boots.
     */
    public void setSize(double size) {
        this.size = size;
    }
}