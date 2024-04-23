package com.example.demo1;

import entites.Comment;
import entites.Post;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import services.ServiceComment;
import services.ServicePost;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class HelloController implements Initializable  {
    ServicePost service =new ServicePost();


    @FXML
    private Label welcomeText;
    @FXML
    private Button ajouterPosteButton;
    @FXML
    private TableColumn<Post, String> NomColum;
    @FXML
    private ImageView image;

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
    private TextArea txtContenu;
    @FXML
    private TextField txtTag;
    String url ;
    Post post =new Post();
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
    public  void  navigateVersafficher(ActionEvent event){
        try{
            Parent root =
                    FXMLLoader.load(getClass().getResource("afficherpost.fxml"));


            ajouterPosteButton.getScene().setRoot(root);
        } catch (IOException ex) {


            System.err.println(ex.getMessage());

        }

    }
    @FXML
    public  void  ajouterPoste() {
        Post post =new Post(txtNom.getText(),txtContenu.getText(),txtTag.getText());

post.setImage(this.url);
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


@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public String addimage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {

            String imagePath = selectedFile.getAbsolutePath();
            System.out.println("addimage"+imagePath);

            this.url="file:///"+imagePath.replace("\\","\\\\");
            System.out.println("imaa"+url);
            Image image0 = new Image(url.replace("\\","\\\\"));

            image.setImage(image0);
            return imagePath;

                  } else {
            System.out.println("no image selected");
this.url="C:\\Users\\ufl\\Pictures\\favicon.png";
            return "C:\\Users\\ufl\\Pictures\\favicon.png";
        }
    }
}
