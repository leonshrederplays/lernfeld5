import instances.ConfigInstance;
import utils.Commander;
import utils.DBUtils;

import java.util.Scanner;

public class Main /*extends Application*/ {

    private Object Scene;

    public static void main(String[] args) {
        //Application.launch(args);
        DBUtils dbUtils = new DBUtils();
        dbUtils.firstBootConnector();
        dbUtils.createSQL();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dbUtils.selectData();
        Commander commander = new Commander();
        Scanner input = new Scanner(System.in);

        String command = null;
        do {
            System.out.println("Enter the command you want to execute: ");
            command = input.next();
            switch (command) {
                case "help":
                    commander.helper();
                    command = null;
                    break;
                case "ingredients":
                    try {
                        String arg = input.nextLine();
                        if(arg.length() != 0) {
                            commander.ingredientDescription();
                        } else {
                            commander.ingredients();
                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                    command = null;
                    System.out.println(" ");
                    break;
                case "exit":
                    commander.shutdown();
                    command = null;
                    System.out.println(" ");
                    break;
                case "test":
                    commander.ingredientDescription();
                    command = null;
                    System.out.println(" ");
                    break;
                default:
                    System.out.println(command);
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
