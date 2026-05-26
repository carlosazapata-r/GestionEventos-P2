package edu.pgii.eventos.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MetodoPagoTest {

    @Test
    void deberiaCrearMetodoPago() {

        MetodoPago metodo = new MetodoPago(
                "M001",
                "Tarjeta",
                "1234"
        );

        assertEquals("M001",metodo.getIdMetodo());
        assertEquals("Tarjeta",metodo.getTipo());
        assertEquals("1234",metodo.getReferencia());
    }

    @Test
    void deberiaMostrarToString() {

        MetodoPago metodo = new MetodoPago(
                "M001",
                "Tarjeta",
                "1234"
        );

        assertEquals(
                "Tarjeta 1234",
                metodo.toString()
        );
    }
}