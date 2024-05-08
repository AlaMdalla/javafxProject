module com.example.jobflow {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.jobflow to javafx.fxml;
    opens com.example.jobflow.entities to javafx.fxml;
    opens com.example.jobflow.controllers to javafx.fxml;
    opens com.example.jobflow.controllers.back to javafx.fxml;
    opens com.example.jobflow.controllers.front to javafx.fxml;
    opens com.example.jobflow.controllers.front.opportunite to javafx.fxml;
    opens com.example.jobflow.controllers.front.test to javafx.fxml;
    opens com.example.jobflow.controllers.back.opportunite to javafx.fxml;
    opens com.example.jobflow.controllers.back.test to javafx.fxml;

    exports com.example.jobflow;
    exports com.example.jobflow.entities;
    exports com.example.jobflow.controllers;
    exports com.example.jobflow.controllers.back;
    exports com.example.jobflow.controllers.front;
    exports com.example.jobflow.controllers.front.opportunite;
    exports com.example.jobflow.controllers.front.test;
    exports com.example.jobflow.controllers.back.opportunite;
    exports com.example.jobflow.controllers.back.test;
}