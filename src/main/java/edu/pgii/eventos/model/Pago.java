package edu.pgii.eventos.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Pago {
    private final String idPago;
    private final MetodoPago metodoPago;
    private final BigDecimal monto;
    private final LocalDateTime fecha;
    private final String referencia;

    /**
     * Crea un registro de pago.
     *
     * @param idPago identificador unico del pago
     * @param metodoPago metodo de pago utilizado
     * @param monto valor pagado
     * @param fecha fecha y hora del pago
     * @param referencia referencia externa o interna del pago
     */
    public Pago(String idPago, MetodoPago metodoPago, BigDecimal monto, LocalDateTime fecha, String referencia) {
        this.idPago = idPago;
        this.metodoPago = metodoPago;
        this.monto = monto;
        this.fecha = fecha;
        this.referencia = referencia;
    }
    public String getIdPago() { return idPago; }
    public MetodoPago getMetodoPago() { return metodoPago; }
    public BigDecimal getMonto() { return monto; }
    public LocalDateTime getFecha() { return fecha; }
    public String getReferencia() { return referencia; }
}

