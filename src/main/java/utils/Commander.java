package utils;

import constructors.CustomerList;
import constructors.IngredientList;
import constructors.OrderList;
import constructors.RecipeList;
import instances.ConfigInstance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Commander {

    public DBUtils dbUtil = new DBUtils();

    public void helper(){
        System.out.print("Here is a list of available commands:\n" +
                "\n" +
                "help -- shows a list of all available commands\n" +
                "ingreds / ingreds <ingredient name/ingredient ID> -- list all ingredients / lists the details of one igredient\n" +
                "recipe / recipe <recipe name/recipe ID> -- lists all recipes / lists the details of one recipe\n" +
                "orders / orders <order ID> -- lists all orders / lists the details of one order\n" +
                "customers / customers <customer ID/customer lastname firstname> -- lists all customers / lists the details of one customer\n" +
                "recreate -- recreates the Database --WARNING-- THIS WILL RESET ALL DATA TO ITS DEFAULT VALUES!\n" +
                "reload -- reloads the entire lists of ingredients, orders etc...\n" +
                "exit -- closes the program\n" +
                "\n\n");
    }

    // ===========================================================================
    // Ingredient Commands
    public void ingredients(){
        List<IngredientList> list = ConfigInstance.ingredientList;
        list.forEach(ingredient -> {
            String str ="ID: " + ingredient.getIngredientID()
                    + " | Name: " + ingredient.getIngredientName();
            System.out.println(str);
        });
    }

    public void ingredientDescription(String arg) {
        List<IngredientList> list = ConfigInstance.ingredientList;
        try {
            int id = Integer.parseInt(arg);
            list.stream().filter(ingredient -> id == ingredient.getIngredientID()).findAny().ifPresentOrElse(ingredient -> {
                String str =
                        "Properties of the Ingredient: "
                                + "ID: " + ingredient.getIngredientID()
                                + " | Name: " + ingredient.getIngredientName()
                                + "\nUnit: " + ingredient.getUnit() + ", "
                                + "\nNetprice: " + ingredient.getNettoprice() + ", "
                                + "\nIn Stock: " + ingredient.getAmount() + ", "
                                + "\nSupplier: " + ingredient.getSupplierID() + ", "
                                + "\nCalories: " + ingredient.getCalorie() + ", "
                                + "\nCarbohydrates: " + ingredient.getCarbohydrates() + ", "
                                + "\nProtein: " + ingredient.getProtein();
                System.out.println(str);
            }, () -> {
                System.out.println("The ingredient: " + arg + " does not exist. type ingreds to list all ingredients.");
            });
        } catch (NumberFormatException e) {
            list.stream().filter(ingredient -> arg.toLowerCase().equals(ingredient.getIngredientName().toLowerCase())).findAny().ifPresentOrElse(ingredient -> {
                String str =
                        "Properties of the Ingredient: "
                                + "ID: " + ingredient.getIngredientID()
                                + " | Name: " + ingredient.getIngredientName()
                                + "\nUnit: " + ingredient.getUnit() + ", "
                                + "\nNetprice: " + ingredient.getNettoprice() + ", "
                                + "\nIn Stock: " + ingredient.getAmount() + ", "
                                + "\nSupplier: " + ingredient.getSupplierID() + ", "
                                + "\nCalories: " + ingredient.getCalorie() + ", "
                                + "\nCarbohydrates: " + ingredient.getCarbohydrates() + ", "
                                + "\nProtein: " + ingredient.getProtein();
                System.out.println(str);
            }, () -> {
                System.out.println("The ingredient: " + arg + " does not exist. type ingreds to list all ingredients.");
            });
        }
    }

    // ===========================================================================
    // Order Commands
    public void orders(){
        List<OrderList> list = ConfigInstance.orderList;
        list.forEach(orders -> {
            String str ="Order-Number: " + orders.getBESTELLNR()
                    + " Customer-Number: " + orders.getKUNDENNR();
            System.out.println(str);
        });
    }

    public void orderDescription(String arg) {
        List<OrderList> list = ConfigInstance.orderList;
        try {
            int id = Integer.parseInt(arg);
            list.stream().filter(orders -> id == orders.getBESTELLNR()).findAny().ifPresentOrElse(orders -> {
                String str =
                        "Properties of this Order: "
                                + "\nOrdering-Number: " + orders.getBESTELLNR()
                                + "\nCustomer-Number: " + orders.getKUNDENNR()
                                + "\nOrder date: " + orders.getBESTELLDATUM()
                                + "\nInvoice amount: " + orders.getRECHNUNGSBETRAG();

                System.out.println(str);
            }, () -> {
                System.out.println("The order: " + arg + " does not exist. type orders to list all orders.");
            });
        } catch (NumberFormatException e) {
            System.out.println();
            System.out.println("The order must be searched with the order number! to display all orders type orders.");
        }
    }

    // ===========================================================================
    // Recipe Commands
    public void recipe(){
        List<RecipeList> list = ConfigInstance.recipeList;
        list.forEach(recipe -> {
            String str ="ID: " + recipe.getRecipeID()
                    + " | Name: " + recipe.getRecipeName();
            System.out.println(str);
        });
    }

    public void recipeDescription(String arg) {
        List<RecipeList> list = ConfigInstance.recipeList;
        int id = 0;
        boolean isIDValid = false;
        try {
            id = Integer.parseInt(arg);
            int finalId = id;
            isIDValid = list.stream().filter(recipe -> finalId == recipe.getRecipeID()).findAny().isPresent();
        } catch (NumberFormatException e) { }
        boolean isNameValid = list.stream().filter(recipe -> arg.toLowerCase().equals(recipe.getRecipeName().toLowerCase())).findAny().isPresent();

        if(isIDValid || isNameValid) {
            int finalId1 = id;
            list.stream().filter(recipe -> recipe.getRecipeID() == finalId1 || arg.toLowerCase().equals(recipe.getRecipeName().toLowerCase() )).findAny().ifPresentOrElse(recipe -> {
                List<String> ingredsList = new ArrayList<>();
                // Defining Atomics cuz in lambda this is needed.
                AtomicInteger ingredients = new AtomicInteger();
                AtomicInteger calories = new AtomicInteger();
                AtomicReference<BigDecimal> protein = new AtomicReference<>(BigDecimal.ZERO);
                AtomicReference<BigDecimal> carbohydrates = new AtomicReference<>(BigDecimal.ZERO);
                AtomicReference<BigDecimal> price = new AtomicReference<>(BigDecimal.ZERO);

                recipe.getIngredients().forEach(recipeIngredient -> {
                    // Search for the ingredient and do stuff.
                    ConfigInstance.ingredientList.stream().filter(ingredient -> recipeIngredient == ingredient.getIngredientID()).findAny().ifPresentOrElse(ingred -> {
                        // If ingredient unit is "Liter" hang an l on it else an x.
                        if(ingred.getUnit().equals("Liter")) {
                            // Add ingredient and amount to string list.
                            ingredsList.add(ingred.getIngredientName() + " " + recipe.getAmount().get(ingredients.get()) + "l");
                        } else {
                            // Add ingredient and amount to string list.
                            ingredsList.add(ingred.getIngredientName() + " " + recipe.getAmount().get(ingredients.get()) + "x");
                        }
                        // Define the amount of the ingredient.
                        BigDecimal amount = new BigDecimal(recipe.getAmount().get(ingredients.get()));
                        // Add calorien multiplied by amount to calories.
                        calories.getAndUpdate(update -> update + (ingred.getCalorie() * recipe.getAmount().get(ingredients.get())));
                        // Add protein multiplied by amount to protein.
                        protein.getAndUpdate(update -> update.add(new BigDecimal("" + ingred.getProtein()).multiply(amount).setScale(2, RoundingMode.HALF_EVEN)));
                        // Add carbohydrates multiplied by amount to carbohydrates.
                        carbohydrates.getAndUpdate(update -> update.add(new BigDecimal("" + ingred.getCarbohydrates()).multiply(amount).setScale(2, RoundingMode.HALF_EVEN)));
                        // Add price multiplied by amount to price.
                        price.getAndUpdate(update -> update.add(new BigDecimal("" + ingred.getNettoprice()).multiply(amount).setScale(2, RoundingMode.HALF_EVEN)));
                        // Increase ingredients by 1.
                        ingredients.getAndIncrement();
                    }, () -> {
                        System.out.println("Wait what there is an ingredient that is not defined!?");
                    });
                });
                // Make a String seperated by ", " from a list.
                String ingreds = String.join(", ", ingredsList);
                // Make a String seperated by ", " from a list.
                String allergens = String.join(", ", recipe.getAllergens());
                // Make a String seperated by ", " from a list.
                String categories = String.join(", ", recipe.getCategories());
                // Call the recipeDescBuilder to send recipe info.
                String str =
                        "Properties of this Recipe: "
                                + "ID: " + recipe.getRecipeID()
                                + " | Name: " + recipe.getRecipeName()
                                + "\nCalories: " + calories + ", "
                                + "\nCarbohydrates: " + carbohydrates + ", "
                                + "\nProtein: " + protein + ", "
                                + "\nIngredients: " + ingreds + ", "
                                + "\nPrice: " + price +"$" + ","
                                + "\nAllergens: " + allergens + ", "
                                + "\nCategroies: " + categories;
                System.out.println(str);
            }, () -> {

            });
        } else {
            System.out.println("The Recipe: " + arg + " does not exist. type recipe to list all recipes.");
        }
    }

    // ===========================================================================
    // Customer Commands
    public void customer(){
        List<CustomerList> list = ConfigInstance.customerList;
        list.forEach(customer -> {
            String str ="Customer-Number: " + customer.getKUNDENNR()
                    + " | Last name: " + customer.getNACHNAME()
                    + " First name: " + customer.getVORNAME();
            System.out.println(str);
        });
    }

    public void customerDescription(String[] arg, boolean isTwo) {
        List<CustomerList> list = ConfigInstance.customerList;
        if (isTwo) {
            list.stream().filter(customer -> arg[1].toLowerCase().equals(customer.getNACHNAME().toLowerCase())).findAny().ifPresentOrElse(customer -> {
                if(customer.getVORNAME().toLowerCase().equals(arg[2].toLowerCase())) {
                    String str =
                            "Data of Customer: "
                                    + "Customer-Number: " + customer.getKUNDENNR()
                                    + " | Last name: " + customer.getNACHNAME()
                                    + "\nFirst name: " + customer.getVORNAME() + ", "
                                    + "\nDate of birth: " + customer.getGEBURTSDATUM() + ", "
                                    + "\nStreet: " + customer.getSTRASSE() + ", "
                                    + "\nHouse-Number: " + customer.getHAUSNR() + ", "
                                    + "\nZIP-Code: " + customer.getPLZ() + ", "
                                    + "\nLocation: " + customer.getORT() + ", "
                                    + "\nPhone: " + customer.getTELEFON() + ", "
                                    + "\nE-Mail: " + customer.getEMAIL();
                    System.out.println(str);
                } else {
                    System.out.println("The Customer with that last name: " + arg[1] + " and first name: " + arg[2] + " does not exist. type customers to list all customers.");
                }
            }, () -> {
                System.out.println("The customer with that last name: " + arg[1] + " does not exist. type customers to list all customers.");
            });
        } else {
            try {
                int id = Integer.parseInt(arg[1]);
                list.stream().filter(customer -> id == customer.getKUNDENNR()).findAny().ifPresentOrElse(customer -> {
                    String str =
                            "Data of Customer: "
                                    + "Customer-Number: " + customer.getKUNDENNR()
                                    + " | Last name: " + customer.getNACHNAME()
                                    + "\nFirst name: " + customer.getVORNAME() + ", "
                                    + "\nDate of birth: " + customer.getGEBURTSDATUM() + ", "
                                    + "\nStreet: " + customer.getSTRASSE() + ", "
                                    + "\nHouse-Number: " + customer.getHAUSNR() + ", "
                                    + "\nZIP-Code: " + customer.getPLZ() + ", "
                                    + "\nLocation: " + customer.getORT() + ", "
                                    + "\nPhone: " + customer.getTELEFON() + ", "
                                    + "\nE-Mail: " + customer.getEMAIL();
                    System.out.println(str);
                }, () -> {
                    System.out.println("The Customer: " + arg[1] + " does not exist. type customers to list all customers.");
                });
            } catch (NumberFormatException e) {
                System.out.println("type help to see the usage of this command.");

            }
        }
        
    }

    // ===========================================================================
    // Recreate Command
    public void recreate() {
        Scanner recreateInput = new Scanner(System.in);
        System.out.println("This will reset the Database to its default state. Are you sure? (Y/N)");
        String confirm = recreateInput.next();
        if (confirm.equalsIgnoreCase("y")){
            dbUtil.recreateSQL();
            dbUtil.selectData();
            System.out.println("Database successfully recreated. Default values were restored.");
        } else if (confirm.equalsIgnoreCase("n")){
            System.out.println("Databse was not recreated.");
        }
    }
    // ===========================================================================
    // Reload Command
    public void reload() {
        try {
            dbUtil.selectData();
            System.out.println("Successfully reloaded all Lists");
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }
    // ===========================================================================
    // Shutdown Command
    public void shutdown(){
        System.out.println("Shutting down...");
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

}
