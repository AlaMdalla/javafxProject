package com.example.jobflow.controllers.front.project;

import com.example.jobflow.entities.chat;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import com.example.jobflow.services.ChatService;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AfficherChatController implements Initializable {

    @FXML
    private ListView<chat> listProjet;

    private ObservableList<chat> chatList;

    private ChatService chatService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatService = new ChatService();
        loadChat();
    }

    private void loadChat() {
        try {
            chatList = FXCollections.observableArrayList(chatService.afficher());
            listProjet.setItems(chatList);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des chats", e.getMessage());
        }
    }

    @FXML
    private void deleteChat() {
        ObservableList<chat> selectedChats = listProjet.getSelectionModel().getSelectedItems();
        if (selectedChats.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Aucun chat sélectionné", "Veuillez sélectionner un chat à supprimer.");
        } else {
            try {
                chatService.supprimer(selectedChats);
                chatList.removeAll(selectedChats);
                showAlert(Alert.AlertType.INFORMATION, "Suppression réussie", "Chat(s) supprimé(s) avec succès.", null);
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression du/des chat(s)", e.getMessage());
            }
        }
    }



    @FXML
    private void modifierChat() {
        chat selectedChat = listProjet.getSelectionModel().getSelectedItem();
        if (selectedChat == null) {
            showAlert(Alert.AlertType.WARNING, "Avertissement", "Aucun chat sélectionné", "Veuillez sélectionner un chat à modifier.");
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/path/to/modifierChat.fxml"));
                Parent modifierChatRoot = loader.load();
                ModifierChatController modifierChatController = loader.getController();
                modifierChatController.setChat(selectedChat);

                Stage stage = new Stage();
                stage.setTitle("Modifier Chat");
                stage.setScene(new Scene(modifierChatRoot));
                stage.show();
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement de l'interface de modification", e.getMessage());
            }
        }
    }

    private static void showAlert(Alert.AlertType type, String title, String headerText, String contentText) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}