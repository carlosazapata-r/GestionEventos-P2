package edu.pgii.eventos.service;

import edu.pgii.eventos.model.Compra;
import edu.pgii.eventos.model.MetodoPago;
import edu.pgii.eventos.model.Pago;
import java.time.LocalDateTime;
import java.util.UUID;

public class EstrategiaPagoTarjetaSimulada implements EstrategiaPago {
    @Override
    public Pago pay(Compra compra, MetodoPago metodoPago) {
        return new Pago("P-" + UUID.randomUUID(), metodoPago, compra.getTotal(), LocalDateTime.now(),
                "APROBADO-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
    }
}

