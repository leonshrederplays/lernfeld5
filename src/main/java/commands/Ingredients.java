package commands;

import constructors.IngredientList;
import constructors.RecipeList;
import instances.ConfigInstance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Ingredients {
    public void ingredient() {
        List<IngredientList> list = ConfigInstance.ingredientList;
        list.forEach(ingredient -> {
            String str ="ID: " + ingredient.getIngredientID()
                    + " | Name: " + ingredient.getIngredientName();
            System.out.println(str);
        });
    }

    public void ingredientDescription(String arg) {
        List<IngredientList> list = ConfigInstance.ingredientList;
        try {
            int id = Integer.parseInt(arg);
            list.stream().filter(ingredient -> new BigDecimal(id).equals(ingredient.getIngredientID())).findAny().ifPresentOrElse(ingredient -> {
                String str =
                        "Properties of the Ingredient: "
                                + "ID: " + ingredient.getIngredientID() + " | Name: " + ingredient.getIngredientName()
                                + "\nUnit: " + ingredient.getUnit() 
                                + "\nNetprice: " + ingredient.getNettoprice() 
                                + "\nIn Stock: " + ingredient.getAmount() 
                                + "\nSupplier: " + ingredient.getSupplierID() + " | " + ingredient.getSupplierName() 
                                + "\nCalories: " + ingredient.getCalorie() 
                                + "\nCarbohydrates: " + ingredient.getCarbohydrates() 
                                + "\nProtein: " + ingredient.getProtein();
                System.out.println(str);
            }, () -> System.out.println("The ingredient: " + arg + " does not exist. type ingreds to list all ingredients."));
        } catch (NumberFormatException e) {
            list.stream().filter(ingredient -> arg.toLowerCase().equals(ingredient.getIngredientName().toLowerCase())).findAny().ifPresentOrElse(ingredient -> {
                String str =
                        "Properties of the Ingredient: "
                                + "\nID: " + ingredient.getIngredientID() + " | Name: " + ingredient.getIngredientName()
                                + "\nUnit: " + ingredient.getUnit()
                                + "\nNetprice: " + ingredient.getNettoprice()
                                + "\nIn Stock: " + ingredient.getAmount() 
                                + "\nSupplierID: " + ingredient.getSupplierID() + " | " + ingredient.getSupplierName() 
                                + "\nCalories: " + ingredient.getCalorie() 
                                + "\nCarbohydrates: " + ingredient.getCarbohydrates() 
                                + "\nProtein: " + ingredient.getProtein();
                System.out.println(str);
            }, () -> System.out.println("The ingredient: " + arg + " does not exist. type ingreds to list all ingredients."));
        }
    }

    public void ingredientUnused() {
        List<IngredientList> list = ConfigInstance.ingredientList;
        List<RecipeList> recipeList = ConfigInstance.recipeList;
        List<BigDecimal> idList = new ArrayList<>();
        list.forEach(ingredientList -> idList.add(ingredientList.getIngredientID()));

        recipeList.forEach(recipe -> recipe.getIngredients().forEach(ingred -> {
            if(idList.contains(ingred)) idList.remove(ingred);
        }));

        idList.forEach(number -> list.stream().filter(filter -> filter.getIngredientID().equals(number)).findAny().ifPresent(ingred -> {
            String str ="ID: " + ingred.getIngredientID()
                    + " | Name: " + ingred.getIngredientName();
            System.out.println(str);
        }));
    }

}
