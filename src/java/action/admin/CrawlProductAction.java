package action.admin;

import com.opensymphony.xwork2.Action;
import crawler.CrawlerInterface;
import java.util.List;
import javax.servlet.ServletContext;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author dangxuananh1997
 */
public class CrawlProductAction {

    // Inputs
    private String action;
    
    // Local Variables
    private List<CrawlerInterface> crawlerList;
    
    public CrawlProductAction() {
    }
    
    public String execute() throws Exception {
        ServletContext context = ServletActionContext.getServletContext();
        crawlerList = (List<CrawlerInterface>) context.getAttribute("CRAWLERLIST");
        for (CrawlerInterface crawler : crawlerList) {
            crawler.crawl();
        }
        return Action.SUCCESS;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
}
