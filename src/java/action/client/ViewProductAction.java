package action.client;

import com.opensymphony.xwork2.Action;
import dto.ProductType;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 *
 * @author dangxuananh1997
 */
public class ViewProductAction {

    // Inputs
    private String searchValue;
    private ProductType productType;
    private int page;
    
    // Outputs
    InputStream result;
    
    public ViewProductAction() {
    }
    
    public String execute() throws Exception {
        this.result = new ByteArrayInputStream("<?xml version=\"1.0\" encoding=\"UTF-8\" ?><foo><bar>Đặng</bar></foo>".getBytes("UTF-8"));
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
