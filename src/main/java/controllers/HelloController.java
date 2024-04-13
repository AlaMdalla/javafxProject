package controllers;

import entites.Post;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import services.ServicePost;

import java.io.IOException;
import java.sql.SQLException;

public class HelloController {
    ServicePost service =new ServicePost();
    @FXML
    private Label welcomeText;
    @FXML
    private Label testText;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtPrenom;
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        testText.setText("test");   };
    @FXML
    public  void  navigateVersAjouter(ActionEvent event){
        try{
            Parent root =
                    FXMLLoader.load(getClass().getResource("ajouterPost.fxml"));


            testText.getScene().setRoot(root);
        } catch (IOException ex) {


     System.err.println(ex.getMessage());

        }
        
    }
    @FXML
    public  void  ajouterPoste() {
        Post post =new Post(txtNom.getText(),txtPrenom.getText());

        try {
            service.ajouter(post);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setContentText("post Ajouter avec succes");
            alert.show();
        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.show();
        }

        System.out.println("Ajouter");


    }

    public static class SowPostController {
        ServicePost service =new ServicePost();
        @FXML
        private Label welcomeText;
        @FXML
        private Label testText;
        @FXML
        private TextField txtNom;
        @FXML
        private TextField txtPrenom;
        @FXML
        protected void onHelloButtonClick() {
            welcomeText.setText("Welcome to JavaFX Application!");
            testText.setText("test");   };
        @FXML
        public  void  navigateVersAjouter(ActionEvent event){
            try{
                Parent root =
                        FXMLLoader.load(getClass().getResource("ajouterPost.fxml"));


                testText.getScene().setRoot(root);
            } catch (IOException ex) {


                System.err.println(ex.getMessage());

            }

        }
        @FXML
        public  void  ajouterPoste() {
            Post post =new Post(txtNom.getText(),txtPrenom.getText());

            try {
                service.ajouter(post);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succes");
                alert.setContentText("post Ajouter avec succes");
                alert.show();
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(ex.getMessage());
                alert.show();
            }

            System.out.println("Ajouter");


        }
    }
}