package entities;

public class Societe {
    private int id;
    private String nomSociete;
    private int numTelephone;
    private String adress;

    public Societe(int id, String nomSociete, int numTelephone, String adress) {
        this.id = id;
        this.nomSociete = nomSociete;
        this.numTelephone = numTelephone;
        this.adress = adress;
    }
    public Societe( String nomSociete, int numTelephone, String adress) {
        this.nomSociete = nomSociete;
        this.numTelephone = numTelephone;
        this.adress = adress;
    }

    public Societe() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomSociete() {
        return nomSociete;
    }

    public void setNomSociete(String nomSociete) {
        this.nomSociete = nomSociete;
    }

    public int getNumTelephone() {
        return numTelephone;
    }

    public void setNumTelephone(int numTelephone) {
        this.numTelephone = numTelephone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Override
    public String toString() {
        return
               + id +
                  nomSociete + +
                 numTelephone +
                    adress
                ;
    }
}
