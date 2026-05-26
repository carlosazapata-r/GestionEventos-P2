package edu.pgii.eventos.model;

import edu.pgii.eventos.model.Enumeraciones.EstadoAsiento;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AsientoTest {

    @Test
    void deberiaCrearAsiento() {

        Asiento asiento = new Asiento(
                "A001",
                "A",
                5,
                EstadoAsiento.DISPONIBLE
        );

        assertEquals("A",asiento.getFila());
        assertEquals(5,asiento.getNumero());
    }

    @Test
    void deberiaCambiarEstado() {

        Asiento asiento = new Asiento(
                "A001",
                "A",
                5,
                EstadoAsiento.DISPONIBLE
        );

        asiento.setEstado(
                EstadoAsiento.RESERVADO
        );

        assertEquals(
                EstadoAsiento.RESERVADO,
                asiento.getEstado()
        );
    }

    @Test
    void deberiaMostrarToString() {

        Asiento asiento = new Asiento(
                "A001",
                "A",
                5,
                EstadoAsiento.DISPONIBLE
        );

        assertEquals(
                "A5 - DISPONIBLE",
                asiento.toString()
        );
    }
}