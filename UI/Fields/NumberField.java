package UI.Fields;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.text.DecimalFormat;

/**
 * Created by Mateusz on 17/01/2016.
 * Represents a number field. You can set the number of decimal places (the default value is two places).
 */
public class NumberField extends TextField {
    private int precision;

    public NumberField(int precision) {
        this.precision = precision;

        // Validation data entered by the user
        this.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            TextField textField = (TextField) e.getSource();

            if (!isValid(textField.getText() + e.getCharacter()))
                e.consume();
        });

        // Validate data entered programmatically
        this.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!isValid(newValue)) {
                DecimalFormat decimalFormat = new DecimalFormat();
                decimalFormat.setMaximumFractionDigits(precision);
                decimalFormat.setMinimumFractionDigits(precision);
                decimalFormat.setGroupingUsed(false);

                setText(decimalFormat.format(Double.parseDouble(newValue)));
            }
        });
    }

    public NumberField() {
        this(2);
    }

    private boolean isValid(String text) {
        return text.matches("^\\d{0,8}(\\.(\\d{0," + precision + "})?)?$");
    }
}