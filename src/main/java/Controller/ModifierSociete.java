package Controller;

import entities.Societe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceSociete;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierSociete {

    private Societe currentSociete;

    @FXML
    private TextField fNS;

    @FXML
    private TextField fNT;

    @FXML
    private TextField fA;

    private boolean validateFields() {
        if (fNS.getText().isEmpty() || fNT.getText().isEmpty() || fA.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Champs obligatoires", "OUPS ! Vous Avez Oublié Des Champs Vides");
            return false;
        }
        return true;
    }

    private void showAlert(Alert.AlertType type, String title, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    @FXML
    void modifierSociete(ActionEvent event) {
        if (!validateFields()) {
            return;
        }
        if (currentSociete == null) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Aucune société sélectionnée");
            return;
        }
        currentSociete.setNomSociete(fNS.getText());
        // Assuming numTelephone is of type int in Societe class
        currentSociete.setNumTelephone(Integer.parseInt(fNT.getText()));
        currentSociete.setAdress(fA.getText());

        ServiceSociete serviceSociete = new ServiceSociete();
        try {
            serviceSociete.modifier(currentSociete);
            returnToSocieteListView(event);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification de la société : " + e.getMessage());
        }
    }

    @FXML
    void retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherSociete.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setSocieteData(Societe societe) {
        this.currentSociete = societe;
        if (societe != null) {
            fNS.setText(societe.getNomSociete());
            fNT.setText(String.valueOf(societe.getNumTelephone()));
            fA.setText(societe.getAdress());
        } else {
            fNS.setText("");
            fNT.setText("");
            fA.setText("");
        }
    }

    @FXML
    void returnToSocieteListView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/afficherSociete.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
