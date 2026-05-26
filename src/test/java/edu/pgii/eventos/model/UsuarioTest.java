package edu.pgii.eventos.model;

import edu.pgii.eventos.model.Enumeraciones.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    @Test
    void crearUsuario() {

        Role rol = Role.values()[0];

        Usuario usuario = new Usuario(
                "U001",
                "Luis",
                "luis@gmail.com",
                "312456789",
                "12345",
                rol
        );

        assertEquals("U001", usuario.getIdUsuario());
        assertEquals("Luis", usuario.getNombreCompleto());
        assertEquals("luis@gmail.com", usuario.getCorreo());
        assertEquals(rol, usuario.getRole());
    }
}