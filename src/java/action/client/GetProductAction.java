package action.client;

import com.opensymphony.xwork2.Action;
import dao.ProductDAO;
import dto.ProductDTO;
import dto.ProductType;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
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
public class GetProductAction {

    private final int pageSize = 16;
    
    // Inputs
    private String searchValue;
    private ProductType productType;
    private int pageNumber = 1;
    
    // Outputs
    InputStream result;
    
    public GetProductAction() {
    }
    
    public String execute() throws Exception {
        ProductDAO productDAO = new ProductDAO();
        List<ProductDTO> productList = productDAO.getAllProduct(productType);
        int lastPage = Math.round((float)productList.size() / this.pageSize);
        
        try {
            JAXBContext context = JAXBContext.newInstance(Data.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            StringWriter sw = new StringWriter();
            Products products = new Products();
            products.setProduct(productList);
            Data data = new Data(lastPage, pageNumber, products);
            marshaller.marshal(data, sw);
            this.result = new ByteArrayInputStream(sw.toString().getBytes("UTF-8"));
        } catch (Exception e) {
            System.out.println(e);
            return Action.ERROR;
        }
        return Action.SUCCESS;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public InputStream getResult() {
        return result;
    }

    public void setResult(InputStream result) {
        this.result = result;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = ProductType.valueOf(productType);
    }
    
}
