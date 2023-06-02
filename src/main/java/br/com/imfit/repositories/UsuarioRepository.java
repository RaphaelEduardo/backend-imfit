package br.com.imfit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.imfit.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByUsername(String username);
    
}
