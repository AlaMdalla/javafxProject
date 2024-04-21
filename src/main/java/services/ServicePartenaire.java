package services;

import entities.Partenaire;
import entities.Societe;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServicePartenaire implements IService<Partenaire> {
    private final Connection con;

    public ServicePartenaire() {
        con = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Partenaire partenaire) throws SQLException {
        String req = "INSERT INTO Partenaire (nom, description, id) VALUES (?, ?, ?)";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setString(1, partenaire.getNom());
            pre.setString(2, partenaire.getDescription());
            pre.setInt(3, partenaire.getSociete().getId());
            int rowsInserted = pre.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new partenaire was inserted successfully!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void modifier(Partenaire partenaire) throws SQLException {
        String req = "UPDATE Partenaire SET nom=?, description=? WHERE id_Partenaire=?";
        try (PreparedStatement pre = con.prepareStatement(req)) {
            pre.setString(1, partenaire.getNom());
            pre.setString(2, partenaire.getDescription());
            pre.setInt(3, partenaire.getId_Partenaire());
            pre.executeUpdate();
        }
    }

    @Override
    public void supprimer(Partenaire partenaire) throws SQLException {
        String req = "DELETE FROM Partenaire WHERE id_Partenaire = ?";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setInt(1, partenaire.getId_Partenaire());
        pre.executeUpdate();
    }

    @Override
    public Set<Partenaire> afficher() throws SQLException {
        Set<Partenaire> partenaires = new HashSet<>();
        String req = "SELECT * FROM Partenaire";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(req)) {
            while (rs.next()) {
                Partenaire partenaire = new Partenaire();
                partenaire.setId_Partenaire(rs.getInt("id_Partenaire"));
                partenaire.setNom(rs.getString("nom"));
                partenaire.setDescription(rs.getString("description"));

                // Set the societe for the partenaire
                int idSociete = rs.getInt("id");
                partenaire.setSociete(new Societe(idSociete, "", 0, ""));

                partenaires.add(partenaire);
            }
        }
        return partenaires;
    }

    public List<Partenaire> getAllPartenaires() throws SQLException {
        List<Partenaire> partenaires = new ArrayList<>();
        String req = "SELECT * FROM Partenaire";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(req)) {
            while (rs.next()) {
                Partenaire partenaire = new Partenaire();
                partenaire.setId_Partenaire(rs.getInt("id_Partenaire"));
                partenaire.setNom(rs.getString("nom"));
                partenaire.setDescription(rs.getString("description"));

                // Set the societe for the partenaire
                int idSociete = rs.getInt("id");
                partenaire.setSociete(new Societe(idSociete, "", 0, ""));

                partenaires.add(partenaire);
            }
        }
        return partenaires;
    }

    public List<Societe> getAllSocietes() throws SQLException {
        List<Societe> societes = new ArrayList<>();
        String req = "SELECT * FROM societes";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(req)) {
            while (rs.next()) {
                Societe societe = new Societe();
                societe.setId(rs.getInt("id"));
                societe.setNomSociete(rs.getString("NomSociete"));
                societe.setNumTelephone(rs.getInt("NumTelephone"));
                societe.setAdress(rs.getString("Adress"));
                societes.add(societe);
            }
        }
        return societes;
    }


    public void setSociete(Societe selectedSociete) {
    }

    public List<Partenaire> recupererPartenairePourSociete(Societe selectedSociete) {
        List<Partenaire> partenaires = new ArrayList<>();
        if (selectedSociete != null) {
            String req = "SELECT * FROM Partenaire WHERE id_societe = ?";
            try (PreparedStatement pre = con.prepareStatement(req)) {
                pre.setInt(1, selectedSociete.getId());
                try (ResultSet rs = pre.executeQuery()) {
                    while (rs.next()) {
                        Partenaire partenaire = new Partenaire();
                        partenaire.setId_Partenaire(rs.getInt("id_Partenaire"));
                        partenaire.setNom(rs.getString("nom"));
                        partenaire.setDescription(rs.getString("description"));

                        partenaires.add(partenaire);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return partenaires;
    }
}
