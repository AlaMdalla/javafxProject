package com.example.jobflow.services;

import com.example.jobflow.entities.Post;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.jobflow.utils.DatabaseConnection;

import java.sql.*;
import java.util.Collections;

public class ServicePost implements Iservice<Post> {
    private Connection connection;

    public ServicePost() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    @Override
    public void ajouter(Post post) throws SQLException {
        String sql = "INSERT INTO post (name, contenu,date,tag,image,SharName,ShareComment) VALUES ('" + post.getNom() + "', '" + post.getContenu() + "','"+post.getDate()+"','"+post.getTag()+"','"+post.getImage()+"','"+post.getSharName()+"','"+post.getShareComment()+"')";

        Statement statement = connection.createStatement();
        int rowsAffected = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);

        int postId = -1; // Default value for postId if no key is generated

        // Check if the insert was successful and auto-generated keys are available
        if (rowsAffected > 0) {
            // Retrieve the auto-generated keys
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                postId = resultSet.getInt(1); // Retrieve the first auto-generated key

                String updateSql = "UPDATE post SET post_reactions_id = ? WHERE id = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setInt(1, postId); // Set postId as the value for the first parameter
                updateStatement.setInt(2, postId); // Set postId as the value for the second parameter
                int rowsAffected0 = updateStatement.executeUpdate();
                updateStatement.close();

            }
            resultSet.close();
        }



        statement.close();
        String sql0 = "INSERT INTO post_reactions (id, likes,dislike) VALUES ('" + postId + "', '" + 0 + "','"+ 0 +"')";

        Statement statement0 = connection.createStatement();
        statement0.executeUpdate(sql0);

    }

    @Override
    public void modifier(Post post) throws SQLException {
        String sql = "UPDATE post SET name = ?, contenu = ?, date = ?, tag = ?, image = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, post.getNom());
        preparedStatement.setString(2, post.getContenu());
        preparedStatement.setDate(3, Date.valueOf(post.getDate()));
        preparedStatement.setString(4, post.getTag());
        preparedStatement.setString(5, post.getImage()); // Set the image parameter
        preparedStatement.setInt(6, post.getId()); // Corrected the index for id parameter
        preparedStatement.executeUpdate();
    }




    public ObservableList<Post> getAll() throws SQLException {
        ObservableList<Post> posts= FXCollections.observableArrayList();




        String sql = "select * from post";
        //lena nzidou bch nraj3ou el ista m3 comments bel innner join

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()){
         Post post = new Post(rs.getInt("id"),rs.getString("name"),rs.getString("contenu"),rs.getDate("date").toLocalDate(), rs.getString("tag"), rs.getString("image"),rs.getString("SharName"),rs.getString("ShareComment"));
         posts.add(post);

        }
        return posts;



    }
    @Override
    public void supprimer(int id) throws SQLException {

        String sql = "delete from post where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    }
public  Post getPost(int id) throws SQLException {
    for (Post post : Collections.unmodifiableList(this.getAll())) {
        if (post.getId()==id)
            return  post;

    }
    return  null;
}


}

