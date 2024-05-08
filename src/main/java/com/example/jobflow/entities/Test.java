package com.example.jobflow.entities;


public class Test implements Comparable<Test> {

    private int id;
    private String type;
    private int score;
    private String duree;
    private String pdf;

    public Test() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String toString() {
        return type;
    }

    public static String compareVar;

    @Override
    public int compareTo(Test test) {
        return switch (compareVar) {
            case "Tri par type" -> test.getType().compareToIgnoreCase(type);
            case "Tri par score" -> test.getScore() - score;
            case "Tri par duree" -> test.getDuree().compareToIgnoreCase(duree);
            default -> 0;
        };
    }

    public String allAttrToString() {
        return " - Test - " +
                "\nId : " + id +
                "\nType : " + type +
                "\nScore : " + score +
                "\nDuree : " + duree;
    }
}