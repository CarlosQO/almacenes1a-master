package controladorAdministrador.PDF_Administrador;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class ContratoProveedorPDF {

    public static void generarPDFDesdeTXT(String rutaPlantilla, String rutaSalidaPDF, Map<String, String> datos)
            throws Exception {

        String texto = new String(Files.readAllBytes(Paths.get(rutaPlantilla)), "UTF-8");

        for (String key : datos.keySet()) {
            texto = texto.replace("{{" + key + "}}", datos.get(key));
        }

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter.getInstance(document, new FileOutputStream(rutaSalidaPDF));

        document.open();

        // Fuente básica
        Font font = new Font(Font.HELVETICA, 11);

        // Crear párrafo completo
        Paragraph parrafo = new Paragraph(texto, font);
        parrafo.setAlignment(Element.ALIGN_JUSTIFIED);

        // Agregar al PDF
        document.add(parrafo);

        document.close();
    }
}
