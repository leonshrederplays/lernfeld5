package commands;

import constructors.AllergensList;
import instances.ConfigInstance;

import java.math.BigDecimal;
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
        int id = 0;
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
            int finalId1 = id;
            AtomicReference<BigDecimal> allergenBigDecimal = new AtomicReference<>(BigDecimal.ZERO);
            allergens.forEach(allergen -> {
                if(new BigDecimal(finalId1).equals(allergen.getAllergenID())) {
                    allergenBigDecimal.set(allergen.getAllergenID());
                }
            });

        }
    }

}
