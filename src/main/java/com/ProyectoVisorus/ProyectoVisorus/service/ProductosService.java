package com.ProyectoVisorus.ProyectoVisorus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ProyectoVisorus.ProyectoVisorus.Repository.CategoriasRepository;
import com.ProyectoVisorus.ProyectoVisorus.Repository.CodigoBarraRepository;
import com.ProyectoVisorus.ProyectoVisorus.Repository.ProductosRepository;
import com.ProyectoVisorus.ProyectoVisorus.model.Categoria;
import com.ProyectoVisorus.ProyectoVisorus.model.CodigoBarra;
import com.ProyectoVisorus.ProyectoVisorus.model.Producto;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service
public class ProductosService {

	 private final ProductosRepository productosRepository;
	    private final CodigoBarraRepository codigoBarraRepository;
	    private final CategoriasRepository categoriaRepository;

	    @Autowired
	    public ProductosService(ProductosRepository productosRepository, CodigoBarraRepository codigoBarraRepository, CategoriasRepository categoriaRepository) {
	        this.productosRepository = productosRepository;
	        this.codigoBarraRepository = codigoBarraRepository;
	        this.categoriaRepository = categoriaRepository;
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


 // Crear un nuevo producto con códigos de barras
    public Producto addProducto(Producto producto) {
        // Verificar si ya existe un producto con el mismo código
        Optional<Producto> existingProducto = productosRepository.findByCodigo(producto.getCodigo());
        if (existingProducto.isPresent()) {
            return null; // Ya existe un producto con ese código
        }

        // Verificar si los códigos de barras ya existen y asociarlos al producto
        for (CodigoBarra codigoBarra : producto.getCodigosBarra()) {
            Optional<CodigoBarra> existingCodigoBarra = codigoBarraRepository.findByCodigo(codigoBarra.getCodigo());
            if (existingCodigoBarra.isPresent()) {
                // Si ya existe un código de barra, asociarlo al producto
                codigoBarra.setProducto(existingCodigoBarra.get().getProducto());
            } else {
                // Si no existe, crear un nuevo código de barras
                codigoBarra.setProducto(producto); // Asignar el producto al código de barras
            }
        }

        // Buscar la categoría por su código
        Optional<Categoria> categoria = categoriaRepository.findByCodigo(producto.getCategoria().getCodigo());
        if (categoria.isEmpty()) {
            return null; // La categoría no existe
        }
        producto.setCategoria(categoria.get());

        // Guardar el producto (esto generará el ID del producto)
        Producto savedProducto = productosRepository.save(producto);

        // Asociar los códigos de barras al producto y establecer la relación bidireccional
        for (CodigoBarra codigoBarra : producto.getCodigosBarra()) {
            codigoBarra.setProducto(savedProducto); // Asegurarse de que el producto está asignado
            savedProducto.agregarCodigoBarra(codigoBarra);
        }

        // Guardar el producto con sus códigos de barras
        return productosRepository.save(savedProducto);
    }

    
    
 // Actualizar un producto
    @Transactional
    public Producto updateProducto(String codigo, Producto productoRequest) {
        Optional<Producto> existingProductoOpt = productosRepository.findByCodigo(codigo);
        
        if (existingProductoOpt.isEmpty()) {
            return null; // El producto no existe
        }

        Producto existingProducto = existingProductoOpt.get();
        
        // Actualizar solo los atributos que fueron pasados en la solicitud
        if (productoRequest.getDescripcion() != null) {
            existingProducto.setDescripcion(productoRequest.getDescripcion());
        }
        
        if (productoRequest.isActivo() != existingProducto.isActivo()) {
            existingProducto.setActivo(productoRequest.isActivo());
        }

        if (productoRequest.getCategoria() != null && productoRequest.getCategoria().getCodigo() != null) {
            Optional<Categoria> categoriaOpt = categoriaRepository.findByCodigo(productoRequest.getCategoria().getCodigo());
            if (categoriaOpt.isPresent()) {
                existingProducto.setCategoria(categoriaOpt.get());
            } else {
                return null; // La categoría no existe
            }
        }

        if (productoRequest.getCodigosBarra() != null && !productoRequest.getCodigosBarra().isEmpty()) {
            // Eliminar los códigos de barras actuales y agregar los nuevos
            existingProducto.getCodigosBarra().clear();  // Limpiar los códigos de barras actuales

            for (CodigoBarra codigoBarra : productoRequest.getCodigosBarra()) {
                Optional<CodigoBarra> existingCodigoBarraOpt = codigoBarraRepository.findByCodigo(codigoBarra.getCodigo());
                if (existingCodigoBarraOpt.isPresent()) {
                    codigoBarra.setProducto(existingProducto); // Asociar el producto existente al código de barras
                    existingProducto.agregarCodigoBarra(codigoBarra); // Agregar el código de barras
                } else {
                    // Si no existe, crear un nuevo código de barras
                    codigoBarra.setProducto(existingProducto);  // Asociar el producto al nuevo código de barras
                    existingProducto.agregarCodigoBarra(codigoBarra); // Agregarlo al producto
                }
            }
        }

        // Guardar el producto actualizado
        return productosRepository.save(existingProducto);
    }
    
    


}
