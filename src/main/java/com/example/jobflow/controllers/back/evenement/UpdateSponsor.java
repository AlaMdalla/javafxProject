package com.example.jobflow.controllers.back.evenement;
import com.example.jobflow.entities.sponsor;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.jobflow.utils.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateSponsor {
    @FXML
    private TextField tNomUpdate;

    @FXML
    private TextField tTypeUpdate;

    @FXML
    private Button btnUpdates;

    private sponsor selectedEvent;
    public void initData(sponsor event) {
        selectedEvent = event;

        tNomUpdate.setText(selectedEvent.getNom());
        tTypeUpdate.setText(selectedEvent.getType());



    }
    @FXML
    void updateSponsor() {
        if (selectedEvent != null) {
            try {
                String updateQuery = "UPDATE Sponsor SET Nom=?, Type=? WHERE id_sponsor=?";
                Connection con = DatabaseConnection.getInstance().getConnection();
                PreparedStatement st = con.prepareStatement(updateQuery);
                st.setString(1, tNomUpdate.getText());
                st.setString(2, tTypeUpdate.getText());

                // Set the id of the sponsor to update
                st.setInt(3, selectedEvent.getId_sponsor());

                int rowsAffected = st.executeUpdate();
                if (rowsAffected > 0) {
                    // The update was successful
                    System.out.println("Sponsor mis à jour avec succès");

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/jobflow/back/evenement/sponsor.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) tTypeUpdate.getScene().getWindow(); // Assuming btnUpdate is a button in the current scene
                    stage.setScene(scene);
                    stage.show();
                    // Close the update window or do any other necessary actions
                } else {
                    // No sponsor found with the given ID
                    System.out.println("Aucun sponsor trouvé avec cet ID");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Aucun sponsor sélectionné pour la mise à jour");
        }
    }

}
