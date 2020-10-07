package commands;

import constructors.AllergensList;
import constructors.CategoriesList;
import constructors.IngredientList;
import instances.ConfigInstance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AddRecipe {

    private static List<BigDecimal> verifiedIngredients = new ArrayList<>();
    private static List<String> verifiedIngredientNames = new ArrayList<>();
    private static List<Integer> verifiedAmount = new ArrayList<>();
    private static List<BigDecimal> verifiedCategoryID = new ArrayList<>();
    private static List<String> verifiedCategoryName = new ArrayList<>();
    private static List<BigDecimal> verifiedAllergenID = new ArrayList<>();
    private static List<String> verifiedAllergenName = new ArrayList<>();
    private static List<String> recipeName = new ArrayList<>();
    private static List<BigDecimal> calories = new ArrayList<>();
    private static List<BigDecimal> carbohydrates = new ArrayList<>();
    private static List<BigDecimal> protein = new ArrayList<>();
    private static List<BigDecimal> price = new ArrayList<>();
    private static List<String> notPresent = new ArrayList<>();
    private static List<String> noCategory = new ArrayList<>();
    private static List<String> noAllergen = new ArrayList<>();
    private static boolean wrongAmount = false;
    private static boolean wrongCategory = false;
    private static boolean wrongAllergen = false;

    public static void addRecipe() {
        System.out.println("You typed addrecipe so now we will create a recipe lets start.");
        System.out.println("First name your Recipe. Type cancel at any time to exit the Command. (Please notice that when you pass a space you cannot search for its name use a - or a _ then)");
        Scanner nameScanner = new Scanner(System.in);
        recipeName.addAll(Arrays.asList(nameScanner.nextLine().split(" ")));
        if (recipeName.get(0).toLowerCase().equals("cancel")) {
            System.out.println("Canceling addrecipe Command...");
            clearData();
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
            verifiedIngredientNames.clear();
            verifiedIngredients.clear();
            calories.clear();
            carbohydrates.clear();
            protein.clear();
            price.clear();
        }
        System.out.println("Next add ingredients space seperated above is a list. (Best if you use ID but you can also use names)");
        list.addAll(Arrays.asList(ingredientScanner.nextLine().split(" ")));
        if (list.get(0).toLowerCase().equals("cancel")) {
            System.out.println("Canceling addrecipe Command...");
            verifiedIngredientNames.clear();
            verifiedIngredients.clear();
            verifiedAmount.clear();
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

    public static boolean verifyIngredients(List<String> ingredients) {
        List<IngredientList> list = ConfigInstance.ingredientList;
        List<BigDecimal> existingIngred = new ArrayList<>();
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
                list.stream().filter(listIngred -> new BigDecimal(finalId).equals(listIngred.getIngredientID())).findAny().ifPresent(ingred -> {
                    if(!existingIngred.contains(ingred.getIngredientID())) {
                        verifiedIngredients.add(ingred.getIngredientID());
                        verifiedIngredientNames.add(ingred.getIngredientName());
                        calories.add(ingred.getCalorie());
                        carbohydrates.add(ingred.getCarbohydrates());
                        protein.add(ingred.getProtein());
                        price.add(ingred.getNettoprice());
                        existingIngred.add(ingred.getIngredientID());
                    }
                });
            } else if (isNameValid) {
                list.stream().filter(listIngred -> ingredient.toLowerCase().equals(listIngred.getIngredientName().toLowerCase())).findAny().ifPresent(ingred -> {
                    if(!existingIngred.contains(ingred.getIngredientID())) {
                        verifiedIngredients.add(ingred.getIngredientID());
                        verifiedIngredientNames.add(ingred.getIngredientName());
                        calories.add(ingred.getCalorie());
                        carbohydrates.add(ingred.getCarbohydrates());
                        protein.add(ingred.getProtein());
                        price.add(ingred.getNettoprice());
                        existingIngred.add(ingred.getIngredientID());
                    }
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

    public static void addAmount() {
        List<String> logString = new ArrayList<>();
        if (wrongAmount) {
            System.out.println("You must pass all amounts for every ingredient only Integers allowed! please try again.");
            wrongAmount = false;
            verifiedAmount.clear();
        }
        Scanner amountScanner = new Scanner(System.in);
        List<String> amountTester = new ArrayList<>();
        System.out.println("Next pass the amount of ingredients in the order of the ingredients you typed space seperated");
        System.out.println("One amount for these: " + String.join(", ", verifiedIngredientNames));
        amountTester.addAll(Arrays.asList(amountScanner.nextLine().split(" ")));
        if (amountTester.get(0).equals("cancel")) {
            System.out.println("Canceling addrecipe Command...");
            clearData();
            return;
        }
        System.out.println("I gotta verify your amount...");
        boolean verifiedAmountBool = verifyAmount(amountTester);
        if (verifiedAmountBool) {
            verifiedAmount.forEach(number -> logString.add(number.toString()));
            //System.out.println(String.join(", ", logString));
            addCategories();
        } else {
            addAmount();
        }
    }

    public static boolean verifyAmount(List<String> amountTest) {

        if (amountTest.size() == verifiedIngredients.size()) {
            int amo = 0;
            try {
                amountTest.forEach(am -> verifiedAmount.add(Integer.parseInt(am)));
                System.out.println("Amounts are valid continuing.");
                return true;
            } catch (NumberFormatException e) {
                wrongAmount = true;
                return false;
            }
        }
        return false;
    }

    public static void addCategories() {
        List<String> logString = new ArrayList<>();
        Categories.categories();
        if (!noCategory.isEmpty()) {
            System.out.println("The Categorie(s): " + String.join(", ", noCategory) + " do not exist. please try again");
            verifiedCategoryID.clear();
            verifiedCategoryName.clear();
            noCategory.clear();
        }
        Scanner categoryScanner = new Scanner(System.in);
        List<String> categoryTester = new ArrayList<>();
        System.out.println("Next pass the categories of the recipe. categories are listed above. space seperated");
        categoryTester.addAll(Arrays.asList(categoryScanner.nextLine().split(" ")));
        if (categoryTester.get(0).equals("cancel")) {
            System.out.println("Canceling addrecipe Command...");
            clearData();
        }
        System.out.println("I gotta verify your categories...");
        boolean verifiedCategoryBool = verifyCategories(categoryTester);
        if (verifiedCategoryBool) {
            verifiedCategoryID.forEach(number -> logString.add(number.toString()));
            //System.out.println(String.join(", ", logString));
            addAllergens();
        } else {
            addCategories();
        }
    }

    public static boolean verifyCategories(List<String> categoryTest) {
        List<CategoriesList> list = ConfigInstance.categoriesList;
        List<BigDecimal> existingCategory = new ArrayList<>();
        categoryTest.forEach(category -> {
            int id = 0;
            try {
                id = Integer.parseInt(category);
            } catch (NumberFormatException e) {
            }
            int finalId = id;
            boolean isIDValid = list.stream().filter(listCateg -> new BigDecimal(finalId).equals(listCateg.getCategoryID())).findAny().isPresent();
            boolean isNameValid = list.stream().filter(listCateg -> category.toLowerCase().equals(listCateg.getCategory().toLowerCase())).findAny().isPresent();

            //boolean isPresent = list.stream().filter(listIngred -> ingredient.equals(listIngred.getIngredientID()) || ingredient.equals(listIngred.getIngredientName())).findAny().isPresent();
            if (isIDValid) {
                list.stream().filter(listCateg -> new BigDecimal(finalId).equals(listCateg.getCategoryID())).findAny().ifPresent(categ -> {
                    if(!existingCategory.contains(categ.getCategoryID())) {
                        verifiedCategoryID.add(categ.getCategoryID());
                        verifiedCategoryName.add(categ.getCategory());
                        existingCategory.add(categ.getCategoryID());
                    }
                });
            } else if (isNameValid) {
                list.stream().filter(listCateg -> category.toLowerCase().equals(listCateg.getCategory().toLowerCase())).findAny().ifPresent(categ -> {
                    if(!existingCategory.contains(categ.getCategoryID())) {
                        verifiedCategoryID.add(categ.getCategoryID());
                        verifiedCategoryName.add(categ.getCategory());
                        existingCategory.add(categ.getCategoryID());
                    }
                });
            } else {
                noCategory.add(category);
            }
        });

        if(noCategory.isEmpty()) {
            return true;
        }

        return false;
    }

    public static void addAllergens() {
        List<String> logString = new ArrayList<>();
        Allergens.allergens();
        if (!noAllergen.isEmpty()) {
            System.out.println("The Allergen(s): " + String.join(", ", noAllergen) + " do not exist. please try again");
            verifiedAllergenID.clear();
            verifiedAllergenName.clear();
            noAllergen.clear();
        }
        Scanner allergenScanner = new Scanner(System.in);
        List<String> allergenTester = new ArrayList<>();
        System.out.println("Next pass the allergens of the recipe. allergens are listed above. space seperated");
        allergenTester.addAll(Arrays.asList(allergenScanner.nextLine().split(" ")));
        if (allergenTester.get(0).equals("cancel")) {
            System.out.println("Canceling addrecipe Command...");
            clearData();
        }
        System.out.println("I gotta verify your allergens...");
        boolean verifiedAllergenBool = verifyAllergens(allergenTester);
        if (verifiedAllergenBool) {
            verifiedAllergenID.forEach(number -> logString.add(number.toString()));
            System.out.println(String.join(", ", logString));
            previewRecipe();
        } else {
            addAllergens();
        }
    }

    public static boolean verifyAllergens(List<String> allergensTest) {

        List<AllergensList> list = ConfigInstance.allergensList;
        List<BigDecimal> existingAllergen = new ArrayList<>();
        allergensTest.forEach(allergen -> {
            int id = 0;
            try {
                id = Integer.parseInt(allergen);
            } catch (NumberFormatException e) {
            }
            int finalId = id;
            boolean isIDValid = list.stream().filter( listAllerg -> new BigDecimal(finalId).equals( listAllerg.getAllergenID())).findAny().isPresent();
            boolean isNameValid = list.stream().filter( listAllerg -> allergen.toLowerCase().equals( listAllerg.getAllergen().toLowerCase())).findAny().isPresent();

            //boolean isPresent = list.stream().filter(listIngred -> ingredient.equals(listIngred.getIngredientID()) || ingredient.equals(listIngred.getIngredientName())).findAny().isPresent();
            if (isIDValid) {
                list.stream().filter( listAllerg -> new BigDecimal(finalId).equals( listAllerg.getAllergenID())).findAny().ifPresent(alleg -> {
                    if(!existingAllergen.contains(alleg.getAllergenID())) {
                        verifiedAllergenID.add(alleg.getAllergenID());
                        verifiedAllergenName.add(alleg.getAllergen());
                        existingAllergen.add(alleg.getAllergenID());
                    }
                });
            } else if (isNameValid) {
                list.stream().filter( listAllerg -> allergen.toLowerCase().equals( listAllerg.getAllergen().toLowerCase())).findAny().ifPresent(alleg -> {
                    if(!existingAllergen.contains(alleg.getAllergenID())) {
                        verifiedAllergenID.add(alleg.getAllergenID());
                        verifiedAllergenName.add(alleg.getAllergen());
                        existingAllergen.add(alleg.getAllergenID());
                    }
                });
            } else {
                noAllergen.add(allergen);
            }
        });

        if(noAllergen.isEmpty()) {
            return true;
        }

        return false;
    }

    public static void previewRecipe() {
        System.out.println("As example i will show you what you will see when executing recipe <name / or id>:");
        AtomicInteger i = new AtomicInteger();
        BigDecimal caloriesMath = BigDecimal.ZERO;
        calories.forEach(calorie -> {
            caloriesMath.add(calorie.multiply(new BigDecimal(verifiedAmount.get(i.get())))).setScale(2, RoundingMode.HALF_EVEN);
            i.getAndIncrement();
        });
        i.set(0);
        BigDecimal carbohydratesMath = BigDecimal.ZERO;
        carbohydrates.forEach(carbo -> {
            carbohydratesMath.add(carbo.multiply(new BigDecimal(verifiedAmount.get(i.get())))).setScale(2, RoundingMode.HALF_EVEN);
            i.getAndIncrement();
        });
        i.set(0);

        BigDecimal proteinMath = BigDecimal.ZERO;
        protein.forEach(prot -> {
            proteinMath.add(prot.multiply(new BigDecimal(verifiedAmount.get(i.get())))).setScale(2, RoundingMode.HALF_EVEN);
        });
        i.set(0);

        BigDecimal priceMath = BigDecimal.ZERO;
        price.forEach(pri -> {
            priceMath.add(pri.multiply(new BigDecimal(verifiedAmount.get(i.get())))).setScale(2, RoundingMode.HALF_EVEN);
        });
        i.set(0);
        String str =
                "Properties of this Recipe: "
                        + "\nID: SOME ID"
                        + " | Name: " + String.join(" ", recipeName)
                        + "\nCalories: " + caloriesMath
                        + "\nCarbohydrates: " + carbohydratesMath
                        + "\nProtein: " + proteinMath
                        + "\nIngredients: " + String.join(", ", verifiedIngredientNames)
                        + "\nPrice: " + priceMath +"$"
                        + "\nAllergens: " + String.join(", ", verifiedAllergenName)
                        + "\nCategroies: " + String.join(", ", verifiedCategoryName);
        System.out.println(str);
        System.out.println("");
        saveWork();
    }

    public static void saveWork() {
        System.out.println("Do you want to save changes? (Y/N)");
        Scanner saveScanner = new Scanner(System.in);
        String save = saveScanner.next();
        if(save.toLowerCase().equals("y")) {
            System.out.println("ACCEPTED");
        } else if(save.toLowerCase().equals("n")) {
            System.out.println("Aborting addRecipe Command...");
            clearData();
        }
    }

    public static void clearData() {
        recipeName.clear();
        verifiedIngredientNames.clear();
        verifiedIngredients.clear();
        verifiedAmount.clear();
        verifiedAllergenID.clear();
        verifiedAllergenID.clear();
        verifiedCategoryID.clear();
        verifiedCategoryName.clear();
        calories.clear();
        carbohydrates.clear();
        protein.clear();
        price.clear();
        wrongAmount = false;
        wrongCategory = false;
        wrongAllergen = false;
        notPresent.clear();
        noCategory.clear();
        noAllergen.clear();
    }

}
