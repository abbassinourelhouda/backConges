package com.esprit.gestionconge.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

import java.util.Collection;

@Entity
public class Contrat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private String datedeb;
    private String datefin;
    private String type;

    @OneToMany(mappedBy = "contrat",cascade = CascadeType.REMOVE)
    private Collection<Employe> employes;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDatedeb() {
        return datedeb;
    }

    public void setDatedeb(String datedeb) {
        this.datedeb = datedeb;
    }

    public String getDatefin() {
        return datefin;
    }

    public void setDatefin(String datefin) {
        this.datefin = datefin;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
@JsonIgnore
    public Collection<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(Collection<Employe> employes) {
        this.employes = employes;
    }
}
