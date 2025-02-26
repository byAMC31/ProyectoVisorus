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
    public CodigoBarra updateCodigoBarra(String codigo, boolean activo) {
        CodigoBarra codigoBarra = null;
        if (codigoBarraRepository.existsById(codigo)) {
            codigoBarra = codigoBarraRepository.findByCodigo(codigo).get();
            codigoBarra.setActivo(activo);  // Solo actualizamos el estado activo
            codigoBarraRepository.save(codigoBarra);
        }
        return codigoBarra;
    }

    
    // Eliminar un código de barra
    public CodigoBarra deleteCodigoBarra(String codigo) {
        CodigoBarra codigoBarra = null;
        if (codigoBarraRepository.existsById(codigo)) {
            codigoBarra = codigoBarraRepository.findByCodigo(codigo).get();
            codigoBarraRepository.deleteById(codigo);
        }
        return codigoBarra;
    }
    

  
    
}
