package gestionpatients.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gestionpatients.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByLibelle(String libelle);
}
