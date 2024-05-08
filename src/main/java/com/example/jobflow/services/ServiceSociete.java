package com.example.jobflow.services;

import com.example.jobflow.entities.Societe;
import com.example.jobflow.utils.DatabaseConnection;


import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceSociete implements Iservice<Societe> {

    private final Connection con;

    public ServiceSociete() {
        con = DatabaseConnection.getInstance().getConnection();
    }



    @Override
    public void ajouter(Societe societe) throws SQLException {
        String req = "insert into societe (id, nom, localisation, descreption, siteweb, numtel, secteur) values (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pre = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            pre.setInt(1, societe.getId());
            pre.setString(2, societe.getNom());
            pre.setString(3, societe.getLocalisation());
            pre.setString(4, societe.getDescription());
            pre.setString(5, societe.getSiteweb());
            pre.setInt(6, societe.getNumtel());
            pre.setString(7, societe.getSecteur());
            pre.executeUpdate();

            ResultSet rs = pre.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                societe.setId(id);
            }
        }
    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    @Override
    public void modifier(Societe societe) throws SQLException {
        try {
            String req = "update societe set nom=?, localisation=?, descreption=?, siteweb=?, numtel=?, secteur=? where id=?";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, societe.getNom());
            pre.setString(2, societe.getLocalisation());
            pre.setString(3, societe.getDescription());
            pre.setString(4, societe.getSiteweb());
            pre.setInt(5, societe.getNumtel());
            pre.setString(6, societe.getSecteur());
            pre.setInt(7, societe.getId());

            int rowsAffected = pre.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("La société est modifiée !");
            } else {
                System.out.println("Aucune société n'a été modifiée.");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    public void supprimer(Societe societe) throws SQLException {
        try {
            String reqDeleteCour = "DELETE FROM societe WHERE id = ?";
            try (PreparedStatement preDeleteCour = con.prepareStatement(reqDeleteCour)) {
                preDeleteCour.setInt(1, societe.getId());
                preDeleteCour.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new SQLException("Erreur lors de la suppression de la société : " + ex.getMessage());
        }
    }


    public Set<Societe> afficher() throws SQLException {
        Set<Societe> societes = new HashSet<>();
        String query = "SELECT * FROM societe";
        try (Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery(query)) {
            while (rs.next()) {
                Societe a = new Societe();
                a.setId(rs.getInt("id"));
                a.setNom(rs.getString("nom"));
                a.setLocalisation(rs.getString("localisation"));
                a.setDescription(rs.getString("descreption"));
                a.setSiteweb(rs.getString("siteweb"));
                a.setNumtel(rs.getInt("numtel"));
                a.setSecteur(rs.getString("secteur"));
                societes.add(a);
            }
        }
        return societes;
    }
}
