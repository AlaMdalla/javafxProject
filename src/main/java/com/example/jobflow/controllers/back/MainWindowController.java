package com.example.jobflow.controllers.back;

import com.example.jobflow.MainApp;
import com.example.jobflow.utils.Animations;
import com.example.jobflow.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    static AnchorPane staticContent;
    private static MainWindowController instance;

    private final Color COLOR_GRAY = new Color(0.9, 0.9, 0.9, 1);
    private final Color COLOR_PRIMARY = Color.web("#1a7312");
    private final Color COLOR_DARK = new Color(1, 1, 1, 0.65);

    private Button[] liens;

    @FXML
    private AnchorPane content;

    @FXML
    private Button btnUsers;
    @FXML
    private Button btnSociete;
    @FXML
    private Button btnPosts;
    @FXML
    private Button btnComments;
    @FXML
    private Button btnOpportunites;
    @FXML
    private Button btnTests;
    @FXML
    private Button btnPartenaire;
    @FXML
    private Button btnEvent;

    public static MainWindowController getInstance() {
        if (instance == null) {
            instance = new MainWindowController();
        }
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        staticContent = content;

        liens = new Button[]{
                btnUsers,
                btnOpportunites,
                btnTests,
                btnPosts,
                btnComments,
                btnSociete,
                btnPartenaire,
                btnEvent
        };

        for (Button lien : liens) {
            lien.setTextFill(COLOR_DARK);
            lien.setBackground(new Background(new BackgroundFill(COLOR_PRIMARY, CornerRadii.EMPTY, Insets.EMPTY)));
            Animations.animateButton(lien, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
        }
        btnUsers.setTextFill(Color.WHITE);


        loadInterface(Constants.FXML_BACK_HOME);
    }


    @FXML
    private void afficherUsers(ActionEvent ignored) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_USER);

        btnUsers.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnUsers, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }
    @FXML
    private void afficherComments(ActionEvent ignored) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_Comments);

        btnUsers.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnUsers, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }
    @FXML
    private void afficherSociete(ActionEvent ignored) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_Societ);

        btnUsers.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnUsers, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }
    @FXML
    private void afficherPostes(ActionEvent ignored) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_Posts);

        btnUsers.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnUsers, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }
    @FXML
    private void afficherOpportunites(ActionEvent ignored) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_OPPORTUNITE);

        btnOpportunites.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnOpportunites, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }

    @FXML
    private void afficherTests(ActionEvent ignored) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_TEST);

        btnTests.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnTests, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }

    @FXML
    public void logout(ActionEvent ignored) {
        MainApp.getInstance().logout();
    }

    private void goToLink(String fxmlLink) {
        for (Button lien : liens) {
            lien.setTextFill(COLOR_DARK);
            Animations.animateButton(lien, COLOR_GRAY, COLOR_DARK, COLOR_PRIMARY, 0, false);
        }
        loadInterface(fxmlLink);
    }

    public void loadInterface(String location) {
        staticContent.getChildren().clear();
        if (getClass().getResource(location) == null) {
            System.out.println("Could not load FXML check the path");
        } else {
            try {
                Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(location)));
                AnchorPane.setTopAnchor(parent, 0.0);
                AnchorPane.setBottomAnchor(parent, 0.0);
                AnchorPane.setRightAnchor(parent, 0.0);
                AnchorPane.setLeftAnchor(parent, 0.0);
                staticContent.getChildren().add(parent);
            } catch (IOException e) {
                System.out.println("Could not load FXML : " + e.getMessage() + " check your controller");
                e.printStackTrace();
            }
        }
    }

    public void afficherPartenaire(ActionEvent actionEvent) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_Partenaire);

        btnPartenaire.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnUsers, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }

    public void afficherEvenements(ActionEvent actionEvent) {
        goToLink(Constants.FXML_BACK_DISPLAY_ALL_Back_Event);

        btnEvent.setTextFill(COLOR_PRIMARY);
        Animations.animateButton(btnEvent, COLOR_GRAY, Color.WHITE, COLOR_PRIMARY, 0, false);
    }
}
