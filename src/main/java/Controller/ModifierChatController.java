package Controller;

import entite.chat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import service.ChatService;

import java.sql.SQLException;

public class ModifierChatController {

    private ChatService chatService;
    private chat currentChat;

    @FXML
    private TextField chatnameField;

    @FXML
    private TextField contentField;

    @FXML
    private TextField usernameField;

    public void setChatService(ChatService chatService) {
        this.chatService = chatService;
    }

    public void setChat(chat chat) {
        this.currentChat = chat;
        if (chat != null) {
            chatnameField.setText(chat.getChatname());
            contentField.setText(chat.getContent());
            usernameField.setText(chat.getUsername());
        } else {
            chatnameField.setText("");
            contentField.setText("");
            usernameField.setText("");
        }
    }

    @FXML
    void modifierChat(ActionEvent event) {
        String chatname = chatnameField.getText().trim();
        String content = contentField.getText().trim();
        String username = usernameField.getText().trim();

        if (chatname.isEmpty() || content.isEmpty() || username.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Champs Obligatoires", "Veuillez remplir tous les champs.");
            return;
        }

        try {
            if (currentChat != null) {
                currentChat.setChatname(chatname);
                currentChat.setContent(content);
                currentChat.setUsername(username);
                chatService.modifier(currentChat);
                showAlert(Alert.AlertType.INFORMATION, "Modification réussie", "Le chat a été modifié avec succès.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Le chat à modifier est null.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la modification du chat : " + e.getMessage());
        }
    }

    @FXML
    void retour(ActionEvent event) {
        // Implement retour method
    }

    private void showAlert(Alert.AlertType type, String title, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}