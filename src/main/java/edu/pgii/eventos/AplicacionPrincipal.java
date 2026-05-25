package edu.pgii.eventos;

import edu.pgii.eventos.controller.ControladorPrincipal;
import edu.pgii.eventos.patterns.creational.ContextoAplicacion;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AplicacionPrincipal extends Application {
    @Override
    public void start(Stage stage) {
        ContextoAplicacion context = ContextoAplicacion.getInstance();
        ControladorPrincipal controller = new ControladorPrincipal(context);
        Scene scene = new Scene(controller.getView(), 1180, 760);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setTitle("PGII Eventos - MVC JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

