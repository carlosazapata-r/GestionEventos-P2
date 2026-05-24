package edu.pgii.eventos.model;

import edu.pgii.eventos.model.Enumeraciones.Role;
import java.util.ArrayList;
import java.util.List;

public class Usuario {
    private final String idUsuario;
    private String nombreCompleto;
    private String correo;
    private String telefono;
    private String password;
    private final Role role;
    private final List<MetodoPago> metodosPago = new ArrayList<>();

    /**
     * Crea un usuario con sus datos de registro.
     *
     * @param idUsuario identificador unico del usuario
     * @param nombreCompleto nombre completo del usuario
     * @param correo correo electronico del usuario
     * @param telefono telefono de contacto
     * @param password contrasena asociada al usuario
     * @param role rol del usuario dentro de la aplicacion
     */
    public Usuario(String idUsuario, String nombreCompleto, String correo, String telefono, String password, Role role) {
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.telefono = telefono;
        this.password = password;
        this.role = role;
    }
    public String getIdUsuario() { return idUsuario; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Role getRole() { return role; }
    public List<MetodoPago> getMetodosPago() { return metodosPago; }

    /**
     * Devuelve una representacion legible del usuario y su rol.
     */
    @Override
    public String toString() {
        return nombreCompleto + " (" + role + ")";
    }
}

