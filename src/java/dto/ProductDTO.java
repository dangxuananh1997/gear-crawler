package dto;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utility.CommonUtilities;

/**
 *
 * @author dangxuananh1997
 */
public class ProductDTO {
    
    private int id;
    private ProductType productType;
    private String hashCode;
    private String name;
    private String image;
    private int price;
    private String productLink;

    public ProductDTO(ProductType productType, String name, String image, int price, String productLink) {
        try {
            this.productType = productType;
            this.name = name;
            this.image = image;
            this.price = price;
            this.productLink = productLink;
            this.hashCode = CommonUtilities.getMD5Hash(name + image + price + productLink);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ProductDTO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ProductDTO(int id, ProductType productType, String hashCode, String name, String image, int price, String productLink) {
        this.id = id;
        this.productType = productType;
        this.hashCode = hashCode;
        this.name = name;
        this.image = image;
        this.price = price;
        this.productLink = productLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductType getProductType() {
        return productType;
    }

    public String getHashCode() {
        return hashCode;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }

    public String getProductLink() {
        return productLink;
    }

}