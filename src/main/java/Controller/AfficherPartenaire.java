package Controller;

import entities.Partenaire;
import entities.Societe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.ComboBox;
import services.ServicePartenaire;

import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AfficherPartenaire implements Initializable {

    @FXML
    private ListView<Partenaire> partenaireListView;

    @FXML
    private TextField nomTextField;

    @FXML
    private TextArea descriptionTextArea;




    private ObservableList<Partenaire> partenaireList;
    private final ServicePartenaire servicePartenaire = new ServicePartenaire();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partenaireList = FXCollections.observableArrayList();
        partenaireListView.setItems(partenaireList);

        partenaireListView.setCellFactory(param -> new ListCell<Partenaire>() {
            @Override
            protected void updateItem(Partenaire partenaire, boolean empty) {
                super.updateItem(partenaire, empty);
                if (empty || partenaire == null) {
                    setText(null);
                } else {
                    setText("Nom: " + partenaire.getNom() + ", Description: " + partenaire.getDescription() + ", Societe ID: " + partenaire.getSociete().getId());
                }
            }
        });

        afficherPartenaires();
        initSocieteComboBox();
    }

    @FXML
    private void ajouterPartenaire() {
        String nom = nomTextField.getText();
        String description = descriptionTextArea.getText();

        if (nom.isEmpty() || description.isEmpty() ) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        Partenaire partenaire = new Partenaire(nom, description);
        try {
            servicePartenaire.ajouter(partenaire);
            afficherPartenaires();
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout du partenaire.");
        }
    }

    @FXML
    private void supprimerPartenaire() {
        Partenaire selectedPartenaire = partenaireListView.getSelectionModel().getSelectedItem();
        if (selectedPartenaire != null) {
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation de suppression");
            confirmationDialog.setHeaderText("Supprimer le partenaire ?");
            confirmationDialog.setContentText("Voulez-vous vraiment supprimer ce partenaire ?");

            Optional<ButtonType> result = confirmationDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    servicePartenaire.supprimer(selectedPartenaire);
                    partenaireList.remove(selectedPartenaire);
                    showAlert(Alert.AlertType.INFORMATION, "Suppression réussie", "Le partenaire a été supprimé avec succès.");
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression du partenaire : " + e.getMessage());
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner un partenaire à supprimer.");
        }
    }

    private void afficherPartenaires() {
        try {
            partenaireList.clear();
            partenaireList.addAll(servicePartenaire.afficher());
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'affichage des partenaires : " + e.getMessage());
        }
    }

    private void clearFields() {
        nomTextField.clear();
        descriptionTextArea.clear();

    }

    private void initSocieteComboBox() {
        try {
            ObservableList<Societe> societeObservableList = FXCollections.observableArrayList(servicePartenaire.getAllSocietes());

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des sociétés : " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String title, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
