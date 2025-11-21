package controladorAdministrador.action;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import javax.swing.table.*;

import controladorAdministrador.PDF_Administrador.ContratoProveedorPDF;
import modelo.crudAdministradorSesion.AdministradorDao;
import modelo.crudProveedor.Proveedor;
import modelo.crudProveedor.ProveedorDao;
import vista.componentes.RoundedJXButton;

public class AccionesContratoProveedor extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private final JPanel panel;
    private final RoundedJXButton btnVisualizar;
    private final RoundedJXButton btnDescargar;
    private long idProveedorActual;
    ProveedorDao proveedorDao = new ProveedorDao();
    AdministradorDao adminDao = new AdministradorDao();
    List<Object> admin = new ArrayList<>();
    private String textoReturnContrato;
    private ContratoProveedorPDF contratoProveedorPDF = new ContratoProveedorPDF();

    public AccionesContratoProveedor() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setBorder(null);

        btnVisualizar = new RoundedJXButton("Visualizar");
        btnDescargar = new RoundedJXButton("Descargar");
        btnVisualizar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDescargar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnVisualizar.setBounds(0, 3, 90, 28);
        btnVisualizar.setBaseColor(Color.CYAN); // Verde
        btnVisualizar.setBorderColor(Color.BLACK);

        btnDescargar.setBounds(100, 3, 120, 28);
        btnDescargar.setBaseColor(Color.CYAN); // Rojo
        btnDescargar.setBorderColor(Color.BLACK);

        panel.add(btnVisualizar);
        panel.add(btnDescargar);

        // Evento botón Aprobar
        btnVisualizar.addActionListener(e -> {
            if (proveedores.isEmpty()) {
                return;

            }
            Proveedor p = proveedores.get(0);

            Map<String, String> datos = new HashMap<>();
            datos.put("NOMBRE_ADMIN", admin.get(1).toString() + " " + admin.get(2).toString());
            datos.put("DOCU_ADMIN", admin.get(0).toString());
            datos.put("NOMBRE_PROVEEDOR", p.getNombre());
            datos.put("NUMERO_DOC_PRO", p.getDocumento());
            datos.put("DIRECCION_PRO", p.getDireccion());
            datos.put("PRODUCTO_PRO", p.getProducto());
            datos.put("PRECIO_PRO", String.valueOf(p.getPrecioProducto()));
            datos.put("METODO_PAGO_PRO", p.getMetodoPagoVarchar());
            datos.put("NUMERO_DIA", "20");
            datos.put("MES", "Noviembre");
            datos.put("ANO", "2026");

            try {
                textoReturnContrato = contratoProveedorPDF.cargarContratoReemplazado(datos);
            } catch (Exception ex) {
                System.out.println("Error al generar el contrato PDF: " + ex.getMessage());
                JOptionPane.showMessageDialog(null, "Error al generar el contrato para visualizar.");
            }
            fireEditingStopped();
        });

        // Evento botón Rechazar
        btnDescargar.addActionListener(e ->

        {
            if (proveedores.isEmpty()) {
                return;

            }
            Proveedor p = proveedores.get(0);

            Map<String, String> datos = new HashMap<>();
            datos.put("NOMBRE_ADMIN", admin.get(1).toString() + " " + admin.get(2).toString());
            datos.put("DOCU_ADMIN", admin.get(0).toString());
            datos.put("NOMBRE_PROVEEDOR", p.getNombre());
            datos.put("NUMERO_DOC_PRO", p.getDocumento());
            datos.put("DIRECCION_PRO", p.getDireccion());
            datos.put("PRODUCTO_PRO", p.getProducto());
            datos.put("PRECIO_PRO", String.valueOf(p.getPrecioProducto()));
            datos.put("METODO_PAGO_PRO", p.getMetodoPagoVarchar());
            datos.put("NUMERO_DIA", "20");
            datos.put("MES", "Noviembre");
            datos.put("ANO", "2025");

            try {
                ContratoProveedorPDF.generarPDFDesdeTXT(
                        "Contrato_" + proveedores.get(0).getNombre() + " " + System.currentTimeMillis() + ".pdf",
                        datos);

                JOptionPane.showMessageDialog(null, "Contrato PDF generado correctamente en la carpeta Descargas.");

            } catch (Exception ex) {
                System.out.println("Error al generar el contrato PDF: " + ex.getMessage());
                JOptionPane.showMessageDialog(null, "Error al generar el contrato PDF.");
            }
        });
    }

    // Renderizado normal (sin eventos)
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        panel.setOpaque(false); // Siempre transparente
        return panel;
    }

    // Editor activo (cuando se hace clic)
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value != null) {
            try {
                idProveedorActual = Long.parseLong(value.toString());
                String proveedor = Long.toString(idProveedorActual);
                proveedores = proveedorDao.listarProveedorPorID(proveedor);
                admin = adminDao.lista();
            } catch (NumberFormatException e) {
                idProveedorActual = -1;
            }
        }
        panel.setOpaque(false);
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return idProveedorActual;
    }

    private List<Proveedor> proveedores = new ArrayList<>();

    public String textoParaVisualizar() {
        return textoReturnContrato;
    }
}