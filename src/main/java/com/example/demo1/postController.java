package com.example.demo1;

import entites.Post;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServicePost;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class postController implements Initializable {
    ServicePost service =new ServicePost();

    @FXML
    private TextField idPost;
    @FXML
    private TextField nomPost;
    @FXML
    private TextArea ContenuPost;
    @FXML
    private TextField DatePost;
    @FXML
    private TextField TagPost;
    @FXML
    private ImageView imagePost;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private Button menuButtonTop;
    @FXML
    private Button menuButtonBottom;
    private boolean menuVisible = false;

    @FXML
    private AnchorPane menuPopup;
    @FXML
    private ImageView editphoto ;
    @FXML
    private ImageView deletephoto ;

    @FXML
    public void handleMenuClick() {
        menuVisible = !menuVisible;  // Toggle the visibility flag
        menuPopup.setVisible(menuVisible);  // Update visibility based on flag
    }

    String url;
    public void setData(Post post) {
        idPost.setText(String.valueOf(post.getId()));
        nomPost.setText(post.getNom());
        ContenuPost.setText(post.getContenu());
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        DatePost.setText(post.getDate().format(formatter));
        TagPost.setText(post.getTag());
        String imageurl=post.getImage();
        System.out.println("imageurl"+imageurl);
        Image image = new Image(imageurl.replace("\\","\\\\"));

        imagePost.setImage(image);
    }






    public void navigatetoPost(ActionEvent actionEvent) throws SQLException, IOException {


        String idText = idPost.getText();
        if (!idText.isEmpty()) {
            int id = Integer.parseInt(idText);
            Post p = service.getPost(id);
            if (p != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("postComments.fxml"));
                Parent root = loader.load();
                postController controller = loader.getController();

                controller.setData(p);
                Scene scene = new Scene(root);

                // Get the current stage and scene
                Stage currentStage = (Stage) idPost.getScene().getWindow();


                // Set the new scene
                currentStage.setScene(scene);
                currentStage.setTitle("Post Details");
                currentStage.show();



            }
        }


    }
    public void update(ActionEvent actionEvent) throws SQLException, IOException {



        Post post= new Post();
        post.setId(Integer.parseInt(idPost.getText()));
        Post old_post= service.getPost(post.getId());
        post.setNom(nomPost.getText());
        post.setContenu(ContenuPost.getText());
        post.setDate();
        post.setTag(TagPost.getText());

        post.setImage(this.url);
        if(this.url==null){
            post.setImage(old_post.getImage());
        }
        service.modifier(post);


        FXMLLoader loader = new FXMLLoader(getClass().getResource("afficherpost.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage currentStage = (Stage) idPost.getScene().getWindow();

        currentStage.setScene(scene);
        currentStage.setTitle("Post Details");
        currentStage.show();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ContenuPost.textProperty().addListener((observable, oldValue, newValue) -> {
            // Adjust the height of the text field based on its content
            ContenuPost.setPrefHeight(ContenuPost.getFont().getSize() * (1 + ContenuPost.getText().split("\n").length));
        });
     /*   String imagePath = "file:///C:\\\\Users\\\\ufl\\\\Downloads\\\\patient\\\\demo1\\\\src\\\\main\\\\resources\\\\images\\\\edit.png";
        String imagePath_delete = "file:///C:\\\\Users\\\\ufl\\\\Downloads\\\\patient\\\\demo1\\\\src\\\\main\\\\resources\\\\images\\\\close.png";
        String image_User_Url = "file:///C:\\\\Users\\\\ufl\\\\Downloads\\\\patient\\\\demo1\\\\src\\\\main\\\\resources\\\\images\\\\zlaga.png";*/

/*
        try {
            //edit photo
            Image image = new Image(imagePath);

            ImageView imageView = new ImageView(image);

            this.editphoto.setImage(imageView.getImage());

            //userphoto

            // Create an ImageView and set the image


            Image image_delete = new Image(imagePath_delete);

            // Create an ImageView and set the image
            ImageView imageView_delete = new ImageView(image_delete);

            // Now, you can use imageView as you wish, for example, setting it to likephoto
            this.editphoto.setImage(imageView.getImage());
            this.deletephoto.setImage(imageView_delete.getImage());
        } catch (Exception e) {
            // Print the stack trace to understand the problem
            e.printStackTrace();
        }
*/

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

            imagePost.setImage(image0);
            return imagePath;

        } else {
            System.out.println("no image selected");
            this.url="C:\\Users\\ufl\\Pictures\\favicon.png";
            return "C:\\Users\\ufl\\Pictures\\favicon.png";
        }
    }

    public void handleMenuClick(ActionEvent actionEvent) {
    }

    public void navtoupdate(MouseEvent mouseEvent) throws SQLException, IOException {

        String idText = idPost.getText();
        if (!idText.isEmpty()) {
            int id = Integer.parseInt(idText);
            Post p = service.getPost(id);
            if (p != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("updatepost.fxml"));
                Parent root = loader.load();
                postController controller = loader.getController();

                controller.setData(p);
                Scene scene = new Scene(root);

                // Get the current stage and scene
                Stage currentStage = (Stage) idPost.getScene().getWindow();


                // Set the new scene
                currentStage.setScene(scene);
                currentStage.setTitle("Post Details");
                currentStage.show();


            }
        }

    }

    public void delete(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete Post"+idPost.getText());
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
