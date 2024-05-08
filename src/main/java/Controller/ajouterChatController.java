package Controller;

import entite.chat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import service.ChatService;

import java.sql.SQLException;

public class ajouterChatController {
    ChatService chatService = new ChatService();

    @FXML
    private TextField chatnameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextArea contentField;

    @FXML
    void ajouterChat(ActionEvent event) {
        String chatname = chatnameField.getText().trim();
        String username = usernameField.getText().trim();
        String content = contentField.getText().trim();

        if (chatname.isEmpty() || username.isEmpty() || content.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Champs Obligatoires", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            chat newChat = new chat(0, content, chatname, username);
            chatService.ajouter(newChat);

            showAlert(Alert.AlertType.INFORMATION, "Succès", "Chat ajouté avec succès.");

            // Clear fields after successful addition
            chatnameField.clear();
            usernameField.clear();
            contentField.clear();

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur SQL", "Erreur lors de l'ajout du chat : " + e.getMessage());
            e.printStackTrace(); // Print the stack trace for debugging
        }
    }

    private void showAlert(Alert.AlertType type, String title, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
