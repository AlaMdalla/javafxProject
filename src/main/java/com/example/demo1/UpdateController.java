package com.example.demo1;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utiles.DBconnexion;
import entities.evenement;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

public class UpdateController {

    @FXML
    private TextField tTitreUpdate;

    @FXML
    private TextField tLocalisationUpdate;

    @FXML
    private TextField tNbParticipantUpdate;

    @FXML
    private DatePicker tDateUpdate;

    @FXML
    private TextField tHeureUpdate;

    private evenement selectedEvent;

    public void initData(evenement event) {
        selectedEvent = event;
        // Définir les champs avec les détails de l'événement sélectionné
        tTitreUpdate.setText(selectedEvent.getTitre());
        tLocalisationUpdate.setText(selectedEvent.getLocalisation());
        tNbParticipantUpdate.setText(String.valueOf(selectedEvent.getNb_participant()));

        // Convertir java.sql.Date en LocalDate
        tDateUpdate.setValue(LocalDate.parse(selectedEvent.getDate().toString()));

        // Convertir java.sql.Time en LocalTime
        tHeureUpdate.setText(selectedEvent.getHeure().toString());
    }





    @FXML
    void updateEvenement() {
        if (selectedEvent != null) {
            try {
                String updateQuery = "UPDATE Evenements SET Titre=?, Localisation=?, nb_participant=?, Date=?, heure=? WHERE id=?";
                Connection con = DBconnexion.getCon();
                PreparedStatement st = con.prepareStatement(updateQuery);
                st.setString(1, tTitreUpdate.getText());
                st.setString(2, tLocalisationUpdate.getText());

                try {
                    int nbParticipants = Integer.parseInt(tNbParticipantUpdate.getText());
                    st.setInt(3, nbParticipants);
                } catch (NumberFormatException e) {
                    System.out.println("Erreur : le champ de texte ne contient pas un entier valide.");
                }

                LocalDate date = tDateUpdate.getValue();
                st.setDate(4, java.sql.Date.valueOf(date));

                // Récupérer l'heure depuis le champ de texte
                LocalTime heure = LocalTime.parse(tHeureUpdate.getText());
                st.setTime(5, java.sql.Time.valueOf(heure));

                // Set the id of the event to update
                st.setInt(6, selectedEvent.getId());

                int rowsAffected = st.executeUpdate();
                if (rowsAffected > 0) {
                    // La mise à jour a réussi
                    System.out.println("Événement mis à jour avec succès");
                    // Naviguer vers hello.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) tDateUpdate.getScene().getWindow(); // Assuming btnUpdate is a button in the current scene
                    stage.setScene(scene);
                    stage.show();
                    // Fermer la fenêtre de mise à jour ou effectuer d'autres actions nécessaires
                } else {
                    // Aucun événement trouvé avec l'ID donné
                    System.out.println("Aucun événement trouvé avec cet ID");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Aucun événement sélectionné pour la mise à jour");
        }
    }

}
