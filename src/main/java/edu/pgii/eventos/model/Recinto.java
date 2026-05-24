package edu.pgii.eventos.model;

import java.util.ArrayList;
import java.util.List;

public class Recinto {
    private final String idRecinto;
    private String nombre;
    private String direccion;
    private String ciudad;
    private final List<Zona> zonas = new ArrayList<>();

    /**
     * Crea un recinto con su informacion basica.
     *
     * @param idRecinto identificador unico del recinto
     * @param nombre nombre del recinto
     * @param direccion direccion fisica del recinto
     * @param ciudad ciudad donde se encuentra el recinto
     */
    public Recinto(String idRecinto, String nombre, String direccion, String ciudad) {
        this.idRecinto = idRecinto;
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
    }
    public String getIdRecinto() { return idRecinto; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public List<Zona> getZonas() { return zonas; }

    /**
     * Devuelve una representacion legible del recinto para mostrar en listas.
     */
    @Override
    public String toString() {
        return nombre + " - " + ciudad;
    }
}

