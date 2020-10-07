package commands;

import constructors.CategoriesList;
import constructors.OrderList;
import instances.ConfigInstance;

import java.util.List;

public class Categories {

    public static void categories() {
        List<CategoriesList> list = ConfigInstance.categoriesList;
        list.forEach(category -> {
            String str ="CategoryID: " + category.getCategoryID()
                    + " Category: " + category.getCategory();
            System.out.println(str);
        });
    }

}
