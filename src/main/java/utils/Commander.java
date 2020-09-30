package utils;

import com.sun.javafx.image.IntPixelGetter;
import constructors.IngredientList;
import constructors.RecipeList;
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
                "ingreds / ingreds <ingredient name>/<ingredient ID> -- lists all available ingredients/lists the details of one ingredient\n" +
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
        try {
            int id = Integer.parseInt(arg);
            list.stream().filter(ingredient -> id == ingredient.getIngredientID()).findAny().ifPresentOrElse(ingredient -> {
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
            }, () -> {
                System.out.println("Die Zutat: " + arg + " existiert nicht. Gebe ingreds ein um alle Zutaten zu listen.");
            });
        } catch (NumberFormatException e) {
            list.stream().filter(ingredient -> arg.toLowerCase().equals(ingredient.getIngredientName().toLowerCase())).findAny().ifPresentOrElse(ingredient -> {
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
            }, () -> {
                System.out.println("Die Zutat: " + arg + " existiert nicht. Gebe ingreds ein um alle Zutaten zu listen.");
            });
        }
    }

    public void shutdown(){
        System.exit(0);
    }

    public void recipeDescription(String arg) {
        List<RecipeList> list = ConfigInstance.recipeList;
        try {
            int id = Integer.parseInt(arg);
            list.stream().filter(recipe -> id == recipe.getRecipeID()).findAny().ifPresentOrElse(recipe -> {
                String str =
                        "Eigenschaften des Rezepts: "
                                + "ID: " + recipe.getRecipeID()
                                + " / Name: " + recipe.getRecipeName()
                                + "\nKalorien: " + recipe.getRecipeCalories() + ", "
                                + "\nKohlenhydrate: " + recipe.getRecipeCarbs() + ", "
                                + "\nProtein: " + recipe.getRecipeProtein();
                System.out.println(str);
            }, () -> {
                System.out.println("Das Rezept: " + arg + " existiert nicht. Gebe recipe ein um alle Rezepte zu listen.");
            });
        } catch (NumberFormatException e) {
            list.stream().filter(recipe -> arg.toLowerCase().equals(recipe.getRecipeName().toLowerCase())).findAny().ifPresentOrElse(recipe -> {
                String str =
                        "Eigenschaften des Rezeptes: "
                                + "ID: " + recipe.getRecipeID()
                                + " / Name: " + recipe.getRecipeID()
                                + "\nKalorien: " + recipe.getRecipeCalories() + ", "
                                + "\nKohlenhydrate: " + recipe.getRecipeCarbs() + ", "
                                + "\nProtein: " + recipe.getRecipeProtein();
                System.out.println(str);
            }, () -> {
                System.out.println("Das Rezept: " + arg + " existiert nicht. Gebe recipe ein um alle Rezepte zu listen.");
            });
        }
    }

    public void recipe(){
        List<RecipeList> list = ConfigInstance.recipeList;
        list.forEach(recipe -> {
            String str ="ID: " + recipe.getRecipeID()
                    + " / Name: " + recipe.getRecipeName();
            System.out.println(str);
        });
    }
}
