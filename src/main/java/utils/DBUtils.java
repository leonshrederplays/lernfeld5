package utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtils {
    Connection conn = null;

    public void connector() {

        try {
            conn = DriverManager.getConnection("jdbc:mysql://51.38.194.126:/test?" + "user=shreder&password=Like1234");
            createSQL();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public void createSQL(){

    }

    public void recreateSQL(){

    }
}
