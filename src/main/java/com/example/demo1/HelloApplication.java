package com.example.demo1;

import entites.Post;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServicePost;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class HelloApplication extends Application {



//    sp.ajouter(post);
    @Override
    public void start(Stage stage) throws IOException {

    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("afficherpost.fxml"));
 FXMLLoader fxmlLoader0 = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader0.load(), 500, 360);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();




    }

    public static void main(String[] args) throws SQLException {
        ServicePost sp = new ServicePost();
        /*Post post =new Post(0,"ala","aaaa","11/22/2002","test");


  try {
            sp.ajouter(post);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        System.out.println("Ajouter");
        System.out.println(sp.getAll());*/
launch();
    }
}