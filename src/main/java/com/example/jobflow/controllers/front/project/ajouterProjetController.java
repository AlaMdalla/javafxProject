package com.example.jobflow.controllers.front.project;

import com.example.jobflow.entities.projet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.jobflow.services.ProjetService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class ajouterProjetController {
    ProjetService serviceProjet = new ProjetService();

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField projectNameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField typeField;


    @FXML
    void ajouterProjet(ActionEvent event) {
        String projectName = projectNameField.getText().trim();
        String description = descriptionField.getText().trim();
        String type = typeField.getText().trim();
        LocalDate startDateValue = startDatePicker.getValue();
        LocalDate endDateValue = endDatePicker.getValue();

        if (projectName.isEmpty() || description.isEmpty() || type.isEmpty() || startDateValue == null || endDateValue == null) {
            showAlert(Alert.AlertType.ERROR, "Champs Obligatoires ", "OUPS ! Vous Avez Oublié Des Champs Vides");
            return;
        }

        String startDate = startDateValue.toString();
        String endDate = endDateValue.toString();

        if (endDate.compareTo(startDate) < 0) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "La date de fin doit être après la date de début.");
            return;
        }

        try {
            projet newProjet = new projet(0, projectName, description, type, startDate, endDate);
            serviceProjet.ajouter(newProjet);

            // Add logic for sending SMS or email if needed

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Projet ajouté avec succès.");
            goToAfficherProjet(event);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur SQL", e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void goToAfficherProjet(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/AfficherProjets.fxml"));
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    private void showAlert(Alert.AlertType type, String title, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    void retour(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/MainWindow.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean isValidChatName(String chatname) {
        // Implement your validation logic for chat name
        // For example, check if it contains only alphanumeric characters
        return chatname.matches("[a-zA-Z0-9]+");
    }

    private boolean isValidUsername(String username) {
        // Implement your validation logic for username
        // For example, check if it has a minimum length requirement
        return username.length() >= 4;

    }
}
