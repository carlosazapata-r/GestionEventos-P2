package edu.pgii.eventos.service;

import edu.pgii.eventos.model.Compra;
import edu.pgii.eventos.model.Enumeraciones.ReportType;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

public class ExportadorPdfSimple implements ExportadorReporte {
    @Override
    public Path export(ReportType type, LocalDate from, LocalDate to, List<Compra> compras) throws IOException {
        Path dir = Path.of("reportes");
        Files.createDirectories(dir);
        Path file = dir.resolve(type.name().toLowerCase() + "-" + System.currentTimeMillis() + ".pdf");
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            try (PDPageContentStream content = new PDPageContentStream(document, page)) {
                content.beginText();
                content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 16);
                content.newLineAtOffset(50, 740);
                content.showText("Reporte operativo: " + type);
                content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 11);
                content.newLineAtOffset(0, -28);
                content.showText("Periodo: " + from + " a " + to);
                content.newLineAtOffset(0, -22);
                content.showText("Compras incluidas: " + compras.size());
                content.newLineAtOffset(0, -22);
                content.showText("Total general: $" + compras.stream().map(Compra::getTotal).reduce(java.math.BigDecimal.ZERO, java.math.BigDecimal::add));
                int rows = 0;
                for (Compra c : compras) {
                    if (++rows > 18) break;
                    content.newLineAtOffset(0, -18);
                    content.showText(c.getIdCompra() + " | " + c.getEvento().getNombre() + " | " + c.getEstado() + " | $" + c.getTotal());
                }
                content.endText();
            }
            document.save(file.toFile());
        }
        return file.toAbsolutePath();
    }
}

