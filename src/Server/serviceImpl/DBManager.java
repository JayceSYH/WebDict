package Server.serviceImpl;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Sun YuHao on 2016/12/3.
 */
public class DBManager {
    static private String url = "jdbc:mysql://115.159.223.65/WebDict";
    static private String user = "user";
    static private String password = "00000000";
    static private Connection conn;


    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    static Connection getConn() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(url, user, password);
            }

            return conn;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
