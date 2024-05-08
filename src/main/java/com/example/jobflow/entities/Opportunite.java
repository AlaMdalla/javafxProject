package com.example.jobflow.entities;


public class Opportunite implements Comparable<Opportunite> {

    private int id;
    private String nom;
    private String descreption;
    private String type;
    private int isFavorite;

    private Test test;
    private User user;


    public Opportunite() {
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

    public String getDescreption() {
        return descreption;
    }

    public void setDescreption(String descreption) {
        this.descreption = descreption;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite) {
        this.isFavorite = isFavorite;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String toString() {
        return nom;
    }

    public static String compareVar;

    @Override
    public int compareTo(Opportunite opportunite) {
        return switch (compareVar) {
            case "Tri par nom" -> opportunite.getNom().compareToIgnoreCase(this.getNom());
            case "Tri par descreption" -> opportunite.getDescreption().compareToIgnoreCase(this.getDescreption());
            case "Tri par type" -> opportunite.getType().compareToIgnoreCase(this.getType());
            default -> 0;
        };
    }
}