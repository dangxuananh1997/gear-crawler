package utility;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author dangxuananh1997
 */
public class DBUtilities implements Serializable {
    public static Connection makeConnection() throws NamingException, SQLException {
        Context context = new InitialContext();
        Context tomcatContext = (Context) context.lookup("java:comp/env");
        DataSource ds = (DataSource) tomcatContext.lookup("GearCrawlerDS");
        Connection con = ds.getConnection();
        return con;
    }
}
