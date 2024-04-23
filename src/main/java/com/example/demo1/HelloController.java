package com.example.demo1;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.w3c.dom.Node;
import org.w3c.dom.events.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;


import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import com.example.demo1.evenement;

import static com.example.demo1.DBconnexion.url;

public class HelloController implements Initializable {

    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;
    String url ="";

    @FXML
    private TextField tTitre;

    @FXML
    private TextField tLocalisation;


    @FXML
    private TextField tNbParticipant;
    @FXML
    private Button bntsave;

    @FXML
    private Button btnupdate;

    @FXML
    private Button btndelete;

    @FXML
    private DatePicker tDate;

    @FXML
    private ImageView TImage;
    @FXML
    private HBox hbox;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private FlowPane eventFlowPane;
    private HBox eventCard;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        con = com.example.demo1.DBconnexion.getCon();
        showEvenements();

    }


    public ObservableList<evenement> getEvenements() {
        ObservableList<evenement> evenements = FXCollections.observableArrayList();
        String query = "SELECT * FROM Evenements";

        try {
            st = con.prepareStatement(query);
            rs = st.executeQuery();
            while (rs.next()) {
                evenement evenement = new evenement();
                evenement.setId(rs.getInt("id"));
                evenement.setTitre(rs.getString("Titre"));
                evenement.setLocalisation(rs.getString("Localisation"));
                evenement.setNbparticipant(rs.getInt("nb_participant"));
                evenement.setDate(Date.valueOf(rs.getDate("Date").toLocalDate()));

                // Ajout de la récupération du chemin de l'image
                String imagePath = rs.getString("Image").replace("\\", "/");
                System.out.println("imagePath"+imagePath);
                evenement.setImage(imagePath);



                evenements.add(evenement);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des événements", e);
        }
        return evenements;
    }

    public void showEvenements() {
        ObservableList<evenement> list = getEvenements();

        // Supprimez le contenu existant du FlowPane
        eventFlowPane.getChildren().clear();

        // Pour chaque événement, créez une carte et ajoutez-la au FlowPane
        for (evenement event : list) {
            HBox card = showEvenement(event);


            eventFlowPane.getChildren().add(card);
        }
    }



    private HBox showEvenement(evenement event) {
        HBox card = new HBox();
        card.getStyleClass().add("event-card");
        card.setPrefSize(500, 100); // Définir une taille préférée pour la carte
        VBox root = new VBox();
        Scene scene = new Scene(root, 800, 600);
        eventCard = card;
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        // Créer un conteneur pour les détails de l'événement
        VBox detailsBox = new VBox();
        detailsBox.setSpacing(10.0);
        detailsBox.setPrefSize(300, 150);

        // Créer le bouton de suppression
        Button deleteButton = new Button("X");
        deleteButton.setOnAction(this::deleteEvenement);
        deleteButton.getStyleClass().add("button-delete"); // Ajouter la classe de style CSS au bouton
        deleteButton.setUserData(event); // Stockez l'événement associé dans les données du bouton

        root.getChildren().add(deleteButton);
        // Créer des labels pour les détails de l'événement
        Label eventNameLabel = new Label("Titre de l'evenement: " + event.getTitre());
        Label eventDateLabel = new Label("Date : " + event.getDate());
        Label eventLocationLabel = new Label("Lieu de l'evenement: " + event.getLocalisation());
        Label eventParticipantsLabel = new Label("Nombre de participant: " + event.getNb_participant());

        // Créer une image affichant l'événement
        ImageView imageafficher = new ImageView(event.getImage());
        imageafficher.setFitWidth(100); // Largeur de l'image
        imageafficher.setFitHeight(100); // Hauteur de l'image

        // Ajouter les labels à la boîte de détails
        detailsBox.getChildren().addAll(eventNameLabel, eventDateLabel, eventLocationLabel, eventParticipantsLabel);

        // Ajouter les éléments à la carte
        card.getChildren().addAll(detailsBox, imageafficher, deleteButton);

        // Ajouter des marges entre les éléments de la carte
        HBox.setMargin(detailsBox, new Insets(10)); // Marge entre les détails et l'image
        HBox.setMargin(imageafficher, new Insets(10)); // Marge entre l'image et le bouton de suppression

        // Ajouter un gestionnaire d'événements sur la carte
        card.setOnMouseClicked(mouseEvent -> {
            // Mettez ici le code que vous souhaitez exécuter lorsque l'utilisateur clique sur la carte
            System.out.println("Carte d'événement cliquée");
        });

        return card;
    }


    @FXML
    void creatEvenement(ActionEvent event) {
        String insert = "INSERT INTO Evenements(Titre, Localisation, nb_participant, Date, Image) VALUES (?, ?, ?, ?, ?)";
        String imagePath = null; // Déclaration de la variable imagePath

        try {
            con = com.example.demo1.DBconnexion.getCon();
            st = con.prepareStatement(insert);
            st.setString(1, tTitre.getText());
            st.setString(2, tLocalisation.getText());

            try {
                int nbParticipants = Integer.parseInt(tNbParticipant.getText());
                st.setInt(3, nbParticipants);
            } catch (NumberFormatException e) {
                System.out.println("Erreur : le champ de texte ne contient pas un entier valide.");
            }

            // Récupérer la date sélectionnée du DatePicker
            LocalDate date = tDate.getValue();
            st.setDate(4, java.sql.Date.valueOf(date));


                imagePath = this.url;
                st.setString(5, imagePath);


            st.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de l'événement", e);
        }
    }


    public String ajouterImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Image");
        fileChooser.setInitialDirectory(new File("C:/Users/elyes/Pictures"));

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {

            String image_Path = selectedFile.getAbsolutePath();
            System.out.println("addimage"+image_Path);
           ;
           this.url= "file:///" + image_Path.replace("\\", "/");
            Image image0 = new Image(this.url);
            System.out.println(this.url);


            TImage.setImage(image0);


        } else {
            System.out.println("no image selected");
            this.url= "file:///" + "C:\\Users\\elyes\\Pictures\\Camera Roll\\1.png";

        }
        return url;
    }


    @FXML
    void deleteEvenement(ActionEvent event) {
        Button deleteButton = (Button) event.getSource(); // Récupérer le bouton sur lequel l'utilisateur a cliqué
        evenement eventToDelete = (evenement) deleteButton.getUserData(); // Récupérer l'événement associé au bouton

        // Vérifier si l'objet evenement est null
        if (eventToDelete != null) {
            int eventId = eventToDelete.getId(); // Récupérer l'ID de l'événement à supprimer

            String deleteQuery = "DELETE FROM Evenements WHERE id = ?";
            try {
                con = DBconnexion.getCon();
                PreparedStatement st = con.prepareStatement(deleteQuery);
                st.setInt(1, eventId);

                int rowsAffected = st.executeUpdate();
                if (rowsAffected > 0) {
                    // La suppression a réussi
                    System.out.println("Événement supprimé avec succès");

                    // Supprimer la carte de l'événement directement en utilisant la référence stockée
                    Node eventCard = (Node) deleteButton.getUserData(); // Récupérer la carte de l'événement depuis les données du bouton
                    if (eventCard != null && eventCard.getParentNode() != null) {
                        ((Pane) eventCard.getParentNode()).getChildren().remove(eventCard);
                    }
                } else {
                    // Aucune ligne n'a été supprimée, peut-être que l'ID n'existe pas dans la table
                    System.out.println("Aucun événement trouvé avec cet ID");
                }
            } catch (SQLException e) {
                // Gérer spécifiquement les exceptions SQL et fournir une rétroaction à l'utilisateur
                e.printStackTrace(); // À remplacer par une gestion appropriée des exceptions
            }
        } else {
            // Si eventToDelete est null, afficher un message d'erreur ou effectuer une action appropriée
            System.out.println("Aucun événement associé au bouton de suppression");
        }
    }






   /* @FXML
    void updateEvenement(ActionEvent event) {
        // Vérifiez si un événement est sélectionné dans le TableView
        evenement selectedEvenement = table.getSelectionModel().getSelectedItem();
        if (selectedEvenement != null) {
            String update = "UPDATE Evenements SET titre = ?, localisation = ?, nb_participant = ? WHERE id = ?";
            con = com.example.demo1.DBconnexion.getCon();
            try {
                st = con.prepareStatement(update);
                st.setString(1, tTitre.getText());
                st.setString(2, tLocalisation.getText());
                st.setInt(3, Integer.parseInt(tNombredeParticipant.getText()));
                st.setInt(4, selectedEvenement.getId()); // Utilisation de l'ID de l'événement sélectionné
                st.executeUpdate();
               // showEvenements(); // Rafraîchir l'affichage des événements dans le TableView
            } catch (SQLException e) {
                // Gérer spécifiquement les exceptions SQL et fournir une rétroaction à l'utilisateur
                e.printStackTrace(); // À remplacer par une gestion appropriée des exceptions
            }
        } else {
            // Gérer le cas où aucun événement n'est sélectionné
            System.out.println("Veuillez sélectionner un événement à mettre à jour.");
        }*/
    }