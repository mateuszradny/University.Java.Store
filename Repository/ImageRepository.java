package Repository;

import Base.Model.Pants;
import Base.Model.Product;
import Base.Repository.IImageRepository;
import javafx.scene.image.Image;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * Created by Mateusz on 18/01/2016.
 * Repository for manipulation of images associated with products.
 * You can get or set Image object for a product.
 */
public class ImageRepository implements IImageRepository {
    private String folderPath;
    private String[] supportedExtensions;

    public ImageRepository(String folderPath, String[] supportedExtensions) {
        if (folderPath == null)
            throw new IllegalArgumentException("Argument folderPath can not be null.");

        if (supportedExtensions == null || supportedExtensions.length == 0)
            throw new IllegalArgumentException("Argument supportedExtensions can not be empty.");

        this.folderPath = folderPath;
        this.supportedExtensions = supportedExtensions;
    }

    /**
     * @param productId ID of product
     * @return Returns Image object associated with a given product or null if Image not exists.
     */
    @Override
    public Image getForProduct(UUID productId) {
        String path = folderPath + "/" + productId.toString();
        for (String extension : supportedExtensions) {
            File file = new File(path + "." + extension);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                return image;
            }
        }

        return null;
    }

    /**
     * Associate Image object with a given Product.
     *
     * @param product  Product object.
     * @param filePath A file path to image which will be associated with product.
     * @throws IOException
     */
    @Override
    public void setForProduct(Product product, String filePath) throws IOException {
        String extension = filePath.substring(filePath.indexOf('.') + 1);
        String path = folderPath + "/" + product.getId() + "." + extension;

        File dir = new File(folderPath);
        if (!dir.exists()) {
            dir.mkdir();
        }

        Files.copy(Paths.get(filePath), Paths.get(path), StandardCopyOption.REPLACE_EXISTING);
    }
}