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
    
    public CrawlProductAction() {
    }
    
    public String execute() throws Exception {
        ServletContext context = ServletActionContext.getServletContext();
        List<CrawlerInterface> crawlerList = (List<CrawlerInterface>) context.getAttribute("CRAWLERLIST");
        switch (action) {
            case "Crawl":
                for (CrawlerInterface crawler : crawlerList) {
                    if (!crawler.isCrawling()) {
                        crawler.setIsCrawling(true);
                        Thread thread = new Thread((Runnable) crawler);
                        thread.start();
                    }
                }
                break;
            case "Pause":
                for (CrawlerInterface crawler : crawlerList) {
                    if (crawler.isCrawling()) {
                        crawler.pause();
                    }
                }
                break;
            default:
                break;
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
