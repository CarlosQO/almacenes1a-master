package controladorAdministrador.action;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

import modelo.crudProveedor.Proveedor;
import modelo.crudProveedor.ProveedorDao;
import vista.componentes.RoundedJXButton;

public class AccionesContratoProveedor extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private final JPanel panel;
    private final RoundedJXButton btnVisualizar;
    private final RoundedJXButton btnDescargar;
    private int idProveedorActual;
    ProveedorDao proveedorDao = new ProveedorDao();

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
            System.out.println("El id del proveedor actual es: " + proveedores.get(0).getNombre());
        });

        // Evento botón Rechazar
        btnDescargar.addActionListener(e -> {
            System.out.println("El id del proveedor actual es: " + idProveedorActual);
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
                idProveedorActual = Integer.parseInt(value.toString());
                String proveedor = Integer.toString(idProveedorActual);
                proveedores = proveedorDao.listarProveedorPorID(proveedor);
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

}