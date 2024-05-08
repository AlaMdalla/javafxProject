package Controller;

import entite.projet;
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
import service.ProjetService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class ModifierProjetController {

    private projet currentProjet;

    @FXML
    private TextField nomProjetField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField typeField;

    @FXML
    private DatePicker stdateField;

    @FXML
    private DatePicker endateField;

    private ProjetService projetService;

    public void setProjetService(ProjetService projetService) {
        this.projetService = projetService;
    }

    public void setProjetData(projet selectedProjet) {
        this.currentProjet = selectedProjet;
        nomProjetField.setText(selectedProjet.getPrname());
        descriptionField.setText(selectedProjet.getDescription());
        typeField.setText(selectedProjet.getType());
        stdateField.setValue(LocalDate.parse(selectedProjet.getStdate()));
        endateField.setValue(LocalDate.parse(selectedProjet.getEnddate()));
    }

    @FXML
    void modifierProjet(ActionEvent event) {
        if (currentProjet == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Le projet à modifier est null.");
            return;
        }

        String projectName = nomProjetField.getText().trim();
        String description = descriptionField.getText().trim();
        String type = typeField.getText().trim();
        LocalDate stDate = stdateField.getValue();
        LocalDate endDate = endateField.getValue();

        if (projectName.isEmpty() || description.isEmpty() || type.isEmpty() || stDate == null || endDate == null) {
            showAlert(Alert.AlertType.ERROR, "Champs Obligatoires", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            projet modifiedProjet = new projet(currentProjet.getId(), projectName, description, type, stDate.toString(), endDate.toString());
            projetService.modifier(modifiedProjet);
            showAlert(Alert.AlertType.INFORMATION, "Modification réussie", "Le projet a été modifié avec succès.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la modification du projet : " + e.getMessage());
        }
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

    private void showAlert(Alert.AlertType type, String title, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}