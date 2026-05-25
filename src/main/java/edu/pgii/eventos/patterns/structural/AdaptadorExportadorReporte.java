package edu.pgii.eventos.patterns.structural;

import edu.pgii.eventos.model.Compra;
import edu.pgii.eventos.model.Enumeraciones.ReportType;
import edu.pgii.eventos.service.ExportadorReporte;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public class AdaptadorExportadorReporte {
    private final ExportadorReporte exporter;

    /**
     * Crea un adaptador para delegar la exportacion en una implementacion concreta.
     *
     * @param exporter exportador que realizara la generacion del reporte
     */
    public AdaptadorExportadorReporte(ExportadorReporte exporter) {
        this.exporter = exporter;
    }

    /**
     * Exporta un reporte usando la interfaz esperada por el modulo estructural.
     *
     * @param type tipo de reporte que se desea exportar
     * @param from fecha inicial del rango del reporte
     * @param to fecha final del rango del reporte
     * @param compras compras utilizadas como fuente de informacion
     * @return ruta del archivo generado
     * @throws IOException si ocurre un error al crear o escribir el reporte
     */
    public Path exportReport(ReportType type, LocalDate from, LocalDate to, List<Compra> compras) throws IOException {
        return exporter.export(type, from, to, compras);
    }
}
