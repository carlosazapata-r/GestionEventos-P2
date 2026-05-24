package edu.pgii.eventos.model;

import edu.pgii.eventos.model.Enumeraciones.TipoIncidencia;
import java.time.LocalDateTime;

public class Incidencia {
    private final String idIncidencia;
    private final TipoIncidencia tipo;
    private final String descripcion;
    private final LocalDateTime fecha;
    private final String entidadAfectada;

    /**
     * Crea una incidencia con su clasificacion y entidad afectada.
     *
     * @param idIncidencia identificador unico de la incidencia
     * @param tipo tipo de incidencia registrada
     * @param descripcion detalle de la incidencia
     * @param fecha fecha y hora del registro
     * @param entidadAfectada entidad relacionada con la incidencia
     */
    public Incidencia(String idIncidencia, TipoIncidencia tipo, String descripcion,
                      LocalDateTime fecha, String entidadAfectada) {
        this.idIncidencia = idIncidencia;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.entidadAfectada = entidadAfectada;
    }
    public String getIdIncidencia() { return idIncidencia; }
    public TipoIncidencia getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }
    public LocalDateTime getFecha() { return fecha; }
    public String getEntidadAfectada() { return entidadAfectada; }
}

