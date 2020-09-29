package instances;

import constructors.IngredientList;

import java.io.Serializable;
import java.sql.Connection;
import java.util.List;

public class ConfigInstance implements Serializable {

    // SQL Connection
    public static Connection conn;

    public static List<IngredientList> ingredientList;
}
