package com.example.jobflow.controllers.back.user;

import com.example.jobflow.controllers.back.MainWindowController;
import com.example.jobflow.entities.User;
import com.example.jobflow.services.UserService;
import com.example.jobflow.utils.AlertUtils;
import com.example.jobflow.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ShowAllController implements Initializable {

    public static User currentUser;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public VBox mainVBox;
    @FXML
    public TextField searchTF;


    List<User> listUser;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listUser = UserService.getInstance().getAll();

        displayData("");
    }

    void displayData(String searchText) {
        mainVBox.getChildren().clear();

        Collections.reverse(listUser);

        if (!listUser.isEmpty()) {
            for (User user : listUser) {
                if (searchText.isEmpty()
                        || user.getEmail().contains(searchText)
                        || user.getLastname().contains(searchText)
                ) {
                    mainVBox.getChildren().add(makeUserModel(user));
                }
            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeUserModel(
            User user
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL_USER)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#emailText")).setText("Email : " + user.getEmail());
            ((Text) innerContainer.lookup("#lastnameText")).setText("Lastname : " + user.getLastname());
            ((Text) innerContainer.lookup("#rolesText")).setText("Roles : " + user.getRoles());

            Text statusText = ((Text) innerContainer.lookup("#statusText"));
            statusText.setText("Status : " + (user.getStatus() == 1 ? "Active" : "Banned"));
            statusText.setStyle("-fx-fill: " + (user.getStatus() == 1 ? "green" : "red"));

            Button banButton = ((Button) innerContainer.lookup("#banButton"));
            banButton.setText(user.getStatus() == 1 ? "Ban" : "Unban");

            banButton.setStyle("-fx-background-color: " + (user.getStatus() == 1 ? "red" : "green"));
            banButton.setTextFill(javafx.scene.paint.Color.WHITE);
            banButton.setOnAction((ignored) -> {
                user.setStatus(user.getStatus() == 1 ? 0 : 1);

                if (UserService.getInstance().edit(user)) {
                    statusText.setText("Status : " + (user.getStatus() == 1 ? "Active" : "Banned"));
                    statusText.setStyle("-fx-fill: " + (user.getStatus() == 1 ? "green" : "red"));
                    banButton.setText(user.getStatus() == 1 ? "Ban" : "Unban");
                    banButton.setStyle("-fx-background-color: " + (user.getStatus() == 1 ? "red" : "green"));
                }
            });

            ((Button) innerContainer.lookup("#editButton")).setOnAction((ignored) -> modifierUser(user));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((ignored) -> supprimerUser(user));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterUser(ActionEvent ignored) {
        currentUser = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_USER);
    }

    private void modifierUser(User user) {
        currentUser = user;
        MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_USER);
    }

    private void supprimerUser(User user) {
        currentUser = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer user ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                if (UserService.getInstance().delete(user.getId())) {
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_USER);
                } else {
                    AlertUtils.makeError("Could not delete user");
                }
            }
        }
    }


    public void search(KeyEvent keyEvent) {
        displayData(searchTF.getText());
    }
}
