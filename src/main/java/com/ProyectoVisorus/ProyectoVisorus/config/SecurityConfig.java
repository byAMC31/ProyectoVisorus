package com.ProyectoVisorus.ProyectoVisorus.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF
            .authorizeHttpRequests(auth -> auth
                .antMatchers(HttpMethod.POST, "/api/usuarios/").permitAll() // Permitir creación de usuarios sin autenticación
                .antMatchers(HttpMethod.POST, "/api/login/").permitAll() // Permitir login sin autenticación
                .antMatchers(HttpMethod.GET, "/api/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_GUEST") // GET permitido para ambos roles
                .antMatchers(HttpMethod.POST, "/api/**").hasAuthority("ROLE_ADMIN") // POST solo para ADMIN
                .antMatchers(HttpMethod.PUT, "/api/**").hasAuthority("ROLE_ADMIN") // PUT solo para ADMIN
                .antMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ROLE_ADMIN") // DELETE solo para ADMIN
                .anyRequest().authenticated() // Cualquier otra solicitud requiere autenticación
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Sin sesiones
            .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class) // Agregar el filtro JWT
            .build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
