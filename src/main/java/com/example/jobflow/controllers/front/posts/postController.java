package com.example.jobflow.controllers.front.posts;

import com.example.jobflow.MainApp;
import com.example.jobflow.entities.*;
import com.example.jobflow.entities.Comment;
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
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.example.jobflow.services.ServiceComment;
import com.example.jobflow.services.ServicePost;
import com.example.jobflow.services.ServivePostReactions;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class postController implements Initializable {
    ServicePost service =new ServicePost();
    ServiceComment serviceComment =new ServiceComment();
    ServivePostReactions ServivePostReactions =new ServivePostReactions();



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
    private TextField likes;
    @FXML
    private TextField dislikes;
    @FXML
    private FlowPane comment_Container;
    ;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField SharName;

    @FXML
    private TextField ShareComment;
    User currentUser;

    private boolean menuVisible = false;

    @FXML
    private AnchorPane menuPopup;


    @FXML
    private TextArea comment_contenu;

    @FXML
    private TextField comment_date;

    @FXML
    private TextField comment_name;

    @FXML
    private TextField comment_time;
    @FXML

    private ScrollPane scroll  ;
    int id;
    public void setData_Comment(Comment comment) {
        System.out.println("test");
        id=comment.getId();
        comment_name.setText(comment.getName());
        comment_contenu.setText(comment.getContenu());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        comment_date.setText(comment.getDate().format(formatter));
        DateTimeFormatter formatterr = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = comment.getTime().format(formatterr);
        comment_time.setText(formattedTime);


    }
    @FXML
    public void handleMenuClick() {
        menuVisible = !menuVisible;  // Toggle the visibility flag
        menuPopup.setVisible(menuVisible);  // Update visibility based on flag
    }

    String url;
    public void setData(Post post)  {
        if(post.getShareComment()!=null&&post.getSharName()!=null){
            System.out.println("not null"+post.getSharName()+post.getShareComment());
            SharName.setText(post.getSharName());
            ShareComment.setText(post.getShareComment());
            SharName.setVisible(true);
            ShareComment.setVisible(true);
        }
        idPost.setText(String.valueOf(post.getId()));
        nomPost.setText(post.getNom());
        ContenuPost.setText(post.getContenu());
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        DatePost.setText(post.getDate().format(formatter));
        TagPost.setText(post.getTag());
        String imageurl=post.getImage();
        System.out.println("imageurl"+imageurl);
        Image image = new Image(imageurl.replace("\\","\\\\"));
id=post.getId();
        imagePost.setImage(image);



    }






    public void navigatetoPost(ActionEvent actionEvent) throws SQLException, IOException {


        String idText = idPost.getText();
        if (!idText.isEmpty()) {
            int id = Integer.parseInt(idText);
            Post p = service.getPost(id);
            if (p != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/jobflow/front/posts/postComments.fxml"));
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

    public void handleMenuClick(ActionEvent actionEvent) {
    }

    public void navtoupdate(MouseEvent mouseEvent) throws SQLException, IOException {

        String idText = idPost.getText();
        if (!idText.isEmpty()) {
            int id = Integer.parseInt(idText);
            Post p = service.getPost(id);
            if (p != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/jobflow/front/posts/updatepost.fxml"));
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


    }  public void navToCommentUpdate(MouseEvent mouseEvent) throws SQLException, IOException {


        if (id!=0) {
            System.out.println(id);
            Comment comment = serviceComment.getComment(id);
            if (comment != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/jobflow/front/posts/commentUpdate.fxml"));
                Parent root = loader.load();
                CommentController controller = loader.getController();

                controller.setData_Comment(comment);
                Scene scene = new Scene(root);

                // Get the current stage and scene
                Stage currentStage = (Stage) comment_name.getScene().getWindow();


                // Set the new scene
                currentStage.setScene(scene);
                currentStage.setTitle("comment Details");
                currentStage.show();


            }
        }


    }
    @FXML
    public  void  ajouterComment() {



        Comment comment =new Comment( Commentname.getText(),commentContenu.getText());

        if ( commentContenu.getText().isEmpty() ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please fill in all fields");
            alert.show();
            return;
        }
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
        scroll.setVisible(false);

        this.chagercomment();

    }


public void  chagercomment(){
        if(scroll.isVisible())
    scroll.setVisible(false);
else
        scroll.setVisible(true);

    try {
        for (Comment comment0 : serviceComment.getAll(Integer.parseInt(idPost.getText()))) {
            System.out.println(comment0);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/jobflow/front/posts/comment.fxml"));
            Parent card = loader.load();
            postController controller = loader.getController();
            controller.setData_Comment(comment0);
            comment_Container.getChildren().add(card);}

    } catch (IOException  e) {
        throw new RuntimeException(e);
    } catch (SQLException e) {
        throw new RuntimeException(e);

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
                    try {
                        for (Comment comment0 : serviceComment.getAll(Integer.parseInt(idPost.getText()))) {

                            serviceComment.supprimer(comment0.getId()); }


                    } catch (SQLException e) {
                        throw new RuntimeException(e);

                    }
                    Parent root = FXMLLoader.load(getClass().getResource("/com/example/jobflow/front/posts/afficherpost.fxml"));

                    Scene scene = idPost.getScene();

                    Stage stage = (Stage) scene.getWindow();

                    stage.setScene(new Scene(root));
                } catch (IOException | SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    public void delete_Comment(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Delete Post"+this.id);
        alert.setContentText("Are you sure you want to delete this post?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    serviceComment.supprimer(Integer.parseInt(String.valueOf(this.id)));
                    System.out.println("deleted");
                } catch (  SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }


    public void navigateVersafficher(MouseEvent mouseEvent) {
        try{
            Parent root =
                    FXMLLoader.load(getClass().getResource("/com/example/jobflow/front/posts/afficherpost.fxml"));


            nomPost.getScene().setRoot(root);
        } catch (IOException ex) {


            System.err.println(ex.getMessage());

        }
    }

    public void update(MouseEvent mouseEvent) throws SQLException, IOException {
        if (nomPost.getText().isEmpty() || ContenuPost.getText().isEmpty() || TagPost.getText().isEmpty()||this.url=="") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please fill in all fields");
            alert.show();
        }

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


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/jobflow/front/posts/afficherpost.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage currentStage = (Stage) idPost.getScene().getWindow();

        currentStage.setScene(scene);
        currentStage.setTitle("Post Details");
        currentStage.show();

    }
    public void incrementlikes() throws SQLException {
        int id= Integer.parseInt(idPost.getText());
        System.out.println(id);
        post_reactions postR =  ServivePostReactions.getPosReeactiont(id);

        System.out.println("id:"+postR.getId());

        postR.setLikes(postR.getLikes()+1);
        System.out.println("likes"+postR.getLikes());
        ServivePostReactions.modifier(postR);
        likes.setText(String.valueOf(postR.getLikes()));





    }
    public void incrementdislikes() throws SQLException {
        int id= Integer.parseInt(idPost.getText());
        System.out.println(id);
        post_reactions postR =  ServivePostReactions.getPosReeactiont(id);

        System.out.println("dislikes:"+postR.getDislike());

        postR.setDislike(postR.getDislike()+1);
        ServivePostReactions.modifier(postR);


        dislikes.setText(String.valueOf(postR.getDislike()));



    }
    public void setreactions(Post post) throws SQLException {
        int id= Integer.parseInt(idPost.getText());
        System.out.println(id);
        post_reactions postR =  ServivePostReactions.getPosReeactiont(id);




        dislikes.setText(String.valueOf(postR.getDislike()));
        likes.setText(String.valueOf(postR.getLikes()));




    }
    public  void  shareComment() throws SQLException {
        if (nomPost.getText().isEmpty() || ContenuPost.getText().isEmpty() || TagPost.getText().isEmpty()||this.url=="") {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please fill in all fields");
            alert.show();
            return;
        }

        Post post =new Post(nomPost.getText(),ContenuPost.getText(),TagPost.getText(),SharName.getText(),ShareComment.getText());
post.setDate();
        Post old_post= service.getPost(this.id);


        post.setImage(this.url);
        if(this.url==null){
            post.setImage(old_post.getImage());
        }
        try {

            service.ajouter(post);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes");
            alert.setContentText("post Ajouter avec succes");
            alert.show();
            try{
                Parent root =
                        FXMLLoader.load(getClass().getResource("/com/example/jobflow/front/posts/afficherpost.fxml"));

                nomPost.getScene().setRoot(root);

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


    public void sharePost(ActionEvent actionEvent) throws SQLException, IOException {

        String idText = idPost.getText();
        if (!idText.isEmpty()) {
            int id = Integer.parseInt(idText);
            Post p = service.getPost(id);
            if (p != null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/jobflow/front/posts/share.fxml"));
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
}