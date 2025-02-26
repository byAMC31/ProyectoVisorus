package com.ProyectoVisorus.ProyectoVisorus.controller;

import com.ProyectoVisorus.ProyectoVisorus.model.Categoria;
import com.ProyectoVisorus.ProyectoVisorus.service.CategoriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/categorias/")
public class CategoriasController {

    private final CategoriasService categoriasService;

    @Autowired
    public CategoriasController(CategoriasService categoriasService) {
        this.categoriasService = categoriasService;
    }

    // Método para obtener todas las categorías
    @GetMapping
    public List<Categoria> getCategorias() {
        return categoriasService.getAllCategories();
    }

    
    // Método para obtener una categoría por código
    @GetMapping(path = "{codigo}")
    public Categoria getCategoria(@PathVariable("codigo") String codigo) {
        return categoriasService.getCategoryByCodigo(codigo);
    }

    
    // Método para eliminar una categoría por código
    @DeleteMapping(path = "{codigo}")
    public Categoria deleteCategoria(@PathVariable("codigo") String codigo) {
        return categoriasService.deleteCategory(codigo);
    }

    
    // Método para agregar una nueva categoría
    @PostMapping
    public Categoria addCategoria(@RequestBody Categoria categoria) {
        return categoriasService.addCategory(categoria);
    }

    // Método para actualizar una categoría
    @PutMapping(path = "{codigo}")
    public Categoria updateCategoria(
            @PathVariable("codigo") String codigo,
            @RequestParam(required = false) String descripcion,
            @RequestParam(required = false) Boolean activo) {
        return categoriasService.updateCategory(codigo, descripcion, activo != null ? activo : true);
    }

    // Método para buscar categorías por descripción (parcial)
    @GetMapping(path = "search")
    public List<Categoria> searchCategorias(@RequestParam String descripcion) {
        return categoriasService.searchCategoriesByDescripcion(descripcion);
    }

    
    // Método para buscar categorías por código exacto
    @GetMapping(path = "searchByCodigo")
    public List<Categoria> searchCategoriasByCodigo(@RequestParam String codigo) {
        return categoriasService.searchCategoriesByCodigo(codigo);
    }
    

    // Método para buscar categorías por descripción exacta
    @GetMapping(path = "searchByExactDescripcion")
    public List<Categoria> searchCategoriasByExactDescripcion(@RequestParam String descripcion) {
        return categoriasService.searchCategoriesByExactDescripcion(descripcion);
    }
    
    
    
}
