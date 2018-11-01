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
@XmlType(name = "", propOrder = { "id", "cpu", "gpu", "ram", "hardDrive", "monitor", "ports", "lan", "wireless", "product" })
@XmlRootElement(name = "laptop")
public class LaptopDTO {
    
    @XmlElement(required = true)
    private int id;
    
    @XmlElement(required = true)
    private String cpu;
    
    @XmlElement(required = true)
    private String gpu;
    
    @XmlElement(required = true)
    private String ram;
    
    @XmlElement(required = true)
    private String hardDrive;
    
    @XmlElement(required = true)
    private String monitor;
    
    @XmlElement(required = true)
    private String ports;
    
    @XmlElement(required = true)
    private String lan;
    
    @XmlElement(required = true)
    private String wireless;
    
    @XmlElement(required = true)
    private ProductDTO product;

    public LaptopDTO(String cpu, String gpu, String ram, String hardDrive, String monitor, String ports, String lan, String wireless, ProductDTO product) {
        this.cpu = cpu;
        this.gpu = gpu;
        this.ram = ram;
        this.hardDrive = hardDrive;
        this.monitor = monitor;
        this.ports = ports;
        this.lan = lan;
        this.wireless = wireless;
        this.product = product;
    }

    public LaptopDTO(int id, String cpu, String gpu, String ram, String hardDrive, String monitor, String ports, String lan, String wireless, ProductDTO product) {
        this.id = id;
        this.cpu = cpu;
        this.gpu = gpu;
        this.ram = ram;
        this.hardDrive = hardDrive;
        this.monitor = monitor;
        this.ports = ports;
        this.lan = lan;
        this.wireless = wireless;
        this.product = product;
    }

    public LaptopDTO() {
    }

    public int getId() {
        return id;
    }

    public String getCpu() {
        return cpu;
    }

    public String getGpu() {
        return gpu;
    }

    public String getRam() {
        return ram;
    }

    public String getHardDrive() {
        return hardDrive;
    }

    public String getMonitor() {
        return monitor;
    }

    public String getPorts() {
        return ports;
    }

    public String getLan() {
        return lan;
    }

    public String getWireless() {
        return wireless;
    }

    public ProductDTO getProduct() {
        return product;
    }
    
}
