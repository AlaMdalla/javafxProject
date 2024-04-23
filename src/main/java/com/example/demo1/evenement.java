package com.example.demo1;

import javafx.scene.image.Image;

import java.util.Date;

public class evenement {
    private int id;
    private String titre;
    private String localisation;
    private int nb_participant;
    private Date Date;
    private String image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public int getNb_participant() {
        return nb_participant;
    }

    public void setNbparticipant(int nb_participant) {
        this.nb_participant = nb_participant;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.sql.Date date) {
        this.Date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

