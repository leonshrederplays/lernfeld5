import utils.Commander;
import utils.DBUtils;

import java.util.Scanner;

public class Main /*extends Application*/ {

    private Object Scene;

    public static void main(String[] args) {
        //Application.launch(args);
        DBUtils dbUtils = new DBUtils();
        dbUtils.connector();
        dbUtils.createSQL();
        Commander commander = new Commander();
        Scanner input = new Scanner(System.in);

        String command = null;
        do {
            System.out.println("Enter the command you want to execute: ");
            command = input.next();
            switch (command) {
                case "help" -> {
                    commander.helper();
                    command = null;
                }
                case "ingredients" -> {
                    commander.ingredients();
                    command = null;
                }
                case "exit" -> {
                    commander.shutdown();
                    command = null;
                }
            }
        } while (true);
    }

    /*@Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("scenes/home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1280, 800);
        BaseController controller = loader.getController();
        controller.setStage(stage);
        stage.setScene(scene);
        stage.setTitle("Name WIP");
        stage.show();
    }*/
}
