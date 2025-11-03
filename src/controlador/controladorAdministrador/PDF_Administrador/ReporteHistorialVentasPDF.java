package controladorAdministrador.PDF_Administrador;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.FileOutputStream;
import java.util.List;

public class ReporteHistorialVentasPDF {

    public void generalHistorialVentasPDF(
            String fechaInicio,
            String fechaFin,
            List<Object[]> historialVenta) {
        try {
            // Ruta de descargas
            String ruta = System.getProperty("user.home") + "/Downloads/ReporteHistorialVenta_" + fechaInicio + "_a_"
                    + fechaFin
                    + "_" + System.currentTimeMillis() + ".pdf";
            Document doc = new Document(PageSize.A4, 36, 36, 54, 36);
            PdfWriter.getInstance(doc, new FileOutputStream(ruta));
            doc.open();

            // Encabezado del Reporte
            Font tituloFont = new Font(Font.HELVETICA, 20, Font.BOLD);
            Paragraph titulo = new Paragraph("REPORTE DE HISTORIAL DE VENTAS", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);

            // Agregar la fechas (Inicio - Fin)
            Font subFont = new Font(Font.HELVETICA, 12, Font.NORMAL, Color.GRAY);
            doc.add(new Paragraph("Periodo: " + fechaInicio + " a " + fechaFin, subFont));
            doc.add(new Paragraph("\n"));

            // Agregar la tabla para el reporte
            PdfPTable tablaReporte = new PdfPTable(5);
            tablaReporte.setWidthPercentage(100);
            tablaReporte.addCell(celda("ID producto", Element.ALIGN_CENTER, true));
            tablaReporte.addCell(celda("Nombre Producto", Element.ALIGN_CENTER, true));
            tablaReporte.addCell(celda("Cantidad Venta", Element.ALIGN_CENTER, true));
            tablaReporte.addCell(celda("Valor Unitario", Element.ALIGN_CENTER, true));
            tablaReporte.addCell(celda("Valor Total", Element.ALIGN_CENTER, true));

            for (Object[] venta : historialVenta) {
                tablaReporte.addCell(celda(venta[0].toString(), Element.ALIGN_LEFT, true));
                tablaReporte.addCell(celda(venta[1].toString(), Element.ALIGN_LEFT, true));
                tablaReporte.addCell(celda(venta[2].toString(), Element.ALIGN_LEFT, true));
                tablaReporte.addCell(celda(venta[3].toString(), Element.ALIGN_LEFT, true));
                tablaReporte.addCell(celda(venta[4].toString(), Element.ALIGN_LEFT, true));
            }
            doc.add(tablaReporte);
            // CIERRE
            doc.add(new Paragraph("\n\nReporte generado automáticamente.", subFont));
            doc.add(new Paragraph("Fecha de generación: " + new java.util.Date(), subFont));
            doc.close();
            System.out.println(" Reporte generado en: " + ruta);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" Error al generar el PDF: " + e.getMessage());
        }
    }

    private static PdfPCell celda(String texto, int alineacion, boolean negrita) {
        Font font = new Font(Font.HELVETICA, 11, negrita ? Font.BOLD : Font.NORMAL);
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setHorizontalAlignment(alineacion);
        cell.setPadding(6);
        cell.setBackgroundColor(negrita ? new Color(230, 230, 250) : Color.WHITE);
        return cell;
    }
}
