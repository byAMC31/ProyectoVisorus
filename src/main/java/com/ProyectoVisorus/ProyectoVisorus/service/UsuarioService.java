package com.ProyectoVisorus.ProyectoVisorus.service;


import com.ProyectoVisorus.ProyectoVisorus.Repository.UserRepository;
import com.ProyectoVisorus.ProyectoVisorus.model.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UserRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // Mantén el prefijo "ROLE_" en los roles
        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword()) // Contraseña en texto plano
                .roles(usuario.getRoles().toArray(new String[0]))  // Asigna los roles al usuario
                .build();
    }
}