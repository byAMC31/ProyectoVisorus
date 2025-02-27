package com.ProyectoVisorus.ProyectoVisorus.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Producto")
public class Producto {

    @Id
    @Column(length = 20, unique = true, nullable = false)
    private String codigo;

    @Column(length = 60)
    private String descripcion;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "categoria_codigo", nullable = false) // Cambiado a categoria_codigo
    private Categoria categoria;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true) // Relación con CódigoBarra
    @JsonManagedReference  // Permitir serialización de los códigos de barras
    private List<CodigoBarra> codigosBarra = new ArrayList<>();

    @Column(name = "activo")
    private boolean activo;

    // Método para agregar códigos de barras y establecer la relación bidireccional
    public void agregarCodigoBarra(CodigoBarra codigoBarra) {
        codigosBarra.add(codigoBarra);
        codigoBarra.setProducto(this);
    }

    // Getters y Setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<CodigoBarra> getCodigosBarra() {
        return codigosBarra;
    }

    public void setCodigosBarra(List<CodigoBarra> codigosBarra) {
        this.codigosBarra = codigosBarra;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Producto [codigo=" + codigo + ", descripcion=" + descripcion + ", categoria=" + categoria + 
               ", codigosBarra=" + codigosBarra + ", activo=" + activo + "]";
    }
}