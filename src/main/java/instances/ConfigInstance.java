package instances;

import constructors.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ConfigInstance implements Serializable {

    // SQL Connection
    //public static Connection conn;

    public static List<IngredientList> ingredientList = new ArrayList<>();
    public static List<RecipeList> recipeList = new ArrayList<>();
    public static List<CustomerList> customerList = new ArrayList<>();
    public static List<OrderList> orderList = new ArrayList<>();
    public static List<CategoriesList> categoriesList = new ArrayList<>();
    public static List<AllergensList> allergensList = new ArrayList<>();

    public static List<BigDecimal> cartIngredients = new ArrayList<>();
    public static List<BigDecimal> cartRecipes = new ArrayList<>();

    public static boolean isSQLfinished = false;
    public static boolean isAdminPassed = false;
    public static String database = "krautundrueben";

    public void lockCustomers() {
        new Thread(() -> {
            try {
                Thread.sleep(300000);
                isAdminPassed = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
