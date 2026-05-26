package edu.pgii.eventos.model;

import edu.pgii.eventos.model.Enumeraciones.EstadoAsiento;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ZonaTest {

    @Test
    void deberiaCrearZona() {

        Zona zona = new Zona(
                "Z001",
                "VIP",
                50,
                new BigDecimal("100000"),
                true
        );

        assertEquals("VIP",zona.getNombre());
        assertEquals(50,zona.getCapacidad());
    }

    @Test
    void deberiaContarAsientosOcupados() {

        Zona zona = new Zona(
                "Z001",
                "VIP",
                50,
                new BigDecimal("100000"),
                true
        );

        Asiento a1 = new Asiento(
                "A1","A",1,
                EstadoAsiento.DISPONIBLE
        );

        Asiento a2 = new Asiento(
                "A2","A",2,
                EstadoAsiento.RESERVADO
        );

        zona.getAsientos().add(a1);
        zona.getAsientos().add(a2);

        assertEquals(
                1,
                zona.ocupados()
        );
    }

    @Test
    void deberiaMostrarToString() {

        Zona zona = new Zona(
                "Z001",
                "VIP",
                50,
                new BigDecimal("100000"),
                true
        );

        assertEquals(
                "VIP $100000",
                zona.toString()
        );
    }

}
