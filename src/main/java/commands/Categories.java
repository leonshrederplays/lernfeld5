package commands;

import constructors.AllergensList;
import constructors.CategoriesList;
import instances.ConfigInstance;

import java.math.BigDecimal;
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
        int id = 0;
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
            int finalId1 = id;
            AtomicReference<BigDecimal> categoryBigDecimal = new AtomicReference<>(BigDecimal.ZERO);
            categories.forEach(category -> {
                if(new BigDecimal(finalId1).equals(category.getCategoryID())) {
                    categoryBigDecimal.set(category.getCategoryID());
                }
            });

        }
    }

}
