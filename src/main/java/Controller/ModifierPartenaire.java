package Controller;

import entities.Partenaire;
import entities.Societe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServicePartenaire;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifierPartenaire implements Initializable {

    @FXML
    private TextField nomField;

    @FXML
    private TextArea descriptionField;

    private Partenaire currentPartenaire;

    private ServicePartenaire servicePartenaire = new ServicePartenaire();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setPartenaireData(Partenaire partenaire) {
        currentPartenaire = partenaire;
        nomField.setText(partenaire.getNom());
        descriptionField.setText(partenaire.getDescription());
    }

    @FXML
    public void modifierPartenaire(ActionEvent actionEvent) {
        if (!validateFields()) {
            return;
        }

        currentPartenaire.setNom(nomField.getText());
        currentPartenaire.setDescription(descriptionField.getText());
        Societe societe = currentPartenaire.getSociete(); // Retrieve the current societe
        currentPartenaire.setSociete(societe);

        try {
            servicePartenaire.modifier(currentPartenaire);
            showAlert(Alert.AlertType.INFORMATION, "Modification réussie", "Le partenaire a été modifié avec succès.");
            navigateToPartnerList(actionEvent);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification du partenaire : " + e.getMessage());
        }
    }

    private void navigateToPartnerList(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPartenaire.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean validateFields() {
        if (nomField.getText().isEmpty() || descriptionField.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Champs obligatoires", "Veuillez remplir tous les champs.");
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

    private void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
    @FXML
    private void retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPartenaire.fxml"));
            Parent root = loader.load();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
