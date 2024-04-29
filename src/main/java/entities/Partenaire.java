package entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Partenaire {
    private final StringProperty nom;
    private final StringProperty description;
    private final StringProperty duree;
    private Societe societe;
    private int id_Partenaire;
    private int id; // id_societe



    public Partenaire() {
        this(null, null, null);
    }

    public Partenaire(String nom, String description, String duree) {
        this(nom, description, duree, null);
    }

    public Partenaire(String nom, String description, String duree, Societe societe) {
        this.nom = new SimpleStringProperty(nom);
        this.description = new SimpleStringProperty(description);
        this.duree = new SimpleStringProperty(duree);
        this.societe = societe;
    }
    public Partenaire(String nom, String description, String duree, int  id) {
        this.nom = new SimpleStringProperty(nom);
        this.description = new SimpleStringProperty(description);
        this.duree = new SimpleStringProperty(duree);
        this.id = id;
    }

    public int getId_Partenaire() {
        return id_Partenaire;
    }

    public void setId_Partenaire(int id_Partenaire) {
        this.id_Partenaire = id_Partenaire;
    }

    public int getId() { // getter for id_societe
        return id;
    }

    public void setId(int id) { // setter for id_societe
        this.id = id;
    }

    public String getNom() {
        return nom.get();
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getDuree() {
        return duree.get();
    }

    public StringProperty dureeProperty() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree.set(duree);
    }

    public Societe getSociete() {
        return societe;
    }

    public void setSociete(Societe societe) {
        this.societe = societe;
    }

    @Override
    public String toString() {
        return id_Partenaire + " " + nom.get() + " " + description.get() + " " + duree.get() + " " + societe;
    }
}
