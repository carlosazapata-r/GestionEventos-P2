package edu.pgii.eventos.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RecintoTest {

    @Test
    void deberiaCrearRecinto() {

        Recinto recinto = new Recinto(
                "R001",
                "Arena",
                "Calle 10",
                "Bogota"
        );

        assertEquals(
                "Arena",
                recinto.getNombre()
        );

        assertEquals(
                "Bogota",
                recinto.getCiudad()
        );
    }

    @Test
    void deberiaMostrarToString() {

        Recinto recinto = new Recinto(
                "R001",
                "Arena",
                "Calle 10",
                "Bogota"
        );

        assertEquals(
                "Arena - Bogota",
                recinto.toString()
        );
    }

}
