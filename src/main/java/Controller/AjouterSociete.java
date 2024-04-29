package Controller;


import com.google.protobuf.Message;
import entities.Societe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceSociete;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterSociete {

    private final ServiceSociete serviceSociete = new ServiceSociete();

    @FXML
    private Button btnsave;

    @FXML
    private TextField fNS;

    @FXML
    private TextField fA;

    @FXML
    private TextField fNT;

    @FXML
    private TextField fD;

    @FXML
    private TextField fSW;

    @FXML
    private TextField fS;

    @FXML
    void createSociete(ActionEvent event) {
        String nomSociete = fNS.getText().trim();
        String numTelephoneStr = fNT.getText().trim();
        String adress = fA.getText().trim();
        String description = fD.getText().trim();
        String siteweb = fSW.getText().trim();
        String secteur = fS.getText().trim();

        if (nomSociete.isEmpty() || numTelephoneStr.isEmpty() || adress.isEmpty() || description.isEmpty() || siteweb.isEmpty() || secteur.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Champs Obligatoires", "OUPS ! Vous avez oublié des champs vides.");
            return;
        }

        try {
            int numTelephone = Integer.parseInt(numTelephoneStr);
            serviceSociete.ajouter(new Societe(nomSociete, adress, description, siteweb, numTelephone, secteur));
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Société ajoutée avec succès.");
            navigateToAfficherSociete(event);
            clearFields();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le numéro de téléphone doit être un nombre.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur SQL", e.getMessage());
        }
    }

    private void navigateToAfficherSociete(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherSociete.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnsave.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private void clearFields() {
        fNS.clear();
        fNT.clear();
        fA.clear();
        fD.clear();
        fSW.clear();
        fS.clear();
    }

}
