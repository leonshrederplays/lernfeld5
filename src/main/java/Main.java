import commands.*;
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
                    Help.helper();
                    command = null;
                    break;
                case "ingreds":
                    try {
                        if (command.length > 1) {
                            Ingredients.ingredientDescription(command[1]);
                        } else {
                            Ingredients.ingredient();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    command = null;
                    System.out.println(" ");
                    break;
                case "recipe":
                    try {
                        if (command.length > 1) {
                            Recipes.recipeDescription(command[1]);
                        } else {
                            Recipes.recipe();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    command = null;
                    System.out.println(" ");
                    break;
                case "orders":
                    try {
                        if (command.length > 1) {
                            Orders.orderDescription(command[1]);
                        } else {
                            Orders.orders();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    command = null;
                    System.out.println(" ");
                    break;
                case "customers":
                    Console console = System.console();
                    int attempts = 0;
                    if(ConfigInstance.isAdminPassed) {
                        try {
                            if (command.length > 1) {
                                if(command.length == 3) {
                                    Customers.customerDescription(command, true);
                                } else {
                                    Customers.customerDescription(command, false);
                                }
                            } else {
                                Customers.customer();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        if(console == null) {
                            Scanner passwordInput = new Scanner(System.in);
                            System.out.println("WARNING: The Console isnt initialized so the password will be visible!");
                            do{
                                System.out.println("Enter the Password:");
                                String password = passwordInput.next();
                                if (password.equals("Admin")){
                                    conf.lockCustomers();
                                    conf.isAdminPassed = true;
                                    attempts = 3;
                                    try {
                                        if (command.length > 1) {
                                            if(command.length == 3) {
                                                Customers.customerDescription(command, true);
                                            } else {
                                                Customers.customerDescription(command, false);
                                            }
                                        } else {
                                            Customers.customer();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    attempts++;
                                    System.out.println("Wrong password! You have " + (3 - attempts) + " tries left!");
                                }
                            } while (attempts < 3);
                        } else {
                            do{
                                char[] password = console.readPassword("Enter your Password:");
                                if (new String(password).equals("Admin")){
                                    conf.lockCustomers();
                                    conf.isAdminPassed = true;
                                    attempts = 3;
                                    try {
                                        if (command.length > 1) {
                                            if(command.length == 3) {
                                                Customers.customerDescription(command, true);
                                            } else {
                                                Customers.customerDescription(command, false);
                                            }
                                        } else {
                                            Customers.customer();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    attempts++;
                                    System.out.println("Wrong password! You have " + (3 - attempts) + " tries left!");
                                }
                            } while (attempts < 3);
                        }
                    }


                    command = null;
                    System.out.println(" ");
                    break;
                case "recreate" :
                    Recreate.recreate();
                    command = null;
                    System.out.println(" ");
                    break;
                case "reload":
                    Reload.reload();
                    break;
                case "exit":
                    Shutdown.shutdown();
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
