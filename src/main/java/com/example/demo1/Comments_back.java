package com.example.demo1;

import entites.Comment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import services.ServiceComment;
import services.ServicePost;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class  Comments_back implements Initializable {

    @FXML
    private FlowPane comment_Container;

    @FXML
    private ScrollPane scroll;
    ServicePost service =new ServicePost();
    ServiceComment serviceComment =new ServiceComment();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.chagercomment();
    }

    public void  chagercomment(){
        if(scroll.isVisible())
            scroll.setVisible(false);
        else
            scroll.setVisible(true);

        try {
            for (Comment comment0 : serviceComment.getAll()) {
                System.out.println(comment0);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("comment.fxml"));
                Parent card = loader.load();
                postController controller = loader.getController();
                controller.setData_Comment(comment0);
                comment_Container.getChildren().add(card);}

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    public void navigatetoback(ActionEvent actionEvent) {
        try{
            Parent root =
                    FXMLLoader.load(getClass().getResource("back/Posts.fxml"));


            comment_Container.getScene().setRoot(root);
        } catch (IOException ex) {


            System.err.println(ex.getMessage());

        }

    }

    public void vavtocomments(ActionEvent actionEvent) {
        try {
            // Load the FXML file
            Parent root = FXMLLoader.load(getClass().getResource("back/Comments.fxml"));

            // Set the loaded FXML as the root of the current scene
            comment_Container.getScene().setRoot(root);
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
