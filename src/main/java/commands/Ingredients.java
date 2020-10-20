package commands;

import constructors.IngredientList;
import instances.ConfigInstance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Ingredients {
    public List<String> ingredient() {
        List<String> ingreds = new ArrayList<>();
        List<IngredientList> list = ConfigInstance.ingredientList;
        list.forEach(ingredient -> {
            String str ="ID: " + ingredient.getIngredientID()
                    + " | Name: " + ingredient.getIngredientName();
            ingreds.add(str);
        });
        return ingreds;
    }
    public List<String> ingredientChoice() {
        List<String> ingredsChoice = new ArrayList<>();
        List<IngredientList> list = ConfigInstance.ingredientList;
        ingredsChoice.add(" ");
        list.forEach(ingredient -> {
            ingredsChoice.add(ingredient.getIngredientName());
        });
        return ingredsChoice;
    }

    public String ingredientDescription(String arg) {
        var ref = new Object() {
            String str = null;
        };
        List<IngredientList> list = ConfigInstance.ingredientList;
        try {
            int id = Integer.parseInt(arg);
            list.stream().filter(ingredient -> new BigDecimal(id).equals(ingredient.getIngredientID())).findAny().ifPresentOrElse(ingredient -> {
                ref.str =
                        "Properties of the Ingredient: "
                                + "ID: " + ingredient.getIngredientID() + " | Name: " + ingredient.getIngredientName()
                                + "\nUnit: " + ingredient.getUnit()
                                + "\nNetprice: " + ingredient.getNettoprice()
                                + "\nIn Stock: " + ingredient.getAmount()
                                + "\nSupplier: " + ingredient.getSupplierID() + " | " + ingredient.getSupplierName()
                                + "\nCalories: " + ingredient.getCalorie()
                                + "\nCarbohydrates: " + ingredient.getCarbohydrates()
                                + "\nProtein: " + ingredient.getProtein();
            }, () -> ref.str = "The ingredient: " + arg + " does not exist. type ingreds to list all ingredients.");
        } catch (NumberFormatException e) {
            list.stream().filter(ingredient -> arg.toLowerCase().equals(ingredient.getIngredientName().toLowerCase())).findAny().ifPresentOrElse(ingredient -> {
                ref.str =
                        "Properties of the Ingredient: "
                                + "\nID: " + ingredient.getIngredientID() + " | Name: " + ingredient.getIngredientName()
                                + "\nUnit: " + ingredient.getUnit()
                                + "\nNetprice: " + ingredient.getNettoprice()
                                + "\nIn Stock: " + ingredient.getAmount() 
                                + "\nSupplierID: " + ingredient.getSupplierID() + " | " + ingredient.getSupplierName() 
                                + "\nCalories: " + ingredient.getCalorie() 
                                + "\nCarbohydrates: " + ingredient.getCarbohydrates() 
                                + "\nProtein: " + ingredient.getProtein();

            }, () -> ref.str = "The ingredient: " + arg + " does not exist. type ingreds to list all ingredients.");
        }return ref.str;
    }

}
