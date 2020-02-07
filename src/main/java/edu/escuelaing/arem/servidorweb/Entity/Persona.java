package edu.escuelaing.arem.servidorweb.Entity;

/**
 * Entidad Persona que está en la base de datos
 */
public class Persona {

    private String nombres;
    private String apellidos;
    private int edad;
    private String telefono;

    public String getNombres() {
        return nombres;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

}