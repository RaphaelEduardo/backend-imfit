package br.com.imfit.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "tb_usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "nome")
    private String nome;

    @Column(name = "senha")
    private String senha;

    @Column(name = "ativo")
    private boolean ativo;

    @ManyToMany
    @JoinTable(name = "tb_usuarios_treino",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "treino_id"))
    private List<Treino> treinos;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "tb_usuario_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<AuthRole> roles;

    public Usuario() {
    }

    public Usuario(int id, String username, String nome, String senha, boolean ativo) {
        this.id = id;
        this.username = username;
        this.nome = nome;
        this.senha = senha;
        this.ativo = ativo;
    }

    public Usuario(int id, String username, String nome, String senha, boolean ativo, Collection<AuthRole> roles) {
        this.id = id;
        this.username = username;
        this.nome = nome;
        this.senha = senha;
        this.ativo = ativo;
        this.roles = roles;
    }

    public Usuario(int id, String username, String nome, String senha, boolean ativo, Collection<AuthRole> roles, List<Treino> treinos) {
        this.id = id;
        this.username = username;
        this.nome = nome;
        this.senha = senha;
        this.ativo = ativo;
        this.roles = roles;
        this.treinos = treinos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Collection<AuthRole> getRoles() {
        return roles;
    }

    public void setRoles(Collection<AuthRole> roles) {
        this.roles = roles;
    }

    public List<Treino> getTreinos() {
        return treinos;
    }

    public void setTreinos(List<Treino> treinos) {
        this.treinos = treinos;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", nome='" + nome + '\'' +
                ", senha='" + senha + '\'' +
                ", ativo=" + ativo +
                ", treinos=" + treinos +
                ", roles=" + roles +
                '}';
    }

}
