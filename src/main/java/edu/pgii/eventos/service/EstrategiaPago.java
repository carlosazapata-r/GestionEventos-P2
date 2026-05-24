package edu.pgii.eventos.service;

import edu.pgii.eventos.model.Compra;
import edu.pgii.eventos.model.MetodoPago;
import edu.pgii.eventos.model.Pago;

public interface EstrategiaPago {
    Pago pay(Compra compra, MetodoPago metodoPago);
}

