package edu.pgii.eventos.service;

import edu.pgii.eventos.model.*;
import edu.pgii.eventos.model.Enumeraciones.*;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExportadorCsvReporteTest {

    @Test
    void deberiaGenerarArchivoCsv() throws Exception {

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

        ExportadorCsvReporte exportador =
                new ExportadorCsvReporte();

        Path archivo = exportador.export(
                ReportType.values()[0],
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(1),
                List.of(compra)
        );

        assertTrue(
                Files.exists(archivo)
        );
    }

}