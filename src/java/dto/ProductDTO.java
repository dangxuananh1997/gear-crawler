package dto;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import utility.CommonUtilities;

/**
 *
 * @author dangxuananh1997
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "product", propOrder = { "id", "productType", "hashCode", "name", "image", "price", "productLink" })
public class ProductDTO {
    
    @XmlElement(required = true)
    private int id;
    @XmlElement(required = true)
    private ProductType productType;
    @XmlElement(required = true)
    private String hashCode;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String image;
    @XmlElement(required = true)
    private int price;
    @XmlElement(required = true)
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