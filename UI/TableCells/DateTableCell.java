package UI.TableCells;

import javafx.scene.control.TableCell;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Mateusz on 18/01/2016.
 * Represents a cell which display Date in a given format. The default format is dd-MM-yyyy.
 */
public class DateTableCell<T> extends TableCell<T, Date> {
    private final String format;

    public DateTableCell(String format) {
        this.format = format;
    }

    public DateTableCell() {
        this("dd-MM-yyyy");
    }

    @Override
    protected void updateItem(Date item, boolean empty) {
        if (empty) {
            setText("");
        } else {
            setText(new SimpleDateFormat(format).format(item));
        }
    }
}