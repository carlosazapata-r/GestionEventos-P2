package edu.pgii.eventos.model;

import edu.pgii.eventos.model.Enumeraciones.EstadoCompra;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Compra {
    private final String idCompra;
    private final Usuario usuario;
    private final Evento evento;
    private final LocalDateTime fechaCreacion;
    private EstadoCompra estado;
    private final List<Entrada> entradas = new ArrayList<>();
    private final List<ServicioAdicional> servicios = new ArrayList<>();
    private Pago pago;
    private String nombreComprador;
    private MetodoPago metodoPagoSeleccionado;

    /**
     * Crea una compra con sus datos principales y estado inicial.
     *
     * @param idCompra identificador unico de la compra
     * @param usuario usuario que realiza la compra
     * @param evento evento asociado a la compra
     * @param fechaCreacion fecha y hora de creacion
     * @param estado estado inicial de la compra
     */
    public Compra(String idCompra, Usuario usuario, Evento evento, LocalDateTime fechaCreacion, EstadoCompra estado) {
        this.idCompra = idCompra;
        this.usuario = usuario;
        this.evento = evento;
        this.fechaCreacion = fechaCreacion;
        this.estado = estado;
    }
    public String getIdCompra() { return idCompra; }
    public Usuario getUsuario() { return usuario; }
    public Evento getEvento() { return evento; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public EstadoCompra getEstado() { return estado; }
    public void setEstado(EstadoCompra estado) { this.estado = estado; }
    public List<Entrada> getEntradas() { return entradas; }
    public List<ServicioAdicional> getServicios() { return servicios; }
    public Pago getPago() { return pago; }
    public void setPago(Pago pago) { this.pago = pago; }
    public String getNombreComprador() { return nombreComprador; }
    public void setNombreComprador(String nombreComprador) { this.nombreComprador = nombreComprador; }
    public MetodoPago getMetodoPagoSeleccionado() { return metodoPagoSeleccionado; }
    public void setMetodoPagoSeleccionado(MetodoPago metodoPagoSeleccionado) { this.metodoPagoSeleccionado = metodoPagoSeleccionado; }
    /**
     * Construye un texto con las zonas o asientos incluidos en la compra.
     *
     * @return resumen separado por comas de las ubicaciones compradas
     */
    public String getResumenAsientos() {
        return entradas.stream()
                .map(e -> e.getAsiento() == null ? e.getZona().getNombre() : e.getAsiento().getFila() + e.getAsiento().getNumero())
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
    }

    /**
     * Calcula el valor total sumando entradas y servicios adicionales.
     *
     * @return total monetario de la compra
     */
    public BigDecimal getTotal() {
        BigDecimal entradasTotal = entradas.stream()
                .map(Entrada::getPrecioFinal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal serviciosTotal = servicios.stream()
                .map(ServicioAdicional::getPrecio)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return entradasTotal.add(serviciosTotal);
    }
}

