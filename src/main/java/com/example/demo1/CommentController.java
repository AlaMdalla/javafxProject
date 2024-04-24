package com.example.demo1;

import entites.Comment;
import entites.Post;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import services.ServiceComment;
import services.ServicePost;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CommentController implements Initializable {
    ServicePost service =new ServicePost();
    ServiceComment serviceComment =new ServiceComment();


    @FXML
    private TextField idPost;
    @FXML
    private TextArea commentContenu;
    @FXML
    private TextField Commentname;
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
    private FlowPane comment_Container;
    ;
    @FXML
    private AnchorPane rootPane;


    private boolean menuVisible = false;

    @FXML
    private AnchorPane menuPopup;


    @FXML
    private TextArea comment_contenu;

    @FXML
    private TextField comment_date;

    @FXML
    private TextField comment_name;
int id;
    @FXML
    private TextField comment_time;
    public void setData_Comment(Comment comment) {
        System.out.println("test");
id=comment.getId();
        comment_name.setText("aaa");
        comment_contenu.setText(comment.getContenu());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        comment_date.setText(comment.getDate().format(formatter));
        comment_time.setText("test");

    }

    @FXML
    public  void  ajouterComment() {


        Comment comment =new Comment( Commentname.getText(),commentContenu.getText());
        comment.setId_post(Integer.parseInt(this.idPost.getText()));
        comment.setDate();
        comment.setTimeToCurrent();
        Post postComments=new Post();
        try {
            postComments=  service.getPost(Integer.parseInt(this.idPost.getText()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("postcomments"+postComments.getComments());


        try {
            serviceComment.ajouter(comment);
            postComments.addComment(comment);
            System.out.println("ppcomments"+postComments.getComments());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setContentText("post Ajouter avec succes");
            alert.show();

//afficherPosts();



        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(ex.getMessage());
            alert.show();
        }

        System.out.println("Ajouter");
        this.chagercomment();

    }


    public void  chagercomment(){
        try {
            for (Comment comment0 : serviceComment.getAll(Integer.parseInt(idPost.getText()))) {
                System.out.println(comment0);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("comment.fxml"));
                Parent card = loader.load();
                CommentController controller = loader.getController();
                controller.setData_Comment(comment0);
                comment_Container.getChildren().add(card);}

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }
    public void delete(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete Post"+this.id);
        alert.setContentText("Are you sure you want to delete this post?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    service.supprimer(Integer.parseInt(String.valueOf(this.id)));
                    Parent root = FXMLLoader.load(getClass().getResource("afficherpost.fxml"));

                    Scene scene = comment_name.getScene();

                    Stage stage = (Stage) scene.getWindow();

                    stage.setScene(new Scene(root));
                } catch (IOException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
