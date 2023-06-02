package br.com.imfit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.imfit.entities.AuthRole;

import java.util.Optional;

public interface AuthRoleRepository extends JpaRepository<AuthRole, Integer> {

    Optional<AuthRole> findByRole(String role);
}
