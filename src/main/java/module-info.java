module com.example.jobflow {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jbcrypt;
    requires java.mail;
    requires simplecaptcha;
    requires javafx.swing;
    requires java.desktop;
    requires com.google.zxing.javase;
    requires com.google.zxing;
    requires org.controlsfx.controls;

    opens com.example.jobflow to javafx.fxml;
    opens com.example.jobflow.entities to javafx.fxml;
    opens com.example.jobflow.controllers to javafx.fxml;
    opens com.example.jobflow.controllers.back to javafx.fxml;
    opens com.example.jobflow.controllers.front to javafx.fxml;
    opens com.example.jobflow.controllers.front.user to javafx.fxml;
    opens com.example.jobflow.controllers.back.user to javafx.fxml;
    opens com.example.jobflow.controllers.forgot_password to javafx.fxml;
    opens com.example.jobflow.controllers.front.opportunite to javafx.fxml;
    opens com.example.jobflow.controllers.front.test to javafx.fxml;
    opens com.example.jobflow.controllers.back.opportunite to javafx.fxml;
    opens com.example.jobflow.controllers.back.test to javafx.fxml;
    opens com.example.jobflow.controllers.front.posts to javafx.fxml;

    exports com.example.jobflow;
    exports com.example.jobflow.entities;
    exports com.example.jobflow.controllers;
    exports com.example.jobflow.controllers.back;
    exports com.example.jobflow.controllers.front;
    exports com.example.jobflow.controllers.front.user;
    exports com.example.jobflow.controllers.back.user;
    exports com.example.jobflow.controllers.forgot_password;
    exports com.example.jobflow.controllers.front.opportunite;
    exports com.example.jobflow.controllers.front.test;
    exports com.example.jobflow.controllers.front.posts;

    exports com.example.jobflow.controllers.back.opportunite;
    exports com.example.jobflow.controllers.back.test;
}