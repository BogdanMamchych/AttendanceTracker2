module module_name {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    requires com.fasterxml.jackson.core;

    opens org.example.models to
            com.fasterxml.jackson.core,
            com.fasterxml.jackson.databind;

    opens org.example.ui to javafx.fxml;
    exports org.example;
}