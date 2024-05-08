package com.example.jobflow.services;

import com.example.jobflow.entities.Opportunite;
import com.example.jobflow.entities.Test;
import com.example.jobflow.entities.User;
import com.example.jobflow.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OpportuniteService implements OpportuniteIService {

    private static OpportuniteService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public OpportuniteService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static OpportuniteService getInstance() {
        if (instance == null) {
            instance = new OpportuniteService();
        }
        return instance;
    }

    public List<Opportunite> getAll() {
        List<Opportunite> listOpportunite = new ArrayList<>();
        try {

            String query = "SELECT * FROM `opportunite` AS x "
                    + "RIGHT JOIN `test` AS y1 ON x.idtest_id = y1.id "
                    + "RIGHT JOIN `user` AS y2 ON x.user_id = y2.id "

                    + "WHERE  x.idtest_id = y1.id  AND   x.user_id = y2.id  ";
            preparedStatement = connection.prepareStatement(query);


            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Opportunite opportunite = new Opportunite();
                opportunite.setId(resultSet.getInt("id"));
                opportunite.setNom(resultSet.getString("nom"));
                opportunite.setDescreption(resultSet.getString("descreption"));
                opportunite.setType(resultSet.getString("type"));
                opportunite.setIsFavorite(resultSet.getInt("is_favorite"));

                Test test = new Test();
                test.setId(resultSet.getInt("y1.id"));
                test.setType(resultSet.getString("y1.type"));
                opportunite.setTest(test);
                User user = new User();
                user.setId(resultSet.getInt("y2.id"));
                user.setEmail(resultSet.getString("y2.email"));
                opportunite.setUser(user);

                listOpportunite.add(opportunite);
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) opportunite : " + exception.getMessage());
        }
        return listOpportunite;
    }

    public List<Test> getAllTests() {
        List<Test> listTests = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `test`");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Test test = new Test();
                test.setId(resultSet.getInt("id"));
                test.setType(resultSet.getString("type"));
                listTests.add(test);
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) tests : " + exception.getMessage());
        }
        return listTests;
    }

    public List<User> getAllUsers() {
        List<User> listUsers = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `user`");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                listUsers.add(user);
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) users : " + exception.getMessage());
        }
        return listUsers;
    }


    public boolean add(Opportunite opportunite) {


        String request = "INSERT INTO `opportunite`(`nom`, `descreption`, `type`, `is_favorite`, `idtest_id`, `user_id`) VALUES(?, ?, ?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setString(1, opportunite.getNom());
            preparedStatement.setString(2, opportunite.getDescreption());
            preparedStatement.setString(3, opportunite.getType());
            preparedStatement.setInt(4, opportunite.getIsFavorite());

            preparedStatement.setInt(5, opportunite.getTest().getId());
            preparedStatement.setInt(6, opportunite.getUser().getId());


            preparedStatement.executeUpdate();
            System.out.println("Opportunite added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) opportunite : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Opportunite opportunite) {

        String request = "UPDATE `opportunite` SET `nom` = ?, `descreption` = ?, `type` = ?, `is_favorite` = ?, `idtest_id` = ?, `user_id` = ? WHERE `id` = ?";

        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setString(1, opportunite.getNom());
            preparedStatement.setString(2, opportunite.getDescreption());
            preparedStatement.setString(3, opportunite.getType());
            preparedStatement.setInt(4, opportunite.getIsFavorite());

            preparedStatement.setInt(5, opportunite.getTest().getId());
            preparedStatement.setInt(6, opportunite.getUser().getId());

            preparedStatement.setInt(7, opportunite.getId());

            preparedStatement.executeUpdate();
            System.out.println("Opportunite edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) opportunite : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `opportunite` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Opportunite deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) opportunite : " + exception.getMessage());
        }
        return false;
    }
}
