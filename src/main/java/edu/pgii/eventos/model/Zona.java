package edu.pgii.eventos.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Zona {
    private final String idZona;
    private String nombre;
    private int capacidad;
    private BigDecimal precioBase;
    private boolean numerada;
    private final List<Asiento> asientos = new ArrayList<>();

    /**
     * Crea una zona con su configuracion principal.
     *
     * @param idZona identificador unico de la zona
     * @param nombre nombre visible de la zona
     * @param capacidad capacidad maxima de la zona
     * @param precioBase precio base de las entradas de la zona
     * @param numerada indica si la zona usa asientos numerados
     */
    public Zona(String idZona, String nombre, int capacidad, BigDecimal precioBase, boolean numerada) {
        this.idZona = idZona;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.precioBase = precioBase;
        this.numerada = numerada;
    }
    public String getIdZona() { return idZona; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
    public BigDecimal getPrecioBase() { return precioBase; }
    public void setPrecioBase(BigDecimal precioBase) { this.precioBase = precioBase; }
    public boolean isNumerada() { return numerada; }
    public void setNumerada(boolean numerada) { this.numerada = numerada; }
    public List<Asiento> getAsientos() { return asientos; }

    /**
     * Cuenta los asientos que no estan disponibles.
     *
     * @return cantidad de asientos reservados, vendidos o bloqueados
     */
    public long ocupados() {
        return asientos.stream().filter(a -> a.getEstado() != Enumeraciones.EstadoAsiento.DISPONIBLE).count();
    }

    /**
     * Devuelve una representacion legible de la zona y su precio.
     */
    @Override
    public String toString() {
        return nombre + " $" + precioBase;
    }
}

