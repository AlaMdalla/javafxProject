package com.example.demo1;

import entites.Post;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import services.ServicePost;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HelloController implements Initializable  {
    ServicePost service =new ServicePost();


    @FXML
    private Label welcomeText;
    @FXML
    private TableColumn<Post, String> NomColum;

    @FXML
    private AnchorPane PostTable;

    @FXML
    private TableView<Post> postTableView;

    @FXML
    private TableColumn<Post, String> prenomColum;

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
            try{
                Parent root =
                        FXMLLoader.load(getClass().getResource("afficherpost.fxml"));

//afficherPosts();
                testText.getScene().setRoot(root);

            } catch (IOException ex) {


                System.err.println(ex.getMessage());

            }

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.show();
        }

        System.out.println("Ajouter");


    }

    public void afficherPosts()  {

       /* try{
            Parent root =
                    FXMLLoader.load(getClass().getResource("ajouterPost.fxml"));


            testText.getScene().setRoot(root);
        } catch (IOException ex) {


            System.err.println(ex.getMessage());


        }*/

       try {
            // TableView.setItems( service.getAll());
            postTableView.setItems(service.getAll());
            NomColum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
            prenomColum.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrenom()));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    //afficherPosts();
    }
}
