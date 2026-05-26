package edu.pgii.eventos.model;

import edu.pgii.eventos.model.Enumeraciones.EstadoEvento;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EventoTest {

    @Test
    void calcularAforo() {

        Recinto recinto = new Recinto(
                "R001",
                "Arena",
                "Calle 10",
                "Bogota"
        );

        Zona zona = new Zona(
                "Z001",
                "VIP",
                100,
                null,
                true
        );

        recinto.getZonas().add(zona);

        Evento evento = new Evento(
                "EV001",
                "Concierto",
                "Musica",
                "Descripcion",
                "Bogota",
                LocalDateTime.now(),
                EstadoEvento.values()[0],
                "No cancelar",
                "No reembolso",
                recinto
        );

        assertEquals(
                100,
                evento.getAforo()
        );
    }
}
