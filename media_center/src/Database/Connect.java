package Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Connect
{
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345678";
    private static final String URL = "localhost";
    private static final String SCHEMA = "MC";

    public static Connection connect() throws SQLException, ClassNotFoundException
    {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://"+URL+"/"+SCHEMA+"?autoReconnect=true&useSSL=false",USERNAME,PASSWORD);
    }

    public static void close(Connection c)
    {
        try { if(c!=null && !c.isClosed()) { c.close(); } } catch (Exception e) { e.printStackTrace(); }
    }
}