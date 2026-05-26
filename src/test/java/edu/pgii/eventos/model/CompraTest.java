package edu.pgii.eventos.model;

import edu.pgii.eventos.model.Enumeraciones.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CompraTest {

    @Test
    void calcularTotalCompra() {

        Usuario usuario = new Usuario(
                "U001",
                "Luis",
                "correo@gmail.com",
                "123",
                "abc",
                Role.values()[0]
        );

        Recinto recinto = new Recinto(
                "R001",
                "Arena",
                "Calle",
                "Bogota"
        );

        Evento evento = new Evento(
                "EV001",
                "Concierto",
                "Musica",
                "Desc",
                "Bogota",
                LocalDateTime.now(),
                EstadoEvento.values()[0],
                "No",
                "No",
                recinto
        );

        Compra compra = new Compra(
                "C001",
                usuario,
                evento,
                LocalDateTime.now(),
                EstadoCompra.values()[0]
        );

        Zona zona = new Zona(
                "Z001",
                "VIP",
                50,
                new BigDecimal("100000"),
                true
        );

        Entrada entrada = new Entrada(
                "EN001",
                zona,
                null,
                new BigDecimal("100000"),
                EstadoEntrada.values()[0]
        );

        compra.getEntradas().add(entrada);

        assertEquals(
                new BigDecimal("100000"),
                compra.getTotal()
        );
    }
}
