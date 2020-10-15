package commands;

import constructors.OrderList;
import instances.ConfigInstance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Orders {

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
            list.stream().filter(orders -> new BigDecimal(id).equals(orders.getBESTELLNR())).findAny().ifPresentOrElse(order -> {
                List<BigDecimal> ingredList = order.getINGREDS();
                List<Integer> ingredAmountList = order.getINGREDAMOUNT();
                List<BigDecimal> recipeList = order.getRECIPES();
                List<Integer> recipeAmountList = order.getRECIPEAMOUNT();

                List<BigDecimal> ingredInvoice = new ArrayList<>();
                List<BigDecimal> recipeInvoice = new ArrayList<>();
                List<String> ingredienString = new ArrayList<>();
                AtomicInteger i = new AtomicInteger(0);
                if(!ingredList.isEmpty()) {
                    ingredList.forEach(ingredDecimal -> {
                        ConfigInstance.ingredientList.stream().filter(ingredientList -> ingredientList.getIngredientID().equals(ingredDecimal)).findAny().ifPresent(ingred -> {
                            if(ingred.getUnit().toLowerCase().equals("liter")) {
                                BigDecimal price = ingred.getNettoprice();
                                BigDecimal priceMath = price.multiply(new BigDecimal(ingredAmountList.get(i.get()))).setScale(2, RoundingMode.HALF_EVEN);
                                ingredienString.add(ingred.getIngredientName() + " " + ingredAmountList.get(i.get()) + "l" + " | " + priceMath + "$");
                                ingredInvoice.add(priceMath);
                                i.getAndIncrement();
                            } else {
                                BigDecimal price = ingred.getNettoprice();
                                BigDecimal priceMath = price.multiply(new BigDecimal(ingredAmountList.get(i.get()))).setScale(2, RoundingMode.HALF_EVEN);
                                ingredienString.add(ingred.getIngredientName() + " " + ingredAmountList.get(i.get()) + "x" + " | " + priceMath + "$");
                                ingredInvoice.add(priceMath);
                                i.getAndIncrement();
                            }
                        });
                    });
                }
                i.set(0);

                List<String> recipeString = new ArrayList<>();
                if(!recipeList.isEmpty()) {
                    recipeList.forEach(recipeDecimal -> ConfigInstance.recipeList.stream().filter(recipeList1 -> recipeList1.getRecipeID().equals(recipeDecimal)).findAny().ifPresent(recipe -> {
                        AtomicInteger o = new AtomicInteger();
                        List<BigDecimal> prices = new ArrayList<>();
                        AtomicReference<BigDecimal> price = new AtomicReference<>(BigDecimal.ZERO);
                        recipe.getIngredients().forEach(recipeIngred -> ConfigInstance.ingredientList.stream().filter(ingredFilter -> ingredFilter.getIngredientID().equals(recipeIngred)).findAny().ifPresent(ingred -> {
                            prices.add(ingred.getNettoprice().multiply(new BigDecimal(recipe.getAmount().get(o.get()))));
                            o.getAndIncrement();
                        }));
                        o.set(0);
                        prices.forEach(ingredPrice -> price.getAndUpdate(update -> update.add(ingredPrice)));
                        price.getAndUpdate(update -> update.multiply(new BigDecimal(recipeAmountList.get(i.get())))).setScale(2, RoundingMode.HALF_EVEN);
                        recipeString.add(recipe.getRecipeID() + " | " + recipe.getRecipeName() + " " + recipeAmountList.get(i.get()) + "x" + " | " + price);
                        recipeInvoice.add(price.get());
                        i.getAndIncrement();
                    }));
                }

                String str =
                        "Properties of this Order: "
                                + "\nOrdering-Number: " + order.getBESTELLNR()
                                + "\nCustomer-Number: " + order.getKUNDENNR()
                                + "\nOrder date: " + order.getBESTELLDATUM();
                String ingredProp = String.join("\n", ingredienString);
                String recipeProp = String.join("\n", recipeString);
                if (ingredProp.isEmpty()) ingredProp = "None";
                if (recipeProp.isEmpty()) recipeProp = "None";
                AtomicReference<BigDecimal> ingredInvoiceAmount = new AtomicReference<>(BigDecimal.ZERO);
                AtomicReference<BigDecimal> recipeInvoiceAmount = new AtomicReference<>(BigDecimal.ZERO);
                AtomicReference<BigDecimal> invoiceAmount = new AtomicReference<>(BigDecimal.ZERO);
                ingredInvoice.forEach(ingredInvoicePrice -> ingredInvoiceAmount.getAndUpdate(update -> update.add(ingredInvoicePrice)));
                recipeInvoice.forEach(recipeInvoicePrice -> recipeInvoiceAmount.getAndUpdate(update -> update.add(recipeInvoicePrice)));
                invoiceAmount.getAndUpdate(update -> update.add(ingredInvoiceAmount.get()).add(recipeInvoiceAmount.get()).setScale(2, RoundingMode.HALF_EVEN));
                System.out.println(str + "\nIngredients:\n" + ingredProp + "\nInvoice of Ingredients: " + ingredInvoiceAmount + "$" +  "\n\nRecipes:\n" + recipeProp + "\nInvoice of Recipes: " + recipeInvoiceAmount + "$" + "\n\nInvoice: " + invoiceAmount.get() + "$");
            }, () -> System.out.println("The order: " + arg + " does not exist. type orders to list all orders."));
        } catch (NumberFormatException e) {
            System.out.println();
            System.out.println("The order must be searched with the order number! to display all orders type orders.");
        }
    }
}
