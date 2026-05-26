package edu.pgii.eventos.service;

import edu.pgii.eventos.model.*;
import edu.pgii.eventos.model.Enumeraciones.*;
import edu.pgii.eventos.patterns.behavioral.CentroNotificaciones;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServicioComprasTest {

    @Test
    void crearCompra() {

        AlmacenDatos<Compra> compras =
                new AlmacenDatos<>(Compra::getIdCompra);

        ServicioCompras servicio =
                new ServicioCompras(
                        compras,
                        new EstrategiaPagoTarjetaSimulada(),
                        new PoliticaCancelacionEstandar(),
                        new CentroNotificaciones()
                );

        Usuario usuario =
                new Usuario(
                        "U001",
                        "Luis",
                        "correo@gmail.com",
                        "123",
                        "abc",
                        Role.values()[0]
                );

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
                        LocalDateTime.now().plusDays(3),
                        EstadoEvento.values()[0],
                        "No",
                        "No",
                        recinto
                );

        Zona zona =
                new Zona(
                        "Z001",
                        "VIP",
                        50,
                        new BigDecimal("100000"),
                        true
                );

        Asiento asiento =
                new Asiento(
                        "A001",
                        "A",
                        1,
                        EstadoAsiento.values()[0]
                );

        MetodoPago metodo =
                new MetodoPago(
                        "M001",
                        "Tarjeta",
                        "1234"
                );

        Compra compra =
                servicio.createCompra(
                        usuario,
                        evento,
                        zona,
                        List.of(asiento),
                        List.of(),
                        "Luis",
                        metodo
                );

        assertNotNull(compra);

        assertEquals(
                1,
                compra.getEntradas().size()
        );
    }

}
