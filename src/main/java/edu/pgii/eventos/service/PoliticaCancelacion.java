package edu.pgii.eventos.service;

import edu.pgii.eventos.model.Compra;

public interface PoliticaCancelacion {
    boolean canCancel(Compra compra);
    String explain(Compra compra);
}

