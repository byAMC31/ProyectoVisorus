package com.ProyectoVisorus.ProyectoVisorus.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;




@Entity
@Table(name = "CodigoBarra")
public class CodigoBarra {

    @Id
    @Column(length = 20, unique = true, nullable = false)
    private String codigo;

    @Column(name = "activo")
    private boolean activo;

    @OneToMany(mappedBy = "codigoBarra", cascade = CascadeType.ALL) // Relación bidireccional
    private List<Producto> productos = new ArrayList<>();

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

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return "CodigoBarra [codigo=" + codigo + ", activo=" + activo + "]";
    }
}

