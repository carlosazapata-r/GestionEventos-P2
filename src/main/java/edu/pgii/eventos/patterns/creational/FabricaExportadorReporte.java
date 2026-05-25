package edu.pgii.eventos.patterns.creational;

import edu.pgii.eventos.model.Enumeraciones.ReportFormat;
import edu.pgii.eventos.service.ExportadorCsvReporte;
import edu.pgii.eventos.service.ExportadorReporte;
import edu.pgii.eventos.service.ExportadorPdfSimple;

public class FabricaExportadorReporte {
    /**
     * Crea el exportador de reportes correspondiente al formato solicitado.
     *
     * @param format formato de salida requerido.
     * @return exportador concreto para generar el reporte.
     */
    public ExportadorReporte create(ReportFormat format) {
        return switch (format) {
            case CSV -> new ExportadorCsvReporte();
            case PDF -> new ExportadorPdfSimple();
        };
    }
}

