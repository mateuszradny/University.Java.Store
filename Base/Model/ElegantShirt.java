package Base.Model;

import Base.Infrastructure.ShowInUI;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Mateusz on 06/01/2016.
 */
@ShowInUI(displayName = "Eleganckie koszule")
@XmlRootElement
public class ElegantShirt extends Shirt {
    @ShowInUI(displayName = "Krawat")
    private boolean tie;

    @ShowInUI(displayName = "Rozmiar kołnierza")
    private int collarSize;

    public boolean isTie() {
        return tie;
    }

    public void setTie(boolean tie) {
        this.tie = tie;
    }

    public int getCollarSize() {
        return collarSize;
    }

    public void setCollarSize(int collarSize) {
        this.collarSize = collarSize;
    }
}
