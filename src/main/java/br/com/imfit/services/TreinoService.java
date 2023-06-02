package br.com.imfit.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.imfit.entities.Exercicio;
import br.com.imfit.entities.Treino;
import br.com.imfit.exceptions.TreinoNotFoundException;
import br.com.imfit.repositories.ExercicioRepository;
import br.com.imfit.repositories.TreinoRepository;

@Service
public class TreinoService {

    private final TreinoRepository treinoRepository;

    private final ExercicioRepository exercicioRepository;

    public TreinoService(TreinoRepository treinoRepository, ExercicioRepository exercicioRepository) {
        this.treinoRepository = treinoRepository;
        this.exercicioRepository = exercicioRepository;
    }

    public List<Treino> findAllTreinos() {
        return Optional.of(treinoRepository.findAll())
                .filter(list -> !list.isEmpty())
                .orElseThrow(() ->
                        new TreinoNotFoundException(
                                "Nenhum Treino encontrado dentro do banco de dados. Verifique conexão com banco de dados ou se existem dados.",
                                System.currentTimeMillis()));
    }

    public Treino findById(int id) {
        return treinoRepository.findById(id).orElseThrow(() ->
                new TreinoNotFoundException(
                        "Treino de id " + id + " não encontrado.",
                        System.currentTimeMillis()));
    }

    public void saveTreino(Treino treino) {
        treinoRepository.save(treino);
    }

    public void delete(int id) {
        treinoRepository.findById(id).ifPresentOrElse(
                treinoRepository::delete, () -> {
                    throw new TreinoNotFoundException(
                            "Treino de id " + id + " não encontrado.",
                            System.currentTimeMillis());
                }
        );
    }

    public Treino exerciciosEmTreino(int treinoId, int exercicioId) {
        List<Exercicio> exercicios = null;
        Treino treino = treinoRepository.findById(treinoId).get();
        Exercicio exercicio = exercicioRepository.findById(exercicioId).get();
        exercicios = treino.getExercicios();
        exercicios.add(exercicio);
        treino.setExercicios(exercicios);
        return treinoRepository.save(treino);
    }

}
