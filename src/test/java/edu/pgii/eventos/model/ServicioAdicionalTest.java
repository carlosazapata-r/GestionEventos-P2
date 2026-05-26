package edu.pgii.eventos.model;

import edu.pgii.eventos.model.Enumeraciones.TipoServicio;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ServicioAdicionalTest {

    @Test
    void deberiaCrearServicio() {

        ServicioAdicional servicio =
                new ServicioAdicional(
                        TipoServicio.PARQUEADERO,
                        "Parqueadero",
                        new BigDecimal("20000")
                );

        assertEquals(
                "Parqueadero",
                servicio.getNombre()
        );

        assertEquals(
                new BigDecimal("20000"),
                servicio.getPrecio()
        );
    }
}
