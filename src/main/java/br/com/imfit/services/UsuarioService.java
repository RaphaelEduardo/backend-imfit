package br.com.imfit.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.imfit.entities.AuthRole;
import br.com.imfit.entities.Usuario;
import br.com.imfit.exceptions.UsuarioNotFoundException;
import br.com.imfit.repositories.AuthRoleRepository;
import br.com.imfit.repositories.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final AuthRoleRepository authRoleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, AuthRoleRepository authRoleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.authRoleRepository = authRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> findAll() {
        return Optional.of(usuarioRepository.findAll()).orElseThrow(() ->
                new UsuarioNotFoundException(
                        "Nenhum usuario encontrado dentro do banco de dados. Verifique conexão com banco de dados ou se existem dados.",
                        System.currentTimeMillis()));
    }

    public Usuario findByUsername(String username) {
        return Optional.of(usuarioRepository.findByUsername(username)).orElseThrow(() ->
                new UsuarioNotFoundException(
                        "Usuario não encontrado - " + username,
                        System.currentTimeMillis()));
    }

    public Usuario findById(int id) {
        return usuarioRepository.findById(id).orElseThrow(() ->
                new UsuarioNotFoundException(
                        "Usuario de id - " + id + " não encontrado",
                        System.currentTimeMillis()));
    }

    public void save(Usuario usuario) {
        Usuario novo_usuario = new Usuario();
        novo_usuario.setNome(usuario.getNome());
        novo_usuario.setUsername(usuario.getUsername());
        novo_usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        novo_usuario.setAtivo(usuario.isAtivo());

        novo_usuario.setTreinos(usuario.getTreinos());

        Collection<AuthRole> managedRoles = new ArrayList<>();
        for(AuthRole role : usuario.getRoles()) {
            AuthRole managedRole = authRoleRepository.findByRole(role.getRole())
                    .orElseThrow(() -> new IllegalArgumentException("Role de autenticação inválida: " + role.getRole()));
            managedRoles.add(managedRole);
        }

        novo_usuario.setRoles(managedRoles);

        usuarioRepository.save(novo_usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username);

        if(usuario == null) {
            throw new UsernameNotFoundException("Inválido username ou senha");
        }

        Collection<SimpleGrantedAuthority> authorities = MapRolesToAuthorities(usuario.getRoles());

        return new org.springframework.security.core.userdetails.User(usuario.getUsername(), usuario.getSenha(), authorities);
    }

    public Collection<SimpleGrantedAuthority> MapRolesToAuthorities(Collection<AuthRole> roles) {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(AuthRole tempRole : roles) {
            SimpleGrantedAuthority tempAuthority = new SimpleGrantedAuthority(tempRole.getRole());
            authorities.add(tempAuthority);
        }

        return authorities;
    }
    
    public void excluir(int id) {
    	usuarioRepository.deleteById(id);
    }
}
