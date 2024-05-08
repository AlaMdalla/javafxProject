package com.example.jobflow.controllers.back.test;

import com.example.jobflow.entities.Test;
import com.example.jobflow.services.TestService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {

    @FXML
    PieChart pieChart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Test> testList = TestService.getInstance().getAll();

        // Types les plus utilisés

        List<String> types = new ArrayList<>();

        for (Test test : testList) {
            if (!types.contains(test.getType())) {
                types.add(test.getType());
            }
        }

        for (String type : types) {
            int count = 0;
            for (Test test : testList) {
                if (test.getType().equals(type)) {
                    count++;
                }
            }
            pieChart.getData().add(new PieChart.Data(type, count));
        }
    }
}
