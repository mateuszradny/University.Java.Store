package Base.Model;

/**
 * Created by Mateusz on 06/01/2016.
 * It represents human sex.
 */
public enum Sex {
    MAN("Mężczyzna"),
    WOMAN("Kobieta");

    private String displayName;

    Sex(String displayName) {
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