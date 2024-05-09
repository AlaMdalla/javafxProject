package com.example.jobflow.controllers.front.societe;

import com.example.jobflow.entities.Societe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import com.example.jobflow.services.ServiceSociete;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

public class afficherSociete implements Initializable {

    @FXML
    private ListView<Societe> societeListView;
    @FXML
    private TextField searchField;

    private ObservableList<Societe> societeList;
    private ObservableList<Societe> originalSocieteList; // Store the original list of Societe objects

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("entred");
        // Initialize the list view and populate it with Societe data
        societeList = FXCollections.observableArrayList();
        societeListView.setItems(societeList);
        societeListView.getStyleClass().add("societeListView");

        // Set custom cell factory
        societeListView.setCellFactory(param -> new ListCell<Societe>() {
            @Override
            protected void updateItem(Societe societe, boolean empty) {
                super.updateItem(societe, empty);
                if (empty || societe == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Create a label to display the Societe information
                    Label label = new Label(societe.getNom() + " - " + societe.getNumtel() + " - " + societe.getLocalisation() + " - " + societe.getDescription()+ " - " + societe.getSecteur()+ " - " + societe.getSiteweb());

                    // Create button


                    // Create an HBox to contain the label and button horizontally
                    HBox hbox = new HBox(label);
                    hbox.setSpacing(10); // Set spacing between label and button

                    // Set HBox as the cell's graphic
                    setGraphic(hbox);
                }
            }
        });

        // Fetch Societe data from the database and add it to the list
        loadSocietes();

        // Apply stylesheet

        // Store the original list of Societe objects
        originalSocieteList = FXCollections.observableArrayList(societeList);

        // Add listener to search field
        searchField.textProperty().addListener((observable, oldValue, newValue) -> rechercherParTitre());
    }

    private void loadSocietes() {
        ServiceSociete serviceSociete = new ServiceSociete();
        try {
            societeList.addAll(serviceSociete.afficher());
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

    // Method to handle the "Modifier" button click


    private void showAlert(Alert.AlertType type, String title, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }



    @FXML
    void rechercherParTitre() {
        String recherche = searchField.getText().toLowerCase();
        ObservableList<Societe> resultatRecherche = FXCollections.observableArrayList();
        for (Societe societe : originalSocieteList) {
            if (societe.getNom().toLowerCase().contains(recherche)) {
                resultatRecherche.add(societe);
            }
        }

        societeListView.setItems(resultatRecherche);
    }

    @FXML
    void trierParMatiere() {
        societeList.sort(Comparator.comparing(Societe::getNom));
    }

    @FXML
    void resetList(ActionEvent event) {
        societeListView.setItems(originalSocieteList);
        searchField.clear();
    }

}
