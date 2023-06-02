package br.com.imfit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.imfit.entities.Exercicio;

public interface ExercicioRepository extends JpaRepository<Exercicio, Integer> {

}
