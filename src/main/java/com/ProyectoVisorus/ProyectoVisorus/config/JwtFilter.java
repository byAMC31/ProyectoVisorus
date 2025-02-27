package com.ProyectoVisorus.ProyectoVisorus.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtFilter extends GenericFilterBean {
    // Clave secreta utilizada para firmar y verificar los tokens JWT
    public static final String secret = "FraseUltraSecreta";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // Convertir la solicitud y la respuesta a tipos específicos de HTTP
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // Obtener el encabezado "Authorization" de la solicitud
        String authHeader = httpServletRequest.getHeader("Authorization");

        // Verificar si la solicitud es para el endpoint /api/usuarios/ con el método POST
        boolean isCreateUserRequest = "POST".equals(httpServletRequest.getMethod()) 
                && httpServletRequest.getRequestURI().contains("/api/usuarios/");

        // Verificar si la solicitud es para el endpoint /api/login con el método POST
        boolean isLoginRequest = "POST".equals(httpServletRequest.getMethod()) 
                && httpServletRequest.getRequestURI().contains("/api/login");

        // Determinar si la solicitud está protegida (requiere un token)
        boolean isProtectedMethod = !isCreateUserRequest && !isLoginRequest 
                && (("POST".equals(httpServletRequest.getMethod()) && !httpServletRequest.getRequestURI().contains("/api/usuarios/")) 
                || ("GET".equals(httpServletRequest.getMethod()) && !httpServletRequest.getRequestURI().contains("/api/productos/"))
                || "PUT".equals(httpServletRequest.getMethod()) 
                || "DELETE".equals(httpServletRequest.getMethod()));

        // Si la solicitud está protegida, validar el token
        if (isProtectedMethod) {
            // Verificar si el encabezado de autorización es nulo o no comienza con "Bearer "
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                System.out.println("1. Token inválido");
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
                return;
            }

            // Extraer el token del encabezado (eliminando "Bearer ")
            String token = authHeader.substring(7);
            try {
                // Parsear y validar el token JWT usando la clave secreta
                Claims claims = Jwts.parser()
                        .setSigningKey(secret)
                        .parseClaimsJws(token)
                        .getBody();

                // Obtener el email y el rol del usuario desde los claims del token
                String email = claims.getSubject();
                String role = claims.get("role", String.class);

                System.out.println("Usuario autenticado: " + email + " con rol: " + role);

                // Configurar la autenticación en Spring Security
                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(email, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (SignatureException | MalformedJwtException | ExpiredJwtException e) {
                // Manejar errores relacionados con el token (firma inválida, token malformado o expirado)
                System.out.println("2. Token inválido o expirado");
                httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido o expirado");
                return;
            }
        }

        // Continuar con la cadena de filtros si la solicitud no está protegida o si el token es válido
        chain.doFilter(request, response);
    }
}