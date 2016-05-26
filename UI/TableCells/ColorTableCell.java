package UI.TableCells;

import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;

import java.awt.*;

/**
 * Created by Mateusz on 17/01/2016.
 * Represents a cell which displays specified Color.
 */
public class ColorTableCell<T> extends TableCell<T, Color> {
    @Override
    protected void updateItem(Color item, boolean empty) {
        if (empty) {
            setGraphic(null);
        } else {
            Rectangle rectangle = new Rectangle(15, 15);
            rectangle.setFill(javafx.scene.paint.Color.rgb(item.getRed(), item.getGreen(), item.getGreen()));
            setGraphic(rectangle);
        }
    }
}