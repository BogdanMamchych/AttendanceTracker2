package org.example.services;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Messages_Service {
    private static volatile Messages_Service instance;

    public static Messages_Service getInstance() {
        if (instance == null) {
            synchronized (Messages_Service.class) {
                if (instance == null) {
                    instance = new Messages_Service();
                }
            }
        }
        return instance;
    }

    public void showErrorMessage(String header, String text) {
        Alert errorAlert = new Alert(AlertType.ERROR);
        errorAlert.setTitle("Error");
        errorAlert.setHeaderText(header);
        errorAlert.setContentText(text);
        errorAlert.showAndWait();
    }
}
