package com.ProyectoVisorus.ProyectoVisorus.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "Categoria")
public class Categoria {

    @Id
    @Column(length = 10, unique = true, nullable = false)
    private String codigo; 

    @Column(length = 40)
    private String descripcion;

    @Column(name = "activo")
    private boolean activo;

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

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "Categoria [codigo=" + codigo + ", descripcion=" + descripcion + ", activo=" + activo + "]";
    }
}
