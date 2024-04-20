package Controller;

import entities.Societe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import services.ServiceSociete;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Optional;

public class afficherSociete implements Initializable {

    @FXML
    private ListView<Societe> societeListView;

    private ObservableList<Societe> societeList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize the list view and populate it with Societe data
        societeList = FXCollections.observableArrayList();
        societeListView.setItems(societeList);
        societeListView.getStyleClass().add("societeListView");

        // Set custom cell factory
        societeListView.setCellFactory(param -> new ListCell<Societe>() {
            @Override
            protected void updateItem(Societe societe, boolean empty) {
                super.updateItem(societe, empty);
                if (empty || societe == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Create a label to display the Societe information
                    Label label = new Label(societe.getNomSociete() + " - " + societe.getNumTelephone() + " - " + societe.getAdress());

                    // Create button
                    Button button = new Button("Modifier");
                    button.setOnAction(event -> {
                        // Get the selected Societe object
                        Societe selectedSociete = getItem();

                        // Call handleModifierButton method with the selected Societe object
                        handleModifierButton(selectedSociete);
                    });

                    // Create an HBox to contain the label and button horizontally
                    HBox hbox = new HBox(label, button);
                    hbox.setSpacing(10); // Set spacing between label and button

                    // Set HBox as the cell's graphic
                    setGraphic(hbox);
                }
            }
        });

        // Fetch Societe data from the database and add it to the list
        loadSocietes();

        // Apply stylesheet
        societeListView.getStylesheets().add(getClass().getResource("/Afficher.css").toExternalForm());
    }

    private void loadSocietes() {
        ServiceSociete serviceSociete = new ServiceSociete();
        try {
            // Assuming 'afficher' method returns a list of Societe objects
            societeList.addAll(serviceSociete.afficher());
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exception
        }
    }

    // Method to handle the "Modifier" button click
    public void handleModifierButton(Societe societe) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierSociete.fxml"));
            Parent root = loader.load();
            ModifierSociete modifierSocieteController = loader.getController();
            modifierSocieteController.setSocieteData(societe); // Pass selected Societe object to ModifierSociete controller
            Stage currentStage = (Stage) societeListView.getScene().getWindow();
            currentStage.setScene(new Scene(root));
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

    @FXML
    void supprimerSociete() {
        Societe selectedSociete = societeListView.getSelectionModel().getSelectedItem();
        if (selectedSociete != null) {
            Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationDialog.setTitle("Confirmation de suppression");
            confirmationDialog.setHeaderText("Supprimer la société ?");
            confirmationDialog.setContentText("Voulez-vous vraiment supprimer cette société ?");

            Optional<ButtonType> result = confirmationDialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    ServiceSociete serviceSociete = new ServiceSociete();
                    serviceSociete.supprimer(selectedSociete);

                    societeList.remove(selectedSociete);
                    showAlert(Alert.AlertType.INFORMATION, "Suppression réussie", "La société a été supprimée avec succès.");
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la suppression de la société : " + e.getMessage());
                }
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Aucune sélection", "Veuillez sélectionner une société à supprimer.");
        }
    }
}
