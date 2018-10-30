package action.client;

/**
 *
 * @author dangxuananh1997
 */
public class ViewProductAction {

    // Inputs
    private String searchValue;
    private int page;
    
    public ViewProductAction() {
    }
    
    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
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
    
}
