package edu.pgii.eventos.patterns.behavioral;

import edu.pgii.eventos.model.Enumeraciones.EstadoEvento;
import edu.pgii.eventos.model.Evento;
import edu.pgii.eventos.service.ServicioEventos;

public class ComandoCambioEstadoEvento implements Comando {
    private final ServicioEventos ServicioEventos;
    private final Evento evento;
    private final EstadoEvento nuevoEstado;

    /**
     * Crea un comando para cambiar el estado de un evento.
     *
     * @param ServicioEventos servicio que aplicara el cambio de estado.
     * @param evento evento que sera actualizado.
     * @param nuevoEstado estado que se asignara al evento.
     */
    public ComandoCambioEstadoEvento(ServicioEventos ServicioEventos, Evento evento, EstadoEvento nuevoEstado) {
        this.ServicioEventos = ServicioEventos;
        this.evento = evento;
        this.nuevoEstado = nuevoEstado;
    }

    /**
     * Ejecuta el cambio de estado configurado para el evento.
     */
    @Override
    public void execute() {
        ServicioEventos.changeState(evento, nuevoEstado);
    }
}