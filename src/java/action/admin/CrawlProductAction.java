package action.admin;

import com.opensymphony.xwork2.Action;
import crawler.HangchinhhieuCrawler;
import javax.servlet.ServletContext;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author dangxuananh1997
 */
public class CrawlProductAction {

    private String action;
    
    public CrawlProductAction() {
    }
    
    public String execute() throws Exception {
        ServletContext context = ServletActionContext.getServletContext();
        HangchinhhieuCrawler hccCrawler = (HangchinhhieuCrawler) context.getAttribute("HCCCRAWLER");
        hccCrawler.crawl();
        return Action.SUCCESS;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
    
}
