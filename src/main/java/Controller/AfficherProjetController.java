package Controller;

import entite.projet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import service.ProjetService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AfficherProjetController implements Initializable {

    @FXML
    private ListView<projet> listProjet;

    @FXML
    private Button modifierProjet;

    @FXML
    private Button retour;

    @FXML
    private Button supprimerProjet;

    @FXML
    private TextField searchField;

    private ObservableList<projet> projetList;

    private ProjetService projetService; // Add this line
    private Object connection;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listProjet.setCellFactory(listView -> new ListCell<projet>() {
            @Override
            protected void updateItem(projet projet, boolean empty) {
                super.updateItem(projet, empty);
                if (empty || projet == null) {
                    setText(null);
                } else {
                    setText(String.format(" %-25s %-25s %-25s", projet.getPrname(), projet.getStdate(), projet.getEnddate()));
                }
            }
        });

        loadProjet();
    }

    public void setProjetService(ProjetService projetService) { // Add this method
        this.projetService = projetService;
    }

    private void loadProjet() {
        ProjetService serviceProjet = new ProjetService();
        try {
            projetList = FXCollections.observableArrayList(serviceProjet.afficher());
            listProjet.setItems(projetList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des projets", e.getMessage());
        }
    }

    @FXML
    void ajouterProjet(ActionEvent event) {
        // Implement the ajouterProjet method to add a new Projet
    }

    @FXML
    void actualiserAction(ActionEvent event) {
        loadProjet();
    }

    @FXML
    public void modifierProjet(ActionEvent event) {
        projet selectedProjet = listProjet.getSelectionModel().getSelectedItem();
        if (selectedProjet != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierProjet.fxml"));
                Parent root = loader.load();

                ModifierProjetController modifierProjetController = loader.getController();
                modifierProjetController.setProjetService(new ProjetService()); // Create and set the service
                modifierProjetController.setProjetData(selectedProjet);

                Stage stage = new Stage();
                stage.setTitle("Modifier Projet");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText("Veuillez sélectionner un projet à modifier.");
            alert.showAndWait();
        }
    }
    @FXML
    void retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
            Parent root = loader.load();

            Stage newStage = new Stage();
            newStage.setTitle("Main Window");

            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.show();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
private final ProjetService pro = new ProjetService();
    @FXML
    void delete(ActionEvent event) {
        ProjetService projetService = new ProjetService();
        // Get the selected item in the ListView
        projet selectedProjet = listProjet.getSelectionModel().getSelectedItem();
        if (selectedProjet != null) {
            try {
                // Delete the project from the database using the appropriate service
                pro.supprimer(selectedProjet.getId());

                // Also remove the project from the displayed list in the ListView
                listProjet.getItems().remove(selectedProjet);
            } catch (SQLException e) {
                // Handle SQL exceptions, e.g., display an error message
                e.printStackTrace();
                // Show an error message to the user
                showAlert("Erreur de suppression", "Une erreur s'est produite lors de la suppression de l'employé.");
            }
        } else {
            // Show a message to the user if no project is selected
            showAlert("Aucun employé sélectionné", "Veuillez sélectionner un employé à supprimer.");
        }
    }

    private void showAlert(String erreurDeSuppression, String s) {
    }

    @FXML
    void rechercherParNom() {
        String keyword = searchField.getText().toLowerCase();
        ObservableList<projet> filteredList = FXCollections.observableArrayList();

        for (projet projet : projetList) {
            if (projet.getPrname().toLowerCase().contains(keyword)) {
                filteredList.add(projet);
            }
        }

        listProjet.setItems(filteredList);
    }

    private void showAlert(Alert.AlertType type, String title, String contentText) {
        showAlert(type, title, null, contentText);
    }

    private void showAlert(Alert.AlertType type, String title, String headerText, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }




}