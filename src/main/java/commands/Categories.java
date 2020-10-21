package commands;

import constructors.CategoriesList;
import constructors.RecipeList;
import instances.ConfigInstance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
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
        AtomicBoolean isIDValid = new AtomicBoolean(false);
        AtomicReference<BigDecimal> catID = new AtomicReference<>(BigDecimal.ZERO);
        try {
            int i = Integer.parseInt(arg);
            categories.stream().filter(category -> new BigDecimal(i).equals(category.getCategoryID())).findAny().ifPresent(cat -> {
                catID.set(cat.getCategoryID());
                isIDValid.set(true);
            });
        } catch (NumberFormatException e) {
            //System.out.println("");
        }
        boolean isNameValid = categories.stream().anyMatch(category -> arg.toLowerCase().equals(category.getCategory().toLowerCase()));

        if(isIDValid.get() || isNameValid) {
            AtomicReference<BigDecimal> categoriesBigDecimal = new AtomicReference<>(BigDecimal.ZERO);
            boolean finalIsIDValid = isIDValid.get();
            AtomicBoolean isSet = new AtomicBoolean(false);
            categories.forEach(category -> {
                if(isSet.get()) return;
                if(finalIsIDValid) {
                    if(category.getCategoryID().equals(catID.get())) {
                        categoriesBigDecimal.set(category.getCategoryID());
                        isSet.set(true);
                    }
                } else {
                    if(category.getCategory().toLowerCase().equals(arg.toLowerCase())) {
                        categoriesBigDecimal.set(category.getCategoryID());
                        isSet.set(true);
                    }
                }
            });
            isSet.set(false);
            List<RecipeList> foundCategoriesList = new ArrayList<>();
            List<RecipeList> recipeList = ConfigInstance.recipeList;
            recipeList.forEach(recipe -> {
                boolean contains = recipe.getCategories().contains(categoriesBigDecimal.get());
                if(contains) foundCategoriesList.add(new RecipeList(recipe.getRecipeID(), recipe.getRecipeName(), recipe.getRecipeCalories(), recipe.getRecipeCarbs(), recipe.getRecipeProtein(), recipe.getIngredients(), recipe.getAmount(), recipe.getAllergens(), recipe.getCategories()));
            });
            if(foundCategoriesList.isEmpty()) {
                System.out.println("No Recipes with this Category.");
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
