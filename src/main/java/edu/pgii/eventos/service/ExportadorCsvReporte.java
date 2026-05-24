package edu.pgii.eventos.service;

import edu.pgii.eventos.model.Compra;
import edu.pgii.eventos.model.Enumeraciones.ReportType;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public class ExportadorCsvReporte implements ExportadorReporte {
    @Override
    public Path export(ReportType type, LocalDate from, LocalDate to, List<Compra> compras) throws IOException {
        Path dir = Path.of("reportes");
        Files.createDirectories(dir);
        Path file = dir.resolve(type.name().toLowerCase() + "-" + System.currentTimeMillis() + ".csv");
        StringBuilder csv = new StringBuilder("idCompra,usuario,evento,fecha,estado,total,servicios\n");
        for (Compra c : compras) {
            if (inRange(c, from, to)) {
                csv.append(c.getIdCompra()).append(',')
                        .append(escape(c.getUsuario().getNombreCompleto())).append(',')
                        .append(escape(c.getEvento().getNombre())).append(',')
                        .append(c.getFechaCreacion().toLocalDate()).append(',')
                        .append(c.getEstado()).append(',')
                        .append(c.getTotal()).append(',')
                        .append(c.getServicios().size()).append('\n');
            }
        }
        csv.append("TOTAL,,,,,")
                .append(compras.stream().filter(c -> inRange(c, from, to)).map(Compra::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add))
                .append(",\n");
        Files.writeString(file, csv.toString(), StandardCharsets.UTF_8);
        return file.toAbsolutePath();
    }

    private boolean inRange(Compra c, LocalDate from, LocalDate to) {
        LocalDate d = c.getFechaCreacion().toLocalDate();
        return (from == null || !d.isBefore(from)) && (to == null || !d.isAfter(to));
    }

    private String escape(String text) {
        return '"' + text.replace("\"", "\"\"") + '"';
    }
}

