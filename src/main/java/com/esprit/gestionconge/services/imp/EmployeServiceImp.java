package com.esprit.gestionconge.services.imp;

import com.esprit.gestionconge.repository.EmployeRepository;
import com.esprit.gestionconge.models.Employe;
import com.esprit.gestionconge.services.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeServiceImp implements EmployeService {

    @Autowired
    EmployeRepository employeRepository;


    @Override
    public Employe createEmploye(Employe employe) {
        return employeRepository.save(employe);
    }

    @Override
    public Employe updateEmploye(Employe employe) {
        return employeRepository.saveAndFlush(employe);
    }

    @Override
    public void deleteEmploye(Long id) {
      employeRepository.deleteById(id);
    }

    @Override
    public Employe getEmployeById(Long id) {
        return employeRepository.findById(id).get();
    }

    @Override
    public List<Employe> getAllEmploye() {
        return employeRepository.findAll();
    }

    @Override
    public List<Employe> getEmployebyequipe(String nom) {
        return employeRepository.GetEmployeByEquipe(nom);
    }
}
