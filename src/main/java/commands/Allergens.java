package commands;

import constructors.AllergensList;
import constructors.RecipeList;
import instances.ConfigInstance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
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
        AtomicReference<BigDecimal> catID = new AtomicReference<>(BigDecimal.ZERO);
        AtomicBoolean isIDValid = new AtomicBoolean(false);
        try {
            int i = Integer.parseInt(arg);
            allergens.stream().filter(allergen -> new BigDecimal(i).equals(allergen.getAllergenID())).findAny().ifPresent(allerg -> {
                catID.set(allerg.getAllergenID());
                isIDValid.set(true);
            });
        } catch (NumberFormatException e) {
            //System.out.println("");
        }
        boolean isNameValid = allergens.stream().anyMatch(allergen -> arg.toLowerCase().equals(allergen.getAllergen().toLowerCase()));

        if(isIDValid.get() || isNameValid) {
            AtomicReference<BigDecimal> allergenBigDecimal = new AtomicReference<>(BigDecimal.ZERO);
            AtomicBoolean isSet = new AtomicBoolean(false);
            allergens.forEach(allergen -> {
                if(isSet.get()) return;
                if(isIDValid.get()) {
                    if(allergen.getAllergenID().equals(catID.get())) {
                        allergenBigDecimal.set(allergen.getAllergenID());
                        isSet.set(true);
                    }
                } else {
                    if(allergen.getAllergen().toLowerCase().equals(arg.toLowerCase())) {
                        allergenBigDecimal.set(allergen.getAllergenID());
                        isSet.set(true);
                    }
                }
            });
            isSet.set(false);
            List<RecipeList> foundAllergenList = new ArrayList<>();
            List<RecipeList> recipeList = ConfigInstance.recipeList;
            recipeList.forEach(recipe -> {
                boolean contains = recipe.getAllergens().contains(allergenBigDecimal.get());
                if(contains) foundAllergenList.add(new RecipeList(recipe.getRecipeID(), recipe.getRecipeName(), recipe.getRecipeCalories(), recipe.getRecipeCarbs(), recipe.getRecipeProtein(), recipe.getIngredients(), recipe.getAmount(), recipe.getAllergens(), recipe.getCategories()));
            });
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
