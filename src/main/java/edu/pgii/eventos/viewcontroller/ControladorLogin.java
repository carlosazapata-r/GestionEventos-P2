package edu.pgii.eventos.viewcontroller;

import edu.pgii.eventos.patterns.creational.ContextoAplicacion;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ControladorLogin {
    private final ContextoAplicacion contexto;
    private final Runnable alIngresar;

    @FXML private TextField campoCorreo;
    @FXML private PasswordField campoClave;
    @FXML private TextField campoNombre;
    @FXML private TextField campoTelefono;

    public ControladorLogin(ContextoAplicacion contexto, Runnable alIngresar) {
        this.contexto = contexto;
        this.alIngresar = alIngresar;
    }

    @FXML private void iniciarSesion() {
        contexto.facade().login(campoCorreo.getText(), campoClave.getText())
                .ifPresentOrElse(usuario -> alIngresar.run(), () -> error("Credenciales invalidas"));
    }

    @FXML private void registrarUsuario() {
        if (campoNombre.getText().isBlank() || campoCorreo.getText().isBlank()) {
            error("Ingrese nombre y correo.");
            return;
        }
        contexto.facade().register(campoNombre.getText(), campoCorreo.getText(), campoTelefono.getText(), campoClave.getText());
        alIngresar.run();
    }

    private void error(String mensaje) {
        new Alert(Alert.AlertType.ERROR, mensaje).showAndWait();
    }
}
