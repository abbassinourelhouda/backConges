package com.esprit.gestionconge.services;

import com.esprit.gestionconge.models.Contrat;

import java.util.List;

public interface ContratService {

    Contrat createContrat(Contrat contrat);
    List<Contrat> getAllContrat();
    Contrat getContratById(Long id);
}
