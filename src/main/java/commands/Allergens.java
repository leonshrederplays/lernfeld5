package commands;

import constructors.AllergensList;
import instances.ConfigInstance;

import java.util.List;

public class Allergens {

    public static void allergens() {
        List<AllergensList> list = ConfigInstance.allergensList;
        list.forEach(allergen -> {
            String str ="AllergenID: " + allergen.getAllergenID()
                    + " Allergen: " + allergen.getAllergen();
            System.out.println(str);
        });
    }

}
