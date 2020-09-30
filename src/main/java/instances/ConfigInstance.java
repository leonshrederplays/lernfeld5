package instances;

import constructors.CustomerList;
import constructors.IngredientList;
import constructors.RecipeList;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class ConfigInstance implements Serializable {

    // SQL Connection
    public static Connection conn;

    public static List<IngredientList> ingredientList = new ArrayList<>();
    public static List<RecipeList> recipeList = new ArrayList<>();
    public static List<CustomerList> customerList = new ArrayList<>();
}
