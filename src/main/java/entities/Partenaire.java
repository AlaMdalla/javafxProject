package entities;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Partenaire {
    private final StringProperty nom;
    private final StringProperty description;
    private Societe societe;
    private int id_Partenaire;
    private int id; // id_societe

    public Partenaire() {
        this(null, null);
    }

    public Partenaire(String nom, String description) {
        this(nom, description, null);
    }

    public Partenaire(String nom, String description, Societe societe) {
        this.nom = new SimpleStringProperty(nom);
        this.description = new SimpleStringProperty(description);
        this.societe = societe;
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

    public Societe getSociete() {
        return societe;
    }

    public void setSociete(Societe societe) {
        this.societe = societe;
    }

    @Override
    public String toString() {
        return id_Partenaire + " " + nom + " " + description + " " + societe;
    }
}
