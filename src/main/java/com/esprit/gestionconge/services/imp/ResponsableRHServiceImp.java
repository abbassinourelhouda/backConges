package com.esprit.gestionconge.services.imp;

import com.esprit.gestionconge.models.ResponsableRH;
import com.esprit.gestionconge.repository.ResponsableRHRepository;
import com.esprit.gestionconge.services.ResponsableRHService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponsableRHServiceImp implements ResponsableRHService {

    @Autowired
    ResponsableRHRepository responsableRHRepository;


    @Override
    public ResponsableRH createResponsableRH(ResponsableRH responsableRH) {
        return responsableRHRepository.save(responsableRH);
    }

    @Override
    public ResponsableRH updateResponsableRH(ResponsableRH responsableRH) {
        return responsableRHRepository.saveAndFlush(responsableRH);
    }

    @Override
    public void deleteResponsableRH(Long id) {
     responsableRHRepository.deleteById(id);
    }

    @Override
    public ResponsableRH getResponsableRHById(Long id) {
        return responsableRHRepository.findById(id).get();
    }

    @Override
    public List<ResponsableRH> getAllResponsableRH() {
        return responsableRHRepository.findAll();
    }
}
