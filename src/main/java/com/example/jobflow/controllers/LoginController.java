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
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nl.captcha.Captcha;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    public TextField inputEmail;
    @FXML
    public PasswordField inputPassword;
    @FXML
    public Button btnSignup;
    @FXML
    public CheckBox remmemberMeCb;
    @FXML
    public TextField captchaTF;
    @FXML
    public ImageView captchaIV;

    Captcha captcha;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCaptcha();

        try {
            File file = new File("savedEmail.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            inputEmail.setText(reader.readLine());
            remmemberMeCb.setSelected(true);
        } catch (Exception e) {
        }

        try {
            File file = new File("savedPassword.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            inputPassword.setText(reader.readLine());
            remmemberMeCb.setSelected(true);
        } catch (Exception e) {
        }
    }


    public void signUp(ActionEvent ignored) {
        MainApp.getInstance().loadSignup();
    }

    public void login(ActionEvent ignored) {
        if (!captcha.isCorrect(captchaTF.getText())) {
            AlertUtils.makeInformation("Captcha invalide");
            return;
        }

        User user = UserService.getInstance().checkUser(inputEmail.getText(), inputPassword.getText());

        if (user != null) {
            if (user.getStatus() == 0) {
                AlertUtils.makeError("Votre compte est désactivé");
                return;
            }

            if (remmemberMeCb.isSelected()) {
                try {
                    FileWriter fileWriter = new FileWriter("savedEmail.txt");
                    fileWriter.write(inputEmail.getText());
                    fileWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    FileWriter fileWriter = new FileWriter("savedPassword.txt");
                    fileWriter.write(inputPassword.getText());
                    fileWriter.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    File file = new File("savedEmail.txt");
                    if (file.exists()) {
                        file.delete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    File file = new File("savedPassword.txt");
                    if (file.exists()) {
                        file.delete();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            MainApp.getInstance().login(user);
        } else {
            AlertUtils.makeError("Identifiants invalides");
        }
    }

    @FXML
    public void back(ActionEvent ignored) {
        MainApp.getInstance().loadBack();
    }

    public void forgotPassword(ActionEvent actionEvent) {
        MainApp.getInstance().loadForgotFirst();
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
