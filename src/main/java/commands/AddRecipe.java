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
    private static List<String> verifiedIngredientNames = new ArrayList<>();
    private static List<Integer> amount = new ArrayList<>();
    private static List<BigDecimal> calories = new ArrayList<>();
    private static List<BigDecimal> carbohydrates = new ArrayList<>();
    private static List<BigDecimal> protein = new ArrayList<>();
    private static List<BigDecimal> price = new ArrayList<>();
    private static List<String> notPresent = new ArrayList<>();
    private static boolean wrongAmount = false;

    public static void addRecipe() {
        System.out.println("You typed addrecipe so now we will create a recipe lets start.");
        System.out.println("First name your Recipe. Type cancel at any time to exit the Command. (Please notice that when you pass a space you cannot search for its name use a - or a _ then)");
        Scanner nameScanner = new Scanner(System.in);
        List<String> recipeName = new ArrayList<>();
        recipeName.addAll(Arrays.asList(nameScanner.nextLine().split(" ")));
        if (recipeName.get(0).toLowerCase().equals("cancel")) {
            System.out.println("Canceling addrecipe Command...");
            verifiedIngredientNames.clear();
            verifiedIngredients.clear();
            amount.clear();
            calories.clear();
            carbohydrates.clear();
            protein.clear();
            price.clear();
            wrongAmount = false;
            notPresent.clear();
            return;
        }
        addIngredients();
    }

    public static void addIngredients() {
        List<String> list = new ArrayList<>();
        Scanner ingredientScanner = new Scanner(System.in);
        Ingredients.ingredient();
        if (!notPresent.isEmpty()) {
            System.out.println("These values were invalid: " + String.join(", ", notPresent) + " please try again.");
            notPresent.clear();
        }
        System.out.println("Next add ingredients space seperated above is a list. (Best if you use ID but you can also use names)");
        list.addAll(Arrays.asList(ingredientScanner.nextLine().split(" ")));
        if (list.get(0).toLowerCase().equals("cancel")) {
            System.out.println("Canceling addrecipe Command...");
            verifiedIngredientNames.clear();
            verifiedIngredients.clear();
            amount.clear();
            calories.clear();
            carbohydrates.clear();
            protein.clear();
            price.clear();
            wrongAmount = false;
            notPresent.clear();
            return;
        }
        System.out.println("I gotta verify your ingredients...");
        boolean verifiedIngreds = verifyIngredients(list);
        if (verifiedIngreds) {
            List<String> logString = new ArrayList<>();
            verifiedIngredients.forEach(number -> logString.add(number.toString()));
            //System.out.println(String.join(", ", logString));
            addAmount();
        } else {
            addIngredients();
        }
    }

    public static void addAmount() {
        List<String> list = new ArrayList<>();
        List<String> logString = new ArrayList<>();
        if (wrongAmount) {
            System.out.println("You must pass all amounts for every ingredient only Integers allowed! please try again.");
            wrongAmount = false;
        }
        verifiedIngredients.forEach(number -> logString.add(number.toString()));
        //System.out.println(String.join(", ", logString));
        Scanner amountScanner = new Scanner(System.in);
        List<String> amountTester = new ArrayList<>();
        System.out.println("Next pass the amount of ingredients in the order of the ingredients you typed space seperated");
        System.out.println("One amount for these: " + String.join(", ", verifiedIngredientNames));
        amountTester.addAll(Arrays.asList(amountScanner.nextLine().split(" ")));
        if (amountTester.get(0).equals("cancel")) {
            System.out.println("Canceling addrecipe Command...");
            verifiedIngredientNames.clear();
            verifiedIngredients.clear();
            amount.clear();
            calories.clear();
            carbohydrates.clear();
            protein.clear();
            price.clear();
            wrongAmount = false;
            notPresent.clear();
            return;
        }
        boolean verifiedAmount = verifyAmount(amountTester);
        if (verifiedAmount) {
            System.out.println("HELLO WORLD");
        } else {
            addAmount();
        }
    }

    public static boolean verifyIngredients(List<String> ingredients) {
        List<IngredientList> list = ConfigInstance.ingredientList;

        ingredients.forEach(ingredient -> {
            int id = 0;
            try {
                id = Integer.parseInt(ingredient);
            } catch (NumberFormatException e) {
            }
            int finalId = id;
            boolean isIDValid = list.stream().filter(listIngred -> new BigDecimal(finalId).equals(listIngred.getIngredientID())).findAny().isPresent();
            boolean isNameValid = list.stream().filter(listIngred -> ingredient.toLowerCase().equals(listIngred.getIngredientName().toLowerCase())).findAny().isPresent();

            //boolean isPresent = list.stream().filter(listIngred -> ingredient.equals(listIngred.getIngredientID()) || ingredient.equals(listIngred.getIngredientName())).findAny().isPresent();
            if (isIDValid) {
                verifiedIngredients.add(new BigDecimal(finalId));
                list.stream().filter(listIngred -> new BigDecimal(finalId).equals(listIngred.getIngredientID())).findAny().ifPresent(ingred -> {
                    verifiedIngredientNames.add(ingred.getIngredientName());
                    calories.add(ingred.getCalorie());
                    carbohydrates.add(ingred.getCarbohydrates());
                    protein.add(ingred.getProtein());
                    price.add(ingred.getNettoprice());
                });
            } else if (isNameValid) {
                list.stream().filter(listIngred -> ingredient.toLowerCase().equals(listIngred.getIngredientName().toLowerCase())).findAny().ifPresent(ingred -> {
                    verifiedIngredients.add(ingred.getIngredientID());
                    verifiedIngredientNames.add(ingred.getIngredientName());
                    calories.add(ingred.getCalorie());
                    carbohydrates.add(ingred.getCarbohydrates());
                    protein.add(ingred.getProtein());
                    price.add(ingred.getNettoprice());
                });
            } else {
                notPresent.add(ingredient);
            }
        });

        if (notPresent.isEmpty()) {
            System.out.println("Ingredients are valid continuing");
            return true;
        }
        return false;
    }

    public static boolean verifyAmount(List<String> amountTest) {

        if (amountTest.size() == verifiedIngredients.size()) {
            int amo = 0;
            try {
                amountTest.forEach(am -> amount.add(Integer.parseInt(am)));
                System.out.println("Amounts are valid continuing.");
                return true;
            } catch (NumberFormatException e) {
                wrongAmount = true;
                return false;
            }
        }
            return false;
    }

}
