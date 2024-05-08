package com.example.jobflow.controllers.front.opportunite;


import com.example.jobflow.controllers.front.MainWindowController;
import com.example.jobflow.entities.Opportunite;
import com.example.jobflow.entities.Test;
import com.example.jobflow.entities.User;
import com.example.jobflow.services.OpportuniteService;
import com.example.jobflow.utils.AlertUtils;
import com.example.jobflow.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageController implements Initializable {

    @FXML
    public TextField nomTF;
    @FXML
    public TextField descreptionTF;
    @FXML
    public TextField typeTF;
    @FXML
    public TextField isFavoriteTF;

    @FXML
    public ComboBox<Test> testCB;
    @FXML
    public ComboBox<User> userCB;

    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    Opportunite currentOpportunite;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (Test test : OpportuniteService.getInstance().getAllTests()) {
            testCB.getItems().add(test);
        }

        for (User user : OpportuniteService.getInstance().getAllUsers()) {
            userCB.getItems().add(user);
        }

        currentOpportunite = ShowAllController.currentOpportunite;

        if (currentOpportunite != null) {
            topText.setText("Modifier opportunite");
            btnAjout.setText("Modifier");

            try {
                nomTF.setText(currentOpportunite.getNom());
                descreptionTF.setText(currentOpportunite.getDescreption());
                typeTF.setText(currentOpportunite.getType());
                isFavoriteTF.setText(String.valueOf(currentOpportunite.getIsFavorite()));

                testCB.setValue(currentOpportunite.getTest());
                userCB.setValue(currentOpportunite.getUser());

            } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
        } else {
            topText.setText("Ajouter opportunite");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void manage(ActionEvent ignored) {

        if (controleDeSaisie()) {

            Opportunite opportunite = new Opportunite();
            opportunite.setNom(nomTF.getText());
            opportunite.setDescreption(descreptionTF.getText());
            opportunite.setType(typeTF.getText());
            opportunite.setIsFavorite(Integer.parseInt(isFavoriteTF.getText()));

            opportunite.setTest(testCB.getValue());
            opportunite.setUser(userCB.getValue());

            if (currentOpportunite == null) {
                if (OpportuniteService.getInstance().add(opportunite)) {
                    AlertUtils.makeSuccessNotification("Opportunite ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_OPPORTUNITE);
                } else {
                    AlertUtils.makeError("Error");
                }
            } else {
                opportunite.setId(currentOpportunite.getId());
                if (OpportuniteService.getInstance().edit(opportunite)) {
                    AlertUtils.makeSuccessNotification("Opportunite modifié avec succés");
                    ShowAllController.currentOpportunite = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_OPPORTUNITE);
                } else {
                    AlertUtils.makeError("Error");
                }
            }

        }
    }


    private boolean controleDeSaisie() {


        if (nomTF.getText().isEmpty()) {
            AlertUtils.makeInformation("nom ne doit pas etre vide");
            return false;
        }


        if (descreptionTF.getText().isEmpty()) {
            AlertUtils.makeInformation("descreption ne doit pas etre vide");
            return false;
        }


        if (typeTF.getText().isEmpty()) {
            AlertUtils.makeInformation("type ne doit pas etre vide");
            return false;
        }


        if (isFavoriteTF.getText().isEmpty()) {
            AlertUtils.makeInformation("isFavorite ne doit pas etre vide");
            return false;
        }


        try {
            Integer.parseInt(isFavoriteTF.getText());
        } catch (NumberFormatException ignored) {
            AlertUtils.makeInformation("isFavorite doit etre un nombre");
            return false;
        }

        if (testCB.getValue() == null) {
            AlertUtils.makeInformation("Veuillez choisir un test");
            return false;
        }
        if (userCB.getValue() == null) {
            AlertUtils.makeInformation("Veuillez choisir un user");
            return false;
        }
        return true;
    }
}