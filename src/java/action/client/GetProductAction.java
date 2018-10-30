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
import jaxb.Products;

/**
 *
 * @author dangxuananh1997
 */
public class GetProductAction {

    // Inputs
    private String searchValue;
    private ProductType productType;
    private int page;
    
    // Outputs
    InputStream result;
    
    public GetProductAction() {
    }
    
    public String execute() throws Exception {
        ProductDAO productDAO = new ProductDAO();
        List<ProductDTO> productList = productDAO.getAllProduct(productType);
        try {
            JAXBContext context = JAXBContext.newInstance(Products.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            StringWriter sw = new StringWriter();
            Products products = new Products();
            products.setProduct(productList);
            marshaller.marshal(products, sw);
            this.result = new ByteArrayInputStream(sw.toString().getBytes("UTF-8"));
        } catch (Exception e) {
            System.out.println(e);
            this.result = new ByteArrayInputStream(("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Context path=\"/GearCrawler\">\n" +
                "  <Resource name=\"GearCrawlerDS\" type=\"javax.sql.DataSource\" auth=\"Container\"\n" +
                "    driverClassName=\"com.microsoft.sqlserver.jdbc.SQLServerDriver\"\n" +
                "    url=\"jdbc:sqlserver://localhost:1433;databaseName=GearCrawler\"\n" +
                "    username=\"sa\" password=\"123\"/>\n" +
                "</Context>").toString().getBytes("UTF-8"));
            return Action.SUCCESS;
        }
        return Action.SUCCESS;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
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
