package com.example.jobflow.services;

import com.example.jobflow.entities.Test;

import java.util.List;

public interface TestIService {


    public List<Test> getAll();

    public boolean add(Test test) ;

    public boolean edit(Test test) ;

    public boolean delete(int id) ;
}
