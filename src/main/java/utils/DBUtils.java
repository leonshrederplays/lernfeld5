package utils;


import constructors.*;
import instances.ConfigInstance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class DBUtils {
    
    public static Connection connector(String db) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/"+db;
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
            assert conn != null;
            conn.setAutoCommit(false);
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return conn;
    }

    public void error() {
        // Some Trolls that i found.
        String path = Objects.requireNonNull(DBUtils.class.getClassLoader().getResource("utils/Commander.class")).getPath();

        System.err.println(path + ":106:" + " error: ';' expected\n" + "                                + \"\\n\"Bestellnummer: \" + orders.getBESTELLNR()");
        System.err.println(path + ":112:" + " error: illegal start of expression\n" +
                "            }, () -> {");

        System.err.println(path + ":138: error: ';' expected\n" + "                                + \"\\n\"ID: \" + recipe.getRecipeID()");
        System.err.println(path + ":144:" + " error: illegal start of expression\n" +
                "            }, () -> {");

        System.err.println(path + ":182:" + " error: ';' expected\n" + "                                + \"\\n\"KundenNr: \" + customer.getKUNDENNR()");
        System.err.println(path + ":196:" + " error: illegal start of expression\n" +
                "            }, () -> {");
        System.exit(1);
    }

    public static void selectData() {
        // Reinizialize of Lists.
        ConfigInstance.ingredientList = new ArrayList<>();
        ConfigInstance.recipeList = new ArrayList<>();
        ConfigInstance.customerList = new ArrayList<>();
        ConfigInstance.orderList = new ArrayList<>();

        // Select everything and drop it in the list.
        selectIngredients();
        selectRecipe();
        selectCustomer();
        selectOrder();
        selectCategories();
        selectAllergens();
    }

    public static void createSQL() {
        // Get Connection to MySQL
        try (Connection conn = connector("")) {
            ScriptRunner sr = new ScriptRunner(conn, false, false);
            // SQL-Skript
            String dbFile = String.valueOf(DBUtils.class.getClassLoader().getResource("dbSQL.sql")).replace("file:", "").replace("%20", " ");
            String dataFile = String.valueOf(DBUtils.class.getClassLoader().getResource("dataSQL.sql")).replace("file:", "").replace("%20", " ");
            // Das SQL-Skript ausführen.
            // Datenbank und Tabellen erstellen.
            sr.runScript(new BufferedReader(new FileReader(dbFile)));
            // Testdaten erstellen.
            sr.runScript(new BufferedReader(new FileReader(dataFile)));
            // Set DBInitialized to true.
            ConfigInstance.isSQLfinished = true;
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void recreateSQL() {
        // Get Connection to MySQL
        try (Connection conn = connector("")) {
            ScriptRunner sr = new ScriptRunner(conn, false, false);
            // SQL-Skript Path
            String dbFile = String.valueOf(DBUtils.class.getClassLoader().getResource("dbSQL_recreate.sql")).replace("file:", "").replace("%20", " ");
            String dataFile = String.valueOf(DBUtils.class.getClassLoader().getResource("dataSQL.sql")).replace("file:", "").replace("%20", " ");
            // Das SQL-Skript ausführen.
            // Datenbank und Tabellen erstellen.
            sr.runScript(new BufferedReader(new FileReader(dbFile)));
            // Testdaten erstellen.
            sr.runScript(new BufferedReader(new FileReader(dataFile)));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void selectIngredients() {
        // Get Connection
        try (Connection conn = connector(ConfigInstance.database)) {
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
                            ConfigInstance.ingredientList.add(new IngredientList(rs.getBigDecimal(1), rs.getString(2), rs.getString(3), rs.getBigDecimal(4), rs.getInt(5), rs.getBigDecimal(6), rs.getString(10), rs.getBigDecimal(7), rs.getBigDecimal(8), rs.getBigDecimal(9)));
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
        List<BigDecimal> allergenBig = new ArrayList<>();
        List<BigDecimal> categoriesBig = new ArrayList<>();
        try (Connection conn = connector(ConfigInstance.database)) {
            String sql =
                    """
                            SELECT REZEPT.*, REZEPTZUTAT.ZUTATENNR, REZEPTZUTAT.MENGE, REZEPTALLERGENE.ALLERGENNR, REZEPTKATEGORIEN.KATEGORIENR
                            FROM REZEPT
                            INNER JOIN REZEPTZUTAT
                            ON REZEPT.REZEPTNR = REZEPTZUTAT.REZEPTNR
                            LEFT JOIN REZEPTALLERGENE
                            ON REZEPT.REZEPTNR = REZEPTALLERGENE.REZEPTNR
                            LEFT JOIN REZEPTKATEGORIEN
                            ON REZEPT.REZEPTNR = REZEPTKATEGORIEN.REZEPTNR
                            ORDER BY REZEPT.REZEPTNR""";

            try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Successfully got Data from Recipes (no entries)");
                    } else {
                        do {
                            boolean hasID = recipeNr.contains(rs.getInt(1));
                            if (hasID) {
                                int recipeID = rs.getInt(1);

                                ConfigInstance.recipeList.stream().filter(recipeList -> recipeList.getRecipeID().equals(new BigDecimal(recipeID))).findAny().ifPresent(recipe -> {
                                    List<BigDecimal> ingreds = recipe.getIngredients();
                                    List<Integer> amount = recipe.getAmount();
                                    List<BigDecimal> allergens = recipe.getAllergens();
                                    List<BigDecimal> categories = recipe.getCategories();
                                    int ingredAmount = 0;
                                    BigDecimal ingredient = BigDecimal.ZERO;
                                    BigDecimal allergen = BigDecimal.ZERO;
                                    BigDecimal category = BigDecimal.ZERO;
                                    try {
                                        ingredAmount = rs.getInt(8);
                                        ingredient = rs.getBigDecimal(7);
                                        allergen = rs.getBigDecimal(9);
                                        category = rs.getBigDecimal(10);
                                    } catch (SQLException throwables) {
                                        throwables.printStackTrace();
                                    }
                                    if (allergen != null && !allergenBig.contains(allergen)) {
                                        allergens.add(new BigDecimal(String.valueOf(allergen)));
                                        allergenBig.add(new BigDecimal(String.valueOf(allergen)));
                                    }

                                    if (category != null && !categoriesBig.contains(category)) {
                                        categories.add(new BigDecimal(String.valueOf(category)));
                                        categoriesBig.add(new BigDecimal(String.valueOf(category)));
                                    }

                                    if (!ingredient.equals(new BigDecimal(0)) && !ingreds.contains(ingredient)) {
                                        ingreds.add(ingredient);
                                        amount.add(ingredAmount);
                                        recipe.setAmount(amount);
                                        recipe.setIngredients(ingreds);
                                    }
                                });
                            } else {
                                List<BigDecimal> ingredient = new ArrayList<>();
                                ingredient.add(rs.getBigDecimal(7));
                                List<Integer> amount = new ArrayList<>();
                                amount.add(rs.getInt(8));
                                List<BigDecimal> allergens = new ArrayList<>();
                                allergens.add(rs.getBigDecimal(9));
                                List<BigDecimal> categories = new ArrayList<>();
                                categories.add(rs.getBigDecimal(10));
                                allergenBig.add(rs.getBigDecimal(9));
                                categoriesBig.add(rs.getBigDecimal(10));
                                recipeNr.add(rs.getInt(1));
                                // Ingredient (Integer-Liste), Amount (Integer-Liste), Allergens (String-List), Categories (String-List)
                                // 1: ID (BigDecimal), 2: Rezeptname (String), 3: Gesamtkalorien (BigDecimal), 4: Gesamtkohlenhydrate (BigDecimal), 5: Gesamtprotein (BigDecimal), 6: Gesamtpreis (BigDecimal)
                                ConfigInstance.recipeList.add(new RecipeList(rs.getBigDecimal(1), rs.getString(2), rs.getBigDecimal(3), rs.getBigDecimal(4), rs.getBigDecimal(5), ingredient, amount, allergens, categories));
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
        try (Connection conn = connector(ConfigInstance.database)) {
            String sql = "SELECT * FROM KUNDE";
            try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("Successfully got Data from Customers (no entries)");
                    } else {
                        do {
                            // 1: KundenNr (BigDecimal), 2: Nachname (String), 3: Vorname (String), 4: Geburtsdatum (Date), 5: Strasse (String), 6: HausNr (Integer), 7: PLZ (Integer), 8: Ort (String), 9: Telefon (String), 10: E-Mail (String)
                            ConfigInstance.customerList.add(new CustomerList(rs.getBigDecimal(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getString(10)));
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
        // Get Connection
        try (Connection conn = connector(ConfigInstance.database)) {
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
                            ConfigInstance.orderList.add(new OrderList(rs.getBigDecimal(1), rs.getBigDecimal(2), rs.getDate(3), rs.getBigDecimal(4)));
                        } while (rs.next());
                        System.out.println("Successfully got Data from Orders");
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void selectCategories() {
        // Get Connection
        try (Connection conn = connector(ConfigInstance.database)) {
            // Pass your SQL in this String.
            String sql = "SELECT * FROM KATEGORIEN";
            // Make a preparedStatement and set Scroll to insensitive (both directions)
            try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                // Execute query and safe Result in rs
                try (ResultSet rs = ps.executeQuery()) {
                    // If no result do nothing
                    if (!rs.next()) {
                        System.out.println("Successfully got Data from Categories (no entries)");
                    } else {
                        // For Each result add it to IngredientList.
                        do {
                            // 1: KategorieNr (BigDecimal), 2: Kategorie (String)
                            ConfigInstance.categoriesList.add(new CategoriesList(rs.getBigDecimal(1), rs.getString(2)));
                        } while (rs.next());
                        System.out.println("Successfully got Data from Categories");
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void selectAllergens() {
        // Get Connection
        try (Connection conn = connector(ConfigInstance.database)) {
            // Pass your SQL in this String.
            String sql = "SELECT * FROM ALLERGENE";
            // Make a preparedStatement and set Scroll to insensitive (both directions)
            try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                // Execute query and safe Result in rs
                try (ResultSet rs = ps.executeQuery()) {
                    // If no result do nothing
                    if (!rs.next()) {
                        System.out.println("Successfully got Data from Allergens (no entries)");
                    } else {
                        // For Each result add it to IngredientList.
                        do {
                            // 1: AllergenNr (BigDecimal), 2: Allergen (String)
                            ConfigInstance.allergensList.add(new AllergensList(rs.getBigDecimal(1), rs.getString(2)));
                        } while (rs.next());
                        System.out.println("Successfully got Data from Allergens");
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static BigDecimal recipeNr = BigDecimal.ZERO;

    public static void insertRecipe(String recipeName, BigDecimal calories, BigDecimal carbohydrates, BigDecimal protein, BigDecimal price, List<BigDecimal> ingredients, List<BigDecimal> allergens, List<BigDecimal> categories, List<Integer> amount) {
        List<RecipeList> list = ConfigInstance.recipeList;
        list.forEach(recipe -> {
            if (recipe.getRecipeName().toLowerCase().equals(recipeName.toLowerCase())) {
                System.out.println("You cannot create a recipe with the same name...");
            }
        });

        try (Connection con = connector(ConfigInstance.database)) {
                String sql = "INSERT INTO REZEPT (REZEPTNAME, GESAMTKALORIEN, GESAMTKH, GESAMTPROTEIN, GESAMTPREIS) " + "VALUES (?,?,?,?,?)";
                try (PreparedStatement psInsertRecipe = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                    psInsertRecipe.setString(1, recipeName);
                    psInsertRecipe.setBigDecimal(2, calories);
                    psInsertRecipe.setBigDecimal(3, carbohydrates);
                    psInsertRecipe.setBigDecimal(4, protein);
                    psInsertRecipe.setBigDecimal(5, price);
                    psInsertRecipe.executeUpdate();
                    sql = "SELECT REZEPT.REZEPTNR FROM REZEPT WHERE REZEPTNAME = ?";
                    try (PreparedStatement psSelectRecipe = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)){
                        psSelectRecipe.setString(1, recipeName);
                        try (ResultSet rs = psSelectRecipe.executeQuery()) {
                            if(rs.next()) {
                                recipeNr = rs.getBigDecimal(1);
                                AtomicInteger i = new AtomicInteger();
                                ingredients.forEach(ingredient -> {
                                    String sqlIngreds = "INSERT INTO REZEPTZUTAT (REZEPTNR, ZUTATENNR, MENGE)" + "VALUES (?,?,?)";
                                    try (PreparedStatement psInsertIngredients = con.prepareStatement(sqlIngreds)) {
                                        psInsertIngredients.setBigDecimal(1, recipeNr);
                                        psInsertIngredients.setBigDecimal(2, ingredient);
                                        psInsertIngredients.setInt(3, amount.get(i.get()));
                                        psInsertIngredients.executeUpdate();
                                        i.getAndIncrement();
                                    } catch(SQLException e) {
                                        System.out.println("Something went wrong inserting into Rezeptzutat.");
                                    }
                                });
                                allergens.forEach(allergen -> {
                                    String sqlAllergens = "INSERT INTO REZEPTALLERGENE (REZEPTNR, ALLERGENNR)" + "VALUES (?,?)";
                                    try (PreparedStatement psInsertAllergens = con.prepareStatement(sqlAllergens, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                                        psInsertAllergens.setBigDecimal(1, recipeNr);
                                        psInsertAllergens.setBigDecimal(2, allergen);
                                        psInsertAllergens.executeUpdate();
                                    } catch (SQLException e) {
                                        System.out.println("Something went wrong inserting into Rezeptallergen.");
                                    }
                                });
                                categories.forEach(category -> {
                                    String sqlCategories = "INSERT INTO REZEPTKATEGORIEN (REZEPTNR, KATEGORIENR)" + "VALUES (?,?)";
                                    try (PreparedStatement psInsertCategories = con.prepareStatement(sqlCategories, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                                        psInsertCategories.setBigDecimal(1, recipeNr);
                                        psInsertCategories.setBigDecimal(2, category);
                                        psInsertCategories.executeUpdate();
                                        Thread.sleep(3000);
                                        ConfigInstance.recipeList.add(new RecipeList(recipeNr, recipeName, calories, carbohydrates, protein, ingredients, amount, allergens, categories));
                                        recipeNr = BigDecimal.ZERO;
                                    } catch (SQLException | InterruptedException e) {
                                        System.out.println("Something went wrong inserting in Rezeptkategorien.");
                                    }
                                });
                            }
                        }
                    }

                }
            con.commit();
            //ConfigInstance.recipeList.add(new RecipeList(finalRecipeNr, recipeName, calories, carbohydrates, protein, ingredients, amount, allergens, categories));
            //selectData();
            } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


