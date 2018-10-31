package action.client;

import com.opensymphony.xwork2.Action;
import dao.ProductDAO;
import dto.ProductDTO;
import dto.ProductType;
import java.io.StringWriter;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import jaxb.Data;
import jaxb.Products;

/**
 *
 * @author dangxuananh1997
 */
public class HomeAction {
    
    private final int pageSize = 16;
    
    // Inputs
    private ProductType productType;
    private int pageNumber = 1;
    
    // Outputs
    private String xmlOutput;
    private int lastPage;
    
    public HomeAction() {
    }
    
    public String execute() throws Exception {
        ProductDAO productDAO = new ProductDAO();
        List<ProductDTO> productList = productDAO.getAllProduct(productType);
        this.lastPage = Math.round((float)productList.size() / this.pageSize);
        productList = productList.subList(this.pageSize * (this.pageNumber - 1), this.pageSize * this.pageNumber);
        
        try {
            JAXBContext context = JAXBContext.newInstance(Data.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            StringWriter sw = new StringWriter();
            Products products = new Products();
            products.setProduct(productList);
            Data data = new Data(lastPage, pageNumber, products);
            marshaller.marshal(data, sw);
            this.xmlOutput = sw.toString();
        } catch (Exception e) {
            System.out.println(e);
            return Action.ERROR;
        }
        
        return Action.SUCCESS;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = ProductType.valueOf(productType);
    }

    public String getXmlOutput() {
        return xmlOutput;
    }

    public void setXmlOutput(String xmlOutput) {
        this.xmlOutput = xmlOutput;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }
    
}
