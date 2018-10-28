package dto;

/**
 *
 * @author dangxuananh1997
 */
public class ProductDTO {
    
    private int id;
    private ProductType productType;
    private int hashCode;
    private String name;
    private String image;
    private int price;
    private String productLink;

    public ProductDTO(ProductType productType, String name, String image, int price, String productLink) {
        this.productType = productType;
        this.name = name;
        this.image = image;
        this.price = price;
        this.productLink = productLink;
        this.hashCode = (name + image + price + productLink).hashCode();
    }

    public int getId() {
        return id;
    }

    public ProductType getProductType() {
        return productType;
    }

    public int getHashCode() {
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

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ProductDTO{" + "id=" + id + ", productType=" + productType + ", hashCode=" + hashCode + ", name=" + name + ", image=" + image + ", price=" + price + ", productLink=" + productLink + '}';
    }

}