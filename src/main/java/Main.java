import commands.Shutdown;
import commands.*;
import instances.ConfigInstance;
import utils.DBUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main /*extends Application*/ {

    //private Object Scene;

    public static void main(String[] args) {
        //Application.launch(args);
        // Define Filename
        File file = new File("config.ini");
        //dbUtils.error();
        try {
            // Try to create File and set DBInitialized to false
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
                // Open a FileWriter to write to this File
                FileWriter myWriter = new FileWriter("config.ini");
                // Connect without Database.
                DBUtils.connector("");
                // Create Database and test Data.
                DBUtils.createSQL();
                // Write DBInitialized true.
                myWriter.write("true");
                // CLose FileWriter.
                myWriter.close();
                // Get Boolean of DBInitialized.
                boolean finished = ConfigInstance.isSQLfinished;
                do {
                    // Loop till the Database and test Data got created.
                    if(finished) {
                        // Select all Data.
                        DBUtils.selectData();
                        break;
                    }
                } while(true);
            } else {
                System.out.println("File exists: " + file.getName());
                System.out.println(file.getAbsolutePath());
                Scanner reader = new Scanner(file);
                while (reader.hasNextLine()) {
                    String data = reader.nextLine();
                    if (data.equals("false")) {
                        DBUtils.connector("");
                        DBUtils.createSQL();
                        FileWriter newWriter = new FileWriter("config.ini");
                        newWriter.write("true");
                        newWriter.close();
                        boolean finished = ConfigInstance.isSQLfinished;
                        do {
                            if(finished) {
                                DBUtils.selectData();
                                break;
                            }
                        } while(true);
                    } else if (data.equals("true")) {
                        DBUtils.selectData();
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner input = new Scanner(System.in);
        //String[] command;
        List<String> argList = new ArrayList<>();
        String command;
        boolean loopBool = true;
        do {
            System.out.println("Enter the command you want to execute: ");
            argList.addAll(Arrays.asList(input.nextLine().split(" ")));
            if(argList.size() == 0) return;
            command = argList.get(0);
            argList.remove(0);
            //command = input.nextLine().split(" ");
            switch (command) {
                case "help" -> {
                    Help help = new Help();
                    help.helper();
                }
                case "ingreds" -> {
                    Ingredients ingred = new Ingredients();
                    try {
                        if (argList.size() == 1 && argList.get(0).toLowerCase().equals("unused")) {
                            ingred.ingredientUnused();
                        } else if (argList.size() == 1) {
                            ingred.ingredientDescription(argList.get(0));
                        } else {
                            ingred.ingredient();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(" ");
                }
                case "calories" -> {
                    try {
                        if (argList.size() == 1) {
                            int number = Integer.parseInt(argList.get(0));
                            DBUtils.selectAverageCalories(new BigDecimal(number));
                        }
                    } catch (NumberFormatException e){
                        System.out.println("please insert a number!!!!!!!");
                    }


                }
                case "recipe" -> {
                    Recipes recipe = new Recipes();
                    try {
                        if (argList.size() == 1) {
                            recipe.recipeDescription(argList.get(0));
                        } else if(argList.size() == 2 && argList.get(0).toLowerCase().equals("ingred")) {
                            argList.remove(0);
                            recipe.recipeWithIngredient(argList.get(0));
                        } else if(argList.size() == 2 && argList.get(0).toLowerCase().equals("amount")){
                            argList.remove(0);
                            recipe.recipeWithAmountOfIngreds(argList.get(0));
                        } else if(argList.size() == 2 && argList.get(0).toLowerCase().equals("calories")) {
                            argList.remove(0);
                            recipe.recipeCalories(argList.get(0));
                        } else {
                            recipe.recipe();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(" ");
                }
                case "addrecipe" -> {
                    AddRecipe addrecipe = new AddRecipe();
                    System.out.println("You typed addrecipe so now we will create a recipe lets start.");
                    addrecipe.addRecipeName();
                }
                case "orders" -> {
                    Orders order = new Orders();
                    try {
                        if (argList.size() == 1) {
                            order.orderDescription(argList.get(0));
                        } else {
                            order.orders();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(" ");
                }
                case "customers" -> {
                    Customers cust = new Customers();
                    cust.passwordManager(argList);
                    System.out.println(" ");
                }
                case "categories" -> {
                    Categories categ = new Categories();
                    try {
                        if (argList.size() == 1) {
                            categ.categoryRecipes(argList.get(0));
                        } else {
                            categ.categories();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case "allergens" -> {
                    Allergens allerg = new Allergens();
                    try {
                        if (argList.size() == 1) {
                            allerg.allergenRecipes(argList.get(0));
                        } else {
                            allerg.allergens();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case "buy" -> {
                    Buy buy = new Buy();
                    try {
                        if (argList.size() >= 2) {
                            if(argList.get(0).equals("ingred")) {
                                argList.remove(0);
                                buy.addToCart(true, argList);
                            } else if(argList.get(0).equals("recipe")) {
                                argList.remove(0);
                                buy.addToCart(false, argList);
                            }
                        } else {
                            System.out.println("You have to pass two arguments like buy <recipe / ingred> <recipeid or name / ingredid or name> you can pass multiple");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case "cart" -> {
                    for (int i = 0; i < argList.size(); i++) {
                        System.out.println(argList.get(i));
                    }
                }
                case "recreate" -> {
                    Recreate rec = new Recreate();
                    rec.recreate();
                    System.out.println(" ");
                }
                case "reload" -> {
                    Reload rel = new Reload();
                    rel.reload();
                }
                case "exit" -> {
                    Shutdown shut = new Shutdown();
                    shut.shutdown();
                    System.out.println(" ");
                    loopBool = false;
                }
                case "error" -> {
                    DBUtils dbUtils = new DBUtils();
                    dbUtils.error();
                    System.out.println(" ");
                }
                default -> {
                    if(!command.isBlank() || !command.isEmpty()) System.out.println("YEET: " + command + " isnt a command type help to get the command list.");
                }
            }
            if(argList.size() > 0) argList.clear();
        } while (loopBool);
    }

    /*@Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("scenes/home.fxml"));
        Parent root = loader.load();*
        Scene scene = new Scene(root, 1280, 800);
        BaseController controller = loader.getController();
        controller.setStage(stage);
        stage.setScene(scene);
        stage.setTitle("Name WIP");
        stage.show();
    }*/
}
