module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires mysql.connector.j;
    requires java.sql;
    requires javafx.web;


    opens com.example.demo1 to javafx.fxml;
    exports com.example.demo1;
    exports entities;
    opens entities to javafx.fxml;
    exports utiles;
    opens utiles to javafx.fxml;
}