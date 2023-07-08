package com.esprit.gestionconge.controllers;

import com.esprit.gestionconge.models.Equipe;
import com.esprit.gestionconge.services.imp.EquipeServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/equipe")
@CrossOrigin("*")
public class EquipeController {

    @Autowired
    private EquipeServiceImp equipeServiceImp;

    @GetMapping("/all")
    public List<Equipe> getAllequipe() {
        return equipeServiceImp.getAllEquipe();
    }
    @PostMapping("/save")
    public Equipe saveequipe(@RequestBody Equipe equipe) {

        return equipeServiceImp.createEquipe(equipe);
    }
}
