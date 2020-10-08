package commands;

import constructors.AllergensList;
import constructors.CategoriesList;
import constructors.IngredientList;
import instances.ConfigInstance;
import utils.DBUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class AddRecipe {

    private List<BigDecimal> verifiedIngredients = new ArrayList<>();
    private List<String> verifiedIngredientNames = new ArrayList<>();
    private List<Integer> verifiedAmount = new ArrayList<>();
    private List<BigDecimal> verifiedCategoryID = new ArrayList<>();
    private List<String> verifiedCategoryName = new ArrayList<>();
    private List<BigDecimal> verifiedAllergenID = new ArrayList<>();
    private List<String> verifiedAllergenName = new ArrayList<>();
    private List<String> recipeName = new ArrayList<>();
    private List<BigDecimal> calories = new ArrayList<>();
    private List<BigDecimal> carbohydrates = new ArrayList<>();
    private List<BigDecimal> protein = new ArrayList<>();
    private List<BigDecimal> price = new ArrayList<>();
    private List<String> notPresent = new ArrayList<>();
    private List<String> noCategory = new ArrayList<>();
    private List<String> noAllergen = new ArrayList<>();
    private boolean wrongAmount = false;
    private boolean wrongCategory = false;
    private boolean wrongAllergen = false;
    private boolean wrongName = false;

    public void addRecipeName() {
        System.out.println("First name your Recipe. (First char uppercase & min 3chars) Type cancel at any time to exit the Command. (Please notice that when you pass a space you cannot search for its name use a - or a _ then)");
        if (wrongName) {
            System.out.println("Notice that you need to make the first char a uppercase one. And have at least 3 chars as a name.");
            wrongName = false;
        }
        Scanner nameScanner = new Scanner(System.in);
        try {
            recipeName.addAll(Arrays.asList(nameScanner.nextLine().split(" ")));
            //nameScanner.close();
            if (recipeName.get(0).toLowerCase().equals("cancel")) {
                System.out.println("Canceling addrecipe Command...");

                return;
            }
        } catch (NoSuchElementException e) {
            System.out.println("Du musst einen Namen angeben!");
            recipeName.clear();
            addRecipeName();
        }
        boolean verifyName = verifyName(recipeName);
        if (verifyName) {
            addIngredients();
        } else {
            wrongName = true;
            recipeName.clear();
            addRecipeName();
        }

    }

    public boolean verifyName(List<String> list) {
        String length = String.join(" ", list);
        try {
            if (Character.isUpperCase(length.charAt(0)) && length.length() >= 3 && length.length() <= 30) return true;
        } catch (StringIndexOutOfBoundsException e) {
            System.out.println("You must provide an name!");
            return false;
        }

        return false;
    }

    public void addIngredients() {
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

    public boolean verifyIngredients(List<String> ingredients) {
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
                    if (!existingIngred.contains(ingred.getIngredientID())) {
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
                    if (!existingIngred.contains(ingred.getIngredientID())) {
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

    public void addAmount() {
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

    public boolean verifyAmount(List<String> amountTest) {

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

    public void addCategories() {
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

    public boolean verifyCategories(List<String> categoryTest) {
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
                    if (!existingCategory.contains(categ.getCategoryID())) {
                        verifiedCategoryID.add(categ.getCategoryID());
                        verifiedCategoryName.add(categ.getCategory());
                        existingCategory.add(categ.getCategoryID());
                    }
                });
            } else if (isNameValid) {
                list.stream().filter(listCateg -> category.toLowerCase().equals(listCateg.getCategory().toLowerCase())).findAny().ifPresent(categ -> {
                    if (!existingCategory.contains(categ.getCategoryID())) {
                        verifiedCategoryID.add(categ.getCategoryID());
                        verifiedCategoryName.add(categ.getCategory());
                        existingCategory.add(categ.getCategoryID());
                    }
                });
            } else {
                noCategory.add(category);
            }
        });

        if (noCategory.isEmpty()) {
            return true;
        }

        return false;
    }

    public void addAllergens() {
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

    public boolean verifyAllergens(List<String> allergensTest) {

        List<AllergensList> list = ConfigInstance.allergensList;
        List<BigDecimal> existingAllergen = new ArrayList<>();
        allergensTest.forEach(allergen -> {
            int id = 0;
            try {
                id = Integer.parseInt(allergen);
            } catch (NumberFormatException e) {
            }
            int finalId = id;
            boolean isIDValid = list.stream().filter(listAllerg -> new BigDecimal(finalId).equals(listAllerg.getAllergenID())).findAny().isPresent();
            boolean isNameValid = list.stream().filter(listAllerg -> allergen.toLowerCase().equals(listAllerg.getAllergen().toLowerCase())).findAny().isPresent();

            //boolean isPresent = list.stream().filter(listIngred -> ingredient.equals(listIngred.getIngredientID()) || ingredient.equals(listIngred.getIngredientName())).findAny().isPresent();
            if (isIDValid) {
                list.stream().filter(listAllerg -> new BigDecimal(finalId).equals(listAllerg.getAllergenID())).findAny().ifPresent(alleg -> {
                    if (!existingAllergen.contains(alleg.getAllergenID())) {
                        verifiedAllergenID.add(alleg.getAllergenID());
                        verifiedAllergenName.add(alleg.getAllergen());
                        existingAllergen.add(alleg.getAllergenID());
                    }
                });
            } else if (isNameValid) {
                list.stream().filter(listAllerg -> allergen.toLowerCase().equals(listAllerg.getAllergen().toLowerCase())).findAny().ifPresent(alleg -> {
                    if (!existingAllergen.contains(alleg.getAllergenID())) {
                        verifiedAllergenID.add(alleg.getAllergenID());
                        verifiedAllergenName.add(alleg.getAllergen());
                        existingAllergen.add(alleg.getAllergenID());
                    }
                });
            } else {
                noAllergen.add(allergen);
            }
        });

        if (noAllergen.isEmpty()) {
            return true;
        }

        return false;
    }

    public void previewRecipe() {
        System.out.println("As example i will show you what you will see when executing recipe <name / or id>:");
        AtomicInteger i = new AtomicInteger();
        AtomicReference<BigDecimal> caloriesMath = new AtomicReference<>(BigDecimal.ZERO);
        calories.forEach(calorie -> {
            caloriesMath.getAndUpdate(update -> update.add(calorie.multiply(new BigDecimal(verifiedAmount.get(i.get()))))).setScale(2, RoundingMode.HALF_EVEN);
            i.getAndIncrement();
        });
        i.set(0);
        AtomicReference<BigDecimal> carbohydratesMath = new AtomicReference<>(BigDecimal.ZERO);
        carbohydrates.forEach(carbo -> {
            carbohydratesMath.getAndUpdate(update -> update.add(carbo.multiply(new BigDecimal(verifiedAmount.get(i.get()))))).setScale(2, RoundingMode.HALF_EVEN);
            i.getAndIncrement();
        });
        i.set(0);

        AtomicReference<BigDecimal> proteinMath = new AtomicReference<>(BigDecimal.ZERO);
        protein.forEach(prot -> {
            proteinMath.getAndUpdate(update -> update.add(prot.multiply(new BigDecimal(verifiedAmount.get(i.get()))))).setScale(2, RoundingMode.HALF_EVEN);
        });
        i.set(0);

        AtomicReference<BigDecimal> priceMath = new AtomicReference<>(BigDecimal.ZERO);
        price.forEach(pri -> {
            priceMath.getAndUpdate(update -> update.add(pri.multiply(new BigDecimal(verifiedAmount.get(i.get()))))).setScale(2, RoundingMode.HALF_EVEN);
            i.getAndIncrement();
        });
        i.set(0);
        String recipeStringName = String.join(" ", recipeName);
        String str =
                "Properties of this Recipe: "
                        + "\nID: SOME ID"
                        + " | Name: " + recipeStringName
                        + "\nCalories: " + caloriesMath.get()
                        + "\nCarbohydrates: " + carbohydratesMath.get()
                        + "\nProtein: " + proteinMath.get()
                        + "\nIngredients: " + String.join(", ", verifiedIngredientNames)
                        + "\nPrice: " + priceMath.get() + "$"
                        + "\nAllergens: " + String.join(", ", verifiedAllergenName)
                        + "\nCategroies: " + String.join(", ", verifiedCategoryName);
        System.out.println(str);
        System.out.println("");
        saveWork(recipeStringName, caloriesMath.get(), carbohydratesMath.get(), proteinMath.get(), priceMath.get());
    }

    public void saveWork(String recipeString, BigDecimal calorie, BigDecimal carbo, BigDecimal prot, BigDecimal pri) {
        System.out.println("Do you want to save changes? (Y/N)");
        Scanner saveScanner = new Scanner(System.in);
        String save = saveScanner.next();
        if (save.toLowerCase().equals("y")) {
            System.out.println("I will now insert the Script into the Database.");
            DBUtils.insertRecipe(recipeString, calorie, carbo, prot, pri, verifiedIngredients, verifiedAllergenID, verifiedCategoryID, verifiedAmount);
        } else if (save.toLowerCase().equals("n")) {
            System.out.println("Aborting addRecipe Command...");
        }
    }
}
