package com.ProyectoVisorus.ProyectoVisorus.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "Producto")
public class Producto {

    @Id
    @Column(length = 20, unique = true, nullable = false)
    private String codigo;

    @Column(length = 60)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "codigo_barra", nullable = false) // Relación con CódigoBarra
    @JsonBackReference
    private CodigoBarra codigoBarra;

    @Column(name = "activo")
    private boolean activo;

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

    public CodigoBarra getCodigoBarra() {
        return codigoBarra;
    }

    public void setCodigoBarra(CodigoBarra codigoBarra) {
        this.codigoBarra = codigoBarra;
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
               ", codigoBarra=" + codigoBarra + ", activo=" + activo + "]";
    }
}

