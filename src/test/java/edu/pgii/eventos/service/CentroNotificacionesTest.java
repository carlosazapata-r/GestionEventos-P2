package edu.pgii.eventos.service;

import edu.pgii.eventos.patterns.behavioral.CentroNotificaciones;
import edu.pgii.eventos.patterns.behavioral.ObservadorNotificacion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CentroNotificacionesTest {

    @Test
    void deberiaNotificarObservador() {

        CentroNotificaciones centro =
                new CentroNotificaciones();

        ObservadorPrueba observador =
                new ObservadorPrueba();

        centro.subscribe(observador);

        centro.notifyAllObservers(
                "Mensaje de prueba"
        );

        assertEquals(
                "Mensaje de prueba",
                observador.mensajeRecibido
        );
    }

    private static class ObservadorPrueba
            implements ObservadorNotificacion {

        String mensajeRecibido;

        @Override
        public void onNotification(String mensaje) {
            this.mensajeRecibido = mensaje;
        }
    }

}