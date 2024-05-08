package com.example.jobflow.services;

import com.example.jobflow.entities.Test;
import com.example.jobflow.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestService {

    private static TestService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public TestService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static TestService getInstance() {
        if (instance == null) {
            instance = new TestService();
        }
        return instance;
    }

    public List<Test> getAll() {
        // Alter table add column upToRent boolean default false / only alter if exist;
        String alterReq = "ALTER TABLE `test` ADD COLUMN `pdf` VARCHAR(255) DEFAULT NULL";
        try {
            PreparedStatement pst = connection.prepareStatement(alterReq);
            pst.executeUpdate();
        } catch (SQLException ignored) {
        }

        alterReq = "alter table test modify id int auto_increment;";
        try {
            PreparedStatement pst = connection.prepareStatement(alterReq);
            pst.executeUpdate();
        } catch (SQLException ignored) {
        }

        alterReq = "alter table test auto_increment = 1;";
        try {
            PreparedStatement pst = connection.prepareStatement(alterReq);
            pst.executeUpdate();
        } catch (SQLException ignored) {
        }

        List<Test> listTest = new ArrayList<>();
        try {

            preparedStatement = connection.prepareStatement("SELECT * FROM `test`");


            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Test test = new Test();
                test.setId(resultSet.getInt("id"));
                test.setType(resultSet.getString("type"));
                test.setScore(resultSet.getInt("score"));
                test.setDuree(resultSet.getString("duree"));
                test.setPdf(resultSet.getString("pdf"));

                listTest.add(test);
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) test : " + exception.getMessage());
        }
        return listTest;
    }


    public boolean add(Test test) {
        String request = "INSERT INTO `test`(`type`, `score`, `duree`, `pdf`) VALUES(?, ?, ?, ?)";

        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setString(1, test.getType());
            preparedStatement.setInt(2, test.getScore());
            preparedStatement.setString(3, test.getDuree());
            preparedStatement.setString(4, test.getPdf());

            preparedStatement.executeUpdate();
            System.out.println("Test added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) test : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Test test) {

        String request = "UPDATE `test` SET `type` = ?, `score` = ?, `duree` = ?, `pdf` = ? WHERE `id` = ?";

        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setString(1, test.getType());
            preparedStatement.setInt(2, test.getScore());
            preparedStatement.setString(3, test.getDuree());
            preparedStatement.setString(4, test.getPdf());
            preparedStatement.setInt(5, test.getId());

            preparedStatement.executeUpdate();
            System.out.println("Test edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) test : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `test` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Test deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) test : " + exception.getMessage());
        }
        return false;
    }
}
