package gestionpatients.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gestionpatients.entity.Personne;
import gestionpatients.entity.Role;

public interface PersonneRepository extends JpaRepository<Personne, Long> {

    Optional<Personne> findByEmailIgnoreCase(String email);

    List<Personne> findByRoleOrderByNomAscPrenomAsc(Role role);

    Optional<Personne> findByIdAndRole(Long id, Role role);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);
}
