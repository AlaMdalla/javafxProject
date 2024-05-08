package com.example.jobflow.controllers.front.test;


import com.example.jobflow.controllers.front.MainWindowController;
import com.example.jobflow.entities.Test;
import com.example.jobflow.services.TestService;
import com.example.jobflow.utils.AlertUtils;
import com.example.jobflow.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageController implements Initializable {

    @FXML
    public TextField typeTF;
    @FXML
    public TextField scoreTF;
    @FXML
    public TextField dureeTF;


    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    Test currentTest;


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        currentTest = ShowAllController.currentTest;

        if (currentTest != null) {
            topText.setText("Modifier test");
            btnAjout.setText("Modifier");

            try {
                typeTF.setText(currentTest.getType());
                scoreTF.setText(String.valueOf(currentTest.getScore()));
                dureeTF.setText(currentTest.getDuree());


            } catch (NullPointerException ignored) {
                System.out.println("NullPointerException");
            }
        } else {
            topText.setText("Ajouter test");
            btnAjout.setText("Ajouter");
        }
    }

    @FXML
    private void manage(ActionEvent ignored) {

        if (controleDeSaisie()) {

            Test test = new Test();
            test.setType(typeTF.getText());
            test.setScore(Integer.parseInt(scoreTF.getText()));
            test.setDuree(dureeTF.getText());


            if (currentTest == null) {
                if (TestService.getInstance().add(test)) {
                    AlertUtils.makeSuccessNotification("Test ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_TEST);
                } else {
                    AlertUtils.makeError("Error");
                }
            } else {
                test.setId(currentTest.getId());
                if (TestService.getInstance().edit(test)) {
                    AlertUtils.makeSuccessNotification("Test modifié avec succés");
                    ShowAllController.currentTest = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_TEST);
                } else {
                    AlertUtils.makeError("Error");
                }
            }

        }
    }


    private boolean controleDeSaisie() {


        if (typeTF.getText().isEmpty()) {
            AlertUtils.makeInformation("type ne doit pas etre vide");
            return false;
        }


        if (scoreTF.getText().isEmpty()) {
            AlertUtils.makeInformation("score ne doit pas etre vide");
            return false;
        }


        try {
            Integer.parseInt(scoreTF.getText());
        } catch (NumberFormatException ignored) {
            AlertUtils.makeInformation("score doit etre un nombre");
            return false;
        }

        if (dureeTF.getText().isEmpty()) {
            AlertUtils.makeInformation("duree ne doit pas etre vide");
            return false;
        }


        return true;
    }
}