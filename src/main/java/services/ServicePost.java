package services;

import entites.Post;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.Mydatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicePost implements Iservice<Post> {
    private Connection connection;

    public ServicePost() {
        this.connection = Mydatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Post post) throws SQLException {
        String sql = "INSERT INTO post (nom, prenom) VALUES ('" + post.getNom() + "', '" + post.getPrenom() + "')";

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);

    }

    @Override
    public void modifier(Post post) throws SQLException {

    }

    public ObservableList<Post> getAll() throws SQLException {
        ObservableList<Post> posts= FXCollections.observableArrayList();




        String sql = "select * from post";

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()){
         Post post = new Post(rs.getInt("id"),rs.getString("nom"),rs.getString("prenom"));
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


}
