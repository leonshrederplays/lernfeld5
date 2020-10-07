package commands;

import constructors.RecipeList;
import instances.ConfigInstance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Recipes {

    public static void recipe(){
        List<RecipeList> list = ConfigInstance.recipeList;
        list.forEach(recipe -> {
            String str ="ID: " + recipe.getRecipeID()
                    + " | Name: " + recipe.getRecipeName();
            System.out.println(str);
        });
    }

    public static void recipeDescription(String arg) {
        List<RecipeList> list = ConfigInstance.recipeList;
        int id = 0;
        boolean isIDValid = false;
        try {
            id = Integer.parseInt(arg);
            int finalId = id;
            isIDValid = list.stream().filter(recipe -> finalId == recipe.getRecipeID()).findAny().isPresent();
        } catch (NumberFormatException e) { }
        boolean isNameValid = list.stream().filter(recipe -> arg.toLowerCase().equals(recipe.getRecipeName().toLowerCase())).findAny().isPresent();

        if(isIDValid || isNameValid) {
            int finalId1 = id;
            list.stream().filter(recipe -> recipe.getRecipeID() == finalId1 || arg.toLowerCase().equals(recipe.getRecipeName().toLowerCase() )).findAny().ifPresentOrElse(recipe -> {
                List<String> ingredsList = new ArrayList<>();
                // Defining Atomics cuz in lambda this is needed.
                AtomicInteger ingredients = new AtomicInteger();
                AtomicInteger calories = new AtomicInteger();
                AtomicReference<BigDecimal> protein = new AtomicReference<>(BigDecimal.ZERO);
                AtomicReference<BigDecimal> carbohydrates = new AtomicReference<>(BigDecimal.ZERO);
                AtomicReference<BigDecimal> price = new AtomicReference<>(BigDecimal.ZERO);

                recipe.getIngredients().forEach(recipeIngredient -> {
                    // Search for the ingredient and do stuff.
                    ConfigInstance.ingredientList.stream().filter(ingredient -> recipeIngredient == ingredient.getIngredientID()).findAny().ifPresentOrElse(ingred -> {
                        // If ingredient unit is "Liter" hang an l on it else an x.
                        if(ingred.getUnit().equals("Liter")) {
                            // Add ingredient and amount to string list.
                            ingredsList.add(ingred.getIngredientName() + " " + recipe.getAmount().get(ingredients.get()) + "l");
                        } else {
                            // Add ingredient and amount to string list.
                            ingredsList.add(ingred.getIngredientName() + " " + recipe.getAmount().get(ingredients.get()) + "x");
                        }
                        // Define the amount of the ingredient.
                        BigDecimal amount = new BigDecimal(recipe.getAmount().get(ingredients.get()));
                        // Add calorien multiplied by amount to calories.
                        calories.getAndUpdate(update -> update + (ingred.getCalorie() * recipe.getAmount().get(ingredients.get())));
                        // Add protein multiplied by amount to protein.
                        protein.getAndUpdate(update -> update.add(new BigDecimal("" + ingred.getProtein()).multiply(amount).setScale(2, RoundingMode.HALF_EVEN)));
                        // Add carbohydrates multiplied by amount to carbohydrates.
                        carbohydrates.getAndUpdate(update -> update.add(new BigDecimal("" + ingred.getCarbohydrates()).multiply(amount).setScale(2, RoundingMode.HALF_EVEN)));
                        // Add price multiplied by amount to price.
                        price.getAndUpdate(update -> update.add(new BigDecimal("" + ingred.getNettoprice()).multiply(amount).setScale(2, RoundingMode.HALF_EVEN)));
                        // Increase ingredients by 1.
                        ingredients.getAndIncrement();
                    }, () -> {
                        System.out.println("Wait what there is an ingredient that is not defined!?");
                    });
                });
                // Make a String seperated by ", " from a list.
                String ingreds = String.join(", ", ingredsList);
                // Make a String seperated by ", " from a list.
                String allergens = String.join(", ", recipe.getAllergens());
                // Make a String seperated by ", " from a list.
                String categories = String.join(", ", recipe.getCategories());
                // Call the recipeDescBuilder to send recipe info.
                String str =
                        "Properties of this Recipe: "
                                + "ID: " + recipe.getRecipeID()
                                + " | Name: " + recipe.getRecipeName()
                                + "\nCalories: " + calories 
                                + "\nCarbohydrates: " + carbohydrates 
                                + "\nProtein: " + protein 
                                + "\nIngredients: " + ingreds 
                                + "\nPrice: " + price +"$"
                                + "\nAllergens: " + allergens 
                                + "\nCategroies: " + categories;
                System.out.println(str);
            }, () -> {

            });
        } else {
            System.out.println("The Recipe: " + arg + " does not exist. type recipe to list all recipes.");
        }
    }

}
