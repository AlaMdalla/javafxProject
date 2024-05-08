package com.example.jobflow;

import com.example.jobflow.entities.Opportunite;
import com.example.jobflow.services.OpportuniteService;

import java.util.List;

public class MainFX {

    public static void main(String[] args) {
        List<Opportunite> opportuniteList = OpportuniteService.getInstance().getAll();
        System.out.println(opportuniteList);

        Opportunite opportunite = new Opportunite();
        opportunite.setNom("Opportunite 1");
        opportunite.setDescreption("Description 1");
        opportunite.setType("Type 1");
        opportunite.setIsFavorite(1);

        OpportuniteService.getInstance().add(opportunite);

        System.out.println(opportunite);

        opportunite.setNom("Opportunite 2");

        OpportuniteService.getInstance().edit(opportunite);

//        OpportuniteService.getInstance().delete(opportunite.getId());
    }
}
