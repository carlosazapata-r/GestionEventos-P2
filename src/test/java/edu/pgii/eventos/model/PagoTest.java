package edu.pgii.eventos.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PagoTest {

    @Test
    void deberiaCrearPago() {

        MetodoPago metodo = new MetodoPago(
                "M001",
                "Tarjeta",
                "1234"
        );

        LocalDateTime fecha = LocalDateTime.now();

        Pago pago = new Pago(
                "P001",
                metodo,
                new BigDecimal("100000"),
                fecha,
                "REF001"
        );

        assertEquals("P001",pago.getIdPago());
        assertEquals(metodo,pago.getMetodoPago());
        assertEquals(
                new BigDecimal("100000"),
                pago.getMonto()
        );
        assertEquals("REF001",pago.getReferencia());
    }
}
