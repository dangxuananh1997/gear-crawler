package crawler;

import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author dangxuananh1997
 */
public interface CrawlerInterface {
    
    void crawl() throws SQLException, NamingException;
    
    void pause();
    
    boolean isCrawling();
    
    void setIsCrawling(boolean isCrawling);
    
}
