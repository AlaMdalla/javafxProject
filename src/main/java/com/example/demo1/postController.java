package com.example.demo1;

import entites.Post;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServicePost;

import java.io.File;
import java.io.IOException;
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
    private TextField ContenuPost;
    @FXML
    private TextField DatePost;
    @FXML
    private TextField TagPost;
    @FXML
    private ImageView imagePost;

    @FXML
    private AnchorPane rootPane;

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


    public void delete(ActionEvent actionEvent) {
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

    public void navtoupdate(ActionEvent actionEvent) throws SQLException, IOException {


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
}
