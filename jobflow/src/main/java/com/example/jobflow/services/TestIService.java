package com.example.jobflow.services;

import com.example.jobflow.entities.Test;
import com.example.jobflow.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface TestIService {


    public List<Test> getAll();

    public boolean add(Test test) ;

    public boolean edit(Test test) ;

    public boolean delete(int id) ;
}
