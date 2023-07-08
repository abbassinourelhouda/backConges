package com.esprit.gestionconge.services.imp;

import com.esprit.gestionconge.services.EquipeService;
import com.esprit.gestionconge.models.Equipe;
import com.esprit.gestionconge.repository.EquipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipeServiceImp implements EquipeService {
    @Autowired
    EquipeRepository equipeRepository;
    @Override
    public Equipe createEquipe(Equipe equipe) {
        return equipeRepository.save(equipe);
    }

    @Override
    public List<Equipe> getAllEquipe() {
        return equipeRepository.findAll();
    }

    @Override
    public Equipe getEquipeById(Long id) {
        return equipeRepository.findById(id).get();
    }
}
