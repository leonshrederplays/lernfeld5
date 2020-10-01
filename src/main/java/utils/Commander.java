package utils;

import com.sun.javafx.image.IntPixelGetter;
import constructors.CustomerList;
import constructors.IngredientList;
import constructors.OrderList;
import constructors.RecipeList;
import instances.ConfigInstance;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Commander {

    public DBUtils dbUtil = new DBUtils();

    public void helper(){
        System.out.print("Here is a list of available commands:\n" +
                "\n" +
                "help -- shows a list of all available commands\n" +
                "ingreds / ingreds <ingredient name/ingredient ID> -- list all ingredients / lists the details of one igredient\n" +
                "recipe / recipe <recipe name/recipe ID> -- lists all recipes / lists the details of one recipe\n" +
                "orders / orders <order ID> -- lists all orders / lists the details of one order\n" +
                "customer / customer <customer ID/customer lastname> -- lists all customers / lists the details of one customer\n" +
                "recreate -- recreates the Database --WARNING-- THIS WILL RESET ALL DATA TO ITS DEFAULT VALUES!\n" +
                "exit -- closes the program\n" +
                "\n\n");
    }

    public void ingredients(){
        List<IngredientList> list = ConfigInstance.ingredientList;
        list.forEach(ingredient -> {
            String str ="ID: " + ingredient.getIngredientID()
                    + " / Name: " + ingredient.getIngredientName();
                    /*+ "Einheit: " + ingredient.getUnit() + ", "
                    + "Nettopreis: " + ingredient.getNettoprice() + ", "
                    + "Bestand: " + ingredient.getAmount() + ", "
                    + "Lieferant: " + ingredient.getSupplierID() + ", "
                    + "" + ingredient.getCalorie() + ", "
                    + "" + ingredient.getCarbohydrates() + ", "
                    + "" + ingredient.getProtein();*/
            System.out.println(str);
        });
    }
    
    public void ingredientDescription(String arg) {
        List<IngredientList> list = ConfigInstance.ingredientList;
        try {
            int id = Integer.parseInt(arg);
            list.stream().filter(ingredient -> id == ingredient.getIngredientID()).findAny().ifPresentOrElse(ingredient -> {
                String str =
                        "Eigenschaften der Zutat: "
                                + "ID: " + ingredient.getIngredientID()
                                + " / Name: " + ingredient.getIngredientName()
                                + "\nEinheit: " + ingredient.getUnit() + ", "
                                + "\nNettopreis: " + ingredient.getNettoprice() + ", "
                                + "\nBestand: " + ingredient.getAmount() + ", "
                                + "\nLieferant: " + ingredient.getSupplierID() + ", "
                                + "\nKalorien: " + ingredient.getCalorie() + ", "
                                + "\nKohlenhydrate: " + ingredient.getCarbohydrates() + ", "
                                + "\nProtein: " + ingredient.getProtein();
                System.out.println(str);
            }, () -> {
                System.out.println("Die Zutat: " + arg + " existiert nicht. Gebe ingreds ein um alle Zutaten zu listen.");
            });
        } catch (NumberFormatException e) {
            list.stream().filter(ingredient -> arg.toLowerCase().equals(ingredient.getIngredientName().toLowerCase())).findAny().ifPresentOrElse(ingredient -> {
                String str =
                        "Eigenschaften der Zutat: "
                                + "ID: " + ingredient.getIngredientID()
                                + " / Name: " + ingredient.getIngredientName()
                                + "\nEinheit: " + ingredient.getUnit() + ", "
                                + "\nNettopreis: " + ingredient.getNettoprice() + ", "
                                + "\nBestand: " + ingredient.getAmount() + ", "
                                + "\nLieferant: " + ingredient.getSupplierID() + ", "
                                + "\nKalorien: " + ingredient.getCalorie() + ", "
                                + "\nKohlenhydrate: " + ingredient.getCarbohydrates() + ", "
                                + "\nProtein: " + ingredient.getProtein();
                System.out.println(str);
            }, () -> {
                System.out.println("Die Zutat: " + arg + " existiert nicht. Gebe ingreds ein um alle Zutaten zu listen.");
            });
        }
    }
    public void orders(){
        List<OrderList> list = ConfigInstance.orderList;
        list.forEach(orders -> {
            String str ="Bestellnummer: " + orders.getBESTELLNR()
                    + " Kundennummer: " + orders.getKUNDENNR();
            System.out.println(str);
        });
    }

    public void orderDescription(String arg) {
        List<OrderList> list = ConfigInstance.orderList;
        try {
            int id = Integer.parseInt(arg);
            list.stream().filter(orders -> id == orders.getBESTELLNR()).findAny().ifPresentOrElse(orders -> {
                String str =
                        "Eigenschaften der Bestellung: "
                                + "\nBestellnummer: " + orders.getBESTELLNR()
                                + "\nKundennummer: " + orders.getKUNDENNR()
                                + "\nBestelldatum: " + orders.getBESTELLDATUM()
                                + "\nRechnungsbetrag: " + orders.getRECHNUNGSBETRAG();

                System.out.println(str);
            }, () -> {
                System.out.println("Die Bestellung: " + arg + " existiert nicht. Gebe orders ein um alle Bestellungen zu listen.");
            });
        } catch (NumberFormatException e) {
            System.out.println("Die Bestellung muss mit der Bestellnummer gesucht werden um dir alle anzuzeigen gebe orders ein.");
        }
    }

    public void shutdown(){
        System.exit(0);
    }

    public void recipeDescription(String arg) {
        List<RecipeList> list = ConfigInstance.recipeList;
        try {
            int id = Integer.parseInt(arg);
            list.stream().filter(recipe -> id == recipe.getRecipeID()).findAny().ifPresentOrElse(recipe -> {
                String str =
                        "Eigenschaften des Rezepts: "
                                + "ID: " + recipe.getRecipeID()
                                + " / Name: " + recipe.getRecipeName()
                                + "\nKalorien: " + recipe.getRecipeCalories() + ", "
                                + "\nKohlenhydrate: " + recipe.getRecipeCarbs() + ", "
                                + "\nProtein: " + recipe.getRecipeProtein();
                System.out.println(str);
            }, () -> {
                System.out.println("Das Rezept: " + arg + " existiert nicht. Gebe recipe ein um alle Rezepte zu listen.");
            });
        } catch (NumberFormatException e) {
            list.stream().filter(recipe -> arg.toLowerCase().equals(recipe.getRecipeName().toLowerCase())).findAny().ifPresentOrElse(recipe -> {
                String str =
                        "Eigenschaften des Rezeptes: "
                                + "ID: " + recipe.getRecipeID()
                                + " / Name: " + recipe.getRecipeID()
                                + "\nKalorien: " + recipe.getRecipeCalories() + ", "
                                + "\nKohlenhydrate: " + recipe.getRecipeCarbs() + ", "
                                + "\nProtein: " + recipe.getRecipeProtein();
                System.out.println(str);
            }, () -> {
                System.out.println("Das Rezept: " + arg + " existiert nicht. Gebe recipe ein um alle Rezepte zu listen.");
            });
        }
    }

    public void recipe(){
        List<RecipeList> list = ConfigInstance.recipeList;
        list.forEach(recipe -> {
            String str ="ID: " + recipe.getRecipeID()
                    + " / Name: " + recipe.getRecipeName();
            System.out.println(str);
        });
    }
    public void customer(){
        List<CustomerList> list = ConfigInstance.customerList;
        list.forEach(customer -> {
            String str ="KundenNr: " + customer.getKUNDENNR()
                    + " / Nachname: " + customer.getNACHNAME()
                    + " Vorname: " + customer.getVORNAME();
            System.out.println(str);
        });
    }
    public void customerDescription(String arg) {
        List<CustomerList> list = ConfigInstance.customerList;
        try {
            int id = Integer.parseInt(arg);
            list.stream().filter(customer -> id == customer.getKUNDENNR()).findAny().ifPresentOrElse(customer -> {
                String str =
                        "Daten des Kunden: "
                                + "KundenNr: " + customer.getKUNDENNR()
                                + " / Nachname: " + customer.getNACHNAME()
                                + "\nVorname: " + customer.getVORNAME() + ", "
                                + "\nGeburtsdatum: " + customer.getGEBURTSDATUM() + ", "
                                + "\nStrasse: " + customer.getSTRASSE() + ", "
                                + "\nHausNr: " + customer.getHAUSNR() + ", "
                                + "\nPLZ: " + customer.getPLZ() + ", "
                                + "\nOrt: " + customer.getORT() + ", "
                                + "\nTelefon: " + customer.getTELEFON() + ", "
                                + "\nEmail: " + customer.getEMAIL();
                System.out.println(str);
            }, () -> {
                System.out.println("Der Kunde: " + arg + " existiert nicht. Gebe customer ein um alle Kunden zu listen.");
            });
        } catch (NumberFormatException e) {
            list.stream().filter(customer -> arg.toLowerCase().equals(customer.getNACHNAME().toLowerCase())).findAny().ifPresentOrElse(customer -> {
                String str =
                        "Daten des Kunden: "
                                + "KundenNr: " + customer.getKUNDENNR()
                                + " / Nachname: " + customer.getNACHNAME()
                                + "\nVorname: " + customer.getVORNAME() + ", "
                                + "\nGeburtsdatum: " + customer.getGEBURTSDATUM() + ", "
                                + "\nStrasse: " + customer.getSTRASSE() + ", "
                                + "\nHausNr: " + customer.getHAUSNR() + ", "
                                + "\nPLZ: " + customer.getPLZ() + ", "
                                + "\nOrt: " + customer.getORT() + ", "
                                + "\nTelefon: " + customer.getTELEFON() + ", "
                                + "\nEmail: " + customer.getEMAIL();
                System.out.println(str);
            }, () -> {
                System.out.println("Der Kunde: " + arg + " existiert nicht. Gebe customer ein um alle Kunden zu listen.");
            });
        }
    }
}
