package services;

import java.sql.SQLException;

import java.util.Set;

public interface IService <T>{
    public  void ajouter(T t ) throws SQLException;
    public  void modifier(T t ) throws SQLException;
    public  void supprimer(T t ) throws SQLException;
    public Set<T> afficher() throws SQLException;



}
