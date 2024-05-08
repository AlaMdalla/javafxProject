package com.example.jobflow.controllers;

import com.example.jobflow.MainApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }


    @FXML
    public void front(ActionEvent ignored) {
        MainApp.getInstance().loadFront();
    }

    @FXML
    public void back(ActionEvent ignored) {
        MainApp.getInstance().loadBack();
    }
}
