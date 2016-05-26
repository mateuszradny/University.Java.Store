package Base.Model;

import Base.Infrastructure.ShowInUI;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Mateusz on 06/01/2016.
 */
@ShowInUI(displayName = "Spodnie")
@XmlRootElement
public class Pants extends Wear {
    @ShowInUI(displayName = "Długość")
    private int lengthSize;

    @ShowInUI(displayName = "Szerokość")
    private int widthSize;

    public int getLengthSize() {
        return lengthSize;
    }

    public void setLengthSize(int lengthSize) {
        this.lengthSize = lengthSize;
    }

    public int getWidthSize() {
        return widthSize;
    }

    public void setWidthSize(int widthSize) {
        this.widthSize = widthSize;
    }
}
