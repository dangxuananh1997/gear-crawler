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
@XmlType(name = "", propOrder = { "id", "numberOfKey", "pressForce", "distance", "led", "weight", "size", "switches", "product" })
@XmlRootElement(name = "keyboard")
public class KeyboardDTO {
    
    @XmlElement(required = true)
    private int id;
    
    @XmlElement(required = true)
    private String numberOfKey;
    
    @XmlElement(required = true)
    private String pressForce;
    
    @XmlElement(required = true)
    private String distance;
    
    @XmlElement(required = true)
    private String led;
    
    @XmlElement(required = true)
    private String weight;
    
    @XmlElement(required = true)
    private String size;
    
    @XmlElement(required = true)
    private String switches;
    
    @XmlElement(required = true)
    private ProductDTO product;

    public KeyboardDTO(String numberOfKey, String pressForce, String distance, String led, String weight, String size, String switches, ProductDTO product) {
        this.numberOfKey = numberOfKey;
        this.pressForce = pressForce;
        this.distance = distance;
        this.led = led;
        this.weight = weight;
        this.size = size;
        this.switches = switches;
        this.product = product;
    }

    public KeyboardDTO(int id, String numberOfKey, String pressForce, String distance, String led, String weight, String size, String switches, ProductDTO product) {
        this.id = id;
        this.numberOfKey = numberOfKey;
        this.pressForce = pressForce;
        this.distance = distance;
        this.led = led;
        this.weight = weight;
        this.size = size;
        this.switches = switches;
        this.product = product;
    }

    public KeyboardDTO() {
    }

    public int getId() {
        return id;
    }

    public String getNumberOfKey() {
        return numberOfKey;
    }

    public String getPressForce() {
        return pressForce;
    }

    public String getDistance() {
        return distance;
    }

    public String getLed() {
        return led;
    }

    public String getWeight() {
        return weight;
    }

    public String getSize() {
        return size;
    }

    public String getSwitches() {
        return switches;
    }

    public ProductDTO getProduct() {
        return product;
    }

}
