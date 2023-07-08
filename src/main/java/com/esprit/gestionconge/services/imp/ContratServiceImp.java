package com.esprit.gestionconge.services.imp;

import com.esprit.gestionconge.models.Contrat;
import com.esprit.gestionconge.repository.ContratRepository;
import com.esprit.gestionconge.services.ContratService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContratServiceImp implements ContratService {

    @Autowired
    ContratRepository contratRepository;


    @Override
    public Contrat createContrat(Contrat contrat) {
        return contratRepository.save(contrat);
    }

    @Override
    public List<Contrat> getAllContrat() {
        return contratRepository.findAll();
    }

    @Override
    public Contrat getContratById(Long id) {
        return contratRepository.findById(id).get();
    }
}
