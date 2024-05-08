package com.example.jobflow.controllers.front.posts;

import com.example.jobflow.entities.Post;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import com.example.jobflow.services.ServicePost;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class AfficherController  implements Initializable {
    ServicePost service =new ServicePost();
    @FXML
    private FlowPane comment_Container;

    @FXML
    private FlowPane cardContainer;

    @FXML
    private Label testText;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            for (Post post : service.getAll()) {
                // Load card view for each post and add it to the container
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/jobflow/front/posts/post.fxml"));
                Parent card = loader.load();
                postController controller = loader.getController();
                controller.setData(post);
                controller.setreactions(post);
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
                    FXMLLoader.load(getClass().getResource("/com/example/jobflow/front/posts/ajouterPost.fxml"));


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

    public void navigatetoback(ActionEvent actionEvent) {
        try{
            Parent root =
                    FXMLLoader.load(getClass().getResource("back/Posts.fxml"));


            cardContainer.getScene().setRoot(root);
        } catch (IOException ex) {


            System.err.println(ex.getMessage());

        }

    }

    public void vavtocomments(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            Parent root = FXMLLoader.load(getClass().getResource("back/Comments.fxml"));

            // Set the loaded FXML as the root of the current scene
            cardContainer.getScene().setRoot(root);
        } catch (IOException ex) {
            // Handle IOException (e.g., FXML file not found)
            System.err.println("Error loading Comments.fxml: " + ex.getMessage());
            ex.printStackTrace(); // Print stack trace for debugging
        } catch (NullPointerException ex) {
            // Handle NullPointerException (getResource() returned null)
            System.err.println("Comments.fxml not found: " + ex.getMessage());
            ex.printStackTrace(); // Print stack trace for debugging
        }
    }

}




