package com.example.jobflow.services;

import com.example.jobflow.entities.Post;

import java.sql.SQLException;

public interface Iservice<T> {
    void ajouter (T t) throws SQLException;

    void supprimer(int id) throws SQLException;
    void modifier (T t) throws SQLException;


}
