package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MyConnection {
    private static Connection con = null;
    
    public static Connection getConnection() {
     String url = "jdbc:mysql://localhost:3306/rkz_restaurant";
     String user = "root";
     String password = "";
     try {
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection(url, user, password);
        if(con != null && checkConnection()) {
            System.out.println("Database connection successful.");
        }
        else {
           System.out.println("Database connection unsuccessful.");
           con = null;
        }
     } catch(ClassNotFoundException | SQLException e) {
     }
     return con;
    }
    
    private static boolean checkConnection() {
        try(Statement stmt = con.createStatement()) {
            stmt.execute("SELECT 1");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
