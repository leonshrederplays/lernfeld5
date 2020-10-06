package commands;

import constructors.IngredientList;
import instances.ConfigInstance;

import java.util.List;

public class Ingredients {
    public static void ingredient() {
        List<IngredientList> list = ConfigInstance.ingredientList;
        list.forEach(ingredient -> {
            String str ="ID: " + ingredient.getIngredientID()
                    + " | Name: " + ingredient.getIngredientName();
            System.out.println(str);
        });
    }

    public static void ingredientDescription(String arg) {
        List<IngredientList> list = ConfigInstance.ingredientList;
        try {
            int id = Integer.parseInt(arg);
            list.stream().filter(ingredient -> id == ingredient.getIngredientID()).findAny().ifPresentOrElse(ingredient -> {
                String str =
                        "Properties of the Ingredient: "
                                + "ID: " + ingredient.getIngredientID()
                                + " | Name: " + ingredient.getIngredientName()
                                + "\nUnit: " + ingredient.getUnit() + ", "
                                + "\nNetprice: " + ingredient.getNettoprice() + ", "
                                + "\nIn Stock: " + ingredient.getAmount() + ", "
                                + "\nSupplierID: " + ingredient.getSupplierID() + ", "
                                + "\nSupplier-Name: " + ingredient.getSupplierName() + ", "
                                + "\nCalories: " + ingredient.getCalorie() + ", "
                                + "\nCarbohydrates: " + ingredient.getCarbohydrates() + ", "
                                + "\nProtein: " + ingredient.getProtein();
                System.out.println(str);
            }, () -> {
                System.out.println("The ingredient: " + arg + " does not exist. type ingreds to list all ingredients.");
            });
        } catch (NumberFormatException e) {
            list.stream().filter(ingredient -> arg.toLowerCase().equals(ingredient.getIngredientName().toLowerCase())).findAny().ifPresentOrElse(ingredient -> {
                String str =
                        "Properties of the Ingredient: "
                                + "ID: " + ingredient.getIngredientID()
                                + " | Name: " + ingredient.getIngredientName()
                                + "\nUnit: " + ingredient.getUnit() + ", "
                                + "\nNetprice: " + ingredient.getNettoprice() + ", "
                                + "\nIn Stock: " + ingredient.getAmount() + ", "
                                + "\nSupplierID: " + ingredient.getSupplierID() + ", "
                                + "\nSupplier-Name: " + ingredient.getSupplierName() + ", "
                                + "\nCalories: " + ingredient.getCalorie() + ", "
                                + "\nCarbohydrates: " + ingredient.getCarbohydrates() + ", "
                                + "\nProtein: " + ingredient.getProtein();
                System.out.println(str);
            }, () -> {
                System.out.println("The ingredient: " + arg + " does not exist. type ingreds to list all ingredients.");
            });
        }
    }

}
