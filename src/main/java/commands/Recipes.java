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

    public void recipe(){
        List<RecipeList> list = ConfigInstance.recipeList;
        list.forEach(recipe -> {
            String str ="ID: " + recipe.getRecipeID()
                    + " | Name: " + recipe.getRecipeName();
            System.out.println(str);
        });
    }

    public void recipeDescription(String arg) {
        List<RecipeList> list = ConfigInstance.recipeList;
        int id = 0;
        boolean isIDValid = false;
        try {
            id = Integer.parseInt(arg);
            int finalId = id;
            isIDValid = list.stream().anyMatch(recipe -> new BigDecimal(finalId).equals(recipe.getRecipeID()));
        } catch (NumberFormatException e) {
            //System.out.println("");
        }
        boolean isNameValid = list.stream().anyMatch(recipe -> arg.toLowerCase().equals(recipe.getRecipeName().toLowerCase()));

        if(isIDValid || isNameValid) {
            int finalId1 = id;
            list.stream().filter(recipe -> recipe.getRecipeID().equals(new BigDecimal(finalId1)) || arg.toLowerCase().equals(recipe.getRecipeName().toLowerCase() )).findAny().ifPresentOrElse(recipe -> {
                List<String> ingredsList = new ArrayList<>();
                // Defining Atomics cuz in lambda this is needed.
                AtomicInteger ingredients = new AtomicInteger();
                AtomicReference<BigDecimal> calories = new AtomicReference<>(BigDecimal.ZERO);
                AtomicReference<BigDecimal> protein = new AtomicReference<>(BigDecimal.ZERO);
                AtomicReference<BigDecimal> carbohydrates = new AtomicReference<>(BigDecimal.ZERO);
                AtomicReference<BigDecimal> price = new AtomicReference<>(BigDecimal.ZERO);
                recipe.getIngredients().forEach(recipeIngredient -> {
                    // Search for the ingredient and do stuff.
                    ConfigInstance.ingredientList.stream().filter(ingredient -> new BigDecimal(String.valueOf(recipeIngredient)).equals(ingredient.getIngredientID())).findAny().ifPresentOrElse(ingred -> {
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
                        calories.getAndUpdate(update -> update.add(ingred.getCalorie().multiply(new BigDecimal(recipe.getAmount().get(ingredients.get())))));
                        // Add protein multiplied by amount to protein.
                        protein.getAndUpdate(update -> update.add(new BigDecimal("" + ingred.getProtein()).multiply(amount).setScale(2, RoundingMode.HALF_EVEN)));
                        // Add carbohydrates multiplied by amount to carbohydrates.
                        carbohydrates.getAndUpdate(update -> update.add(new BigDecimal("" + ingred.getCarbohydrates()).multiply(amount).setScale(2, RoundingMode.HALF_EVEN)));
                        // Add price multiplied by amount to price.
                        price.getAndUpdate(update -> update.add(new BigDecimal("" + ingred.getNettoprice()).multiply(amount).setScale(2, RoundingMode.HALF_EVEN)));
                        // Increase ingredients by 1.
                        ingredients.getAndIncrement();
                    }, () -> System.out.println("Wait what there is an ingredient that is not defined!?"));
                });
                // Make a String seperated by ", " from a list.
                String ingreds = String.join(", ", ingredsList);
                // Make a String seperated by ", " from a list.
                List<String> allergens = new ArrayList<>();
                recipe.getAllergens().forEach(allergen -> ConfigInstance.allergensList.stream().filter(allergenList -> allergen.equals(allergenList.getAllergenID())).findAny().ifPresent(alleg -> allergens.add(alleg.getAllergen())));
                String allergensString = String.join(", ", allergens);
                // Make a String seperated by ", " from a list.
                List<String> categories = new ArrayList<>();
                recipe.getCategories().forEach(category -> ConfigInstance.categoriesList.stream().filter(categoryList -> category.equals(categoryList.getCategoryID())).findAny().ifPresent(categ -> categories.add(categ.getCategory())));
                String categoriesString = String.join(", ", categories);
                // Call the recipeDescBuilder to send recipe info.
                String str =
                        "Properties of this Recipe: "
                                + "\nID: " + recipe.getRecipeID()
                                + " | Name: " + recipe.getRecipeName()
                                + "\nCalories: " + calories 
                                + "\nCarbohydrates: " + carbohydrates 
                                + "\nProtein: " + protein 
                                + "\nIngredients: " + ingreds 
                                + "\nPrice: " + price +"$"
                                + "\nAllergens: " + allergensString
                                + "\nCategroies: " + categoriesString;
                System.out.println(str);
            }, () -> {

            });
        } else {
            System.out.println("The Recipe: " + arg + " does not exist. type recipe to list all recipes.");
        }
    }
    public void recipecalories(String arg){
        List<RecipeList> list = ConfigInstance.recipeList;
        list.forEach(recipe -> {
            if (recipe.getRecipeCalories().doubleValue() <= Double.parseDouble(arg)) {
                String str = "ID: " + recipe.getRecipeID()
                        + " | Name: " + recipe.getRecipeName()
                        + " | Kalorien: " + recipe.getRecipeCalories();
                System.out.println(str);
            }
            else{
                System.out.println("UFF");
            }
        });

    }

}
