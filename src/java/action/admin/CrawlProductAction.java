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
        System.out.println("wtf");
        HangchinhhieuCrawler hccCrawler = new HangchinhhieuCrawler();
        hccCrawler.crawlLaptop();
        return "success";
    }
    
}
