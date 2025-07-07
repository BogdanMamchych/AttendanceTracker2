package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.services.Languages_List_Service;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Languages_List_Service.getInstance().receiveLanguagesFromJSON();
        ResourceBundle bundle = Languages_List_Service.getInstance().getBundle();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ui/Sign_In_UI.fxml"), bundle);
        Parent root = loader.load();
        primaryStage.setTitle("Sign In");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
