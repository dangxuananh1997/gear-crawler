package dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author dangxuananh1997
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "id", "weight", "maxDPI", "led", "numberOfButton", "product" })
@XmlRootElement(name = "mouse")
public class MouseDTO {
    
    @XmlElement(required = true)
    private int id;
    
    @XmlElement(required = true)
    private String weight;
    
    @XmlElement(required = true)
    private String maxDPI;
    
    @XmlElement(required = true)
    private String led;
    
    @XmlElement(required = true)
    private String numberOfButton;
    
    @XmlElement(required = true)
    private ProductDTO product;

    public MouseDTO(String weight, String maxDPI, String led, String numberOfButton, ProductDTO product) {
        this.weight = weight;
        this.maxDPI = maxDPI;
        this.led = led;
        this.numberOfButton = numberOfButton;
        this.product = product;
    }

    public MouseDTO(int id, String weight, String maxDPI, String led, String numberOfButton, ProductDTO product) {
        this.id = id;
        this.weight = weight;
        this.maxDPI = maxDPI;
        this.led = led;
        this.numberOfButton = numberOfButton;
        this.product = product;
    }

    public MouseDTO() {
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
