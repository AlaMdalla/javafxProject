package com.example.jobflow.services;

import com.example.jobflow.entities.post_reactions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.jobflow.utils.DatabaseConnection;

import java.sql.*;
import java.util.Collections;

public class ServivePostReactions {
    private Connection connection;

    public ServivePostReactions() {
        this.connection = DatabaseConnection.getInstance().getConnection();;
    }


    public void ajouter(post_reactions post) throws SQLException {
        String sql = "INSERT INTO post_reactions (id, like,dislike,tag,image) VALUES ('" + post.getId() + "', '" + post.getLikes() + "','"+post.getDislike()+"')";

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);

    }
    public  post_reactions getPosReeactiont(int id) throws SQLException {
        for (post_reactions post : Collections.unmodifiableList(this.getAll())) {
            if (post.getId()==id)
                return  post;

        }
        return  null;
    }

    public ObservableList<post_reactions> getAll() throws SQLException {
        ObservableList<post_reactions> posts= FXCollections.observableArrayList();




        String sql = "select * from post_reactions";
        //lena nzidou bch nraj3ou el ista m3 comments bel innner join

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()){
            post_reactions post = new post_reactions(rs.getInt("id"),rs.getInt("likes"),rs.getInt("dislike"));
            posts.add(post);

        }
        return posts;



    }

    public void modifier(post_reactions post) throws SQLException {
        String sql = "UPDATE post_reactions SET  likes = ?, dislike = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(3, post.getId());
        preparedStatement.setInt(1, post.getLikes());
        preparedStatement.setInt(2, post.getDislike());


        preparedStatement.executeUpdate();
    }

}
