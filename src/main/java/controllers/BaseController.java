package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseController {

    public Stage myStage;

    public void setStage(Stage stage) {
        myStage = stage;
    }

    public Parent setScene(String fullPath) {
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fullPath));
            root = loader.load();
            Scene scene = new Scene(root, 1280, 800);
            BaseController controller = loader.getController();
            controller.setStage(myStage);
            myStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    public void changeScene(ActionEvent event) throws IOException {
        Object o = event.getTarget();
        if (o instanceof Button) {
            Button b = (Button) o;
            //System.out.println(b.getId());
            switch (b.getId()) {
                case "createRecipe":
                    setScene("scenes/createRecipe.fxml");
                    break;
                case "friends":
                    
                    setScene("scenes/friends.fxml");
                    break;
                case "requests":
                    
                    setScene("scenes/requests.fxml");
                    break;
                case "users":
                    
                    setScene("scenes/users.fxml");
                    break;
                case "blocked":
                    
                    setScene("scenes/blocked.fxml");
                    break;
                case "config":
                    
                    setScene("scenes/config.fxml");
                    break;
                case "logs":
                    
                    setScene("scenes/log.fxml");
                    break;
                case "search":
                    
                    setScene("scenes/search.fxml");
                    break;
            }
        }
    }
    
}
