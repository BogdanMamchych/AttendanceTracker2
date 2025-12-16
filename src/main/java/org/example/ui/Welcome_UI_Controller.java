package org.example.ui;

import com.fasterxml.jackson.core.JsonParseException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.example.services.Languages_List_Service;
import org.example.services.Messages_Service;

import java.io.IOException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Welcome_UI_Controller {

    @FXML
    private ComboBox<String> languagesComboBox;

    @FXML
    private Button signInZoomButton;

    @FXML
    private void initialize() {
        Languages_List_Service.getInstance().getLanguagesToComboBox(languagesComboBox);
        languagesComboBox.setOnAction(this::onLanguageSelected);
        Languages_List_Service.getInstance().getCurrentLanguageName(languagesComboBox);
    }

    @FXML
    void onLanguageSelected(ActionEvent event) {
        String selected = languagesComboBox.getValue();
        if (Languages_List_Service.getInstance().getLanguageCode(selected)) {
            try {
                ResourceBundle bundle = Languages_List_Service.getInstance().getBundle();
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/org/example/ui/Welcome_UI.fxml"), bundle);
                Parent newRoot = loader.load();
                Stage signInStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                String title = bundle.getString("welcome.windowName");
                signInStage.setTitle(title);
                signInStage.getScene().setRoot(newRoot);
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
    }

    @FXML
    void onSignInZoomButtonPressed(ActionEvent event) {
        Stage currentStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        currentStage.close();

        ResourceBundle bundle = Languages_List_Service.getInstance().getBundle();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/ui/Database_Selection_UI.fxml"),
                bundle
        );
        try {
            Parent newRoot = loader.load();

            Stage newStage = new Stage();
            String title = bundle.getString("database_selection.windowTitle");
            newStage.setTitle(title);
            newStage.setScene(new Scene(newRoot));
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
