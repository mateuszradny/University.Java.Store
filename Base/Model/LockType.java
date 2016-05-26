package Base.Model;

/**
 * Created by Mateusz on 06/01/2016.
 */
public enum LockType  {
    ZIPPER("Zamek błyskawiczny"),
    BUTTONS("Guziki");

    private String displayName;

    LockType(String displayName) {
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