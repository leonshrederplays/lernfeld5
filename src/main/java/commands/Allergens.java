package commands;

import constructors.AllergensList;
import constructors.RecipeList;
import instances.ConfigInstance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Allergens {

    public void allergens() {
        List<AllergensList> list = ConfigInstance.allergensList;
        list.forEach(allergen -> {
            String str ="AllergenID: " + allergen.getAllergenID()
                    + " Allergen: " + allergen.getAllergen();
            System.out.println(str);
        });
    }

    public void allergenRecipes(String arg) {
        List<AllergensList> allergens = ConfigInstance.allergensList;
        //List<RecipeList> recipes = ConfigInstance.recipeList;
        int id;
        boolean isIDValid = false;
        try {
            id = Integer.parseInt(arg);
            int finalId = id;
            isIDValid = allergens.stream().anyMatch(allergen -> new BigDecimal(finalId).equals(allergen.getAllergenID()));
        } catch (NumberFormatException e) {
            //System.out.println("");
        }
        boolean isNameValid = allergens.stream().anyMatch(allergen -> arg.toLowerCase().equals(allergen.getAllergen().toLowerCase()));

        if(isIDValid || isNameValid) {
            AtomicReference<BigDecimal> allergenBigDecimal = new AtomicReference<>(BigDecimal.ZERO);
            boolean finalIsIDValid = isIDValid;
            allergens.forEach(allergen -> {
                if(finalIsIDValid) {
                    allergenBigDecimal.set(allergen.getAllergenID());
                } else {
                    if(allergen.getAllergen().toLowerCase().equals(arg.toLowerCase())) allergenBigDecimal.set(allergen.getAllergenID());
                }
            });
            List<RecipeList> foundAllergenList = new ArrayList<>();
            List<RecipeList> recipeList = ConfigInstance.recipeList;
            recipeList.forEach(recipe -> recipe.getAllergens().forEach(recipeAllergen -> {
                if(recipeAllergen.equals(allergenBigDecimal.get())) foundAllergenList.add(new RecipeList(recipe.getRecipeID(), recipe.getRecipeName(), recipe.getRecipeCalories(), recipe.getRecipeCarbs(), recipe.getRecipeProtein(), recipe.getIngredients(), recipe.getAmount(), recipe.getAllergens(), recipe.getCategories()));
            }));
            if(foundAllergenList.isEmpty()) {
                System.out.println("No Recipes with this Allergen.");
            } else {
                foundAllergenList.forEach(result -> {
                    String str ="ID: " + result.getRecipeID()
                            + " | Name: " + result.getRecipeName();
                    System.out.println(str);
                });
            }
        }
    }

}
