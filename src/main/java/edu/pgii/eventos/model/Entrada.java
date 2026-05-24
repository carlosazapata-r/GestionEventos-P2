package edu.pgii.eventos.model;

import edu.pgii.eventos.model.Enumeraciones.EstadoEntrada;
import java.math.BigDecimal;

public class Entrada {
    private final String idEntrada;
    private final Zona zona;
    private final Asiento asiento;
    private final BigDecimal precioFinal;
    private EstadoEntrada estado;

    /**
     * Crea una entrada para una zona o asiento especifico.
     *
     * @param idEntrada identificador unico de la entrada
     * @param zona zona a la que pertenece la entrada
     * @param asiento asiento asignado; puede ser null en zonas no numeradas
     * @param precioFinal precio final de la entrada
     * @param estado estado inicial de la entrada
     */
    public Entrada(String idEntrada, Zona zona, Asiento asiento, BigDecimal precioFinal, EstadoEntrada estado) {
        this.idEntrada = idEntrada;
        this.zona = zona;
        this.asiento = asiento;
        this.precioFinal = precioFinal;
        this.estado = estado;
    }
    public String getIdEntrada() { return idEntrada; }
    public Zona getZona() { return zona; }
    public Asiento getAsiento() { return asiento; }
    public BigDecimal getPrecioFinal() { return precioFinal; }
    public EstadoEntrada getEstado() { return estado; }
    public void setEstado(EstadoEntrada estado) { this.estado = estado; }
}

