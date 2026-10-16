package controladorAdministrador.PDF_Administrador;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class ContratoProveedorPDF {

    private static Path obtenerRutaPlantilla() throws IOException {
        String[] rutas = {
                Paths.get("src", "contrato", "contrato_proveedor.txt").toString(),
                Paths.get("src", "contrato", "Contrato_proveedor.txt").toString(),
                Paths.get(System.getProperty("user.dir"), "src", "contrato", "contrato_proveedor.txt").toString(),
                Paths.get(System.getProperty("user.dir"), "src", "contrato", "Contrato_proveedor.txt").toString()
        };

        for (String ruta : rutas) {
            Path path = Paths.get(ruta);
            if (Files.exists(path)) {
                return path;
            }
        }

        throw new FileNotFoundException("No se encontró la plantilla del contrato del proveedor en src/contrato.");
    }

    private static String leerTextoContrato() throws IOException {
        return Files.readString(obtenerRutaPlantilla(), java.nio.charset.StandardCharsets.UTF_8);
    }

    public static void generarPDFDesdeTXT(String nombreArchivoPDF, Map<String, String> datos) throws Exception {

        String rutaDescargas = System.getProperty("user.home") + "\\Downloads\\";
        String rutaSalidaPDF = rutaDescargas + nombreArchivoPDF;

        String texto = leerTextoContrato();

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
        String texto = leerTextoContrato();

        for (String key : datos.keySet()) {
            texto = texto.replace("{{" + key + "}}", datos.get(key));
        }

        return texto;
    }
}
