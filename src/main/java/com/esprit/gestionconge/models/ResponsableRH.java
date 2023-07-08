package com.esprit.gestionconge.models;

import javax.persistence.Entity;

@Entity
public class ResponsableRH extends User{
    private String matricule;
    private String service;

    public ResponsableRH(String username, String email, String password, String matricule, String service) {
        super(username, email, password);
        this.matricule = matricule;
        this.service = service;
    }

    public ResponsableRH() {
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
