package dto;

/**
 *
 * @author dangxuananh1997
 */
public class MouseDTO extends ProductDTO {
    
    private String weight;
    private String maxDPI;
    private String led;
    private String numberOfButton;

    public MouseDTO(String weight, String maxDPI, String led, String numberOfButton, ProductDTO product) {
        super(product.getProductType(), product.getName(), product.getImage(), product.getPrice(), product.getProductLink());
        this.weight = weight;
        this.maxDPI = maxDPI;
        this.led = led;
        this.numberOfButton = numberOfButton;
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

    @Override
    public String toString() {
        return "{" + "weight=" + weight + ", maxDPI=" + maxDPI + ", led=" + led + ", numberOfButton=" + numberOfButton + '}';
    }
    
}
