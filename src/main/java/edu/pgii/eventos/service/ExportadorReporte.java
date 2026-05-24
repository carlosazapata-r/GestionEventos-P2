package edu.pgii.eventos.service;

import edu.pgii.eventos.model.Compra;
import edu.pgii.eventos.model.Enumeraciones.ReportType;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public interface ExportadorReporte {
    Path export(ReportType type, LocalDate from, LocalDate to, List<Compra> compras) throws IOException;
}

