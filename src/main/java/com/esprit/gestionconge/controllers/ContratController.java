package com.esprit.gestionconge.controllers;

import com.esprit.gestionconge.models.Contrat;
import com.esprit.gestionconge.services.imp.ContratServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contrat")
@CrossOrigin("*")
public class ContratController {

    @Autowired
    private ContratServiceImp contratServiceImp;

    @GetMapping("/all")
    public List<Contrat> getAllcontrat() {
        return contratServiceImp.getAllContrat();
    }
    @PostMapping("/save")
    public Contrat saveemploye(@RequestBody Contrat contrat) {

        return contratServiceImp.createContrat(contrat);
    }
}
