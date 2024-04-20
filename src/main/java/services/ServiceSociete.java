package services;

import entities.Societe;
import utils.MyDB;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceSociete implements IService<Societe> {

    private final Connection con;

    public ServiceSociete() {
        con = MyDB.getInstance().getConnection();
    }



    @Override
    public void ajouter(Societe societe) throws SQLException {
        String req = "insert into societes (NomSociete, NumTelephone, Adress) values (?, ?, ?)";
        try (PreparedStatement pre = con.prepareStatement(req, Statement.RETURN_GENERATED_KEYS)) {
            pre.setString(1, societe.getNomSociete());
            pre.setInt(2, societe.getNumTelephone());
            pre.setString(3, societe.getAdress());
            pre.executeUpdate();

            ResultSet rs = pre.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                societe.setId(id);
            }
        }
    }

    @Override
    public void modifier(Societe societe) throws SQLException {
        try {
            String req = "update societes set NomSociete=?, NumTelephone=?, Adress=? where id=?";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, societe.getNomSociete());
            pre.setInt(2, societe.getNumTelephone());
            pre.setString(3, societe.getAdress());
            pre.setInt(4, societe.getId());

            int rowsAffected = pre.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("La societe est modifiée !");
            } else {
                System.out.println("Aucune societe n'a été modifiée.");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(Societe societe) throws SQLException {
        try {
            String reqDeleteCour = "DELETE FROM societes WHERE id = ?";
            try (PreparedStatement preDeleteCour = con.prepareStatement(reqDeleteCour)) {
                preDeleteCour.setInt(1, societe.getId());
                preDeleteCour.executeUpdate();
            }
        } catch (SQLException ex) {
            throw new SQLException("Erreur lors de la suppression du cours : " + ex.getMessage());
        }
    }

    @Override
    public Set<Societe> afficher() throws SQLException {
        Set<Societe> societes = new HashSet<>();
        String query = "SELECT * FROM societes";
        try (Statement stm = con.createStatement();
             ResultSet rs = stm.executeQuery(query)) {
            while (rs.next()) {
                Societe a = new Societe();
                a.setId(rs.getInt("id"));
                a.setNomSociete(rs.getString("NomSociete"));
                a.setNumTelephone(rs.getInt("NumTelephone"));
                a.setAdress(rs.getString("Adress"));
                societes.add(a);
            }
        }
        return societes;
    }
}
