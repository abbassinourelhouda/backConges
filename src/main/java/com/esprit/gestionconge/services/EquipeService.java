package com.esprit.gestionconge.services;

import com.esprit.gestionconge.models.Equipe;

import java.util.List;

public interface EquipeService {

    Equipe createEquipe(Equipe equipe);
    List<Equipe> getAllEquipe();
    Equipe getEquipeById(Long id);
}
