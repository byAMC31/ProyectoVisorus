package com.ProyectoVisorus.ProyectoVisorus.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ProyectoVisorus.ProyectoVisorus.model.Usuario;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}
