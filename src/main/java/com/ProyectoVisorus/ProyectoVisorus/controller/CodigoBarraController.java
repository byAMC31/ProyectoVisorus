package com.ProyectoVisorus.ProyectoVisorus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ProyectoVisorus.ProyectoVisorus.model.CodigoBarra;
import com.ProyectoVisorus.ProyectoVisorus.service.CodigoBarraService;

@RestController
@RequestMapping(path = "/api/codigosbarra/")
public class CodigoBarraController {

    private final CodigoBarraService codigoBarraService;

    @Autowired
    public CodigoBarraController(CodigoBarraService codigoBarraService) {
        this.codigoBarraService = codigoBarraService;
    }

    // Método para obtener todos los códigos de barras
    @GetMapping
    public List<CodigoBarra> getCodigoBarras() {
        return codigoBarraService.getAllCodigoBarras();
    }
    
    

    // Método para obtener un código de barras por su código
    @GetMapping(path = "{codigo}")
    public CodigoBarra getCodigoBarra(@PathVariable("codigo") String codigo) {
        return codigoBarraService.getCodigoBarra(codigo);
    }

    
    
    // Método para eliminar un código de barras por su código
    @DeleteMapping(path = "{codigo}")
    public CodigoBarra deleteCodigoBarra(@PathVariable("codigo") String codigo) {
        return codigoBarraService.deleteCodigoBarra(codigo);
    }

    
    // Método para agregar un nuevo código de barras
    @PostMapping
    public CodigoBarra addCodigoBarra(@RequestBody CodigoBarra codigoBarra) {
        return codigoBarraService.addCodigoBarra(codigoBarra);
    }

    
    
    // Método para actualizar el estado de un código de barras
    @PutMapping(path = "{codigo}")
    public CodigoBarra updateCodigoBarra(
            @PathVariable("codigo") String codigo,
            @RequestParam boolean activo) {
        return codigoBarraService.updateCodigoBarra(codigo, activo);
    }
    
    
    
}
