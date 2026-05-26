package edu.pgii.eventos.service;

import edu.pgii.eventos.model.AlmacenDatos;
import edu.pgii.eventos.model.Usuario;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServicioAutenticacionTest {

    @Test
    void registrarUsuario() {

        AlmacenDatos<Usuario> usuarios =
                new AlmacenDatos<>(Usuario::getIdUsuario);

        ServicioAutenticacion auth =
                new ServicioAutenticacion(usuarios);

        Usuario usuario = auth.register(
                "Luis",
                "luis@gmail.com",
                "312456789",
                "1234"
        );

        assertNotNull(usuario);

        assertEquals(
                "Luis",
                usuario.getNombreCompleto()
        );

        assertEquals(
                usuario,
                auth.getCurrentUser()
        );
    }

    @Test
    void loginCorrecto() {

        AlmacenDatos<Usuario> usuarios =
                new AlmacenDatos<>(Usuario::getIdUsuario);

        ServicioAutenticacion auth =
                new ServicioAutenticacion(usuarios);

        auth.register(
                "Luis",
                "luis@gmail.com",
                "312456789",
                "1234"
        );

        assertTrue(
                auth.login(
                        "luis@gmail.com",
                        "1234"
                ).isPresent()
        );
    }

    @Test
    void logout() {

        AlmacenDatos<Usuario> usuarios =
                new AlmacenDatos<>(Usuario::getIdUsuario);

        ServicioAutenticacion auth =
                new ServicioAutenticacion(usuarios);

        auth.register(
                "Luis",
                "luis@gmail.com",
                "312456789",
                "1234"
        );

        auth.logout();

        assertNull(
                auth.getCurrentUser()
        );
    }

}
