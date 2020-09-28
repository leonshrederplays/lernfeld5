package utils;


import java.sql.*;
import java.util.UUID;

public class DBUtils {
    Connection conn = null;

    public Connection connector() {
        try {
            String url = "jdbc:mysql://51.38.194.126:3306/test";
            String user = "shreder";
            String pass = "like1234";
            conn = DriverManager.getConnection(url, user, pass);
            conn.setAutoCommit(false);
            createSQL();
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return conn;
    }

    public void createSQL(){
        try (Connection con = connector()) {
            // Hier SQL Befehl in den String schreiben.
            String sql = "SELECT FRIENDID FROM FRIENDS WHERE NAME = ?";
            try (PreparedStatement ps = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                ps.setObject(1, "Hendrik");
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("No requests");
                    } else {
                        do {
                            // rs.getObject or etc. And Column Number required.
                            rs.getString(1);
                        } while (rs.next());
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void recreateSQL(){

    }
}
