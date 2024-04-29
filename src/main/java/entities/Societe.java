package entities;

public class Societe {
    private int id;
    private String nom;
    private String localisation;
    private String description;
    private String siteweb;
    private int numtel;
    private String secteur;

    public Societe(int id, String nom, String localisation, String description, String siteweb, int numtel, String secteur) {
        this.id = id;
        this.nom = nom;
        this.localisation = localisation;
        this.description = description;
        this.siteweb = siteweb;
        this.numtel = numtel;
        this.secteur = secteur;
    }
    public Societe(String nom, String localisation, String description, String siteweb, int numtel, String secteur) {
        this.nom = nom;
        this.localisation = localisation;
        this.description = description;
        this.siteweb = siteweb;
        this.numtel = numtel;
        this.secteur = secteur;
    }
    public Societe(int id, String nom, int numtel, String secteur) {
        this.id = id;
        this.nom = nom;
        this.numtel = numtel;
        this.secteur = secteur;
    }
    public Societe() {}

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

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSiteweb() {
        return siteweb;
    }

    public void setSiteweb(String siteweb) {
        this.siteweb = siteweb;
    }

    public int getNumtel() {
        return numtel;
    }

    public void setNumtel(int numtel) {
        this.numtel = numtel;
    }

    public String getSecteur() {
        return secteur;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    @Override
    public String toString() {
        return "Societe{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", localisation='" + localisation + '\'' +
                ", description='" + description + '\'' +
                ", siteweb='" + siteweb + '\'' +
                ", numtel=" + numtel +
                ", secteur='" + secteur + '\'' +
                '}';
    }
}
