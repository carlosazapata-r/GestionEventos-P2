package edu.pgii.eventos.controller;

import edu.pgii.eventos.patterns.creational.ContextoAplicacion;
import edu.pgii.eventos.viewcontroller.ControladorLogin;
import edu.pgii.eventos.viewcontroller.ControladorVistaPrincipal;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import java.io.IOException;

public class ControladorPrincipal {
    private final ContextoAplicacion contexto;
    private final StackPane contenedor = new StackPane();

    /**
     * Crea el controlador principal de navegacion usando el contexto de la aplicacion.
     * Al inicializarse, muestra la vista de inicio de sesion.
     *
     * @param contexto contexto compartido con servicios y estado de la aplicacion
     */
    public ControladorPrincipal(ContextoAplicacion contexto) {
        this.contexto = contexto;
        mostrarLogin();
    }

    /**
     * Obtiene el contenedor raiz que se inserta en la escena principal.
     *
     * @return vista raiz administrada por este controlador
     */
    public Parent getView() {
        return contenedor;
    }

    /**
     * Carga la vista de inicio de sesion y configura su controlador.
     */
    private void mostrarLogin() {
        cargarVista("/edu/pgii/eventos/view/vista-login.fxml",
                new ControladorLogin(contexto, this::mostrarPrincipal));
    }

    /**
     * Carga la vista principal de la aplicacion y configura su controlador.
     */
    private void mostrarPrincipal() {
        cargarVista("/edu/pgii/eventos/view/vista-principal.fxml",
                new ControladorVistaPrincipal(contexto, this::mostrarLogin));
    }

    /**
     * Carga una vista FXML dentro del contenedor principal usando el controlador indicado.
     *
     * @param ruta ruta del archivo FXML que se desea cargar
     * @param controlador controlador asociado a la vista FXML
     * @throws IllegalStateException si la vista FXML no puede cargarse
     */
    private void cargarVista(String ruta, Object controlador) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(ruta));
        loader.setController(controlador);
        try {
            contenedor.getChildren().setAll((Parent) loader.load());
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cargar la vista FXML: " + ruta, e);
        }
    }
}

