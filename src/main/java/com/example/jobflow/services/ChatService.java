package com.example.jobflow.services;

import com.example.jobflow.entities.chat;
import javafx.collections.ObservableList;
import com.example.jobflow.utils.DatabaseConnection;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ChatService {
    private Connection cnx;

    public ChatService() {
        cnx = DatabaseConnection.getInstance().getConnection();
    }

    public void ajouter(chat c) throws SQLException {
        String sql = "INSERT INTO ch (content, chatname, username) VALUES (?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setString(1, c.getContent());
            pst.setString(2, c.getChatname());
            pst.setString(3, c.getUsername());
            pst.executeUpdate();
        }
    }

    public void modifier(chat c) throws SQLException {
        String sql = "UPDATE ch SET content = ?, chatname = ?, username = ? WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setString(1, c.getContent());
            pst.setString(2, c.getChatname());
            pst.setString(3, c.getUsername());
            pst.setInt(4, c.getId());
            pst.executeUpdate();
        }
    }

    public Set<chat> afficher() throws SQLException {
        Set<chat> chats = new HashSet<>();
        String query = "SELECT * FROM ch";
        try (Statement stm = cnx.createStatement();
             ResultSet rs = stm.executeQuery(query)) {
            while (rs.next()) {
                chat c = new chat(
                        rs.getInt("id"),
                        rs.getString("content"),
                        rs.getString("chatname"),
                        rs.getString("username")
                );
                chats.add(c);
            }
        }
        return chats;
    }

    public void supprimer(ObservableList<chat> chats) throws SQLException {
        String sql = "DELETE FROM ch WHERE id = ?";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            for (chat c : chats) {
                pst.setInt(1, c.getId());
                pst.addBatch();
            }
            pst.executeBatch();
        }
    }

    // Ensure proper closure of the connection
    @Override
    protected void finalize() throws Throwable {
        if (cnx != null && !cnx.isClosed()) {
            cnx.close();
        }
        super.finalize();
    }
}