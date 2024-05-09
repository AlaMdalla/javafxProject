package com.example.jobflow.controllers.back.societe;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.example.jobflow.entities.Partenaire;
import com.example.jobflow.entities.Societe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.jobflow.services.ServicePartenaire;
import com.example.jobflow.services.ServiceSociete;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Set;;


public class AjouterPartenaire {

    @FXML
    private TextField nomField;

    @FXML
    private TextArea descriptionField;
    @FXML
    private TextField dureeField;
    @FXML
    private ChoiceBox<Societe> societeChoiceBox;

    private final ServicePartenaire servicePartenaire = new ServicePartenaire();
    private final ServiceSociete serviceSociete = new ServiceSociete();

    @FXML
    public void initialize() {
        loadSocietes();
    }

    private void loadSocietes() {
        try {
            Set<Societe> societes = serviceSociete.afficher();
            societeChoiceBox.getItems().addAll(societes);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ajouterPartenaire(ActionEvent event) {
        String nom = nomField.getText();
        String description = descriptionField.getText();
        String duree = dureeField.getText();
        Societe selectedSociete = societeChoiceBox.getValue();
        int id = selectedSociete.getId();

        if (nom.isEmpty() || description.isEmpty() || selectedSociete == null) {
            showAlert(Alert.AlertType.WARNING, "Champs obligatoires", "OUPS ! Vous avez oublié des champs vides ou vous n'avez pas sélectionné de société.");
            return;
        }

        Partenaire partenaire = new Partenaire(nom, description, duree, id);
        partenaire.setSociete(selectedSociete);

        try {
            servicePartenaire.ajouter(partenaire);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Le partenaire a été ajouté avec succès.");
            clearFields();

            // Send SMS
            sendSMS("+21628178182", partenaire);

            // Send Email
            sendEmail(partenaire);

            // Load the partner list view
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherPartenaire.fxml"));
            Parent root = loader.load();

            AfficherPartenaire afficherPartenaireController = loader.getController();
            afficherPartenaireController.initialize(null, null); // Call initialize method of the controller

            Stage currentStage = (Stage) nomField.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        } catch (IOException | SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Échec de l'ajout du partenaire.");
        }
    }



    private void clearFields() {
        nomField.clear();
        descriptionField.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    private void sendSMS(String recipientNumber, Partenaire newPartenaire) {
        // Initialize Twilio library with your account information
        String ACCOUNT_SID = "ACa0f21dcd8850e53c6475d30b715ef089";
        String AUTH_TOKEN = "696a7359109ab7601fe7f3c374f0e445";
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String PhoneNumber ="+21628178182";
        String message = "Bonjour,\n"
                + "Nous sommes ravis de vous informer qu'une nouvelle partenaire a été ajoutée.\n"
                + "Nom de la société: " + newPartenaire.getNom() + "\n"
                + "Description: " + newPartenaire.getDescription() + "\n"
                + "Site Web: " + newPartenaire.getDuree() + "\n"
                + "Merci et à bientôt.\n"
                + "Cordialement,\n"
                + "jobflow";

        // Send the SMS message
        Message twilioMessage = Message.creator(
                new PhoneNumber(PhoneNumber),
                new PhoneNumber("+12563630943"),
                message).create();

        System.out.println("SMS envoyé : " + twilioMessage.getSid());
    }
    private void sendEmail(Partenaire newPartenaire) {
        final String username = "rayenfarhani9@gmail.com"; // Enter your email here
        final String password = "zxyd omhl obhs xfhv"; // Enter your password here

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            javax.mail.Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(javax.mail.Message.RecipientType.TO,
                    InternetAddress.parse("rayenfarhani9@gmail.com")); // Enter recipient email
            message.setSubject("Nouvelle partenaire ajoutée");
            message.setText("Bonjour,\n"
                    + "Nous sommes ravis de vous informer qu'une nouvelle partenaire a été ajoutée.\n"
                    + "Nom de la société: " + newPartenaire.getNom() + "\n"
                    + "Description: " + newPartenaire.getDescription() + "\n"
                    + "Site Web: " + newPartenaire.getDuree() + "\n"
                    + "Merci et à bientôt.\n"
                    + "Cordialement,\n"
                    + "jobflow");

            Transport.send(message);
            System.out.println("Email envoyé");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
