package edu.pgii.eventos.model;

import edu.pgii.eventos.model.Enumeraciones.TipoServicio;
import java.math.BigDecimal;

public class ServicioAdicional {
    private final TipoServicio tipo;
    private final String nombre;
    private final BigDecimal precio;

    /**
     * Crea un servicio adicional.
     *
     * @param tipo tipo de servicio adicional
     * @param nombre nombre visible del servicio
     * @param precio precio del servicio
     */
    public ServicioAdicional(TipoServicio tipo, String nombre, BigDecimal precio) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.precio = precio;
    }
    public TipoServicio getTipo() { return tipo; }
    public String getNombre() { return nombre; }
    public BigDecimal getPrecio() { return precio; }

    /**
     * Devuelve una representacion legible del servicio y su precio.
     */
    @Override
    public String toString() {
        return nombre + " $" + precio;
    }
}

