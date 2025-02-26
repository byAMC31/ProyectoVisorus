package com.ProyectoVisorus.ProyectoVisorus.service;

import com.ProyectoVisorus.ProyectoVisorus.model.Categoria;
import com.ProyectoVisorus.ProyectoVisorus.Repository.CategoriasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriasService {

    private final CategoriasRepository categoriasRepository;

    @Autowired
    public CategoriasService(CategoriasRepository categoriasRepository) {
        this.categoriasRepository = categoriasRepository;
    }

    // Obtener todas las categorías
    public List<Categoria> getAllCategories() {
        return categoriasRepository.findAll();
    }

    // Obtener categoría por código
    public Categoria getCategoryByCodigo(String codigo) {
        return categoriasRepository.findByCodigo(codigo)
                .orElseThrow(() -> new IllegalArgumentException("La categoría con el código [" + codigo + "] no existe."));
    }

    // Buscar categorías por descripción exacta
    public Categoria getCategoryByDescripcion(String descripcion) {
        return categoriasRepository.findByDescripcion(descripcion)
                .orElseThrow(() -> new IllegalArgumentException("La categoría con la descripción [" + descripcion + "] no existe."));
    }

    // Buscar categorías por descripción parcial (LIKE)
    public List<Categoria> searchCategoriesByDescripcion(String descripcion) {
        return categoriasRepository.findByDescripcionContainingIgnoreCase(descripcion);
    }

    // Buscar por código exacto 
    public List<Categoria> searchCategoriesByCodigo(String codigo) {
        return categoriasRepository.findByCodigo(codigo)
                .map(List::of)  // Si el código existe, lo convertimos en una lista para mantener la consistencia de la respuesta
                .orElseThrow(() -> new IllegalArgumentException("La categoría con el código [" + codigo + "] no existe."));
    }

    // Buscar por descripción exacta 
    public List<Categoria> searchCategoriesByExactDescripcion(String descripcion) {
        return categoriasRepository.findByDescripcion(descripcion)
                .map(List::of)  // Si la descripción exacta existe, lo convertimos en una lista
                .orElseThrow(() -> new IllegalArgumentException("La categoría con la descripción [" + descripcion + "] no existe."));
    }

    // Eliminar categoría por código
    public Categoria deleteCategory(String codigo) {
        Categoria categoria = null;
        if (categoriasRepository.existsById(codigo)) {
            categoria = categoriasRepository.findByCodigo(codigo).get();
            categoriasRepository.deleteById(codigo);
        }
        return categoria;
    }

    // Agregar nueva categoría
    public Categoria addCategory(Categoria categoria) {
        Optional<Categoria> existingCategoria = categoriasRepository.findByCodigo(categoria.getCodigo());
        if (existingCategoria.isEmpty()) {
            return categoriasRepository.save(categoria);
        } else {
            return null;  // Ya existe una categoría con ese código
        }
    }

    // Actualizar categoría
    public Categoria updateCategory(String codigo, String descripcion, boolean activo) {
        Categoria categoria = null;
        if (categoriasRepository.existsById(codigo)) {
            categoria = categoriasRepository.findByCodigo(codigo).get();
            if (descripcion != null) categoria.setDescripcion(descripcion);
            categoria.setActivo(activo);  // Actualizamos el estado "activo" siempre
            categoriasRepository.save(categoria);
        }
        return categoria;
    }
}

