package controladorAdministrador.PDF_Administrador;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class ContratoProveedorPDF {

    private static final String RUTA_PLANTILLA = "C:\\Users\\ASUS VIVOBOOK\\Documents\\NetBeansProjects\\almacenes1a-master\\src\\contrato\\Contrato_proveedor.txt";

    public static void generarPDFDesdeTXT(String nombreArchivoPDF, Map<String, String> datos) throws Exception {

        String rutaDescargas = System.getProperty("user.home") + "\\Downloads\\";
        String rutaSalidaPDF = rutaDescargas + nombreArchivoPDF;

        String texto = new String(Files.readAllBytes(Paths.get(RUTA_PLANTILLA)), "UTF-8");

        for (String key : datos.keySet()) {
            texto = texto.replace("{{" + key + "}}", datos.get(key));
        }

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter.getInstance(document, new FileOutputStream(rutaSalidaPDF));

        document.open();

        Font font = new Font(Font.HELVETICA, 11);
        Paragraph p = new Paragraph(texto, font);
        p.setAlignment(Element.ALIGN_JUSTIFIED);

        document.add(p);
        document.close();
    }

    public String cargarContratoReemplazado(Map<String, String> datos) throws Exception {

        String ruta = "C:\\Users\\ASUS VIVOBOOK\\Documents\\NetBeansProjects\\almacenes1a-master\\src\\contrato\\Contrato_proveedor.txt";

        // Leer todo el archivo
        String texto = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(ruta)), "UTF-8");

        // Reemplazar variables {{VARIABLE}}
        for (String key : datos.keySet()) {
            texto = texto.replace("{{" + key + "}}", datos.get(key));
        }

        return texto;
    }
}
