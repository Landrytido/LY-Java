module com.comptaapp.comptaappfrontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.google.gson;
    requires okhttp3;

    opens com.comptaapp.comptaappfrontend to javafx.fxml;
    exports com.comptaapp.comptaappfrontend;
    exports com.comptaapp.comptaappfrontend.controller;

    opens com.comptaapp.comptaappfrontend.controller to javafx.fxml;
    opens com.comptaapp.comptaappfrontend.model to com.google.gson;
    opens com.comptaapp.comptaappfrontend.dto to com.google.gson;
    opens com.comptaapp.comptaappfrontend.service to com.google.gson;

}
