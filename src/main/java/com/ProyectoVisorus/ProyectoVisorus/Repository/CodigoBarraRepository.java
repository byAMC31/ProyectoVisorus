package com.ProyectoVisorus.ProyectoVisorus.Repository;

import com.ProyectoVisorus.ProyectoVisorus.model.CodigoBarra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CodigoBarraRepository extends JpaRepository<CodigoBarra, Long> {

    // Buscar el Código de Barra y obtener el Producto asociado
    Optional<CodigoBarra> findByCodigo(String codigo);
    Optional<CodigoBarra> findById(Long id);

    // Buscar todos los códigos de barras activos
    List<CodigoBarra> findByActivo(boolean activo);
}
