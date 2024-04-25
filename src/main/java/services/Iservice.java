package services;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.sql.SQLException;

public interface Iservice extends Initializable {
    void initialize() throws SQLException; // Méthode pour l'initialisation
    void creatEvenement() throws SQLException; // Méthode pour créer un événement
    String ajouterImage(); // Méthode pour ajouter une image
    void deleteEvenement() throws SQLException; // Méthode pour supprimer un événement
    void updateEvenement() throws IOException; // Méthode pour mettre à jour un événement
}
