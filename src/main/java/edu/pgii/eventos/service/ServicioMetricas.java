package edu.pgii.eventos.service;

import edu.pgii.eventos.model.Compra;
import edu.pgii.eventos.model.Enumeraciones.EstadoCompra;
import edu.pgii.eventos.model.Evento;
import edu.pgii.eventos.model.Zona;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServicioMetricas {
    private final ServicioEventos ServicioEventos;
    private final ServicioCompras ServicioCompras;

    public ServicioMetricas(ServicioEventos ServicioEventos, ServicioCompras ServicioCompras) {
        this.ServicioEventos = ServicioEventos;
        this.ServicioCompras = ServicioCompras;
    }

    public Map<String, BigDecimal> ventasPorEvento() {
        return ServicioCompras.all().stream()
                .filter(this::compraConfirmada)
                .collect(Collectors.groupingBy(
                        c -> c.getEvento().getNombre(),
                        LinkedHashMap::new,
                        Collectors.mapping(Compra::getTotal, Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
    }

    public Map<String, Number> ocupacionPorZona() {
        Map<String, Number> result = new LinkedHashMap<>();
        for (Evento evento : ServicioEventos.listAll()) {
            for (Zona zona : evento.getRecinto().getZonas()) {
                long ocupados = ServicioCompras.all().stream()
                        .filter(this::compraConfirmada)
                        .filter(compra -> compra.getEvento().getIdEvento().equals(evento.getIdEvento()))
                        .flatMap(compra -> compra.getEntradas().stream())
                        .filter(entrada -> entrada.getZona().getIdZona().equals(zona.getIdZona()))
                        .count();
                result.put(evento.getNombre() + " / " + zona.getNombre(), ocupados);
            }
        }
        return result;
    }

    public Map<String, Number> serviciosPorTipo() {
        Map<String, Number> result = new LinkedHashMap<>();
        ServicioCompras.all().stream().filter(this::compraConfirmada).forEach(c -> c.getServicios().forEach(s ->
                result.merge(s.getTipo().name(), s.getPrecio(), (a, b) -> ((BigDecimal) a).add((BigDecimal) b))));
        return result;
    }

    public double tasaCancelacion() {
        List<Compra> all = ServicioCompras.all();
        if (all.isEmpty()) return 0;
        long canceladas = all.stream().filter(c -> c.getEstado() == EstadoCompra.CANCELADA || c.getEstado() == EstadoCompra.REEMBOLSADA).count();
        return (canceladas * 100.0) / all.size();
    }

    private boolean compraConfirmada(Compra compra) {
        return compra.getEstado() == EstadoCompra.PAGADA || compra.getEstado() == EstadoCompra.CONFIRMADA;
    }
}

