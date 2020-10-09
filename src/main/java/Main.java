import commands.*;
import constructors.RecipeList;
import instances.ConfigInstance;
import utils.DBUtils;

import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main /*extends Application*/ {

    private Object Scene;

    public static void main(String[] args) {
        //Application.launch(args);
        DBUtils dbUtils = new DBUtils();
        ConfigInstance conf = new ConfigInstance();
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
                dbUtils.firstBootConnector();
                // Create Database and test Data.
                dbUtils.createSQL();
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
                        dbUtils.selectData();
                        break;
                    }
                } while(true);
            } else {
                System.out.println("File exists: " + file.getName());
                Scanner reader = new Scanner(file);
                while (reader.hasNextLine()) {
                    String data = reader.nextLine();
                    if (data.equals("false")) {
                        dbUtils.firstBootConnector();
                        dbUtils.createSQL();
                        FileWriter newWriter = new FileWriter("config.ini");
                        newWriter.write("true");
                        newWriter.close();
                        boolean finished = ConfigInstance.isSQLfinished;
                        do {
                            if(finished) {
                                dbUtils.selectData();
                                break;
                            }
                        } while(true);
                    } else if (data.equals("true")) {
                        dbUtils.selectData();
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scanner input = new Scanner(System.in);
        String[] command = null;
        do {
            System.out.println("Enter the command you want to execute: ");
            command = input.nextLine().split(" ");
            switch (command[0]) {
                case "help":
                    Help help = new Help();
                    help.helper();
                    command = null;
                    break;

                case "ingreds":
                    Ingredients ingred = new Ingredients();
                    try {
                        if (command.length > 1) {
                            ingred.ingredientDescription(command[1]);
                        } else {
                            ingred.ingredient();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    command = null;
                    System.out.println(" ");
                    break;

                case "recipe":
                    Recipes recipe = new Recipes();
                    try {
                        if (command.length > 1) {
                            recipe.recipeDescription(command[1]);
                        } else {
                            recipe.recipe();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    command = null;
                    System.out.println(" ");
                    break;

                case "addrecipe":
                    AddRecipe addrecipe = new AddRecipe();
                    System.out.println("You typed addrecipe so now we will create a recipe lets start.");
                    addrecipe.addRecipeName();
                    break;

                case "orders":
                    Orders order = new Orders();
                    try {
                        if (command.length > 1) {
                            order.orderDescription(command[1]);
                        } else {
                            order.orders();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    command = null;
                    System.out.println(" ");
                    break;

                case "customers":
                    Customers cust = new Customers();
                    cust.passwordManager(command);
                    command = null;
                    System.out.println(" ");
                    break;

                case "categories":
                    Categories categ = new Categories();
                    categ.categories();
                    break;

                case "allergens":
                    Allergens allerg = new Allergens();
                    allerg.allergens();
                    break;

                case "recreate" :
                    Recreate rec = new Recreate();
                    rec.recreate();
                    command = null;
                    System.out.println(" ");
                    break;

                case "reload":
                    Reload rel = new Reload();
                    rel.reload();
                    break;

                case "exit":
                    Shutdown shut = new Shutdown();
                    shut.shutdown();
                    command = null;
                    System.out.println(" ");
                    break;

                default:
                    System.out.println("YEET: " + command[0] +  " isnt a command type help to get the command list.");
                    command = null;
                    break;
            }
        } while (true);
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
