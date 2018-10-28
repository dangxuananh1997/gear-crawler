package dto;

/**
 *
 * @author dangxuananh1997
 */
public class MouseDTO {
    
    private int id;
    private String weight;
    private String maxDPI;
    private String led;
    private String numberOfButton;
    private ProductDTO product;

    public MouseDTO(String weight, String maxDPI, String led, String numberOfButton, ProductDTO product) {
        this.weight = weight;
        this.maxDPI = maxDPI;
        this.led = led;
        this.numberOfButton = numberOfButton;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public String getWeight() {
        return weight;
    }

    public String getMaxDPI() {
        return maxDPI;
    }

    public String getLed() {
        return led;
    }

    public String getNumberOfButton() {
        return numberOfButton;
    }

    public ProductDTO getProduct() {
        return product;
    }
    
}
