package listener;

import crawler.CrawlerInterface;
import crawler.HangchinhhieuCrawler;
import crawler.XgearCrawler;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author dangxuananh1997
 */
public class ContextListener implements ServletContextListener {

    private List<CrawlerInterface> crawlerList = new LinkedList<>();
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("CREATING CRAWLERS!");
        HangchinhhieuCrawler hccCrawler = new HangchinhhieuCrawler();
        XgearCrawler xgearCrawler = new XgearCrawler();
        crawlerList.add(hccCrawler);
        crawlerList.add(xgearCrawler);
        System.out.println("CRAWLERS CREATED!");
        sce.getServletContext().setAttribute("CRAWLERLIST", crawlerList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
}
