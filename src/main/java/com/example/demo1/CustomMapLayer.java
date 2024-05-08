package com.example.demo1;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.gluonhq.maps.MapView;
import com.gluonhq.maps.MapPoint;

public class CustomMapLayer extends Application {
    private TextField searchField;

    private final MapPoint tunisiaPoint = new MapPoint(36.8065, 10.1815); // Coordonnées de Tunis
    private VBox address;
    private MapView mapView;

    @Override
    public void start(Stage primaryStage) {
        init();

        VBox root = new VBox();
        // Créer une barre de recherche
        BorderPane searchBar = createSearchBar();

        // Ajouter la barre de recherche et la carte à la vue principale
        root.getChildren().addAll(searchBar, address);
        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Voila la Map");
        primaryStage.show();
    }

    public void init() {
        mapView = createMapView(); // Assignez le résultat de createMapView() à la variable mapView
        address = new VBox(mapView);
        VBox.setVgrow(mapView, Priority.ALWAYS);
    }

    private MapView createMapView() {
        MapView mapView = new MapView();
        mapView.setPrefSize(1000, 1000);
        mapView.setZoom(5);
        mapView.flyTo(0, tunisiaPoint, 0.1); // Faites pointer la carte sur la Tunisie
        return mapView;
    }
    private BorderPane createSearchBar() {
        BorderPane searchBar = new BorderPane();
        searchField = new TextField();
        searchField.setPromptText("Entrez l'emplacement...");
        Button searchButton = new Button("Rechercher");
        searchButton.setOnAction(event -> searchLocation());
        searchBar.setCenter(searchField);
        searchBar.setRight(searchButton);
        BorderPane.setAlignment(searchButton, Pos.CENTER);
        return searchBar;
    }
    private void searchLocation() {
        String location = searchField.getText();
        // Supposons que l'utilisateur entre juste "Tunis" pour cet exemple
        MapPoint coordinates = getCoordinatesFromLocation(location);

        if (coordinates != null) {
            // Déplacez la carte vers les coordonnées de l'emplacement trouvé
            mapView.flyTo(0, coordinates, 0.1);
        } else {
            // Si l'emplacement saisi n'est pas trouvé, affichez un message d'erreur
            showAlert(Alert.AlertType.ERROR, "Emplacement non trouvé");
        }
    }
    private MapPoint getCoordinatesFromLocation(String location) {
        // Implémentez ici votre logique de recherche d'emplacement
        // Vous pouvez utiliser une API de géocodage ou une base de données locale pour rechercher les coordonnées d'un emplacement
        // Pour cet exemple, je retournerai des coordonnées pour quelques lieux prédéfinis
        switch (location.toLowerCase()) {
            case "tunis":
                return new MapPoint(36.8065, 10.1815);
            case "paris":
                return new MapPoint(48.8566, 2.3522);
            case "new york":
                return new MapPoint(40.7128, -74.0060);
            case "london":
                return new MapPoint(51.5074, -0.1278);
            case "toronto":
                return new MapPoint(43.65107, -79.347015);
            // Ajoutez d'autres lieux ici selon vos besoins
            default:
                return null; // Retourne null si l'emplacement n'est pas trouvé
        }
    }
    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
