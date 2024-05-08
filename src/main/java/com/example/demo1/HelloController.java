package com.example.demo1;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import utiles.DBconnexion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;


import javafx.scene.image.Image;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;






import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.function.Predicate;


import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;


import entities.evenement;

import javax.imageio.ImageIO;

public class HelloController implements Initializable {

    public TextField tserach;
    public Button btnsponsor;
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
    @FXML
    private WebView mapWebView;
    @FXML
    private TextField theure;

    @FXML
    private Button btnOuvrirCarte;



    private HBox eventCard;
    @FXML
    private TextField textField;

    @FXML
    private TextField tsearch;

    @FXML
    private Button btnsearch;
    @FXML
    private Button btnSort;
    @FXML
    private Button bntsponsor;
    @FXML
    private Button btnSponsor;// Assurez-vous que l'ID FX est correctement défini dans votre fichier FXML

    private ObservableList<evenement> evenements;






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        con = DBconnexion.getCon();
        evenements = getEvenements();
        showEvenements();
        setupDynamicSearch(tsearch, evenements);
        // Associer le gestionnaire d'événements au bouton de tri
        btnSort.setOnAction(event -> sortEventsByTitle());
        bntsponsor.setOnAction(event -> loadSponsorPageView());
    }

    // Méthode pour trier les événements par titre dans l'ordre alphabétique
    @FXML
    private void sortEventsByTitle() {
        // Trier la liste d'événements par titre
        evenements.sort(Comparator.comparing(evenement::getTitre));

        // Mettre à jour l'affichage des cartes avec les événements triés
        updateDisplayedCards(new FilteredList<>(evenements));
    }

    // Méthode pour mettre en place la recherche dynamique
    public void setupDynamicSearch(TextField searchField, ObservableList<evenement> evenements) {
        // Création d'une liste filtrée pour la recherche dynamique
        FilteredList<evenement> filteredList = new FilteredList<>(evenements, null);

        // Ajout d'un écouteur sur le champ de recherche pour mettre à jour la liste filtrée et l'affichage
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(createPredicate(newValue));
            updateDisplayedCards(filteredList); // Mettre à jour l'affichage avec les éléments filtrés
        });
    }

    // Méthode pour mettre à jour l'affichage des cartes avec les éléments filtrés
    private void updateDisplayedCards(FilteredList<evenement> filteredList) {
        // Vider le contenu actuel du FlowPane
        eventFlowPane.getChildren().clear();

        // Pour chaque événement dans la liste filtrée, créer une carte et l'ajouter au FlowPane
        for (evenement event : filteredList) {
            HBox card = showEvenement(event);
            eventFlowPane.getChildren().add(card);
        }
    }

    // Méthode pour créer un prédicat en fonction de la chaîne de recherche
    private Predicate<evenement> createPredicate(String searchText) {
        if (searchText == null || searchText.trim().isEmpty()) {
            // Si la chaîne de recherche est vide, retourner vrai pour inclure tous les événements
            return event -> true;
        } else {
            // Sinon, créer un prédicat pour filtrer les événements en fonction du titre
            String lowerCaseSearchText = searchText.toLowerCase();
            return event -> event.getTitre().toLowerCase().startsWith(lowerCaseSearchText);
        }
    }
    @FXML
    private void loadSponsorPageView() {
        try {
            // Obtenez la fenêtre (Stage) à partir du Node du bouton
            Stage currentStage = (Stage) bntsponsor.getScene().getWindow();

            // Chargez la nouvelle page des sponsors
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sponsor.fxml"));
            AnchorPane sponsorPage = fxmlLoader.load();

            // Créer une nouvelle scène avec la page des sponsors chargée
            Scene newScene = new Scene(sponsorPage);

            // Définissez la nouvelle scène sur la fenêtre actuelle
            currentStage.setScene(newScene);
        } catch (IOException e) {
            System.out.println("Erreur lors du chargement de la vue de la page des sponsors : " + e.getMessage());
        }
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

                // Récupération de la valeur de la colonne "heure"
                Time heureTime = rs.getTime("heure");
                evenement.setHeure(heureTime); // Utilisation de la méthode setHeure pour définir l'heure de l'événement

                // Ajout de la récupération du chemin de l'image
                String imagePath = rs.getString("Image").replace("\\", "/");
                System.out.println("imagePath" + imagePath);
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
        Button btnupdate = new Button("U");
        btnupdate.getStyleClass().add("button-update");

        root.getChildren().add(btnupdate);
        btnupdate.setOnMouseClicked(mouseEvent -> {
            // Get the selected event to update
            evenement selectedEvent = (evenement) btnupdate.getUserData();

            if (selectedEvent != null) {
                try {
                    // Load the update.fxml file
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("modifier.fxml"));
                    Parent roott = loader.load();
                    UpdateController controller = loader.getController();

                    controller.initData(selectedEvent);
                    Scene scenee = new Scene(roott);

                    // Get the current stage and scene
                    Stage currentStage = (Stage) card.getScene().getWindow();


                    // Set the new scene
                    currentStage.setScene(scenee);
                    currentStage.setTitle("Post Details");
                    currentStage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Aucun événement sélectionné pour la mise à jour");
            }
        });


        btnupdate.setUserData(event);


        // Créer des labels pour les détails de l'événement
        // Créer des labels pour les détails de l'événement
        Label eventNameLabel = new Label("Titre de l'evenement: " + event.getTitre());
        Label eventDateLabel = new Label("Date : " + event.getDate());
        Label eventHeureLabel = new Label("Heure : " + event.getHeure()); // Ajout du label pour afficher l'heure
        Label eventLocationLabel = new Label("Lieu de l'evenement: " + event.getLocalisation());
        Label eventParticipantsLabel = new Label("Nombre de participant: " + event.getNb_participant());


        // Créer une image affichant l'événement
        ImageView imageafficher = new ImageView(event.getImage());
        imageafficher.setFitWidth(100); // Largeur de l'image
        imageafficher.setFitHeight(100); // Hauteur de l'image

        // Créer le QR code avec une taille spécifiée

        String qrCodeText = "VotreQrCode/event?id=" + event.getId(); // Supposons que vous ayez un ID pour chaque événement
        ImageView qrCodeImageView = new ImageView();
        qrCodeImageView.setPreserveRatio(true);
        qrCodeImageView.setFitWidth(100); // Largeur de l'image
        qrCodeImageView.setFitHeight(100); // Hauteur de l'image
        qrCodeImageView.setImage(createQRCodeImage(qrCodeText, 100, 100)); // Génération du QR code

        // Ajoutez la `ImageView` du QR code à la carte
        card.getChildren().add(qrCodeImageView);

        // Ajouter le bouton à la carte


        // Créer une imageView pour afficher le QR code

        // Ajouter les labels à la boîte de détails
        detailsBox.getChildren().addAll(eventNameLabel, eventDateLabel,eventHeureLabel ,eventLocationLabel, eventParticipantsLabel);

        // Ajouter les éléments à la carte
        card.getChildren().addAll(detailsBox, imageafficher, deleteButton ,btnupdate);

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


    private Image createQRCodeImage(String qrCodeText, int width, int height) {
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(qrCodeText, BarcodeFormat.QR_CODE, width, height);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, "jpg", out);
            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            return new Image(in);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @FXML
    private void openMap() {
        // Créez une instance de la classe CustomMapLayer
        CustomMapLayer mapLayer = new CustomMapLayer();

        // Appelez la méthode start() pour afficher la carte
        Stage mapStage = new Stage();
        mapLayer.start(mapStage);
    }
    @FXML
    void creatEvenement(ActionEvent event) {
        String insert = "INSERT INTO Evenements(Titre, Localisation, nb_participant, Date, Image, heure) VALUES (?, ?, ?, ?, ?, ?)";
        String imagePath = null;

        String titre = tTitre.getText();
        String localisation = tLocalisation.getText();
        String nbParticipantText = tNbParticipant.getText();
        LocalDate date = tDate.getValue();

        // Récupérer l'heure depuis le champ de texte
        String heureText = theure.getText();

        if (titre.isEmpty() || localisation.isEmpty() || nbParticipantText.isEmpty() || date == null || heureText.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Veuillez remplir tous les champs.");
            return;
        }

        // Validation du format de l'heure
        if (!isValidHourFormat(heureText)) {
            showAlert(Alert.AlertType.ERROR, "Format d'heure invalide. Veuillez saisir l'heure au format HH:mm:ss.");
            return;
        }

        // Convertir l'heure texte en LocalTime
        LocalTime heure = LocalTime.parse(heureText);

        try {
            con = DBconnexion.getCon();
            st = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);

            st.setString(1, tTitre.getText());
            st.setString(2, tLocalisation.getText());

            try {
                int nbParticipants = Integer.parseInt(tNbParticipant.getText());
                st.setInt(3, nbParticipants);
            } catch (NumberFormatException e) {
                System.out.println("Erreur : le champ de texte ne contient pas un entier valide.");
            }

            st.setDate(4, java.sql.Date.valueOf(date));

            imagePath = this.url;
            st.setString(5, imagePath);

            // Set the heure column
            st.setTime(6, java.sql.Time.valueOf(heure));

            st.executeUpdate();

            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int eventId = generatedKeys.getInt(1);
                    generateQRCodeForEvent(eventId);
                } else {
                    throw new SQLException("Échec de la création de l'événement, aucun ID obtenu.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de l'ajout de l'événement", e);
        }

        showEvenements();
    }


    private void generateQRCodeForEvent(int eventId) {
            // Générer le texte du QR code en utilisant l'ID de l'événement
            String qrCodeText = "https://www.example.com/event?id=" + eventId;
            String path = "C:\\Users\\elyes\\Desktop\\QrCode\\event_" + eventId + ".jpg";

            try {
                BitMatrix matrix = new MultiFormatWriter().encode(qrCodeText, BarcodeFormat.QR_CODE, 500, 500);

                // Write QR code to file
                MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));
                System.out.println("QR code saved successfully for event ID: " + eventId);
            } catch (IOException | WriterException ex) {
                ex.printStackTrace();
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
                    showEvenements();
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


    private boolean isValidNbParticipant(String input) {
        try {
            int nbParticipants = Integer.parseInt(input);
            return nbParticipants >= 0; // Vérifie que le nombre de participants n'est pas négatif
        } catch (NumberFormatException e) {
            return false; // Si la conversion en entier échoue, le format n'est pas valide
        }
    }

    // Fonction de validation pour le DatePicker
    private boolean isValidDate(LocalDate date) {
        LocalDate currentDate = LocalDate.now();
        return date != null && !date.isBefore(currentDate); // Vérifie que la date n'est pas dans le passé
    }

    // Méthode pour afficher une alerte en cas d'erreur de saisie
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
    // Validation du format de l'heure
    private boolean isValidHourFormat(String hourText) {
        try {
            // Tentative de conversion de l'heure texte en LocalTime
            LocalTime.parse(hourText);
            return true; // Si aucune exception n'est levée, l'heure est au format valide
        } catch (DateTimeParseException e) {
            return false; // Si une exception est levée, l'heure n'est pas au format valide
        }
    }



}