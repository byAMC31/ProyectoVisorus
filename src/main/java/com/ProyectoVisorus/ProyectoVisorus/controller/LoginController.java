package com.ProyectoVisorus.ProyectoVisorus.controller;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ProyectoVisorus.ProyectoVisorus.config.JwtFilter;
import com.ProyectoVisorus.ProyectoVisorus.dto.UserLogin;
import com.ProyectoVisorus.ProyectoVisorus.dto.UserResponse;
import com.ProyectoVisorus.ProyectoVisorus.model.Usuario;
import com.ProyectoVisorus.ProyectoVisorus.service.UsuarioService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping(path="/api/login/")
public class LoginController {
	private final UsuarioService usuarioService;
	
	
	public LoginController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	
	@PostMapping
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserLogin user) throws ServletException {
	    if (usuarioService.validateUser(user)) {
	        Usuario usuario = usuarioService.getUserByEmail(user.getEmail());
	        String token = generateToken(user.getEmail(), usuario.getRol()); // Ahora pasamos el rol

	        // Retornamos el objeto UserResponse con el token y el usuario
	        return ResponseEntity.ok(new UserResponse(token, usuario));
	    }
	    throw new ServletException("Nombre o contraseña inválidos.");
	}

	
	private String generateToken(String email, String role) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.HOUR, 24); // Token válido por 24 horas

	    return Jwts.builder()
	            .setSubject(email)
	            .claim("role", role) // Agregar el rol al token
	            .setIssuedAt(new Date())
	            .setExpiration(calendar.getTime()) // Expiración agregada
	            .signWith(SignatureAlgorithm.HS256, JwtFilter.secret)
	            .compact();
	}

	
	
	
	
}
