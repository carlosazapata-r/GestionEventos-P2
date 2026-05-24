package edu.pgii.eventos.patterns.behavioral;

public interface ObservadorNotificacion {
    /**
     * Recibe un mensaje emitido por el centro de notificaciones.
     *
     * @param message mensaje publicado por el sistema.
     */
    void onNotification(String message);
}

