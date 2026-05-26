package edu.pgii.eventos.service;

import edu.pgii.eventos.model.*;
import edu.pgii.eventos.model.Enumeraciones.*;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ServicioEventosTest {

    @Test
    void guardarEvento() {

        AlmacenDatos<Evento> datos =
                new AlmacenDatos<>(Evento::getIdEvento);

        ServicioEventos servicio =
                new ServicioEventos(datos);

        Recinto recinto =
                new Recinto(
                        "R001",
                        "Arena",
                        "Calle",
                        "Bogota"
                );

        Evento evento =
                new Evento(
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

        servicio.save(evento);

        assertEquals(
                1,
                servicio.listAll().size()
        );
    }

    @Test
    void crearAsiento() {

        AlmacenDatos<Evento> datos =
                new AlmacenDatos<>(Evento::getIdEvento);

        ServicioEventos servicio =
                new ServicioEventos(datos);

        Zona zona =
                new Zona(
                        "Z001",
                        "VIP",
                        50,
                        new BigDecimal("100000"),
                        true
                );

        Asiento asiento =
                servicio.createSeat(
                        zona,
                        "A",
                        1
                );

        assertEquals(
                "A",
                asiento.getFila()
        );

        assertEquals(
                1,
                zona.getAsientos().size()
        );
    }

}