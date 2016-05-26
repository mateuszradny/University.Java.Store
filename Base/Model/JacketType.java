package Base.Model;

/**
 * Created by Mateusz on 06/01/2016.
 */
public enum JacketType {
    SUMMER_JACKET("Kurtka letnia"),
    WINTER_JACKET("Kurtka zimowa")
    ;
    private String displayName;

    JacketType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return Returns the display name for the specified value.
     */
    @Override
    public String toString() {
        return this.displayName;
    }
}