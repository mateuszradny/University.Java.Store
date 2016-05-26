package Base.Repository;

import Base.Model.Product;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Mateusz on 18/01/2016.
 */
public interface IImageRepository {
    Image getForProduct(UUID productId);

    void setForProduct(Product product, String filePath) throws IOException;
}