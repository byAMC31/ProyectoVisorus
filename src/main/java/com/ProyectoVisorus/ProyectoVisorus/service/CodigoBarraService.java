package com.ProyectoVisorus.ProyectoVisorus.service;

import com.ProyectoVisorus.ProyectoVisorus.Repository.CodigoBarraRepository;
import com.ProyectoVisorus.ProyectoVisorus.model.CodigoBarra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CodigoBarraService {

    private final CodigoBarraRepository codigoBarraRepository;

    @Autowired
    public CodigoBarraService(CodigoBarraRepository codigoBarraRepository) {
        this.codigoBarraRepository = codigoBarraRepository;
    }

    // Obtener todos los códigos de barras
    public List<CodigoBarra> getAllCodigoBarras() {
        return codigoBarraRepository.findAll();
    }

    // Obtener un Código de Barra por su código
    public CodigoBarra getCodigoBarra(String codigo) {
        return codigoBarraRepository.findByCodigo(codigo)
                .orElseThrow(() -> new IllegalArgumentException("El código de barras [" + codigo + "] no existe."));
    }

    
    // Crear un nuevo código de barra
    public CodigoBarra addCodigoBarra(CodigoBarra codigoBarra) {
        Optional<CodigoBarra> existingCodigoBarra = codigoBarraRepository.findByCodigo(codigoBarra.getCodigo());
        if (existingCodigoBarra.isEmpty()) {
            return codigoBarraRepository.save(codigoBarra);
        } else {
            return null;  // Ya existe un código de barras con ese código
        }
    }

 // Actualizar un código de barra
    public CodigoBarra updateCodigoBarra(Long id, String codigo, boolean activo) {
        // Buscar el código de barras por ID
        Optional<CodigoBarra> optionalCodigoBarra = codigoBarraRepository.findById(id);
        if (optionalCodigoBarra.isPresent()) {
            CodigoBarra codigoBarra = optionalCodigoBarra.get();

            // Si el nuevo código de barras es diferente, actualizarlo
            if (!codigoBarra.getCodigo().equals(codigo)) {
                // Verificar si el nuevo código ya existe
                Optional<CodigoBarra> existingCodigoBarra = codigoBarraRepository.findByCodigo(codigo);
                if (existingCodigoBarra.isPresent()) {
                    return null;  // Ya existe un código de barras con ese valor
                }
                // Actualizar el código
                codigoBarra.setCodigo(codigo);
            }

            // Actualizar el estado 'activo'
            codigoBarra.setActivo(activo);

            // Guardar los cambios
            return codigoBarraRepository.save(codigoBarra);
        }

        return null;  // Si no se encuentra el código de barras con el ID dado
    }



    
 // Eliminar un código de barra por id
    public CodigoBarra deleteCodigoBarra(Long id) {
        CodigoBarra codigoBarra = null;
        if (codigoBarraRepository.existsById(id)) {  // Ahora usas Long para el ID
            codigoBarra = codigoBarraRepository.findById(id).get();
            codigoBarraRepository.deleteById(id);
        }
        return codigoBarra;
    }
    
    
  
    
}
