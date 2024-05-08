package com.example.demo1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.gluonhq.maps.MapView;
import com.gluonhq.maps.MapPoint;

public class CustomMapLayer extends Application {

    private final MapPoint tunisiaPoint = new MapPoint(36.8065, 10.1815); // Coordonnées de Tunis
    private VBox address;

    @Override
    public void start(Stage primaryStage) {
        init();

        VBox root = new VBox();
        root.getChildren().add(address);

        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Custom Map Layer Test");
        primaryStage.show();
    }

    public void init() {
        MapView mapView = createMapView();
        address = new VBox(mapView);
        VBox.setVgrow(mapView, Priority.ALWAYS);
    }

    private MapView createMapView() {
        MapView mapView = new MapView();
        mapView.setPrefSize(1000, 1000);
        mapView.setZoom(5);
        mapView.flyTo(0, tunisiaPoint, 0.1); // Faites pointer la carte sur la Tunisie
        return mapView;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
