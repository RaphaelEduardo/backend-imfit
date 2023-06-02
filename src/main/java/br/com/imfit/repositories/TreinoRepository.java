package br.com.imfit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.imfit.entities.Treino;

public interface TreinoRepository extends JpaRepository<Treino, Integer> {

}
