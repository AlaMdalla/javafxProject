package Controller;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import entities.Societe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ServiceSociete;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;


import java.io.IOException;
import java.sql.SQLException;

public class AjouterSociete {

    private final ServiceSociete serviceSociete = new ServiceSociete();

    @FXML
    private Button btnsave;

    @FXML
    private TextField fNS;

    @FXML
    private TextField fA;

    @FXML
    private TextField fNT;

    @FXML
    private TextField fD;

    @FXML
    private TextField fSW;

    @FXML
    private TextField fS;

    @FXML
    void createSociete(ActionEvent event) {
        String nomSociete = fNS.getText().trim();
        String numTelephoneStr = fNT.getText().trim();
        String adress = fA.getText().trim();
        String description = fD.getText().trim();
        String siteweb = fSW.getText().trim();
        String secteur = fS.getText().trim();

        if (nomSociete.isEmpty() || numTelephoneStr.isEmpty() || adress.isEmpty() || description.isEmpty() || siteweb.isEmpty() || secteur.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Champs Obligatoires", "OUPS ! Vous avez oublié des champs vides.");
            return;
        }

        try {
            int numTelephone = Integer.parseInt(numTelephoneStr);
            Societe newSociete = new Societe(nomSociete, adress, description, siteweb, numTelephone, secteur);
            serviceSociete.ajouter(newSociete);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Société ajoutée avec succès.");
            sendSMS(numTelephoneStr, newSociete);
            sendEmail(newSociete); // Add this line
            navigateToAfficherSociete(event);
            clearFields();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de saisie", "Le numéro de téléphone doit être un nombre.");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur SQL", e.getMessage());
        }
    }

    private void sendSMS(String recipientNumber, Societe newSociete) {
        // Initialize Twilio library with your account information
        String ACCOUNT_SID = "ACbed9c3e48616651cdcdcf8cbd1770f8e";
        String AUTH_TOKEN = "d0fe91f7e8b9902e638f5c61d7987d2d";
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String PhoneNumber ="+21628178182";
        String message = "Bonjour,\n"
                + "Nous sommes ravis de vous informer qu'une nouvelle société a été ajoutée.\n"
                + "Nom de la société: " + newSociete.getNom() + "\n"
                + "Description: " + newSociete.getDescription() + "\n"
                + "Site Web: " + newSociete.getSiteweb() + "\n"
                + "Secteur: " + newSociete.getSecteur() + "\n"
                + "Merci et à bientôt.\n"
                + "Cordialement,\n"
                + "jobflow";

        // Send the SMS message
        Message twilioMessage = Message.creator(
                new PhoneNumber(PhoneNumber),
                new PhoneNumber("+12569803965"),
                message).create();

        System.out.println("SMS envoyé : " + twilioMessage.getSid());
    }

    private void navigateToAfficherSociete(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherSociete.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) btnsave.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    private void clearFields() {
        fNS.clear();
        fNT.clear();
        fA.clear();
        fD.clear();
        fSW.clear();
        fS.clear();
    }

    private void sendEmail(Societe newSociete) {
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
            message.setSubject("Nouvelle société ajoutée");
            message.setText("Bonjour,\n"
                    + "Nous sommes ravis de vous informer qu'une nouvelle société a été ajoutée.\n"
                    + "Nom de la société: " + newSociete.getNom() + "\n"
                    + "Description: " + newSociete.getDescription() + "\n"
                    + "Site Web: " + newSociete.getSiteweb() + "\n"
                    + "Secteur: " + newSociete.getSecteur() + "\n"
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