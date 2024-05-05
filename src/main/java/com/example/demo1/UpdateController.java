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

public class UpdateController {

    @FXML
    private TextField tTitreUpdate;

    @FXML
    private TextField tLocalisationUpdate;

    @FXML
    private TextField tNbParticipantUpdate;

    @FXML
    private DatePicker tDateUpdate;

    private evenement selectedEvent;

    public void initData(evenement event) {
        selectedEvent = event;
        //Définir les champs avec les détails de l'événement sélectionné
        tTitreUpdate.setText(selectedEvent.getTitre());
        tLocalisationUpdate.setText(selectedEvent.getLocalisation());
        tNbParticipantUpdate.setText(String.valueOf(selectedEvent.getNb_participant()));

        // Convert java.sql.Date to LocalDate
        tDateUpdate.setValue(LocalDate.parse(selectedEvent.getDate().toString()));
    }


    @FXML
    void updateEvenement() {
        if (selectedEvent != null) {
            try {
                String updateQuery = "UPDATE Evenements SET Titre=?, Localisation=?, nb_participant=?, Date=? WHERE id=?";
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

                // Set the id of the event to update
                st.setInt(5, selectedEvent.getId());

                int rowsAffected = st.executeUpdate();
                if (rowsAffected > 0) {
                    // The update was successful
                    System.out.println("Événement mis à jour avec succès");
                    // Navigate to hello.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) tDateUpdate.getScene().getWindow(); // Assuming btnUpdate is a button in the current scene
                    stage.setScene(scene);
                    stage.show();
                    // Close the update window or do any other necessary actions
                } else {
                    // No event found with the given ID
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
