package services;

import entites.Post;

import java.sql.SQLException;

public interface Iservice<T> {
    void ajouter (T t) throws SQLException;
    void modifier (Post p) throws SQLException;

    void supprimer(int id) throws SQLException;

}
