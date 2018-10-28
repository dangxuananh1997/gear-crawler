package action.admin;

import crawler.HangchinhhieuCrawler;

/**
 *
 * @author dangxuananh1997
 */
public class CrawlProductAction {
    
    public CrawlProductAction() {
    }
    
    public String execute() throws Exception {
        HangchinhhieuCrawler hccCrawler = new HangchinhhieuCrawler();
        hccCrawler.crawlLaptop();
        return "success";
    }
    
}
