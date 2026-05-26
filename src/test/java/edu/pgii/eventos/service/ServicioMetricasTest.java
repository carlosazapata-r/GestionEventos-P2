package edu.pgii.eventos.service;

import edu.pgii.eventos.model.Compra;
import edu.pgii.eventos.model.Evento;
import edu.pgii.eventos.model.AlmacenDatos;
import edu.pgii.eventos.patterns.behavioral.CentroNotificaciones;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ServicioMetricasTest {

    @Test
    void tasaCancelacionInicialDebeSerCero() {

        ServicioEventos eventos =
                new ServicioEventos(
                        new AlmacenDatos<>(Evento::getIdEvento)
                );

        ServicioCompras compras =
                new ServicioCompras(
                        new AlmacenDatos<>(Compra::getIdCompra),
                        new EstrategiaPagoTarjetaSimulada(),
                        new PoliticaCancelacionEstandar(),
                        new CentroNotificaciones()
                );

        ServicioMetricas metricas =
                new ServicioMetricas(
                        eventos,
                        compras
                );

        assertEquals(
                0,
                metricas.tasaCancelacion()
        );
    }

    @Test
    void ocupacionPorZonaDebeRetornarMapa() {

        ServicioEventos eventos =
                new ServicioEventos(
                        new AlmacenDatos<>(Evento::getIdEvento)
                );

        ServicioCompras compras =
                new ServicioCompras(
                        new AlmacenDatos<>(Compra::getIdCompra),
                        new EstrategiaPagoTarjetaSimulada(),
                        new PoliticaCancelacionEstandar(),
                        new CentroNotificaciones()
                );

        ServicioMetricas metricas =
                new ServicioMetricas(
                        eventos,
                        compras
                );

        Map<String, Number> datos =
                metricas.ocupacionPorZona();

        assertNotNull(datos);
    }

}
