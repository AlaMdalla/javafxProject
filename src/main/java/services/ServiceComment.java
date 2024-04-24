package services;

import entites.Comment;
import entites.Post;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.Mydatabase;

import java.sql.*;

public class ServiceComment implements Iservice<Comment> {
    private Connection connection;

    public ServiceComment() {
        this.connection = Mydatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(Comment comment) throws SQLException {
        String sql = "INSERT INTO comment (id_post,name,contenu,date,time) VALUES ('" + comment.getId_post() + "','" + comment.getName() + "', '" + comment.getContenu() + "','"+comment.getDate()+"','"+comment.getTime()+"')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);

    }

    @Override
    public void modifier(Post p) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {

    }

    public ObservableList<Comment> getAll(int id) throws SQLException {
        ObservableList<Comment> comments= FXCollections.observableArrayList();




        String sql = "SELECT * FROM comment WHERE id_post = '" + id + "'";

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()){
            Comment comment = new Comment(rs.getInt("id"),rs.getInt("id_post"), rs.getString("name"),rs.getString("contenu"),rs.getDate("date").toLocalDate(), Time.valueOf(rs.getString("time")));
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
            Comment comment = new Comment(rs.getInt("id"),rs.getInt("id_post"), rs.getString("name"),rs.getString("contenu"),rs.getDate("date").toLocalDate(), Time.valueOf(rs.getString("time")));
            comments.add(comment);

        }
        return comments;



    }
}
