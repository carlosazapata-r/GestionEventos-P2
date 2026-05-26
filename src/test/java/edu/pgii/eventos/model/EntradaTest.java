package edu.pgii.eventos.model;

import edu.pgii.eventos.model.Enumeraciones.EstadoEntrada;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class EntradaTest {

    @Test
    void crearEntrada() {

        Zona zona = new Zona(
                "Z001",
                "VIP",
                50,
                new BigDecimal("50000"),
                true
        );

        Entrada entrada = new Entrada(
                "E001",
                zona,
                null,
                new BigDecimal("50000"),
                EstadoEntrada.values()[0]
        );

        assertEquals(
                "E001",
                entrada.getIdEntrada()
        );

        assertEquals(
                new BigDecimal("50000"),
                entrada.getPrecioFinal()
        );

        assertEquals(
                zona,
                entrada.getZona()
        );
    }
}