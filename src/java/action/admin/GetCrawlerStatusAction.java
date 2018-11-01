package action.admin;

import com.opensymphony.xwork2.Action;
import crawler.CrawlerInterface;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import javax.servlet.ServletContext;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author dangxuananh1997
 */
public class GetCrawlerStatusAction {
    
    // Output
    InputStream result;
    
    public GetCrawlerStatusAction() {
    }
    
    public String execute() throws Exception {
        ServletContext context = ServletActionContext.getServletContext();
        List<CrawlerInterface> crawlerList = (List<CrawlerInterface>) context.getAttribute("CRAWLERLIST");
        String isCrawling = "false";
        for (CrawlerInterface crawler : crawlerList) {
            if (crawler.isCrawling()) {
                isCrawling = "true";
            }
        }
        this.result = new ByteArrayInputStream(("<isCrawling>" + isCrawling + "</isCrawling>").getBytes("UTF-8"));
        return Action.SUCCESS;
    }

    public InputStream getResult() {
        return result;
    }

    public void setResult(InputStream result) {
        this.result = result;
    }
    
}
