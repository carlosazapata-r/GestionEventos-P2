package edu.pgii.eventos.patterns.structural;

import edu.pgii.eventos.model.ServicioAdicional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DecoradorServicioAdicional {
    private final List<ServicioAdicional> servicios = new ArrayList<>();

    /**
     * Agrega un servicio adicional al conjunto decorado.
     *
     * @param servicio servicio adicional que se desea incluir
     * @return esta misma instancia para permitir encadenamiento de llamadas
     */
    public DecoradorServicioAdicional add(ServicioAdicional servicio) {
        servicios.add(servicio);
        return this;
    }

    public List<ServicioAdicional> getServicios() {
        return servicios;
    }

    /**
     * Calcula el valor total de todos los servicios adicionales agregados.
     *
     * @return suma de los precios de los servicios adicionales
     */
    public BigDecimal total() {
        return servicios.stream().map(ServicioAdicional::getPrecio).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

