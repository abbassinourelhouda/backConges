package com.esprit.gestionconge.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Employe extends User{
    private String numcin;
    private String nom;
    private String prenom;
    private String adresse;
    private String tel;


    @ManyToOne
    @JoinColumn(name="equipe_id")
    private Equipe equipe;

    @ManyToOne
    @JoinColumn(name="contrat_id")
    private Contrat contrat;

    public Employe(String username, String email, String password, String numcin, String nom, String prenom, String adresse, String tel) {
        super(username, email, password);
        this.numcin = numcin;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.tel = tel;
    }

    public Employe() {
    }

    public String getNumcin() {
        return numcin;
    }

    public void setNumcin(String numcin) {
        this.numcin = numcin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }

    public Contrat getContrat() {
        return contrat;
    }

    public void setContrat(Contrat contrat) {
        this.contrat = contrat;
    }
}
