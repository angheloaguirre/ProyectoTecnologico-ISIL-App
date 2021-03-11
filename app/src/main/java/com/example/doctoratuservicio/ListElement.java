package com.example.doctoratuservicio;

public class ListElement {
    public String color;
    public String nombre;
    public String codigo;
    public String modalidad;
    public String precioConsulta;

    public ListElement() {}

    public ListElement(String color, String nombre, String codigo, String modalidad, String precioConsulta) {
        this.color = color;
        this.nombre = nombre;
        this.codigo = codigo;
        this.modalidad = modalidad;
        this.precioConsulta = precioConsulta;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCodigo() { return codigo; }

    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getModalidad() { return modalidad; }

    public void setModalidad(String modalidad) { this.modalidad = modalidad; }

    public String getPrecioConsulta() {
        return precioConsulta;
    }

    public void setPrecioConsulta(String precioConsulta) {
        this.precioConsulta = precioConsulta;
    }
}
