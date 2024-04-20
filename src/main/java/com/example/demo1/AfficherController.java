package com.example.demo1;

import entites.Post;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import services.ServicePost;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class AfficherController  implements Initializable {
    ServicePost service =new ServicePost();

    @FXML
    private FlowPane cardContainer;

    @FXML
    private Label testText;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            for (Post post : service.getAll()) {
                // Load card view for each post and add it to the container
                FXMLLoader loader = new FXMLLoader(getClass().getResource("post.fxml"));
                Parent card = loader.load();
                postController controller = loader.getController();
                controller.setData(post);
          cardContainer.getChildren().add(card);
            }
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    public  void  navigateVersAjouter(ActionEvent event) {
        try {
            Parent root =
                    FXMLLoader.load(getClass().getResource("ajouterPost.fxml"));


            cardContainer.getScene().setRoot(root);
        } catch (IOException ex) {


            System.err.println(ex.getMessage());

        }
    }


        @FXML
    public  void  navigateVersafficher(ActionEvent event){
        try{
            Parent root =
                    FXMLLoader.load(getClass().getResource("afficherpost.fxml"));


            testText.getScene().setRoot(root);
        } catch (IOException ex) {


            System.err.println(ex.getMessage());

        }

    }

}




