package com.ProyectoVisorus.ProyectoVisorus.controller;

import com.ProyectoVisorus.ProyectoVisorus.model.Producto;
import com.ProyectoVisorus.ProyectoVisorus.service.ProductosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos/")
public class ProductosController {

    private final ProductosService productosService;

    @Autowired
    public ProductosController(ProductosService productosService) {
        this.productosService = productosService;
    }

    
    // Método para obtener todos los productos
    @GetMapping
    public List<Producto> getProductos() {
        return productosService.getProductosActivos();
    }
    

    // Método para obtener un producto por su código
    @GetMapping(path = "{codigo}")
    public Producto getProducto(@PathVariable("codigo") String codigo) {
        return productosService.getProductoByCodigo(codigo).orElse(null);
    }

    
    // Método para obtener productos por descripción (coincidencia parcial)
    @GetMapping("/descripcion")
    public List<Producto> getProductosByDescripcion(@RequestParam String descripcion) {
        return productosService.getProductosByDescripcionContaining(descripcion);
    }

    
    // Método para obtener productos por código o descripción (coincidencia exacta o parcial)
    @GetMapping("/buscar")
    public List<Producto> getProductosByCodigoOrDescripcion(@RequestParam String codigo, @RequestParam String descripcion) {
        return productosService.getProductosByCodigoOrDescripcion(codigo, descripcion);
    }

    
    // Método para agregar un nuevo producto
    @PostMapping
    public Producto addProducto(@RequestBody Producto producto) {
        return productosService.addProducto(producto);
    }

    


 // Método para eliminar un producto por su código y devolverlo
    @DeleteMapping(path = "{codigo}")
    public ResponseEntity<Producto> deleteProducto(@PathVariable("codigo") String codigo) {
        Optional<Producto> productoOptional = productosService.getProductoByCodigo(codigo); // Obtener el producto antes de eliminarlo      
        if (productoOptional.isEmpty()) {
            return ResponseEntity.notFound().build(); // Si no existe, retorna 404
        }
        Producto producto = productoOptional.get(); // Obtener el objeto Producto
        productosService.deleteProducto(codigo); // Eliminar el producto
        return ResponseEntity.ok(producto); // Retornar el producto eliminado
    }

    
    
    
    
}
