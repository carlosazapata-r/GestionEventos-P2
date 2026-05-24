package edu.pgii.eventos.service;

import edu.pgii.eventos.model.AlmacenDatos;
import edu.pgii.eventos.model.Asiento;
import edu.pgii.eventos.model.Compra;
import edu.pgii.eventos.model.Entrada;
import edu.pgii.eventos.model.Enumeraciones.EstadoAsiento;
import edu.pgii.eventos.model.Enumeraciones.EstadoCompra;
import edu.pgii.eventos.model.Enumeraciones.EstadoEntrada;
import edu.pgii.eventos.model.Evento;
import edu.pgii.eventos.model.MetodoPago;
import edu.pgii.eventos.model.ServicioAdicional;
import edu.pgii.eventos.model.Usuario;
import edu.pgii.eventos.model.Zona;
import edu.pgii.eventos.patterns.behavioral.CentroNotificaciones;
import edu.pgii.eventos.patterns.creational.ConstructorCompra;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ServicioCompras {
    private final AlmacenDatos<Compra> compras;
    private final EstrategiaPago EstrategiaPago;
    private final PoliticaCancelacion PoliticaCancelacion;
    private final CentroNotificaciones notifications;

    public ServicioCompras(AlmacenDatos<Compra> compras, EstrategiaPago EstrategiaPago,
                           PoliticaCancelacion PoliticaCancelacion, CentroNotificaciones notifications) {
        this.compras = compras;
        this.EstrategiaPago = EstrategiaPago;
        this.PoliticaCancelacion = PoliticaCancelacion;
        this.notifications = notifications;
    }

    public Compra createCompra(Usuario usuario, Evento evento, Zona zona, List<Asiento> asientos,
                               List<ServicioAdicional> servicios, String nombreComprador, MetodoPago metodoPago) {
        if (asientos == null || asientos.isEmpty()) {
            throw new IllegalStateException("Seleccione al menos un asiento.");
        }
        for (Asiento asiento : asientos) {
            if (asiento.getEstado() != EstadoAsiento.DISPONIBLE) {
                throw new IllegalStateException("El asiento " + asiento + " no esta disponible.");
            }
        }
        ConstructorCompra builder = new ConstructorCompra(usuario, evento);
        for (Asiento asiento : asientos) {
            asiento.setEstado(EstadoAsiento.RESERVADO);
            builder.addEntrada(new Entrada("E-" + UUID.randomUUID(), zona, asiento, zona.getPrecioBase(), EstadoEntrada.ACTIVA));
        }
        servicios.forEach(builder::addServicio);
        Compra compra = builder.build();
        compra.setNombreComprador(nombreComprador);
        compra.setMetodoPagoSeleccionado(metodoPago);
        compras.save(compra);
        notifications.notifyAllObservers("Compra creada: " + compra.getIdCompra());
        return compra;
    }

    public void pay(Compra compra, MetodoPago metodoPago) {
        compra.setPago(EstrategiaPago.pay(compra, metodoPago));
        compra.setEstado(EstadoCompra.PAGADA);
        compra.getEntradas().forEach(e -> {
            if (e.getAsiento() != null) {
                e.getAsiento().setEstado(EstadoAsiento.VENDIDO);
            }
        });
        compras.save(compra);
        notifications.notifyAllObservers("Pago aprobado para compra " + compra.getIdCompra());
    }

    public void updateDatosCompra(Compra compra, String nombreComprador, MetodoPago metodoPago) {
        if (compra.getEstado() != EstadoCompra.CREADA) {
            throw new IllegalStateException("Solo se pueden editar compras creadas.");
        }
        compra.setNombreComprador(nombreComprador);
        compra.setMetodoPagoSeleccionado(metodoPago);
        compras.save(compra);
        notifications.notifyAllObservers("Datos actualizados para compra " + compra.getIdCompra());
    }

    public void confirm(Compra compra) {
        compra.setEstado(EstadoCompra.CONFIRMADA);
        compras.save(compra);
        notifications.notifyAllObservers("Compra confirmada: " + compra.getIdCompra());
    }

    public void cancel(Compra compra) {
        if (!PoliticaCancelacion.canCancel(compra)) {
            throw new IllegalStateException(PoliticaCancelacion.explain(compra));
        }
        compra.setEstado(compra.getEstado() == EstadoCompra.PAGADA || compra.getEstado() == EstadoCompra.CONFIRMADA
                ? EstadoCompra.REEMBOLSADA : EstadoCompra.CANCELADA);
        compra.getEntradas().forEach(e -> {
            e.setEstado(EstadoEntrada.ANULADA);
            if (e.getAsiento() != null) {
                e.getAsiento().setEstado(EstadoAsiento.DISPONIBLE);
            }
        });
        compras.save(compra);
        notifications.notifyAllObservers("Compra cancelada/reembolsada: " + compra.getIdCompra());
    }

    public List<Compra> byUsuario(Usuario usuario) {
        return compras.findAll().stream()
                .filter(c -> c.getUsuario().getIdUsuario().equals(usuario.getIdUsuario()))
                .collect(Collectors.toList());
    }

    public List<Compra> all() {
        return compras.findAll();
    }
}

