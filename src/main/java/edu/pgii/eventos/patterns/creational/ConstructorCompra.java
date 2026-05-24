package edu.pgii.eventos.patterns.creational;

import edu.pgii.eventos.model.Compra;
import edu.pgii.eventos.model.Entrada;
import edu.pgii.eventos.model.Enumeraciones.EstadoCompra;
import edu.pgii.eventos.model.Evento;
import edu.pgii.eventos.model.ServicioAdicional;
import edu.pgii.eventos.model.Usuario;
import java.time.LocalDateTime;
import java.util.UUID;

public class ConstructorCompra {
    private final Compra compra;

    /**
     * Inicializa una compra en estado creado para el usuario y evento indicados.
     *
     * @param usuario usuario que realiza la compra.
     * @param evento evento asociado a la compra.
     */
    public ConstructorCompra(Usuario usuario, Evento evento) {
        this.compra = new Compra("C-" + UUID.randomUUID(), usuario, evento, LocalDateTime.now(), EstadoCompra.CREADA);
    }

    /**
     * Agrega una entrada a la compra en construccion.
     *
     * @param entrada entrada que se asociara a la compra.
     * @return este constructor para continuar encadenando pasos.
     */
    public ConstructorCompra addEntrada(Entrada entrada) {
        compra.getEntradas().add(entrada);
        return this;
    }

    /**
     * Agrega un servicio adicional a la compra en construccion.
     *
     * @param servicio servicio opcional que se sumara a la compra.
     * @return este constructor para continuar encadenando pasos.
     */
    public ConstructorCompra addServicio(ServicioAdicional servicio) {
        compra.getServicios().add(servicio);
        return this;
    }

    /**
     * Finaliza la construccion de la compra y valida que tenga entradas.
     *
     * @return compra construida.
     * @throws IllegalStateException si la compra no contiene entradas.
     */
    public Compra build() {
        if (compra.getEntradas().isEmpty()) {
            throw new IllegalStateException("Una compra debe tener al menos una entrada.");
        }
        return compra;
    }
}

