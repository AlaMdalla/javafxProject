package com.example.jobflow.controllers.back.societe;

import com.example.jobflow.entities.Societe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.jobflow.services.ServiceSociete;

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

    @FXML
    private TextField fD;

    @FXML
    private TextField fSW;

    @FXML
    private TextField fS;

    private boolean validateFields() {
        if (fNS.getText().isEmpty() || fNT.getText().isEmpty() || fA.getText().isEmpty() || fD.getText().isEmpty() || fSW.getText().isEmpty() || fS.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Champs obligatoires", "OUPS ! Vous avez oublié des champs vides.");
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
        currentSociete.setNom(fNS.getText());
        currentSociete.setNumtel(Integer.parseInt(fNT.getText()));
        currentSociete.setLocalisation(fA.getText());
        currentSociete.setDescription(fD.getText());
        currentSociete.setSiteweb(fSW.getText());
        currentSociete.setSecteur(fS.getText());

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
        returnToSocieteListView(event);
    }

    public void setSocieteData(Societe societe) {
        this.currentSociete = societe;
        if (societe != null) {
            fNS.setText(societe.getNom());
            fNT.setText(String.valueOf(societe.getNumtel()));
            fA.setText(societe.getLocalisation());
            fD.setText(societe.getDescription());
            fSW.setText(societe.getSiteweb());
            fS.setText(societe.getSecteur());
        } else {
            fNS.setText("");
            fNT.setText("");
            fA.setText("");
            fD.setText("");
            fSW.setText("");
            fS.setText("");
        }
    }

    @FXML
    void returnToSocieteListView(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/jobflow/back/societe/afficherSociete.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
