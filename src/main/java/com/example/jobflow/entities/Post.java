package com.example.jobflow.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Post {
    int id ;
    String nom;
    String contenu;
    LocalDate date;
    String tag;
    String image;
    private List<Comment> comments;

    public String getSharName() {
        return SharName;
    }

    public void setSharName(String sharName) {
        SharName = sharName;
    }

    public Post(String nom, String contenu, String tag, String sharName, String shareComment) {
        this.nom = nom;
        this.contenu = contenu;
        this.tag = tag;
        SharName = sharName;
        ShareComment = shareComment;
    }

    public String getShareComment() {
        return ShareComment;
    }

    public void setShareComment(String shareComment) {
        ShareComment = shareComment;
    }

    private String SharName;
    private String ShareComment;


    public Post(int id, String nom, String contenu, LocalDate date, String tag, String image, String sharName, String shareComment) {
        this.id = id;
        this.nom = nom;
        this.contenu = contenu;
        this.date = date;
        this.tag = tag;
        this.image = image;
        SharName = sharName;
        ShareComment = shareComment;
    }

    public Post(int id, String nom, String contenu, LocalDate date, String tag, String image) {
        this.id = id;
        this.nom = nom;
        this.contenu = contenu;

        this.tag = tag;
        this.date = date;
        this.image =image;
        this.comments=new ArrayList<>();


    }
    public Post(String nom, String contenu, String tag ,String image) {
        this.nom = nom;
        this.contenu = contenu;
        this.tag = tag;
        this.date = LocalDate.now();
        this.image =image;
        this.comments=new ArrayList<>();
    }
    public Post(String nom, String contenu, String tag ) {
        this.nom = nom;
        this.contenu = contenu;
        this.tag = tag;
        this.date = LocalDate.now();
        this.comments=new ArrayList<>();


    }


    public Post() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate() {
        this.date = LocalDate.now();
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        comments.add(comment);
    }
}
