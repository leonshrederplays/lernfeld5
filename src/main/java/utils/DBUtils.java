package utils;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class DBUtils {


    public Connection connector() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/";
            String user = "root";
            String pass = "";
            try {
                conn = DriverManager.getConnection(url, user, pass);
            } catch (SQLException e) {
                try {
                    pass = "like1234";
                    conn = DriverManager.getConnection(url, user, pass);
                } catch (SQLException ec) {
                    ec.printStackTrace();
                }
            }
            conn = DriverManager.getConnection(url, user, pass);
            conn.setAutoCommit(false);
            System.out.println(conn);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return conn;
    }

    public void createSQL() {
        try (Connection conn = connector()) {
            ScriptRunner sr = new ScriptRunner(conn, false, false);
            // SQL-Skript
            String dbFile = String.valueOf(getClass().getClassLoader().getResource("dbSQL.sql")).replace("file:", "").replace("%20"," ");
            String dataFile = String.valueOf(getClass().getClassLoader().getResource("dataSQL.sql")).replace("file:", "").replace("%20"," ");
            // Das SQL-Skript ausführen.
            // Datenbank und Tabellen erstellen.
            sr.runScript(new BufferedReader(new FileReader(dbFile)));
            // Testdaten erstellen.
            sr.runScript(new BufferedReader(new FileReader(dataFile)));
        } catch(SQLException | IOException e) {
            e.printStackTrace();
        }
    }



    public void recreateSQL(){

    }
}
