package com.ProyectoVisorus.ProyectoVisorus.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ProyectoVisorus.ProyectoVisorus.model.Producto;


@Repository
public interface ProductosRepository extends JpaRepository<Producto, String> {
    
    // Buscar por código (coincidencia exacta)
    Optional<Producto> findByCodigo(String codigo);
    
    // Buscar por descripción (coincidencia exacta)
    Optional<Producto> findByDescripcion(String descripcion);
    
    // Buscar por descripción utilizando LIKE (coincidencia parcial)
    List<Producto> findByDescripcionContainingIgnoreCase(String descripcion);
    
    // Buscar por código o descripción (coincidencia exacta o parcial)
    List<Producto> findByCodigoContainingIgnoreCaseOrDescripcionContainingIgnoreCase(String codigo, String descripcion);
}


