package com.example.jobflow.controllers.front.test;

import com.example.jobflow.controllers.front.MainWindowController;
import com.example.jobflow.entities.Test;
import com.example.jobflow.services.TestService;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.Collections;
import java.util.Comparator;

public class ShowAllController implements Initializable {

    public static Test currentTest;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public VBox mainVBox;
    @FXML
    private TextField searchField;

    List<Test> listTest;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listTest = TestService.getInstance().getAll();

        displayData();
    }

    void displayData() {
        mainVBox.getChildren().clear();

        Collections.reverse(listTest);

        if (!listTest.isEmpty()) {
            for (Test test : listTest) {

                mainVBox.getChildren().add(makeTestModel(test));

            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeTestModel(
            Test test
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_TEST)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#typeText")).setText("Type : " + test.getType());
            ((Text) innerContainer.lookup("#scoreText")).setText("Score : " + test.getScore());
            ((Text) innerContainer.lookup("#dureeText")).setText("Duree : " + test.getDuree());


            ((Button) innerContainer.lookup("#editButton")).setOnAction((ignored) -> modifierTest(test));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((ignored) -> supprimerTest(test));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterTest(ActionEvent ignored) {
        currentTest = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_TEST);
    }

    private void modifierTest(Test test) {
        currentTest = test;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_TEST);
    }

    private void supprimerTest(Test test) {
        currentTest = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer test ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                if (TestService.getInstance().delete(test.getId())) {
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_TEST);
                } else {
                    AlertUtils.makeError("Could not delete test");
                }
            }
        }
    }



    @FXML
    private void rechercheParType() {
        String typeRecherche = searchField.getText().trim().toLowerCase(); // Obtenez le texte de recherche et convertissez-le en minuscules

        // Filtrer la liste des tests en fonction du type
        List<Test> testsFiltres = listTest.stream()
                .filter(test -> test.getType().toLowerCase().contains(typeRecherche))
                .toList();

        // Afficher les tests filtrés
        mainVBox.getChildren().clear();
        if (!testsFiltres.isEmpty()) {
            for (Test test : testsFiltres) {
                mainVBox.getChildren().add(makeTestModel(test));
            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée correspondant au type de recherche"));
            mainVBox.getChildren().add(stackPane);
        }
    }
    @FXML
    void trierParType() {
        // Utilisez un comparateur personnalisé pour trier les tests par type de Z à A
        Collections.sort(listTest, Comparator.comparing(Test::getType, Comparator.reverseOrder()));

        // Mettez à jour l'affichage après le tri
        displayData();
    }



}
