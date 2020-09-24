package utils;

import java.sql.*;

public class DBUtil {
    public static Connection conn = null;
    public static Statement stmt = null;
    public static ResultSet rs = null;

    public static void connect() {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/test?user=root&password=like1234");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    public static void testConnection() {
        try {
            stmt = conn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
