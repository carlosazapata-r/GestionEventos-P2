package edu.pgii.eventos.model;

import edu.pgii.eventos.model.Enumeraciones.EstadoEvento;
import java.time.LocalDateTime;

public class Evento {
    private final String idEvento;
    private String nombre;
    private String categoria;
    private String descripcion;
    private String ciudad;
    private LocalDateTime fechaHora;
    private EstadoEvento estado;
    private String politicaCancelacion;
    private String politicaReembolso;
    private Recinto recinto;

    /**
     * Crea un evento con sus datos principales y recinto asociado.
     *
     * @param idEvento identificador unico del evento
     * @param nombre nombre comercial del evento
     * @param categoria categoria del evento
     * @param descripcion descripcion visible del evento
     * @param ciudad ciudad donde se realiza
     * @param fechaHora fecha y hora programadas
     * @param estado estado inicial del evento
     * @param politicaCancelacion politica de cancelacion aplicable
     * @param politicaReembolso politica de reembolso aplicable
     * @param recinto recinto donde se realiza el evento
     */
    public Evento(String idEvento, String nombre, String categoria, String descripcion, String ciudad,
                  LocalDateTime fechaHora, EstadoEvento estado, String politicaCancelacion,
                  String politicaReembolso, Recinto recinto) {
        this.idEvento = idEvento;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.ciudad = ciudad;
        this.fechaHora = fechaHora;
        this.estado = estado;
        this.politicaCancelacion = politicaCancelacion;
        this.politicaReembolso = politicaReembolso;
        this.recinto = recinto;
    }
    public String getIdEvento() { return idEvento; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public EstadoEvento getEstado() { return estado; }
    public void setEstado(EstadoEvento estado) { this.estado = estado; }
    public String getPoliticaCancelacion() { return politicaCancelacion; }
    public void setPoliticaCancelacion(String politicaCancelacion) { this.politicaCancelacion = politicaCancelacion; }
    public String getPoliticaReembolso() { return politicaReembolso; }
    public void setPoliticaReembolso(String politicaReembolso) { this.politicaReembolso = politicaReembolso; }
    public Recinto getRecinto() { return recinto; }
    public void setRecinto(Recinto recinto) { this.recinto = recinto; }
    /**
     * Calcula el aforo total a partir de la capacidad de todas las zonas.
     *
     * @return capacidad total del recinto para este evento
     */
    public int getAforo() { return recinto.getZonas().stream().mapToInt(Zona::getCapacidad).sum(); }

    /**
     * Devuelve una representacion legible del evento para listas y controles.
     */
    @Override
    public String toString() {
        return nombre + " - " + ciudad + " - " + estado;
    }
}

