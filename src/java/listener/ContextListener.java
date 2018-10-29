package listener;

import crawler.HangchinhhieuCrawler;
import crawler.XgearCrawler;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author dangxuananh1997
 */
public class ContextListener implements ServletContextListener {

    private HangchinhhieuCrawler hccCrawler;
    private XgearCrawler xgearCrawler;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("CREATING CRAWLERS!");
        hccCrawler = new HangchinhhieuCrawler();
        xgearCrawler = new XgearCrawler();
        System.out.println("CRAWLERS CREATED!");
        sce.getServletContext().setAttribute("HCCCRAWLER", hccCrawler);
        sce.getServletContext().setAttribute("XGEARCRAWLER", xgearCrawler);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
}
