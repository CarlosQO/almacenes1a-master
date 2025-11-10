package controladorAdministrador.PDF_Administrador;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.awt.Color;
import java.io.FileOutputStream;
import java.util.List;

public class HistoricoVentaPDF {

    public void generalHistoricoVentasPDF(
            String fechaInicio,
            String fechaFin,
            List<Object[]> historialVenta) {
        try {
            // Ruta de descargas
            String ruta = System.getProperty("user.home") + "/Downloads/ReporteHistoricoVentaPorPeriodo_"
                    + fechaInicio + "_a_" + fechaFin + "_" + System.currentTimeMillis() + ".pdf";

            // Documento horizontal
            Document doc = new Document(PageSize.A4.rotate(), 20, 20, 30, 20);
            PdfWriter.getInstance(doc, new FileOutputStream(ruta));
            doc.open();

            // --- TÍTULO ---
            Font tituloFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph titulo = new Paragraph("REPORTE DE HISTÓRICO DE VENTAS POR PERIODO", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);

            // --- FECHAS ---
            Font subFont = new Font(Font.HELVETICA, 12, Font.NORMAL, Color.GRAY);
            doc.add(new Paragraph("Periodo: " + fechaInicio + " a " + fechaFin, subFont));
            doc.add(new Paragraph("\n"));

            // --- TABLA ---
            PdfPTable tablaReporte = new PdfPTable(14); // 14 columnas
            tablaReporte.setWidthPercentage(100);
            tablaReporte.setSpacingBefore(10f);
            tablaReporte.setSpacingAfter(10f);

            // Ajustar proporciones de columnas (puedes modificarlas si algo se ve muy ancho
            // o estrecho)
            float[] anchos = { 7f, 6f, 10f, 12f, 14f, 6f, 10f, 10f, 6f, 7f, 8f, 8f, 10f, 9f };
            tablaReporte.setWidths(anchos);

            // --- ENCABEZADOS ---
            String[] columnas = {
                    "Fecha Venta", "Código", "Documento Usuario", "Nombre Cliente", "Correo",
                    "ID Producto", "Producto", "Categoría", "Cantidad Venta", "Precio Unitario",
                    "Subtotal", "Total Venta", "Método de Pago", "Estado del Pedido"
            };

            for (String col : columnas) {
                tablaReporte.addCell(crearCelda(col, true, Element.ALIGN_CENTER, new Color(220, 230, 250)));
            }

            // --- DATOS ---
            for (Object[] venta : historialVenta) {
                for (int i = 0; i < venta.length; i++) {
                    String valor = (venta[i] != null) ? venta[i].toString() : "-";
                    tablaReporte.addCell(crearCelda(valor, false, Element.ALIGN_CENTER, Color.WHITE));
                }
            }

            // Agregar tabla al documento
            doc.add(tablaReporte);

            // --- PIE DE PÁGINA ---
            doc.add(new Paragraph("\n\nReporte generado automáticamente.", subFont));
            doc.add(new Paragraph("Fecha de generación: " + new java.util.Date(), subFont));

            doc.close();
            System.out.println("Reporte generado correctamente en: " + ruta);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al generar el PDF: " + e.getMessage());
        }
    }

    // Método auxiliar para crear celdas con formato
    private static PdfPCell crearCelda(String texto, boolean encabezado, int alineacion, Color fondo) {
        Font fuente = new Font(Font.HELVETICA, encabezado ? 11 : 10, encabezado ? Font.BOLD : Font.NORMAL);
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));
        celda.setHorizontalAlignment(alineacion);
        celda.setVerticalAlignment(Element.ALIGN_MIDDLE);
        celda.setBackgroundColor(fondo);
        celda.setPadding(6);
        celda.setBorderColor(Color.GRAY);
        return celda;
    }
}
