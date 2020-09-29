package utils;


import constructors.IngredientList;
import instances.ConfigInstance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {


    public Connection firstBootConnector() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/";
            String user = "root";
            String pass = "";
            try {
                conn = DriverManager.getConnection(url, user, pass);
                conn.createStatement().executeQuery("USE krautundrueben");
            } catch (SQLException e) {
                try {
                    pass = "like1234";
                    conn = DriverManager.getConnection(url, user, pass);
                    conn.createStatement().executeQuery("USE krautundrueben");
                } catch (SQLException ec) {
                    ec.printStackTrace();
                }
            }
            conn.setAutoCommit(false);
            System.out.println(conn);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return conn;
    }

    public Connection connector() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/krautundrueben";
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
            conn.setAutoCommit(false);
            System.out.println(conn);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return conn;
    }

    public void createSQL() {
        try (Connection conn = firstBootConnector()) {
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

    public List<IngredientList> selectIngredients() {
        List<IngredientList> list = new ArrayList<>();
        try (Connection conn = connector()) {
            String sql = "SELECT * FROM ZUTAT";
            try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("No requests");
                    } else {
                        do {
                            // rs.getObject or etc. And Column Number required.
                            list.add(new IngredientList(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9)));
                        } while (rs.next());
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return list;
    }
}
