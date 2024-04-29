package test;

import entities.Societe;
import services.ServiceSociete;
import utils.MyDB;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws RuntimeException {
        MyDB conn1 = MyDB.getInstance();

        // Création d'un objet Societe
        Societe societe = new Societe(1, "Societe1", 123456789, "Adresse1");

        // Initialisation du service Societe
        ServiceSociete serviceSociete = new ServiceSociete();

        try {
            // Ajout de la societe à la base de données
            serviceSociete.ajouter(societe);

            // Modification de la societe
            societe.setNom("NouveauNomSociete");
            societe.setNumtel(987654321);
            societe.setSecteur("NouvelleAdresse");
            serviceSociete.modifier(societe);

            // Affichage des societes (pas nécessaire pour cette partie du code)
            // serviceSociete.afficher();
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        } finally {
            // Fermez la connexion à la base de données une fois les opérations terminées
            MyDB.getInstance().closeConnection();
        }
    }
}
