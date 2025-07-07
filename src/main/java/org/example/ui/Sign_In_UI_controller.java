package org.example.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.example.services.Languages_List_Service;

import java.io.IOException;
import java.util.ResourceBundle;

public class Sign_In_UI_controller {

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
            ResourceBundle bundle = Languages_List_Service.getInstance().getBundle();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/example/ui/Sign_In_UI.fxml"), bundle);
            try {
                Parent newRoot = loader.load();
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.getScene().setRoot(newRoot);
            } catch (IOException e) {
                System.out.println("error");
            }
        }
    }

    @FXML
    private ComboBox<String> languagesComboBox;

    @FXML
    private Button signInGuestButton;

    @FXML
    private Button signInGuestDatabaseButton;

    @FXML
    void signInGuestButton_pressed(ActionEvent event) {
        signInGuestButton.getText();
    }

    @FXML
    void signInGuestDatabaseButton_pressed(ActionEvent event) {

    }

}
