package edu.pgii.eventos.patterns.behavioral;

import java.util.ArrayList;
import java.util.List;

public class CentroNotificaciones {
    private final List<ObservadorNotificacion> observers = new ArrayList<>();

    /**
     * Suscribe un observador para recibir mensajes del centro de notificaciones.
     *
     * @param observer observador que sera notificado.
     */
    public void subscribe(ObservadorNotificacion observer) {
        observers.add(observer);
    }

    /**
     * Publica un mensaje para todos los observadores suscritos.
     *
     * @param message mensaje que se enviara a cada observador.
     */
    public void notifyAllObservers(String message) {
        observers.forEach(observer -> observer.onNotification(message));
    }
}

