package com.example.jobflow.controllers.front.evenement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Hellosponsor extends Application {
    @Override
    public void start(Stage Stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Hellosponsor.class.getResource("/com/example/jobflow/front/evenement/sponsor.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage.setTitle(" Sponsor ");
        Stage.setScene(scene);
        Stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
