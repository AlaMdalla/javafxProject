package services;

import entites.Comment;
import entites.Post;
import util.Mydatabase;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ServiceComment implements Iservice<Comment> {
    private Connection connection;

    public ServiceComment() {
        this.connection = Mydatabase.getInstance().getConnection();
    }
    @Override
    public void ajouter(Comment comment) throws SQLException {
        String sql = "INSERT INTO comment (name, contenu,date,tag,image) VALUES ('" + comment.getName() + "', '" + comment.getContenu() + "','"+comment.getDate()+"','"+comment.getTime()+"')";

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);

    }

    @Override
    public void modifier(Post p) throws SQLException {

    }

    @Override
    public void supprimer(int id) throws SQLException {

    }
}
