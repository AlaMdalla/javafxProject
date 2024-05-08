package com.example.jobflow.controllers.front.test;

import com.example.jobflow.controllers.front.MainWindowController;
import com.example.jobflow.entities.Test;
import com.example.jobflow.services.TestService;
import com.example.jobflow.utils.AlertUtils;
import com.example.jobflow.utils.Constants;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;

public class ShowAllController implements Initializable {

    public static Test currentTest;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public VBox mainVBox;
    @FXML
    public ComboBox<String> sortCB;
    @FXML
    public TextField searchField;

    List<Test> listTest;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listTest = TestService.getInstance().getAll();

        sortCB.getItems().addAll(
                "Tri par type",
                "Tri par score",
                "Tri par duree"
        );

        displayData("");
    }

    void displayData(String searchText) {
        mainVBox.getChildren().clear();

        Collections.reverse(listTest);

        if (!listTest.isEmpty()) {
            for (Test test : listTest) {
                if (
                        searchText.isEmpty()
                                || test.getType().toLowerCase().contains(searchText.toLowerCase())
                                || String.valueOf(test.getScore()).contains(searchText.toLowerCase())
                                || test.getDuree().toLowerCase().contains(searchText.toLowerCase())
                ) {
                    mainVBox.getChildren().add(makeTestModel(test));
                }
            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeTestModel(
            Test test
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_TEST)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#typeText")).setText("Type : " + test.getType());
            ((Text) innerContainer.lookup("#scoreText")).setText("Score : " + test.getScore());
            ((Text) innerContainer.lookup("#dureeText")).setText("Duree : " + test.getDuree());

            try {
                String data = test.allAttrToString();
                String path = "./qr_code.jpg";
                BitMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, 500, 500);
                MatrixToImageWriter.writeToPath(matrix, "jpg", Paths.get(path));

                Path qrPath = FileSystems.getDefault().getPath(path);
                if (qrPath.toFile().exists()) {
                    ((ImageView) innerContainer.lookup("#qrImage")).setImage(new Image(qrPath.toUri().toString()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            ((Button) innerContainer.lookup("#pdfButton")).setOnAction((ignored) -> openPdf(test));
            ((Button) innerContainer.lookup("#editButton")).setOnAction((ignored) -> modifierTest(test));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((ignored) -> supprimerTest(test));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    private void openPdf(Test test) {
        try {
            File file = new File(test.getPdf());

            if (file.exists()) {
                Desktop.getDesktop().open(file);
            } else {
                System.out.println("Pdf not found");
                System.out.println(file.getAbsolutePath());
                AlertUtils.makeError("Pdf not found");
            }
        } catch (Exception e) {
            System.out.println("Pdf invalid");
            e.printStackTrace();
            AlertUtils.makeError("Pdf invalid");
        }
    }

    @FXML
    private void ajouterTest(ActionEvent ignored) {
        currentTest = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_TEST);
    }

    private void modifierTest(Test test) {
        currentTest = test;
        MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_MANAGE_TEST);
    }

    private void supprimerTest(Test test) {
        currentTest = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer test ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                if (TestService.getInstance().delete(test.getId())) {
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_TEST);
                } else {
                    AlertUtils.makeError("Could not delete test");
                }
            }
        }
    }

    public void sort(ActionEvent actionEvent) {
        Test.compareVar = sortCB.getValue();
        Collections.sort(listTest);
        displayData(searchField.getText());
    }

    public void search(KeyEvent keyEvent) {
        displayData(searchField.getText());
    }
}
