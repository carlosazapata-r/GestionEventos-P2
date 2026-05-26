package edu.pgii.eventos.service;

import edu.pgii.eventos.model.*;
import edu.pgii.eventos.model.Enumeraciones.EstadoEvento;
import edu.pgii.eventos.patterns.behavioral.ComandoCambioEstadoEvento;
import edu.pgii.eventos.service.ServicioEventos;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ComandoCambioEstadoEventoTest {

    @Test
    void ejecutarCambioEstado() {

        AlmacenDatos<Evento> datos =
                new AlmacenDatos<>(Evento::getIdEvento);

        ServicioEventos servicioEventos =
                new ServicioEventos(datos);

        Recinto recinto =
                new Recinto(
                        "R001",
                        "Arena",
                        "Calle 10",
                        "Bogota"
                );

        Evento evento =
                new Evento(
                        "EV001",
                        "Concierto",
                        "Musica",
                        "Descripcion",
                        "Bogota",
                        LocalDateTime.now(),
                        EstadoEvento.values()[0],
                        "No",
                        "No",
                        recinto
                );

        servicioEventos.save(evento);

        EstadoEvento nuevoEstado =
                EstadoEvento.values().length > 1
                        ? EstadoEvento.values()[1]
                        : EstadoEvento.values()[0];

        ComandoCambioEstadoEvento comando =
                new ComandoCambioEstadoEvento(
                        servicioEventos,
                        evento,
                        nuevoEstado
                );

        comando.execute();

        assertEquals(
                nuevoEstado,
                evento.getEstado()
        );
    }
}
