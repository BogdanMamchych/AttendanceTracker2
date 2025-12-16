package org.example;

import com.fasterxml.jackson.core.JsonParseException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.services.Languages_List_Service;
import org.example.services.Messages_Service;

import java.io.IOException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Languages_List_Service.getInstance().receiveLanguagesFromJSON();
            ResourceBundle bundle = Languages_List_Service.getInstance().getBundle();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/ui/Welcome_UI.fxml"), bundle);
            Parent root = loader.load();
            String title = bundle.getString("welcome.windowName");
            primaryStage.setTitle(title);
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (JsonParseException ex) {
            String errorMsg = Languages_List_Service.getInstance().getBundle().getString("error.json");
            Messages_Service.getInstance().showErrorMessage(errorMsg, ex.getMessage());
        } catch (MissingResourceException ex) {
            String errorMsg = Languages_List_Service.getInstance().getBundle().getString("error.bundle");
            Messages_Service.getInstance().showErrorMessage(errorMsg, ex.getMessage());
        } catch (IOException ex) {
            String errorMsg = Languages_List_Service.getInstance().getBundle().getString("error.fxml");
            Messages_Service.getInstance().showErrorMessage(errorMsg, ex.getMessage());
        } catch (Exception ex) {
            String errorMsg = Languages_List_Service.getInstance().getBundle().getString("error.unexpected");
            Messages_Service.getInstance().showErrorMessage(errorMsg, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
