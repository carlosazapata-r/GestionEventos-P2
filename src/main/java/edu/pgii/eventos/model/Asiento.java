package edu.pgii.eventos.model;

import edu.pgii.eventos.model.Enumeraciones.EstadoAsiento;

public class Asiento {
    private final String idAsiento;
    private String fila;
    private int numero;
    private EstadoAsiento estado;

    /**
     * Crea un asiento con su ubicacion y estado inicial.
     *
     * @param idAsiento identificador unico del asiento
     * @param fila fila a la que pertenece el asiento
     * @param numero numero del asiento dentro de la fila
     * @param estado estado inicial del asiento
     */
    public Asiento(String idAsiento, String fila, int numero, EstadoAsiento estado) {
        this.idAsiento = idAsiento;
        this.fila = fila;
        this.numero = numero;
        this.estado = estado;
    }
    public String getIdAsiento() { return idAsiento; }
    public String getFila() { return fila; }
    public void setFila(String fila) { this.fila = fila; }
    public int getNumero() { return numero; }
    public void setNumero(int numero) { this.numero = numero; }
    public EstadoAsiento getEstado() { return estado; }
    public void setEstado(EstadoAsiento estado) { this.estado = estado; }

    /**
     * Devuelve una representacion legible para mostrar el asiento en la interfaz.
     */
    @Override
    public String toString() {
        return fila + numero + " - " + estado;
    }
}

