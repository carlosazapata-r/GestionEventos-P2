package edu.pgii.eventos.service;

import edu.pgii.eventos.model.AlmacenDatos;
import edu.pgii.eventos.model.Enumeraciones.EstadoAsiento;
import edu.pgii.eventos.model.Enumeraciones.EstadoEvento;
import edu.pgii.eventos.model.Asiento;
import edu.pgii.eventos.model.Evento;
import edu.pgii.eventos.model.Zona;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.Collectors;

public class ServicioEventos {
    private final AlmacenDatos<Evento> eventos;

    public ServicioEventos(AlmacenDatos<Evento> eventos) {
        this.eventos = eventos;
    }

    public List<Evento> listAll() {
        return eventos.findAll().stream()
                .sorted(Comparator.comparing(Evento::getFechaHora))
                .collect(Collectors.toList());
    }

    public List<Evento> filter(String ciudad, String categoria, LocalDate fecha, BigDecimal maxPrecio) {
        String c = ciudad == null ? "" : ciudad.toLowerCase(Locale.ROOT);
        String cat = categoria == null ? "" : categoria.toLowerCase(Locale.ROOT);
        return listAll().stream()
                .filter(e -> e.getEstado() == EstadoEvento.PUBLICADO)
                .filter(e -> c.isBlank() || e.getCiudad().toLowerCase(Locale.ROOT).contains(c))
                .filter(e -> cat.isBlank() || e.getCategoria().toLowerCase(Locale.ROOT).contains(cat))
                .filter(e -> fecha == null || e.getFechaHora().toLocalDate().equals(fecha))
                .filter(e -> maxPrecio == null || e.getRecinto().getZonas().stream().anyMatch(z -> z.getPrecioBase().compareTo(maxPrecio) <= 0))
                .collect(Collectors.toList());
    }

    public void save(Evento evento) {
        eventos.save(evento);
    }

    public void delete(Evento evento) {
        eventos.delete(evento.getIdEvento());
    }

    public void changeState(Evento evento, EstadoEvento estado) {
        evento.setEstado(estado);
        eventos.save(evento);
    }

    public Asiento createSeat(Zona zona, String fila, int numero) {
        Asiento asiento = new Asiento("A-" + UUID.randomUUID(), fila, numero, EstadoAsiento.DISPONIBLE);
        zona.getAsientos().add(asiento);
        return asiento;
    }
}

