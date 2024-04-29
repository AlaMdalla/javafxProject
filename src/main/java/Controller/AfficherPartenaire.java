package Controller;

import entities.Partenaire;
import entities.Societe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.ServicePartenaire;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.Optional;
import java.util.ResourceBundle;

public class AfficherPartenaire implements Initializable {

    @FXML
    private ListView<Partenaire> partenaireListView;

    @FXML
    private TextField nomTextField;

    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private TextArea dureeTextArea;
    private ObservableList<Partenaire> partenaireList;
    private final ServicePartenaire servicePartenaire = new ServicePartenaire();
    @FXML
    private TextField searchField;
    private Partenaire currentPartenaire;
    private ObservableList<Partenaire> originalPartenaireList; // Store the original list of Partenaire objects

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
                    Button modifierButton = new Button("Modifier");
                    modifierButton.setOnAction(event -> {
                        try {
                            modifierPartenaire(partenaire);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    setText("Nom: " + partenaire.getNom() + ", Description: " + partenaire.getDescription() + ",duree:" + partenaire.getDuree() + ", Societe ID: " + partenaire.getSociete().getId());
                    setGraphic(modifierButton);
                }
            }
        });

        afficherPartenaires();
        initSocieteComboBox();

        // Store the original list of Partenaire objects
        originalPartenaireList = FXCollections.observableArrayList(partenaireList);
    }

    @FXML
    private void ajouterPartenaire() {
        String nom = nomTextField.getText();
        String description = descriptionTextArea.getText();
        String duree = dureeTextArea.getText();
        if (nom.isEmpty() || description.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        Partenaire partenaire = new Partenaire(nom, description, duree);
        try {
            servicePartenaire.ajouter(partenaire);
            afficherPartenaires(); // Refresh partner list
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout du partenaire.");
            return; // Return to prevent further execution
        }

        // Load the partner list view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPartenaire.fxml"));
        Parent root;
        try {
            root = loader.load();
            AfficherPartenaire afficherPartenaireController = loader.getController();
            afficherPartenaireController.initialize(null, null); // Call initialize method of the controller
            Stage currentStage = (Stage) nomTextField.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de la liste des partenaires.");
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

    private void modifierPartenaire(Partenaire partenaire) throws IOException {
        currentPartenaire = partenaire;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierPartenaire.fxml"));
        Parent root = loader.load();

        ModifierPartenaire modifierPartenaireController = loader.getController();
        modifierPartenaireController.setPartenaireData(partenaire);

        Stage currentStage = (Stage) partenaireListView.getScene().getWindow();
        currentStage.setScene(new Scene(root));
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

    public void rechercherParTitre() {
        String recherche = nomTextField.getText().toLowerCase();
        ObservableList<Partenaire> partenaires = partenaireListView.getItems();
        ObservableList<Partenaire> resultatRecherche = FXCollections.observableArrayList();
        for (Partenaire partenaire : partenaires) {
            if (partenaire.getNom().toLowerCase().contains(recherche)) {
                resultatRecherche.add(partenaire);
            }
        }

        partenaireListView.setItems(resultatRecherche);
    }

    @FXML
    void trierParMatiere() {
        partenaireList.sort(Comparator.comparing(Partenaire::getNom));
    }

    @FXML
    void resetList() {
        partenaireListView.setItems(originalPartenaireList);
        nomTextField.clear();
    }
}
