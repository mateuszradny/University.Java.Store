package UI;

import Base.Infrastructure.ShowInUI;
import javafx.scene.control.Alert;

import java.lang.reflect.Field;

/**
 * Created by Mateusz on 17/01/2016.
 * The helper class used to common tasks for UI.
 */
public class UIHelper {
    /**
     * @param type
     * @return Returns a display name associated with a given Class or simple name of class.
     */
    public static String getDisplayName(Class<?> type) {
        ShowInUI showInUI = type.getAnnotation(ShowInUI.class);

        if (showInUI == null)
            return type.getSimpleName();

        String displayName = showInUI.displayName();
        if (displayName == null || displayName == "")
            return type.getSimpleName();

        return displayName;
    }

    /**
     * @param field A field of class.
     * @return Returns a display name associated with a given Field or name of field.
     */
    public static String getDisplayName(Field field) {
        ShowInUI showInUI = field.getAnnotation(ShowInUI.class);

        if (showInUI == null)
            return field.getName();

        String displayName = showInUI.displayName();
        if (displayName == null || displayName == "")
            return field.getName();

        return displayName;
    }

    /**
     * Displays alert which contains information about an exception.
     * @param ex An exception to display.
     */
    public static void showException(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Coś poszło nie tak!");
        alert.setContentText(ex.getMessage());
        alert.showAndWait();
    }
}