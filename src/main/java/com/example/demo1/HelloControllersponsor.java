package com.example.demo1;

import javafx.scene.control.Alert;
import javafx.scene.layout.*;
import utiles.DBconnexion;
import entities.sponsor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Node;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class HelloControllersponsor implements Initializable {
    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    String url ="";
    @FXML
    private TextField tNom;

    @FXML
    private TextField tType;

    @FXML
    private Button btnsavesponsor;

    @FXML
    private FlowPane eventFlowPane;
    private HBox eventCard;
    @FXML
    private AnchorPane sponsorPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        con = DBconnexion.getCon();
        showSponsors();
    }

    public ObservableList<sponsor> getSponsor() {
        ObservableList<sponsor> sponsors = FXCollections.observableArrayList();
        String query = "SELECT * FROM Sponsor";

        try {
            con = DBconnexion.getCon();
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                sponsor sponsor = new sponsor();
                sponsor.setId_sponsor(rs.getInt("id_sponsor"));
                sponsor.setNom(rs.getString("Nom"));
                sponsor.setType(rs.getString("Type"));
                sponsors.add(sponsor);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des sponsors", e);
        }
        return sponsors;
    }
    public void showSponsors() {
        ObservableList<sponsor> list = getSponsor();

        // Supprimez le contenu existant du FlowPane
        eventFlowPane.getChildren().clear();

        // Pour chaque sponsor, créez une carte et ajoutez-la au FlowPane
        for (sponsor sponsor : list) {
            HBox card = showSponsor(sponsor);

            eventFlowPane.getChildren().add(card);
        }
    }

    private HBox showSponsor(sponsor sponsor) {
        HBox card = new HBox();
        card.getStyleClass().add("event-card");
        card.setPrefSize(500, 100); // Définir une taille préférée pour la carte
        VBox root = new VBox();
        Scene scene = new Scene(root, 800, 600);
        eventCard = card;

        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        // Créer un conteneur pour les détails du sponsor
        VBox detailsBox = new VBox();
        detailsBox.setSpacing(10.0);
        detailsBox.setPrefSize(300, 150);

        // Créer le bouton de suppression
        Button deleteButton = new Button("X");
        deleteButton.setOnAction(this::deleteSponsor);
        deleteButton.getStyleClass().add("button-delete"); // Ajouter la classe de style CSS au bouton
        deleteButton.setUserData(sponsor); // Stockez le sponsor associé dans les données du bouton
        Button btnUpdates = new Button("U");
        // Créer le bouton de mise à jour
        btnUpdates.getStyleClass().add("button-update"); // Ajouter la classe de style CSS au bouton


        root.getChildren().add(btnUpdates);
        btnUpdates.setOnMouseClicked(mouseEvent -> {
            // Get the selected event to update
            sponsor selectedEvent = (sponsor) btnUpdates.getUserData();

            if (selectedEvent != null) {
                try {
                    //Charger le fichier update.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("modifiersponsor.fxml"));
                    Parent roott = loader.load();
                    UpdateSponsor controller = loader.getController();

                    controller.initData(selectedEvent);
                    Scene scenee = new Scene(roott);

                    // Get the current stage and scene
                    Stage currentStage = (Stage) card.getScene().getWindow();


                    // Set the new scene
                    currentStage.setScene(scenee);
                    currentStage.setTitle("Update");
                    currentStage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Aucun sponsor sélectionné pour la mise à jour");
            }
        });


        btnUpdates.setUserData(sponsor);

        // Créer le bouton de mise à jour


        // Créer des labels pour les détails du sponsor
        Label sponsorNameLabel = new Label("Nom du sponsor: " + sponsor.getNom());
        Label sponsorTypeLabel = new Label("Type de sponsor: " + sponsor.getType());

        // Ajouter les labels à la boîte de détails
        detailsBox.getChildren().addAll(sponsorNameLabel, sponsorTypeLabel);

        // Ajouter les éléments à la carte
        card.getChildren().addAll(detailsBox, deleteButton ,btnUpdates);

        // Ajouter des marges entre les éléments de la carte
        HBox.setMargin(detailsBox, new Insets(10)); // Marge entre les détails et les boutons

        // Ajouter un gestionnaire d'événements sur la carte
        card.setOnMouseClicked(mouseEvent -> {
            // Mettez ici le code que vous souhaitez exécuter lorsque l'utilisateur clique sur la carte
            System.out.println("Carte de sponsor cliquée");
        });

        return card;
    }


    private void deleteSponsor(ActionEvent actionEvent) {
        Button deleteButton = (Button) actionEvent.getSource(); // Récupérer le bouton sur lequel l'utilisateur a cliqué
        sponsor sponsorToDelete = (sponsor) deleteButton.getUserData(); // Récupérer le sponsor associé au bouton

        // Vérifier si l'objet sponsor est null
        if (sponsorToDelete != null) {
            int sponsorId = sponsorToDelete.getId_sponsor(); // Récupérer l'ID du sponsor à supprimer

            String deleteQuery = "DELETE FROM Sponsor WHERE id_sponsor = ?";
            try {
                con = DBconnexion.getCon();
                PreparedStatement st = con.prepareStatement(deleteQuery);
                st.setInt(1, sponsorId);

                int rowsAffected = st.executeUpdate();
                showSponsors();

                if (rowsAffected > 0) {
                    // La suppression a réussi
                    System.out.println("Sponsor supprimé avec succès");

                    // Supprimer la carte du sponsor directement en utilisant la référence stockée
                    Node sponsorCard = (Node) deleteButton.getUserData(); // Récupérer la carte du sponsor depuis les données du bouton
                    if (sponsorCard != null && sponsorCard.getParentNode() != null) {
                        ((Pane) sponsorCard.getParentNode()).getChildren().remove(sponsorCard);
                    }
                } else {
                    // Aucune ligne n'a été supprimée, peut-être que l'ID n'existe pas dans la table
                    System.out.println("Aucun sponsor trouvé avec cet ID");
                }
            } catch (SQLException e) {
                // Gérer spécifiquement les exceptions SQL et fournir une rétroaction à l'utilisateur
                e.printStackTrace(); // À remplacer par une gestion appropriée des exceptions
            }
        } else {
            // Si sponsorToDelete est null, afficher un message d'erreur ou effectuer une action appropriée
            System.out.println("Aucun sponsor associé au bouton de suppression");
        }
    }

    @FXML
    void creatSponsor(ActionEvent event) {
        String nom = tNom.getText(); // Récupérer le nom du sponsor depuis le champ de texte
        String type = tType.getText(); // Récupérer le type du sponsor depuis le champ de texte

        // Vérifier que le nom et le type sont des chaînes de caractères non vides
        if (nom.isEmpty() || type.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Veuillez saisir le nom et le type du sponsor.");
            return; // Arrêter l'exécution de la méthode si les champs sont vides
        }

        // Vérifier que le nom et le type contiennent uniquement des lettres
        if (!nom.matches("[a-zA-Z]+") || !type.matches("[a-zA-Z]+")) {
            showAlert(Alert.AlertType.ERROR, "Le nom et le type du sponsor doivent contenir uniquement des lettres.");
            return; // Arrêter l'exécution de la méthode si les champs ne contiennent pas uniquement des lettres
        }

        String insertSponsor = "INSERT INTO Sponsor(nom, type) VALUES (?, ?)";

        try {
            con = DBconnexion.getCon();
            st = con.prepareStatement(insertSponsor);
            st.setString(1, nom);
            st.setString(2, type);

            st.executeUpdate();

            // Afficher une alerte pour confirmer que le sponsor a été ajouté avec succès
            showAlert(Alert.AlertType.INFORMATION, "Sponsor ajouté avec succès.");

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout du sponsor", e);
        }
        showSponsors();
    }



    // Fonction de validation pour le champ de texte nom du sponsor
    private boolean isValidNomSponsor(String input) {
        return !input.isEmpty(); // Vérifie que le champ n'est pas vide
    }

    // Fonction de validation pour le champ de texte type du sponsor
    private boolean isValidTypeSponsor(String input) {
        return !input.isEmpty(); // Vérifie que le champ n'est pas vide
    }
    // Méthode pour afficher une alerte
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }
}
