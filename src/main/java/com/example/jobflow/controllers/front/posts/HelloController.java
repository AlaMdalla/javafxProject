package com.example.jobflow.controllers.front.posts;

import com.example.jobflow.MainApp;
import com.example.jobflow.entities.Post;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import com.example.jobflow.services.ServicePost;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.example.jobflow.entities.User;


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
    User currentUser;

    Post post =new Post();
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        testText.setText("test");   };
    @FXML
    public  void  navigateVersAjouter(ActionEvent event){
        try{
            System.out.println("userr");
            Parent root =
                    FXMLLoader.load(getClass().getResource("/com/example/jobflow/front/posts/ajouterPost.fxml"));


            testText.getScene().setRoot(root);

        } catch (IOException ex) {


            System.err.println(ex.getMessage());

        }

    }
    @FXML
    public void navigateVersafficher(MouseEvent mouseEvent) {
        try{
            Parent root =
                    FXMLLoader.load(getClass().getResource("/com/example/jobflow/front/posts/afficherpost.fxml"));


            ajouterPosteButton.getScene().setRoot(root);
        } catch (IOException ex) {


            System.err.println(ex.getMessage());

        }}
    @FXML
    public  void  ajouterPoste() {



        if (txtNom.getText().isEmpty() || txtContenu.getText().isEmpty() || txtTag.getText().isEmpty()||this.url=="") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please fill in all fields");
            alert.show();
            return;
        }
        Post post =new Post(txtNom.getText(),txtContenu.getText(),txtTag.getText());

post.setImage(this.url);
        try {

            service.ajouter(post);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Image image=new Image("file:///C:\\Users\\ufl\\Downloads\\Notification_Project_youtube - 1\\Notification_Project_youtube\\src\\image\\error.png");

            Notifications notifications=Notifications.create();
            notifications.graphic(new ImageView(image));
            notifications.text("post added ");
            notifications.title("Success Message");
            notifications.hideAfter(Duration.seconds(4));
            /*notifications.darkStyle();*/
            /*   notifications.position(Pos.BOTTOM_CENTER);*/
            notifications.show();
            try{
                Parent root =
                        FXMLLoader.load(getClass().getResource("/com/example/jobflow/front/posts/afficherpost.fxml"));

                txtNom.getScene().setRoot(root);

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
    currentUser = MainApp.getSession();
    txtNom.setText(currentUser.getLastname());
    System.out.println("username+"+currentUser.getLastname());
    txtNom.setEditable(false);
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
this.url="";
            return "C:\\Users\\ufl\\Pictures\\favicon.png";
        }
    }
}
