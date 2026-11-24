package gestionpatients.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gestionpatients.entity.Personne;

public interface PersonneRepository extends JpaRepository<Personne, Long> {

    Optional<Personne> findByEmailIgnoreCase(String email);

    List<Personne> findByRoleLibelleOrderByNomAscPrenomAsc(String libelleRole);

    Optional<Personne> findByIdAndRoleLibelle(Long id, String libelleRole);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
}
