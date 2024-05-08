package com.example.jobflow.services;

import com.example.jobflow.entities.Opportunite;
import com.example.jobflow.entities.Test;
import com.example.jobflow.entities.User;

import java.util.List;

public interface OpportuniteIService   {

    public List<Opportunite> getAll();

    public List<Test> getAllTests();

    public List<User> getAllUsers();


    public boolean add(Opportunite opportunite);

    public boolean edit(Opportunite opportunite);

    public boolean delete(int id);
}
