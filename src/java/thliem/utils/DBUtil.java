package thliem.utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * @author LiemNguyen
 */

public class DBUtil {
        public static Connection getConnection() throws ClassNotFoundException, SQLException, NamingException {
        
        Connection conn = null;
        Context context = new InitialContext();
        Context end = (Context) context.lookup("java:comp/env");
        DataSource ds = (DataSource) end.lookup("testCon");
        conn = ds.getConnection();

        return conn;
    }
}
