package Controller;

import entities.Partenaire;
import entities.Societe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import services.ServicePartenaire;
import services.ServiceSociete;

import java.sql.SQLException;
import java.util.Set;

public class AjouterPartenaire {

    @FXML
    private TextField nomField;

    @FXML
    private TextArea descriptionField;

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
        Societe selectedSociete = societeChoiceBox.getValue();

        if (nom.isEmpty() || description.isEmpty() || selectedSociete == null) {
            showAlert(Alert.AlertType.WARNING, "Champs obligatoires", "OUPS ! Vous avez oublié des champs vides ou vous n'avez pas sélectionné de société.");
            return;
        }

        Partenaire partenaire = new Partenaire(nom, description);
        partenaire.setSociete(selectedSociete);

        try {
            servicePartenaire.ajouter(partenaire);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Le partenaire a été ajouté avec succès.");
            clearFields();
        } catch (SQLException e) {
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
