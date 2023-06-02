package br.com.imfit.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.imfit.entities.Exercicio;
import br.com.imfit.services.ExercicioService;

@RestController
@RequestMapping("/api/exercicios")
public class ExercicioController {

    private final ExercicioService exercicioService;

    public ExercicioController(ExercicioService exercicioService) {
        this.exercicioService = exercicioService;
    }
    
    // ADMIN
    @GetMapping
    public List<Exercicio> getAllExercicios() {
        return exercicioService.findAllExercicios();
    }

    // USER
    @GetMapping("/{exercicioId}")
    public Exercicio getExercicio(@PathVariable int exercicioId) {
        return exercicioService.findById(exercicioId);
    }

    // ADMIN
    @PostMapping
    public ResponseEntity<Integer> saveExercicio(@RequestBody Exercicio exercicio) {
        exercicioService.saveExercicio(exercicio);
        return new ResponseEntity<>(exercicio.getId(), HttpStatus.CREATED);
    }
    
  // ADMIN
  @PutMapping("/{exercicioId}")
	public ResponseEntity<String> atualizar(@PathVariable int exercicioId, @RequestBody Exercicio exercicio) {
		if (getExercicio(exercicioId) == null) {
			return ResponseEntity.notFound().build();
		}
		exercicio.setId(exercicioId); 
		exercicioService.saveExercicio(exercicio);
		return new ResponseEntity<>(exercicio.getNome(), HttpStatus.OK);
	}

    // ADMIN
    @DeleteMapping("/{exercicioId}")
    public ResponseEntity<Void> deleteExercicio(@PathVariable int exercicioId) {
        exercicioService.delete(exercicioId);
        return ResponseEntity.noContent().build();
    }

}
