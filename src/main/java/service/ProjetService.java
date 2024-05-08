package service;

import util.DataSource;
import entite.projet;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ProjetService implements IService<projet> {
    private Connection cnx;
    private Connection connection;

    public ProjetService() {
        cnx = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(projet p) throws SQLException {
        String sql = "INSERT INTO pr (prname, description, type, stdate, enddate) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = cnx.prepareStatement(sql)) {
            pst.setString(1, p.getPrname());
            pst.setString(2, p.getDescription());
            pst.setString(3, p.getType());
            pst.setString(4, p.getStdate());
            pst.setString(5, p.getEnddate());
            pst.executeUpdate();
        }
    }

    @Override
    public void modifier(projet p) throws SQLException {
        String sql = "UPDATE pr SET prname=?, description=?, type=?, stdate=?, enddate=? WHERE id=?";
        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            pstmt.setString(1, p.getPrname());
            pstmt.setString(2, p.getDescription());
            pstmt.setString(3, p.getType());
            pstmt.setString(4, p.getStdate());
            pstmt.setString(5, p.getEnddate());
            pstmt.setInt(6, p.getId());
            pstmt.executeUpdate();
        }
    }

    @Override
    public void supprimer(int projetId) throws SQLException {
        String query = "DELETE FROM pr WHERE id = ?";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setInt(1, projetId);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public Set<projet> afficher() throws SQLException {
        Set<projet> projects = new HashSet<>();
        String query = "SELECT * FROM pr";
        try (Statement stm = cnx.createStatement();
             ResultSet rs = stm.executeQuery(query)) {
            while (rs.next()) {
                projet p = new projet(
                        rs.getInt("id"),
                        rs.getString("prname"),
                        rs.getString("description"),
                        rs.getString("type"),
                        rs.getString("stdate"),
                        rs.getString("enddate")
                );
                projects.add(p);
            }
        }
        return projects;
    }

    public void closeConnection() throws SQLException {
        if (cnx != null && !cnx.isClosed()) {
            cnx.close();
        }
    }
}