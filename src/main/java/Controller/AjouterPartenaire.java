package Controller;

import entities.Partenaire;
import entities.Societe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServicePartenaire;
import services.ServiceSociete;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Set;

public class AjouterPartenaire {

    @FXML
    private TextField nomField;

    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField dureeField;
    @FXML
    private ChoiceBox<Societe> societeChoiceBox;

    private final ServicePartenaire servicePartenaire = new ServicePartenaire();
    private final ServiceSociete serviceSociete = new ServiceSociete();

    @FXML
    public void initialize() {
        loadSocietes();
    }

    private void loadSocietes() {
        try {
            Set<Societe> societes = serviceSociete.afficher();
            societeChoiceBox.getItems().addAll(societes);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ajouterPartenaire(ActionEvent event) {
        String nom = nomField.getText();
        String description = descriptionField.getText();
        String duree = dureeField.getText();
        Societe selectedSociete = societeChoiceBox.getValue();
        int id =selectedSociete.getId();

        if (nom.isEmpty() || description.isEmpty() || selectedSociete == null) {
            showAlert(Alert.AlertType.WARNING, "Champs obligatoires", "OUPS ! Vous avez oublié des champs vides ou vous n'avez pas sélectionné de société.");
            return;
        }

        Partenaire partenaire = new Partenaire(nom, description,duree,id);
        partenaire.setSociete(selectedSociete);

        try {
            servicePartenaire.ajouter(partenaire);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Le partenaire a été ajouté avec succès.");
            clearFields();

            // Load the partner list view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPartenaire.fxml"));
            Parent root = loader.load();

            AfficherPartenaire afficherPartenaireController = loader.getController();
            afficherPartenaireController.initialize(null, null); // Call initialize method of the controller

            Stage currentStage = (Stage) nomField.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException | SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'ajout du partenaire.");
        }
    }


    private void clearFields() {
        nomField.clear();
        descriptionField.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
