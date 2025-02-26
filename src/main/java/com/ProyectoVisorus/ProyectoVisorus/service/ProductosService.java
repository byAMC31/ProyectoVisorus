package com.ProyectoVisorus.ProyectoVisorus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ProyectoVisorus.ProyectoVisorus.Repository.ProductosRepository;
import com.ProyectoVisorus.ProyectoVisorus.model.Producto;

import java.util.List;
import java.util.Optional;

@Service
public class ProductosService {

    private final ProductosRepository productosRepository;

    @Autowired
    public ProductosService(ProductosRepository productosRepository) {
        this.productosRepository = productosRepository;
    }

    // Obtener un producto por su código
    public Optional<Producto> getProductoByCodigo(String codigo) {
        return productosRepository.findByCodigo(codigo);
    }

    // Obtener un producto por su descripción
    public Optional<Producto> getProductoByDescripcion(String descripcion) {
        return productosRepository.findByDescripcion(descripcion);
    }

    // Obtener productos por descripción (coincidencia parcial)
    public List<Producto> getProductosByDescripcionContaining(String descripcion) {
        return productosRepository.findByDescripcionContainingIgnoreCase(descripcion);
    }

    // Obtener productos por código o descripción (coincidencia parcial o exacta)
    public List<Producto> getProductosByCodigoOrDescripcion(String codigo, String descripcion) {
        return productosRepository.findByCodigoContainingIgnoreCaseOrDescripcionContainingIgnoreCase(codigo, descripcion);
    }

    
    // Obtener todos los productos activos
    public List<Producto> getProductosActivos() {
        return productosRepository.findAll()
                .stream()
                .filter(Producto::isActivo)
                .toList();
    }
    

    
    // Eliminar un producto por código
    public void deleteProducto(String codigo) {
        productosRepository.deleteById(codigo);
    }
    
    
    // Crear un nuevo producto
    public Producto addProducto(Producto producto) {
        Optional<Producto> existingProducto = productosRepository.findByCodigo(producto.getCodigo());
        if (existingProducto.isEmpty()) {
            return productosRepository.save(producto);
        } else {
            return null;  // Ya existe un producto con ese código
        }
    }

    

    
}
