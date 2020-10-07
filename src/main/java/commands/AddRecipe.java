package commands;

import constructors.IngredientList;
import instances.ConfigInstance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AddRecipe {

    private static List<BigDecimal> verifiedIngredients = new ArrayList<>();
    private static List<Integer> amount = new ArrayList<>();
    private static BigDecimal calories;
    private static List<BigDecimal> carbohydrates = new ArrayList<>();
    private static List<BigDecimal> protein = new ArrayList<>();
    private static List<BigDecimal> price = new ArrayList<>();
    private static List<String> notPresent = new ArrayList<>();

    public static void addRecipe() {
        System.out.println("You typed addrecipe so now we will create a recipe lets start.");
        System.out.println("First name your Recipe. Type cancel in any step to cancel the Command. (Please notice that when you pass a space you cannot search for its name use a - or a _ then)");
        Scanner nameScanner = new Scanner(System.in);
        List<String> recipeName = new ArrayList<>();
        recipeName.addAll(Arrays.asList(nameScanner.nextLine().split(" ")));
        if(recipeName.get(0).toLowerCase().equals("cancel")) {
            System.out.println("Canceling addrecipe Command...");
            return;
        }
        addIngredients();
    }

    public static void addIngredients() {
        List<String> list = new ArrayList<>();
        List<String> emptyList = new ArrayList<>();
        Scanner ingredientScanner = new Scanner(System.in);
        Ingredients.ingredient();
        if(!notPresent.isEmpty()) {
            System.out.println("These values were invalid: " + String.join(", ", notPresent) + " please try again.");
            notPresent.clear();
        }
        System.out.println("Next add ingredients space seperated above is a list. (Best if you use ID but you can also use names)");
        list.addAll(Arrays.asList(ingredientScanner.nextLine().split(" ")));
        if(list.get(0).toLowerCase().equals("cancel")) {
            System.out.println("Canceling addrecipe Command...");
            return;
        }
        System.out.println("I gotta verify your ingredients...");
        boolean verifiedIngreds = verifyIngredients(list);
        if(verifiedIngreds) {
            List<String> logString = new ArrayList<>();
            verifiedIngredients.forEach(number -> logString.add(number.toString()));
            //System.out.println(String.join(", ", logString));
            Scanner amountScanner = new Scanner(System.in);
            List<String> amountTester = new ArrayList<>();
            amountTester.addAll(Arrays.asList(amountScanner.nextLine().split(" ")));
            if(amountTester.get(0).equals("cancel")) {
                System.out.println("Canceling addrecipe Command...");
                return;
            }
            boolean verifiedAmount = verifyAmount(amountTester);
        } else {
            addIngredients();
        }
    }

    public static boolean verifyIngredients(List<String> ingredients) {
        List<IngredientList> list = ConfigInstance.ingredientList;

        ingredients.forEach(ingredient -> {
            int id = 0;
            try {
                id = Integer.parseInt(ingredient);
            } catch (NumberFormatException e) { }
            int finalId = id;
            boolean isIDValid = list.stream().filter(listIngred -> new BigDecimal(finalId) == listIngred.getIngredientID()).findAny().isPresent();
            boolean isNameValid = list.stream().filter(listIngred -> ingredient.toLowerCase().equals(listIngred.getIngredientName().toLowerCase())).findAny().isPresent();

            //boolean isPresent = list.stream().filter(listIngred -> ingredient.equals(listIngred.getIngredientID()) || ingredient.equals(listIngred.getIngredientName())).findAny().isPresent();
            if(isIDValid) {
                verifiedIngredients.add(new BigDecimal(finalId));
            } else if(isNameValid) {
                list.stream().filter(listIngred -> ingredient.toLowerCase().equals(listIngred.getIngredientName().toLowerCase())).findAny().ifPresent(ingred -> {
                    verifiedIngredients.add(ingred.getIngredientID());
                    calories = ingred.getCalorie();
                    carbohydrates.add(new BigDecimal(String.valueOf(ingred.getCarbohydrates())));
                    protein.add(ingred.getProtein());
                });
            } else {
                notPresent.add(ingredient);
            }
        });

        if(notPresent.isEmpty()) {
            System.out.println("Ingredients are valid continuing");
            return true;
        }
        return false;
    }

    public static boolean verifyAmount(List<String> amountTest) {
        return false;
    }

}
