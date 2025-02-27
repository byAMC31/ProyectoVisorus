package com.ProyectoVisorus.ProyectoVisorus.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "CodigoBarra")
public class CodigoBarra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Clave primaria autoincremental
    private Long id;

    @Column(length = 20, unique = true, nullable = false)
    private String codigo; // Valor del código de barras

    @Column(name = "activo")
    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "producto_codigo", nullable = false)
    @JsonBackReference  // Evitar serialización infinita en el lado de CódigoBarra
    private Producto producto;


    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "CodigoBarra [id=" + id + ", codigo=" + codigo + ", activo=" + activo + "]";
    }
}
