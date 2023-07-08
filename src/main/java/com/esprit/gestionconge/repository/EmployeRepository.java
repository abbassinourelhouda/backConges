package com.esprit.gestionconge.repository;

import com.esprit.gestionconge.models.Employe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeRepository extends JpaRepository<Employe, Long> {
    @Query("SELECT e FROM Employe e WHERE e.equipe.nom like :nom")
    List<Employe> GetEmployeByEquipe(@Param("nom") String nom);
}
