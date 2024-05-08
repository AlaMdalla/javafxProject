package com.example.jobflow.controllers.back.test;


import com.example.jobflow.MainApp;
import com.example.jobflow.controllers.back.MainWindowController;
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
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.ResourceBundle;

public class ManageController implements Initializable {

    @FXML
    public TextField typeTF;
    @FXML
    public TextField scoreTF;
    @FXML
    public TextField dureeTF;
    @FXML
    public Text pdfPathText;


    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    Test currentTest;
    Path selectedPdfPath;


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

    public void createPdfFile() {
        try {
            Path newPath = FileSystems.getDefault().getPath("src/main/resources/com/example/jobflow/pdf/" + selectedPdfPath.getFileName());
            Files.copy(selectedPdfPath, newPath, StandardCopyOption.REPLACE_EXISTING);
            selectedPdfPath = newPath;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void manage(ActionEvent ignored) {

        if (controleDeSaisie()) {
            if (selectedPdfPath != null) {
                createPdfFile();
            }

            String pdfPath = selectedPdfPath.toString();

            Test test = new Test();
            test.setType(typeTF.getText());
            test.setScore(Integer.parseInt(scoreTF.getText()));
            test.setDuree(dureeTF.getText());
            test.setPdf(pdfPath);

            if (currentTest == null) {
                if (TestService.getInstance().add(test)) {
                    AlertUtils.makeSuccessNotification("Test ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_TEST);
                } else {
                    AlertUtils.makeError("Error");
                }
            } else {
                test.setId(currentTest.getId());
                if (TestService.getInstance().edit(test)) {
                    AlertUtils.makeSuccessNotification("Test modifié avec succés");
                    ShowAllController.currentTest = null;
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_TEST);
                } else {
                    AlertUtils.makeError("Error");
                }
            }

        }
    }

    @FXML
    public void choosePDF(ActionEvent ignored) {

        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(MainApp.mainStage);
        if (file != null) {
            selectedPdfPath = Paths.get(file.getPath());
            pdfPathText.setText(selectedPdfPath.toString());
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

        if (selectedPdfPath == null) {
            AlertUtils.makeInformation("Veuillez choisir un PDF");
            return false;
        }

        return true;
    }
}