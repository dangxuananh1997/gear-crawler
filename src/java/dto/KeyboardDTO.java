package dto;

/**
 *
 * @author dangxuananh1997
 */
public class KeyboardDTO {
    
    private int id;
    private String numberOfKey;
    private String pressForce;
    private String distance;
    private String led;
    private String weight;
    private String size;
    private String switches;
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
