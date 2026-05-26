package edu.pgii.eventos.service;

import edu.pgii.eventos.model.*;
import edu.pgii.eventos.model.Enumeraciones.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PoliticaCancelacionEstandarTest {

    @Test
    void deberiaPermitirCancelar() {

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
                LocalDateTime.now().plusDays(5),
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

        PoliticaCancelacionEstandar politica =
                new PoliticaCancelacionEstandar();

        assertTrue(
                politica.canCancel(compra)
        );
    }

    @Test
    void deberiaRetornarMensaje() {

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
                LocalDateTime.now().plusDays(5),
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

        PoliticaCancelacionEstandar politica =
                new PoliticaCancelacionEstandar();

        assertNotNull(
                politica.explain(compra)
        );
    }
}