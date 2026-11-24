package gestionpatients.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gestionpatients.entity.Sexe;

public interface SexeRepository extends JpaRepository<Sexe, Integer> {

    Optional<Sexe> findByLibelle(String libelle);
}
