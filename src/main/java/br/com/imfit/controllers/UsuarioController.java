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

import br.com.imfit.entities.Usuario;
import br.com.imfit.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // ADMIN
    @GetMapping
    public List<Usuario> getAllUsuario() {
        return usuarioService.findAll();
    }

    // USER
    @GetMapping("/{usuarioId}")
    public Usuario getUsuario(@PathVariable int usuarioId) {
        return usuarioService.findById(usuarioId);
    }

    // ADMIN
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody Usuario usuario) {
        System.out.println(usuario);
        usuarioService.save(usuario);
        return new ResponseEntity<>(usuario.getUsername(), HttpStatus.CREATED);
    }
    
    //ADMIN
    @PutMapping("/{usuarioId}")
	public ResponseEntity<String> atualizar(@PathVariable int usuarioId, @RequestBody Usuario usuario) {
		if (getUsuario(usuarioId) == null) {
			return ResponseEntity.notFound().build();
		}
		usuario.setId(usuarioId); 
		usuarioService.save(usuario);
		return new ResponseEntity<>(usuario.getUsername(), HttpStatus.OK);
	}

    //ADMIN
	@DeleteMapping("/{usuarioId}")
	public ResponseEntity<Void> remover(@PathVariable int usuarioId) { 
		if (getUsuario(usuarioId) == null) {
			return ResponseEntity.notFound().build();
		}
		usuarioService.excluir(usuarioId);
		return ResponseEntity.noContent().build();
	} 

}
