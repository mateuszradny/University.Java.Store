package Base.Model;

import Base.Infrastructure.ShowInUI;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Mateusz on 06/01/2016.
 */
@ShowInUI(displayName = "Kurtki")
@XmlRootElement
public class Jacket extends Product {
    @ShowInUI(displayName = "Pora roku")
    private JacketType jacketType;

    @ShowInUI(displayName = "Rodzaj zamka")
    private LockType lockType;

    @ShowInUI(displayName = "Rozmiar")
    private Size size;

    public JacketType getJacketType() {
        return jacketType;
    }

    public void setJacketType(JacketType jacketType) {
        this.jacketType = jacketType;
    }

    public LockType getLockType() {
        return lockType;
    }

    public void setLockType(LockType lockType) {
        this.lockType = lockType;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
}