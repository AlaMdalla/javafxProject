package services;

import entites.Post;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import util.Mydatabase;

import java.sql.*;
import java.util.Collections;

public class ServicePost implements Iservice<Post> {
    private Connection connection;

    public ServicePost() {
        this.connection = Mydatabase.getInstance().getConnection();
    }

    @Override
    public void ajouter(Post post) throws SQLException {
        String sql = "INSERT INTO post (name, contenu,date,tag,image) VALUES ('" + post.getNom() + "', '" + post.getContenu() + "','"+post.getDate()+"','"+post.getTag()+"','"+post.getImage()+"')";

        Statement statement = connection.createStatement();
        statement.executeUpdate(sql);

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
         Post post = new Post(rs.getInt("id"),rs.getString("name"),rs.getString("contenu"),rs.getDate("date").toLocalDate(), rs.getString("tag"), rs.getString("image"));
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
