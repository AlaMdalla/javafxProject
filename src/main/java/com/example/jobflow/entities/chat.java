package com.example.jobflow.entities;

public class chat {

    private int id;
    private String content;
    private String chatname;
    private String username;

    // Constructor
    public chat(int id, String content, String chatname, String username) {
        this.id = id;
        this.content = content;
        this.chatname = chatname;
        this.username = username;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getChatname() {
        return chatname;
    }

    public String getUsername() {
        return username;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setChatname(String chatname) {
        this.chatname = chatname;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", chatname='" + chatname + '\'' + // corrected variable name
                ", username='" + username + '\'' +
                '}';
    }
}
