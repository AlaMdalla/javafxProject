package services;

import entites.Comment;
import entites.Post;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.Mydatabase;

import java.sql.*;
import java.util.Collections;

public class ServiceComment implements Iservice<Comment> {
    private Connection connection;

    public ServiceComment() {
        this.connection = Mydatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(Comment comment) throws SQLException {
        String sql = "INSERT INTO comment (post_id,name,contenu,date,time) VALUES ('" + comment.getId_post() + "','" + comment.getName() + "', '" + comment.getContenu() + "','"+comment.getDate()+"','"+comment.getTime()+"')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);

    }

    @Override
    public void modifier(Post p) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {
        String sql = "delete from comment where id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    }


    public ObservableList<Comment> getAll(int id) throws SQLException {
        ObservableList<Comment> comments= FXCollections.observableArrayList();




        String sql = "SELECT * FROM comment WHERE post_id = '" + id + "'";

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()){
            Comment comment = new Comment(rs.getInt("id"),rs.getInt("post_id"), rs.getString("name"),rs.getString("contenu"),rs.getDate("date").toLocalDate(),rs.getTime ("time").toLocalTime());
            comments.add(comment);

        }
        return comments;



    }
    public ObservableList<Comment> getAll() throws SQLException {
        ObservableList<Comment> comments= FXCollections.observableArrayList();




        String sql = "SELECT * FROM comment ";

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()){
            Comment comment = new Comment(rs.getInt("id"),rs.getInt("post_id"), rs.getString("name"),rs.getString("contenu"),rs.getDate("date").toLocalDate(),rs.getTime("time").toLocalTime());
            comments.add(comment);

        }
        return comments;



    }
    @Override
    public void modifier(Comment comment) throws SQLException {
        String sql = "UPDATE comment SET name = ?, contenu = ?, date = ?, time = ?  WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, comment.getName());
        preparedStatement.setString(2, comment.getContenu());
        preparedStatement.setDate(3, Date.valueOf(comment.getDate()));
        preparedStatement.setTime(4,Time.valueOf(comment.getTime()));
        preparedStatement.setInt(5, comment.getId()); // Corrected the index for id parameter
        preparedStatement.executeUpdate();
    }
    public  Comment getComment(int id) throws SQLException {
        for (Comment comment : Collections.unmodifiableList(this.getAll())) {
            if (comment.getId()==id)
                return  comment;

        }
        return  null;
    }
}
