package edu.pgii.eventos.service;

import edu.pgii.eventos.model.AlmacenDatos;
import edu.pgii.eventos.model.Enumeraciones.Role;
import edu.pgii.eventos.model.MetodoPago;
import edu.pgii.eventos.model.Usuario;
import java.util.Optional;
import java.util.UUID;

public class ServicioAutenticacion {
    private final AlmacenDatos<Usuario> usuarios;
    private Usuario currentUser;

    public ServicioAutenticacion(AlmacenDatos<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Optional<Usuario> login(String correo, String password) {
        currentUser = usuarios.findAll().stream()
                .filter(u -> u.getCorreo().equalsIgnoreCase(correo) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);
        return Optional.ofNullable(currentUser);
    }

    public Usuario register(String nombre, String correo, String telefono, String password) {
        Usuario usuario = new Usuario("U-" + UUID.randomUUID(), nombre, correo, telefono, password, Role.USUARIO);
        usuario.getMetodosPago().add(new MetodoPago("MP-" + UUID.randomUUID(), "Tarjeta", "**** 0000"));
        usuarios.save(usuario);
        currentUser = usuario;
        return usuario;
    }

    public Usuario getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }
}

