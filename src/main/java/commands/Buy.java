package commands;

import constructors.IngredientList;
import instances.ConfigInstance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Buy {

    public void addToCart(boolean isIngredient, List<String> args) {

        List<Integer> amountList = new ArrayList<>();
        List<IngredientList> saveIngredientInfo = new ArrayList<>();
        System.out.println("Now pass a amount space seperated in the order you passed the ingredients / recipes.");
        System.out.println(String.join(", ", args));
        Scanner amountScanner = new Scanner(System.in);
        List<String> scannedString = Arrays.asList(amountScanner.nextLine().split(" "));

        List<IngredientList> ingreds = ConfigInstance.ingredientList;

        if(isIngredient) {
            System.out.println("Verifying ingredients");
            args.forEach(ingredientListItem -> {
                BigDecimal id = BigDecimal.ZERO;
                boolean isID = false;
                try {
                    id = new BigDecimal(Integer.parseInt(ingredientListItem));
                    BigDecimal finalId = id;
                    isID = ingreds.stream().anyMatch(ingredID -> ingredID.equals(finalId));
                } catch (NumberFormatException e) {
                    //System.out.println("");
                }
                boolean isName = ingreds.stream().anyMatch(ingredName -> ingredName.getIngredientName().toLowerCase().equals(ingredientListItem.toLowerCase()));
                if (isID) {
                    BigDecimal finalId1 = id;
                    ingreds.stream().filter(ingredID -> ingredID.getIngredientID().equals(finalId1)).findAny().ifPresent(ingred -> {
                        
                    });
                } else if (isName) {
                    ingreds.stream().filter(ingredName -> ingredName.getIngredientName().toLowerCase().equals(ingredientListItem.toLowerCase())).findAny().ifPresent(ingred -> {

                    });
                } else {
                    System.out.println("There was an error adding: " + ingredientListItem + " this ingredient dosent exist!");
                }
            });
        } else {
            System.out.println("Verifying recipes");
        }

    }

}
