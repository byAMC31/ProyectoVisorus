package com.ProyectoVisorus.ProyectoVisorus.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ProyectoVisorus.ProyectoVisorus.model.Usuario;

import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<Usuario, Long>{
	Optional<Usuario> findByEmail(String email);
}
