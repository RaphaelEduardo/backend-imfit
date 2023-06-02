package br.com.imfit.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.imfit.entities.Exercicio;
import br.com.imfit.exceptions.ExercicioNotFoundException;
import br.com.imfit.repositories.ExercicioRepository;

@Service
public class ExercicioService {

    private final ExercicioRepository exercicioRepository;

    public ExercicioService(ExercicioRepository exercicioRepository) {
        this.exercicioRepository = exercicioRepository;
    }

    public List<Exercicio> findAllExercicios() {
        return Optional.of(exercicioRepository.findAll())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() ->
                        new ExercicioNotFoundException(
                                "Nenhum Exercicio encontrado dentro do banco de dados. Verifique conexão com banco de dados ou se existem dados.",
                                System.currentTimeMillis()));
    }

    public Exercicio findById(int id){
        return exercicioRepository.findById(id).orElseThrow(() ->
                new ExercicioNotFoundException(
                        "Exercicio de id " + id + " não encontrado.",
                        System.currentTimeMillis()));
    }

    public void saveExercicio(Exercicio exercicio) {
        exercicioRepository.save(exercicio); 
    }

    public void delete(int id) {
        exercicioRepository.findById(id).ifPresentOrElse(
                exercicioRepository::delete, () -> {
                    throw new ExercicioNotFoundException(
                            "Exercicio de id " + id + " não encontrado.",
                            System.currentTimeMillis());
                }
        );
    }
}
