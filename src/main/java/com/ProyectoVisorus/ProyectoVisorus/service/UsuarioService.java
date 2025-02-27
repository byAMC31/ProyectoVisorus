package com.ProyectoVisorus.ProyectoVisorus.service;


import com.ProyectoVisorus.ProyectoVisorus.Repository.UsuariosRepository;
import com.ProyectoVisorus.ProyectoVisorus.dto.UserLogin;
import com.ProyectoVisorus.ProyectoVisorus.model.Usuario;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
	private final UsuariosRepository usuariosRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	public UsuarioService(UsuariosRepository usuariosRepository) {
		this.usuariosRepository = usuariosRepository;
	}
	
	// Obtener todos los usuarios
	public List<Usuario> getAllUsers() {
		return usuariosRepository.findAll();
	}
	
	// Obtener 1 usuario
	public Usuario getUser(Long id) {
		if (usuariosRepository.existsById(id)) {
			return usuariosRepository.findById(id).get();
		}
		return null;
	}
	 
	
	// Obtener 1 usuario por email
	public Usuario getUserByEmail(String email) {
	    return usuariosRepository.findByEmail(email).orElse(null); 
	}


	// Borrar usuario
	public Usuario delUser(Long id) {
		Usuario usr = getUser(id);
		if (usr != null ) {
			usuariosRepository.deleteById(id);
		}
		return usr;
	}
	
	// Añadir usuario
	public Usuario addUser(Usuario usuario) {
		Optional<Usuario> user = usuariosRepository.findByEmail(usuario.getEmail());
		if(user.isEmpty()) {
			usuario.setPassword(encoder.encode(usuario.getPassword()));
			return usuariosRepository.save(usuario);
		}else {
			return null;
		}
	}
	


	public boolean validateUser(UserLogin user) {
		Optional<Usuario> usr = usuariosRepository.findByEmail(user.getEmail());
		if (usr.isPresent()) {
			
			Usuario tmpUser = usr.get();
			if (encoder.matches(user.getPassword(), tmpUser.getPassword())) {
				return true;
			}
		}
		return false;
	}
}