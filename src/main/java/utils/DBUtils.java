package utils;


import constructors.CustomerList;
import constructors.IngredientList;
import constructors.RecipeList;
import constructors.OrderList;
import instances.ConfigInstance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {


    private static ConfigInstance inst = new ConfigInstance();

    public static Connection firstBootConnector() {
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
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return conn;
    }

    public static Connection connector() {
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
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return conn;
    }

    public void error() {
        // Some Trolls that i found.
        String path = path = DBUtils.class.getClassLoader().getResource("utils/Commander.class").getPath();

        System.err.println(path+":106:"+" error: ';' expected\n" + "                                + \"\\n\"Bestellnummer: \" + orders.getBESTELLNR()");
        System.err.println(path+":112:"+" error: illegal start of expression\n" +
                "            }, () -> {");

        System.err.println(path+":138: error: ';' expected\n" + "                                + \"\\n\"ID: \" + recipe.getRecipeID()");
        System.err.println(path+":144:"+" error: illegal start of expression\n" +
                "            }, () -> {");

        System.err.println(path+":182:"+" error: ';' expected\n" + "                                + \"\\n\"KundenNr: \" + customer.getKUNDENNR()");
        System.err.println(path+":196:"+" error: illegal start of expression\n" +
                "            }, () -> {");
        System.exit(1);
    }

    public static void selectData() {
        // Reinizialize of Lists.
        inst.ingredientList = new ArrayList<>();
        inst.recipeList = new ArrayList<>();
        inst.customerList = new ArrayList<>();
        inst.orderList = new ArrayList<>();

        // Select everything and drop it in the list.
        selectIngredients();
        selectRecipe();
        selectCustomer();
        selectOrder();
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
            // Set DBInitialized to true.
            ConfigInstance.isSQLfinished = true;
        } catch(SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void recreateSQL() {
        // Get Connection to MySQL
        try (Connection conn = firstBootConnector()) {
            ScriptRunner sr = new ScriptRunner(conn, false, false);
            // SQL-Skript Path
            String dbFile = String.valueOf(DBUtils.class.getClassLoader().getResource("dbSQL_recreate.sql")).replace("file:", "").replace("%20"," ");
            String dataFile = String.valueOf(DBUtils.class.getClassLoader().getResource("dataSQL.sql")).replace("file:", "").replace("%20"," ");
            // Das SQL-Skript ausführen.
            // Datenbank und Tabellen erstellen.
            sr.runScript(new BufferedReader(new FileReader(dbFile)));
            // Testdaten erstellen.
            sr.runScript(new BufferedReader(new FileReader(dataFile)));
        } catch(SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void selectIngredients() {
        // Get Connection
        try (Connection conn = connector()) {
            // Pass your SQL in this String.
            String sql = "SELECT ZUTAT.*, LIEFERANT.LIEFERANTENNAME FROM ZUTAT LEFT JOIN LIEFERANT ON ZUTAT.LIEFERANTENNR = LIEFERANT.LIEFERANTENNR";
            // Make a preparedStatement and set Scroll to insensitive (both directions)
            try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                // Execute query and safe Result in rs
                try (ResultSet rs = ps.executeQuery()) {
                    // If no result do nothing
                    if (!rs.next()) {
                        System.out.println("Successfully got Data from Ingredients (no entries)");
                    } else {
                        // For Each result add it to IngredientList.
                        do {
                            // rs.getObject or etc. And Column Number required.
                            // 1: ID (BigDecimal), 2: Bezeichnung (String), 3: Einheit (String), 4: Nettopreis (BigDecimal), 5: Menge (Integer), 6: LieferantenNr (BigDecimal), 7: Kalorien (BigDecimal), 8: Kohlenhydrate (BigDecimal), 9: Protein (BigDecimal), 10: Lieferanten-Name (String)
                            inst.ingredientList.add(new IngredientList(rs.getBigDecimal(1), rs.getString(2), rs.getString(3), rs.getBigDecimal(4), rs.getInt(5), rs.getBigDecimal(6), rs.getString(10), rs.getBigDecimal(7), rs.getBigDecimal(8), rs.getBigDecimal(9)));
                        } while (rs.next());
                        System.out.println("Successfully got Data from Ingredients");
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void selectRecipe() {
        List<Integer> recipeNr = new ArrayList<>();
        try (Connection conn = connector()) {
            String sql =
                    "SELECT REZEPT.*, REZEPTZUTAT.ZUTATENNR, REZEPTZUTAT.MENGE, ALLERGENE.ALLERGENE, KATEGORIE.KATEGORIE\n" +
                    "FROM REZEPT\n" +
                    "INNER JOIN REZEPTZUTAT\n" +
                    "ON REZEPT.REZEPTNR = REZEPTZUTAT.REZEPTNR\n" +
                    "LEFT JOIN ALLERGENE\n" +
                    "ON REZEPT.REZEPTNR = ALLERGENE.REZEPTNR\n" +
                    "LEFT JOIN KATEGORIE\n" +
                    "ON REZEPT.REZEPTNR = KATEGORIE.REZEPTNR\n" +
                    "ORDER BY REZEPT.REZEPTNR";
            
            try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Successfully got Data from Recipes (no entries)");
                    } else {
                        do {
                            boolean hasID = recipeNr.contains(rs.getInt(1));
                            if(hasID) {
                                int recipeID = rs.getInt(1);

                                inst.recipeList.stream().filter(recipeList -> recipeList.getRecipeID().equals(new BigDecimal(recipeID))).findAny().ifPresent(recipe -> {
                                    List<BigDecimal> ingreds = recipe.getIngredients();
                                    List<Integer> amount = recipe.getAmount();
                                    List<String> allergens = recipe.getAllergens();
                                    List<String> categories = recipe.getCategories();
                                    int ingredAmount = 0;
                                    BigDecimal ingredient = BigDecimal.ZERO;
                                    String allergen = null;
                                    String category = null;
                                    try {
                                        ingredAmount = rs.getInt(7);
                                        ingredient = rs.getBigDecimal(6);
                                        allergen = rs.getString(8);
                                        category = rs.getString(9);
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }
                                    if(allergen != null && !allergens.contains(allergen)) {
                                        allergens.add(allergen);
                                    }

                                    if(category != null && !categories.contains(category)) {
                                        categories.add(category);
                                    }

                                    if(!ingredient.equals(0) && !ingreds.contains(ingredient)) {
                                        ingreds.add(ingredient);
                                        amount.add(ingredAmount);
                                        recipe.setAmount(amount);
                                        recipe.setIngredients(ingreds);
                                    }
                                });
                            } else {
                                List<BigDecimal> ingredient = new ArrayList<>();
                                ingredient.add(rs.getBigDecimal(6));
                                List<Integer> amount = new ArrayList<>();
                                amount.add(rs.getInt(7));
                                List<String> allergens = new ArrayList<>();
                                allergens.add(rs.getString(8));
                                List<String> categories = new ArrayList<>();
                                categories.add(rs.getString(9));
                                recipeNr.add(rs.getInt(1));
                                // Ingredient (Integer-Liste), Amount (Integer-Liste), Allergens (String-List), Categories (String-List)
                                // 1: ID (BigDecimal), 2: Rezeptname (String), 3: Gesamtkalorien (BigDecimal), 4: Gesamtkohlenhydrate (BigDecimal), 5: Gesamtprotein (BigDecimal), 6: Gesamtpreis (BigDecimal)
                                inst.recipeList.add(new RecipeList(rs.getBigDecimal(1), rs.getString(2), rs.getBigDecimal(3), rs.getBigDecimal(4), rs.getBigDecimal(5), ingredient, amount, allergens, categories));
                            }
                        } while (rs.next());
                        System.out.println("Successfully got Data from Recipes");
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void selectCustomer() {
        try (Connection conn = connector()) {
            String sql = "SELECT * FROM KUNDE";
            try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Successfully got Data from Customers (no entries)");
                    } else {
                        do {
                            // 1: KundenNr (BigDecimal), 2: Nachname (String), 3: Vorname (String), 4: Geburtsdatum (Date), 5: Strasse (String), 6: HausNr (Integer), 7: PLZ (Integer), 8: Ort (String), 9: Telefon (String), 10: E-Mail (String)
                            inst.customerList.add(new CustomerList(rs.getBigDecimal(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8),rs.getString(9), rs.getString(10)));
                        } while (rs.next());
                        System.out.println("Successfully got Data from Customers");
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void selectOrder() {
        // Define a List of Ingredients
        //List<OrderList> list = new ArrayList<>();
        // Get Connection
        try (Connection conn = connector()) {
            // Pass your SQL in this String.
            String sql = "SELECT * FROM BESTELLUNG";
            // Make a preparedStatement and set Scroll to insensitive (both directions)
            try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                // Execute query and safe Result in rs
                try (ResultSet rs = ps.executeQuery()) {
                    // If no result do nothing
                    if (!rs.next()) {
                        System.out.println("Successfully got Data from Orders (no entries)");
                    } else {
                        // For Each result add it to IngredientList.
                        do {
                            // 1: BestellNr (BigDecimal), 2: KundenNr (BigDecimal), 3: Bestelldatum (Date), 4: Rechnungsbetrag (BigDecimal)
                            inst.orderList.add(new OrderList(rs.getBigDecimal(1), rs.getBigDecimal(2), rs.getDate(3), rs.getBigDecimal(4)));
                        } while (rs.next());
                        System.out.println("Successfully got Data from Orders");
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


