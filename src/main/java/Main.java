import utils.Commander;
import utils.DBUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main /*extends Application*/ {

    private Object Scene;

    public static void main(String[] args) {
        //Application.launch(args);
        DBUtils dbUtils = new DBUtils();
        String path = Main.class.getClassLoader().getResource("").toString().replace("file:", "").replace("%20", " ");
        File file = new File(path+"config.ini");
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
                FileWriter myWriter = new FileWriter(path+"config.ini");
                myWriter.write("false");
                myWriter.close();
            } else {
                System.out.println("File exists: " + file.getName());
                Scanner reader = new Scanner(file);
                while (reader.hasNextLine()) {
                    String data = reader.nextLine();
                    if (data.equals("false")) {
                        dbUtils.firstBootConnector();
                        dbUtils.createSQL();
                        FileWriter newWriter = new FileWriter(path+"config.ini");
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

        Commander commander = new Commander();
        Scanner input = new Scanner(System.in);

        String[] command = null;
        do {
            System.out.println("Enter the command you want to execute: ");
            command = input.nextLine().split(" ");
            switch (command[0]) {
                case "help":
                    commander.helper();
                    command = null;
                    break;
                case "ingreds":
                    try {
                        if (command.length > 1) {
                            commander.ingredientDescription(command[1]);
                        } else {
                            commander.ingredients();
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
                            commander.recipeDescription(command[1]);
                        } else {
                            commander.recipe();
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
                            commander.orderDescription(command[1]);
                        } else {
                            commander.orders();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    command = null;
                    System.out.println(" ");
                    break;
                case "customer":

                    int attempts = 0;
                    Scanner passwordInput = new Scanner(System.in);
                    do{
                        System.out.println("Enter the Password: ");
                        String password = passwordInput.next();
                        if (password.equals("Admin")){
                            attempts = 5;
                            try {
                                if (command.length > 1) {
                                    commander.customerDescription(command[1]);
                                } else {
                                    commander.customer();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            attempts++;
                            System.out.println("Wrong password! You have " + (5 - attempts) + " tries left!");
                    } } while (attempts < 5);
                    command = null;
                    System.out.println(" ");
                    break;
                case "recreate" :
                    Scanner recreateInput = new Scanner(System.in);
                    System.out.println("This will reset the Database to its default state. Are you sure? (Y/N)");
                    String confirm = recreateInput.next();
                    if (confirm.equalsIgnoreCase("y")){
                        dbUtils.recreateSQL();
                        System.out.println("Database successfully recreated. Default values were restored.");
                    } else if (confirm.equalsIgnoreCase("n")){
                        System.out.println("Databse was not recreated.");
                    } else {

                    }
                    command = null;
                    System.out.println(" ");
                    break;
                case "exit":
                    commander.shutdown();
                    command = null;
                    System.out.println(" ");
                    break;
                default:
                    System.out.println("YEET type help to get the command list.");
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
