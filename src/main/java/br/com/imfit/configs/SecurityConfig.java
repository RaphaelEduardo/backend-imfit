package br.com.imfit.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import br.com.imfit.services.UsuarioService;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UsuarioService usuarioService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(usuarioService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(config ->
                config
                        // CONTROLLER TREINO
                        .requestMatchers(HttpMethod.POST, "/api/treinos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/treinos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/treinos").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/treinos/**").hasRole("USER")
                        //.requestMatchers(HttpMethod.DELETE, "/api/treinos/**").hasRole("ADMIN")
                        
                        // CONTROLLER EXERCICIO
                        .requestMatchers(HttpMethod.POST, "/api/exercicios").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/exercicios/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/exercicios").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/exercicios/**").hasRole("USER")
                        //.requestMatchers(HttpMethod.PUT, "/api/exercicios/**").hasRole("ADMIN")
                        
                        // CONTROLLER USUARIO
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/usuarios").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/usuarios/{usuarioId}").hasRole("USER")
//                        .requestMatchers(HttpMethod.PUT, "/api/usuarios/**").hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.DELETE, "/api/usuarios/**").hasRole("ADMIN")
        );

        http.httpBasic();
        // CSRF Desativado (Geralmente em Stateless API é recomendado deixar desativado).
        http.csrf().disable();

        return http.build();
    }
}
