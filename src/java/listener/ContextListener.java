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
        XgearCrawler xgearCrawler = new XgearCrawler();
        HangchinhhieuCrawler hccCrawler = new HangchinhhieuCrawler();
        crawlerList.add(xgearCrawler);
        crawlerList.add(hccCrawler);
        System.out.println("CRAWLERS CREATED!");
        sce.getServletContext().setAttribute("CRAWLERLIST", crawlerList);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
}
