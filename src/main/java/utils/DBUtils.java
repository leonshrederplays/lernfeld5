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


    private ConfigInstance inst = new ConfigInstance();

    public Connection firstBootConnector() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/";
            String user = "root";
            String pass = "";
            try {
                // Connect to MySQL
                conn = DriverManager.getConnection(url, user, pass);
            } catch (SQLException e) {
                try {
                    pass = "like1234";
                    // Connect to MySQL with password
                    conn = DriverManager.getConnection(url, user, pass);
                } catch (SQLException ec) {
                    ec.printStackTrace();
                }
            }
            // Dont push changes automatically to MySQL
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
                // Connect to MySQL
                conn = DriverManager.getConnection(url, user, pass);
            } catch (SQLException e) {
                try {
                    pass = "like1234";
                    // COnnect to MySQL with password
                    conn = DriverManager.getConnection(url, user, pass);
                } catch (SQLException ec) {
                    ec.printStackTrace();
                }
            }
            // Dont push changes automatically to MySQL
            conn.setAutoCommit(false);
            System.out.println(conn);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return conn;
    }

    public void selectData() {
        selectIngredients();
    }

    public void createSQL() {
        // Get Connection to MySQL
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

    public /*List<IngredientList>*/ void selectIngredients() {
        // Define a List of Ingredients
        //List<IngredientList> list = new ArrayList<>();
        // Get Connection
        try (Connection conn = connector()) {
            // Pass your SQL in this String.
            String sql = "SELECT * FROM ZUTAT";
            // Make a preparedStatement and set Scroll to insensitive (both directions)
            try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                // Execute query and safe Result in rs
                try (ResultSet rs = ps.executeQuery()) {
                    // If no result do nothing
                    if (!rs.next()) {
                        System.out.println("No results");
                    } else {
                        // For Each result add it to IngredientList.
                        do {
                            // rs.getObject or etc. And Column Number required.
                            //list.add(new IngredientList(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9)));
                            inst.ingredientList.add(new IngredientList(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9)));
                        } while (rs.next());
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        // Return the list which can be obtained by List<IngredientList> list = dbUtil.selectIngredients(); !!Dependency it needs to have DBUtils dbUtil = new DBUtils(); to be defined!!
        //return list;
    }
}
