package dto;

/**
 *
 * @author dangxuananh1997
 */
public class LaptopDTO {
    
    private int id;
    private String cpu;
    private String gpu;
    private String ram;
    private String hardDrive;
    private String monitor;
    private String ports;
    private String lan;
    private String wireless;
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
