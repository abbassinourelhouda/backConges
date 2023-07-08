package com.esprit.gestionconge.repository;

import com.esprit.gestionconge.models.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratRepository extends JpaRepository<Contrat, Long> {
}
