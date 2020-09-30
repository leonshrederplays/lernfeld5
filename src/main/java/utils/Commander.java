package utils;

import constructors.IngredientList;
import instances.ConfigInstance;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Commander {

    public DBUtils dbUtil = new DBUtils();

    public void helper(){
        System.out.print("Here is a list of available commands\n" +
                "\n" +
                "help -- shows a list of all available commands\n" +
                "ingredients / ingredients <recipe name> -- lists all available ingredients/lists the ingredients of a recipe\n" +
                "exit -- exit the program\n\n");
    }

    public void ingredients(){
        List<IngredientList> list = ConfigInstance.ingredientList;
        list.forEach(ingredient -> {
            String str ="ID: " + ingredient.getIngredientID()
                    + " / Name: " + ingredient.getIngredientName();
                    /*+ "Einheit: " + ingredient.getUnit() + ", "
                    + "Nettopreis: " + ingredient.getNettoprice() + ", "
                    + "Bestand: " + ingredient.getAmount() + ", "
                    + "Lieferant: " + ingredient.getSupplierID() + ", "
                    + "" + ingredient.getCalorie() + ", "
                    + "" + ingredient.getCarbohydrates() + ", "
                    + "" + ingredient.getProtein();*/
            System.out.println(str);
        });
    }

    public void ingredientDescription(String arg) {
        List<IngredientList> list = ConfigInstance.ingredientList;
        list.forEach(ingredient -> {
            if(ingredient.getIngredientName().toLowerCase().equals(arg.toLowerCase())) {
                String str =
                        "Eigenschaften der Zutat: "
                                + "ID: " + ingredient.getIngredientID()
                                + " / Name: " + ingredient.getIngredientName()
                                + "\nEinheit: " + ingredient.getUnit() + ", "
                                + "\nNettopreis: " + ingredient.getNettoprice() + ", "
                                + "\nBestand: " + ingredient.getAmount() + ", "
                                + "\nLieferant: " + ingredient.getSupplierID() + ", "
                                + "\nKalorien: " + ingredient.getCalorie() + ", "
                                + "\nKohlenhydrate: " + ingredient.getCarbohydrates() + ", "
                                + "\nProtein: " + ingredient.getProtein();
                System.out.println(str);
            }
        });
    }

    public void shutdown(){
        System.exit(0);
    }
}
