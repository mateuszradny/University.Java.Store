package Base.Model;

import Base.Infrastructure.ShowInUI;

/**
 * Created by Mateusz on 06/01/2016.
 * This class represents clothes.
 */
public class Wear extends Product {
    @ShowInUI(displayName = "Materiał")
    private String material;

    /**
     * @return Returns the material from which it was made for.
     */
    public String getMaterial() {
        return material;
    }

    /**
     * @param material Set the material from which it was made for.
     */
    public void setMaterial(String material) {
        this.material = material;
    }
}