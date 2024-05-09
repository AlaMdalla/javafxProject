package com.example.jobflow.controllers.back.evenement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage primaryStage; // Ajoutez une référence au stage principal

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage; // Stockez la référence au stage principal
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/jobflow/back/evenement/hello-view.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Gestion des Evenements");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }


}
