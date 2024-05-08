package com.example.jobflow.controllers;

import com.example.jobflow.MainApp;
import com.example.jobflow.entities.User;
import com.example.jobflow.services.UserService;
import com.example.jobflow.utils.AlertUtils;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import nl.captcha.Captcha;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class RegisterController implements Initializable {

    @FXML
    public TextField emailTF;
    @FXML
    public TextField passwordTF;
    @FXML
    public TextField lastnameTF;

    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;
    @FXML
    public TextField captchaTF;
    @FXML
    public ImageView captchaIV;

    Captcha captcha;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCaptcha();

        topText.setText("Inscription");
        btnAjout.setText("S'inscrire");
    }

    @FXML
    private void manage(ActionEvent ignored) {

        if (controleDeSaisie()) {


            User user = new User();
            user.setEmail(emailTF.getText());
            user.setPassword(passwordTF.getText());
            user.setLastname(lastnameTF.getText());
            user.setRoles("[\"ROLE_USER\"]");
            user.setStatus(1);

            if (UserService.getInstance().add(user)) {
                AlertUtils.makeSuccessNotification("Inscription effectué avec succés");
                MainApp.getInstance().loadLogin();
            } else {
                AlertUtils.makeError("Existe");
            }
        }
    }


    @FXML
    public void connexion(ActionEvent ignored) {
        MainApp.getInstance().loadLogin();
    }

    private boolean controleDeSaisie() {

        if (!captcha.isCorrect(captchaTF.getText())) {
            AlertUtils.makeInformation("Captcha invalide");
            return false;
        }

        if (emailTF.getText().isEmpty()) {
            AlertUtils.makeInformation("Email ne doit pas etre vide");
            return false;
        }
        if (!Pattern.compile("^(.+)@(.+)$").matcher(emailTF.getText()).matches()) {
            AlertUtils.makeInformation("Email invalide");
            return false;
        }


        if (passwordTF.getText().isEmpty()) {
            AlertUtils.makeInformation("Password ne doit pas etre vide");
            return false;
        }


        if (lastnameTF.getText().isEmpty()) {
            AlertUtils.makeInformation("Lastname ne doit pas etre vide");
            return false;
        }

        return true;
    }

    @FXML
    public void resetCaptcha(ActionEvent ignored) {
        setCaptcha();
        captchaTF.clear();
    }


    void setCaptcha() {
        captcha = new Captcha.Builder(250, 200)
                .addText()
                .addBackground()
                .addNoise()
                .addBorder()
                .build();

        Image image = SwingFXUtils.toFXImage(captcha.getImage(), null);
        captchaIV.setImage(image);
    }
}