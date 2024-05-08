package test;

import entite.projet;
import service.ProjetService;
import util.DataSource;

import java.sql.SQLException;
import java.util.Date;

public class Main {

    public static void main(String[] args) {
        DataSource conn1 = DataSource.getInstance();

        // Création des objets Date pour Date_debut et Date_fin
        Date dateDebutC1 = new Date(2024 - 1900, 02, 1); // 2024-01-01
        Date dateFinC1 = new Date(2024 - 1900, 04, 2);   // 2024-01-02
        Date dateDebutC2 = new Date(2024 - 1900, 05, 3); // 2024-01-03
        Date dateFinC2 = new Date(2024 - 1900, 05, 4);   // 2024-01-04

        // Création des objets projet avec les dates
        projet c1 = new projet(1, "Java", dateDebutC2.toString(), dateFinC2.toString(), "Description", "Type");

        ProjetService service = new ProjetService();
        try {
            // Ajout d'un projet
            service.ajouter(c1);
            System.out.println("Projet ajouté avec succès.");

            // Modification d'un projet
            c1.setPrname("Java SE");
            service.modifier(c1);
            System.out.println("Projet modifié avec succès.");
        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
    }
}
