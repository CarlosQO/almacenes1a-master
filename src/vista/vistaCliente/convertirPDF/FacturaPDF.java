package vista.vistaCliente.convertirPDF;

import java.awt.Color;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import modelo.crudFactura.ProductoDetalleFactura;

public class FacturaPDF {
    public static void generar(String fechaInicio, String fechaFin, int idFactura,
            List<ProductoDetalleFactura> productos, double total) {
        try {
            // Ruta en Descargas con nombre dinámico
            String nombreArchivo = "Factura_" + fechaInicio + "_a_" + fechaFin  + "_" + System.currentTimeMillis() + ".pdf";
            String ruta = System.getProperty("user.home") + "/Downloads/" + nombreArchivo;

            Document doc = new Document(PageSize.A4, 36, 36, 54, 36);
            PdfWriter.getInstance(doc, new FileOutputStream(ruta));
            doc.open();

            // Título principal
            Font tituloFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            Paragraph titulo = new Paragraph("FACTURA", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);

            // Subtítulo con fechas
            Font subFont = new Font(Font.HELVETICA, 12, Font.NORMAL, Color.GRAY);
            doc.add(new Paragraph("Periodo: " + fechaInicio + " a " + fechaFin, subFont));
            doc.add(new Paragraph("\n"));

            Paragraph info2 = new Paragraph("Factura: " + idFactura);
            doc.add(info2);
            doc.add(new Paragraph("\n"));

            PdfPTable tabla = new PdfPTable(4);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[] { 3, 2, 1, 2 });

            // Encabezados
            tabla.addCell(celda("Producto", Element.ALIGN_CENTER, true));
            tabla.addCell(celda("Valor Unitario", Element.ALIGN_CENTER, true));
            tabla.addCell(celda("Cantidad", Element.ALIGN_CENTER, true));
            tabla.addCell(celda("Subtotal", Element.ALIGN_CENTER, true));

            // Filas de productos
            for (ProductoDetalleFactura pd : productos) {
                tabla.addCell(celda(pd.getNombreProducto(), Element.ALIGN_LEFT, false));
                tabla.addCell(celda(String.valueOf(pd.getPrecioUnitario()), Element.ALIGN_CENTER, false));
                tabla.addCell(celda(String.valueOf(pd.getCantidad()), Element.ALIGN_CENTER, false));
                tabla.addCell(celda(String.valueOf(pd.getSubtotal()), Element.ALIGN_RIGHT, false));
            }

            doc.add(tabla);
            // Total del pedido
            Font totalFont = new Font(Font.HELVETICA, 12, Font.BOLD);
            Paragraph totalPedido = new Paragraph(
                    "Total del pedido: " + total,
                    totalFont);
            totalPedido.setAlignment(Element.ALIGN_RIGHT);
            doc.add(totalPedido);
            // Espacio entre pedidos (sin línea separadora)
            doc.add(new Paragraph("\n"));
            // Pie del reporte
            doc.add(new Paragraph("\nFactura generado automáticamente.", subFont));
            doc.add(new Paragraph("Fecha de generación: " + new Date(), subFont));
            doc.close();
           JOptionPane.showMessageDialog(
                    null,
                    "Factura generada en la carpeta Descargas.\nArchivo: " + nombreArchivo,
                    "Factura Generada",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al generar el PDF: " + e.getMessage());
        }
    }

    // Método auxiliar para crear celdas
    private static PdfPCell celda(String texto, int alineacion, boolean negrita) {
        Font font = new Font(Font.HELVETICA, 11, negrita ? Font.BOLD : Font.NORMAL);
        PdfPCell cell = new PdfPCell(new Phrase(texto, font));
        cell.setHorizontalAlignment(alineacion);
        cell.setPadding(6);
        cell.setBackgroundColor(negrita ? new Color(230, 230, 250) : Color.WHITE);
        return cell;
    }
}
