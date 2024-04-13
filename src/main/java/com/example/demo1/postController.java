package com.example.demo1;

import entites.Post;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServicePost;

import java.io.IOException;
import java.sql.SQLException;


public class postController {
    ServicePost service =new ServicePost();

    @FXML
    private TextField idPost;
    @FXML
    private TextField nomPost;
    @FXML
    private TextField prenomPost;
    public void setData(Post post) {
        idPost.setText(String.valueOf(post.getId()));
        nomPost.setText(post.getNom());
        prenomPost.setText(post.getPrenom());

    }


    public void delete(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete Post");
        alert.setContentText("Are you sure you want to delete this post?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    service.supprimer(Integer.parseInt(idPost.getText()));
                    Parent root = FXMLLoader.load(getClass().getResource("afficherpost.fxml"));

                    Scene scene = idPost.getScene();

                    Stage stage = (Stage) scene.getWindow();

                    stage.setScene(new Scene(root));
                } catch (IOException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
