package commands;

import constructors.CategoriesList;
import constructors.RecipeList;
import instances.ConfigInstance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Categories {

    public void categories() {
        List<CategoriesList> list = ConfigInstance.categoriesList;
        list.forEach(category -> {
            String str ="CategoryID: " + category.getCategoryID()
                    + " Category: " + category.getCategory();
            System.out.println(str);
        });
    }

    public void categoryRecipes(String arg) {
        List<CategoriesList> categories = ConfigInstance.categoriesList;
        //List<RecipeList> recipes = ConfigInstance.recipeList;
        int id;
        boolean isIDValid = false;
        try {
            id = Integer.parseInt(arg);
            int finalId = id;
            isIDValid = categories.stream().anyMatch(category -> new BigDecimal(finalId).equals(category.getCategoryID()));
        } catch (NumberFormatException e) {
            //System.out.println("");
        }
        boolean isNameValid = categories.stream().anyMatch(category -> arg.toLowerCase().equals(category.getCategory().toLowerCase()));

        if(isIDValid || isNameValid) {
            AtomicReference<BigDecimal> categoriesBigDecimal = new AtomicReference<>(BigDecimal.ZERO);
            boolean finalIsIDValid = isIDValid;
            categories.forEach(category -> {
                if(finalIsIDValid) {
                    categoriesBigDecimal.set(category.getCategoryID());
                } else {
                    if(category.getCategory().toLowerCase().equals(arg.toLowerCase())) categoriesBigDecimal.set(category.getCategoryID());
                }
            });
            List<RecipeList> foundCategoriesList = new ArrayList<>();
            List<RecipeList> recipeList = ConfigInstance.recipeList;
            recipeList.forEach(recipe -> recipe.getCategories().forEach(recipeCategory -> {
                if(recipeCategory.equals(categoriesBigDecimal.get())) foundCategoriesList.add(new RecipeList(recipe.getRecipeID(), recipe.getRecipeName(), recipe.getRecipeCalories(), recipe.getRecipeCarbs(), recipe.getRecipeProtein(), recipe.getIngredients(), recipe.getAmount(), recipe.getAllergens(), recipe.getCategories()));
            }));
            if(foundCategoriesList.isEmpty()) {
                System.out.println("No Recipes with this Allergen.");
            } else {
                foundCategoriesList.forEach(result -> {
                    String str ="ID: " + result.getRecipeID()
                            + " | Name: " + result.getRecipeName();
                    System.out.println(str);
                });
            }
        }
    }

}
