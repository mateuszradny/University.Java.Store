package UI.TableCells;

import javafx.scene.control.TableCell;

import java.text.DecimalFormat;

/**
 * Created by Mateusz on 18/01/2016.
 * Represents a cell which displays a given number. You can set the number of decimal places (default value is two places).
 */
public class NumberTableCell<T, P extends Number> extends TableCell<T, P> {
    private final int precision;

    public NumberTableCell(int precision) {
        this.precision = precision;
    }

    public NumberTableCell() {
        this(2);
    }

    @Override
    protected void updateItem(P item, boolean empty) {
        if (empty) {
            setText("");
        } else {
            DecimalFormat decimalFormat = new DecimalFormat();
            decimalFormat.setMaximumFractionDigits(precision);
            decimalFormat.setMinimumFractionDigits(precision);
            decimalFormat.setGroupingUsed(false);

            setText(decimalFormat.format(item));
        }
    }
}