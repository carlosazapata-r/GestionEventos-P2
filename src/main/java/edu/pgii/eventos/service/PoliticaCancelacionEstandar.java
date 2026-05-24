package edu.pgii.eventos.service;

import edu.pgii.eventos.model.Compra;
import edu.pgii.eventos.model.Enumeraciones.EstadoCompra;
import java.time.LocalDateTime;

public class PoliticaCancelacionEstandar implements PoliticaCancelacion {
    @Override
    public boolean canCancel(Compra compra) {
        boolean beforeEvent = compra.getEvento().getFechaHora().isAfter(LocalDateTime.now().plusHours(24));
        return beforeEvent && compra.getEstado() != EstadoCompra.CANCELADA && compra.getEstado() != EstadoCompra.REEMBOLSADA;
    }

    @Override
    public String explain(Compra compra) {
        return canCancel(compra)
                ? "Cancelacion permitida por estar a mas de 24 horas del evento."
                : "No se puede cancelar: evento cercano o compra ya cerrada.";
    }
}

