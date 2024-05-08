package com.example.jobflow.controllers.back.opportunite;

import com.example.jobflow.entities.Opportunite;
import com.example.jobflow.services.OpportuniteService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.*;

public class StatisticsController implements Initializable {

    @FXML
    PieChart pieChart;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Opportunite> opportuniteList = OpportuniteService.getInstance().getAll();

        // Types les plus utilisés

        List<String> types = new ArrayList<>();

        for (Opportunite opportunite : opportuniteList) {
            if (!types.contains(opportunite.getType())) {
                types.add(opportunite.getType());
            }
        }

        Map<String, Integer> map = new HashMap<>();

        for (String type : types) {
            map.put(type, 0);
        }

        for (Opportunite opportunite : opportuniteList) {
            map.put(opportunite.getType(), map.get(opportunite.getType()) + 1);
        }

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            pieChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }
    }
}
