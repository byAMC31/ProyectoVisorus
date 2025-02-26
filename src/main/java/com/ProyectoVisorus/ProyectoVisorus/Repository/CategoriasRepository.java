package com.ProyectoVisorus.ProyectoVisorus.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ProyectoVisorus.ProyectoVisorus.model.Categoria;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriasRepository extends JpaRepository<Categoria, String> {

    // Buscar por código (coincidencia exacta)
    Optional<Categoria> findByCodigo(String codigo);

    // Buscar por descripción (coincidencia exacta)
    Optional<Categoria> findByDescripcion(String descripcion);

    // Buscar por descripción utilizando LIKE (coincidencia parcial)
    List<Categoria> findByDescripcionContainingIgnoreCase(String descripcion);
}
