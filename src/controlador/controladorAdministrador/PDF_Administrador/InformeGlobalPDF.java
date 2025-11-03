package controladorAdministrador.PDF_Administrador;

import java.awt.Color;
import java.io.FileOutputStream;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import modelo.crudProducto.Producto;

public class InformeGlobalPDF {

    public void generar(
            String fechaInicio,
            String fechaFin,
            List<Producto> prodMasVendi,
            List<Producto> prodMenosVendi,
            List<Producto> prodStock,
            List<String> ventaYingresosGenerados,
            int numeroCliente,
            int cantidadPedido,
            String metodoDePago,
            String tendenciaCompra

    ) {
        try {
            // Ruta de Descargas del usuario
            String ruta = System.getProperty("user.home") + "/Downloads/InformeGlobal_" + fechaInicio + "_a_" + fechaFin
                    + "_" + System.currentTimeMillis() + ".pdf";

            Document doc = new Document(PageSize.A4, 36, 36, 54, 36);
            PdfWriter.getInstance(doc, new FileOutputStream(ruta));
            doc.open();

            // ENCABEZADO DEL INFORME
            Font tituloFont = new Font(Font.HELVETICA, 20, Font.BOLD);
            Paragraph titulo = new Paragraph("INFORME GLOBAL DE VENTAS", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            doc.add(titulo);

            Font subFont = new Font(Font.HELVETICA, 12, Font.NORMAL, Color.GRAY);
            doc.add(new Paragraph("Periodo: " + fechaInicio + " a " + fechaFin, subFont));
            doc.add(new Paragraph("\n"));

            // RESUMEN GENERAL
            Font seccionFont = new Font(Font.HELVETICA, 14, Font.BOLD, Color.BLUE);
            doc.add(new Paragraph("Resumen General", seccionFont));
            doc.add(new Paragraph("\n"));

            PdfPTable tablaResumen = new PdfPTable(2);
            tablaResumen.setWidthPercentage(100);
            tablaResumen.setSpacingBefore(10);

            tablaResumen.addCell(celda("Total de Ventas", Element.ALIGN_LEFT, true));
            tablaResumen.addCell(celda(ventaYingresosGenerados.get(0) + " unidades", Element.ALIGN_RIGHT, false));

            tablaResumen.addCell(celda("Total de Ingresos", Element.ALIGN_LEFT, true));
            tablaResumen.addCell(celda("$ " + ventaYingresosGenerados.get(1), Element.ALIGN_RIGHT, false));

            tablaResumen.addCell(celda("Clientes Registrados", Element.ALIGN_LEFT, true));
            tablaResumen.addCell(celda(String.valueOf(numeroCliente), Element.ALIGN_RIGHT, false));

            tablaResumen.addCell(celda("Pedidos Realizados", Element.ALIGN_LEFT, true));
            tablaResumen.addCell(celda(String.valueOf(cantidadPedido), Element.ALIGN_RIGHT, false));

            tablaResumen.addCell(celda("Método de Pago Más Utilizado", Element.ALIGN_LEFT, true));
            tablaResumen.addCell(celda(metodoDePago, Element.ALIGN_RIGHT, false));

            tablaResumen.addCell(celda("Categoría con Mayor Tendencia", Element.ALIGN_LEFT, true));
            tablaResumen.addCell(celda(tendenciaCompra, Element.ALIGN_RIGHT, false));

            doc.add(tablaResumen);
            doc.add(new Paragraph("\n"));

            // PRODUCTOS MÁS VENDIDOS
            doc.add(new Paragraph("Productos Más Vendidos", seccionFont));
            doc.add(new Paragraph("\n"));
            agregarTablaProductos(doc, prodMasVendi);

            // PRODUCTOS MENOS VENDIDOS
            doc.add(new Paragraph("\nProductos Menos Vendidos", seccionFont));
            doc.add(new Paragraph("\n"));
            agregarTablaProductos(doc, prodMenosVendi);

            // STOCK ACTUAL
            doc.add(new Paragraph("\nStock Actual por Producto", seccionFont));
            doc.add(new Paragraph("\n"));
            PdfPTable tablaStock = new PdfPTable(3);
            tablaStock.setWidthPercentage(100);
            tablaStock.addCell(celda("Categoría", Element.ALIGN_CENTER, true));
            tablaStock.addCell(celda("Producto", Element.ALIGN_CENTER, true));
            tablaStock.addCell(celda("Cantidad", Element.ALIGN_CENTER, true));

            for (Producto p : prodStock) {
                tablaStock.addCell(celda(p.getCategoria(), Element.ALIGN_LEFT, false));
                tablaStock.addCell(celda(p.getNombre(), Element.ALIGN_LEFT, false));
                tablaStock.addCell(celda(String.valueOf(p.getCantidad()), Element.ALIGN_RIGHT, false));
            }

            doc.add(tablaStock);

            // CIERRE
            doc.add(new Paragraph("\n\nInforme generado automáticamente.", subFont));
            doc.add(new Paragraph("Fecha de generación: " + new java.util.Date(), subFont));

            doc.close();
            System.out.println(" Informe generado en: " + ruta);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" Error al generar el PDF: " + e.getMessage());
        }
    }

    // MÉTODOS AUXILIARES
    private static void agregarTablaProductos(Document doc, List<Producto> lista) throws Exception {
        PdfPTable tabla = new PdfPTable(4);
        tabla.setWidthPercentage(100);
        tabla.addCell(celda("ID", Element.ALIGN_CENTER, true));
        tabla.addCell(celda("Nombre", Element.ALIGN_CENTER, true));
        tabla.addCell(celda("Talla", Element.ALIGN_CENTER, true));
        tabla.addCell(celda("Cantidad Vendida", Element.ALIGN_CENTER, true));

        for (Producto p : lista) {
            tabla.addCell(celda(String.valueOf(p.getId()), Element.ALIGN_CENTER, false));
            tabla.addCell(celda(p.getNombre(), Element.ALIGN_LEFT, false));
            tabla.addCell(celda(p.getTalla(), Element.ALIGN_CENTER, false));
            tabla.addCell(celda(String.valueOf(p.getCantidad()), Element.ALIGN_RIGHT, false));
        }

        doc.add(tabla);
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
