package com.example.jobflow.controllers.front.opportunite;

import com.example.jobflow.controllers.front.MainWindowController;
import com.example.jobflow.entities.Opportunite;
import com.example.jobflow.services.OpportuniteService;
import com.example.jobflow.utils.AlertUtils;
import com.example.jobflow.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
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

    public static Opportunite currentOpportunite;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public VBox mainVBox;
    @FXML
    public ComboBox<String> sortCB;
    @FXML
    public TextField searchField;

    List<Opportunite> listOpportunite;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listOpportunite = OpportuniteService.getInstance().getAll();

        sortCB.getItems().addAll(
                "Tri par nom",
                "Tri par descreption",
                "Tri par type"
        );

        displayData("");
    }

    void displayData(String searchText) {
        mainVBox.getChildren().clear();

        Collections.reverse(listOpportunite);

        if (!listOpportunite.isEmpty()) {
            for (Opportunite opportunite : listOpportunite) {
                if (
                        searchText.isEmpty()
                                || opportunite.getNom().toLowerCase().contains(searchText.toLowerCase())
                                || opportunite.getDescreption().toLowerCase().contains(searchText.toLowerCase())
                                || opportunite.getType().toLowerCase().contains(searchText.toLowerCase())
                ) {
                    mainVBox.getChildren().add(makeOpportuniteModel(opportunite));
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

    public Parent makeOpportuniteModel(
            Opportunite opportunite
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_OPPORTUNITE)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#nomText")).setText("Nom : " + opportunite.getNom());
            ((Text) innerContainer.lookup("#descreptionText")).setText("Descreption : " + opportunite.getDescreption());
            ((Text) innerContainer.lookup("#typeText")).setText("Type : " + opportunite.getType());
            ((Text) innerContainer.lookup("#isFavoriteText")).setText("IsFavorite : " + opportunite.getIsFavorite());

            ((Text) innerContainer.lookup("#testText")).setText("Test : " + opportunite.getTest().toString());
            ((Text) innerContainer.lookup("#userText")).setText("User : " + opportunite.getUser().toString());


            ((Button) innerContainer.lookup("#editButton")).setOnAction((ignored) -> modifierOpportunite(opportunite));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((ignored) -> supprimerOpportunite(opportunite));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterOpportunite(ActionEvent ignored) {
        currentOpportunite = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_OPPORTUNITE);
    }

    private void modifierOpportunite(Opportunite opportunite) {
        currentOpportunite = opportunite;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_OPPORTUNITE);
    }

    private void supprimerOpportunite(Opportunite opportunite) {
        currentOpportunite = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer opportunite ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                if (OpportuniteService.getInstance().delete(opportunite.getId())) {
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_OPPORTUNITE);
                } else {
                    AlertUtils.makeError("Could not delete opportunite");
                }
            }
        }
    }

    public void sort(ActionEvent actionEvent) {
        Opportunite.compareVar = sortCB.getValue();
        Collections.sort(listOpportunite);
        displayData(searchField.getText());
    }

    public void search(KeyEvent keyEvent) {
        displayData(searchField.getText());
    }
}