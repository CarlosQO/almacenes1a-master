package controladorAdministrador.action;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import modelo.crudProveedor.ProveedorDao;
import vista.componentes.RoundedJXButton;

public class AccionesDeshabilitarProveedor extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private final JPanel panel;
    private final RoundedJXButton btnDeshabilitar;
    private int idProveedorActual;
    private AccionProveedorListener listener;

    public AccionesDeshabilitarProveedor(AccionProveedorListener listener) {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setBorder(null);
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnDeshabilitar = new RoundedJXButton("Deshabilitar");
        btnDeshabilitar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDeshabilitar.setBounds(0, 3, 140, 28);
        btnDeshabilitar.setBaseColor(new Color(46, 204, 113)); // Verde
        btnDeshabilitar.setBorderColor(Color.BLACK);

        panel.add(btnDeshabilitar);

        // Evento botón Aprobar
        btnDeshabilitar.addActionListener(e -> {
            ProveedorDao proveedorDao = new ProveedorDao();
            int confirm = JOptionPane.showConfirmDialog(null,
                    "¿Desea Deshabilitar al proveedor con Documento " + idProveedorActual + "?",
                    "Confirmar Deshabilitación",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                
                int result = proveedorDao.CambiarEstado(idProveedorActual, 2);
                if (result != 0) {
                    JOptionPane.showMessageDialog(null, "Se Deshabilito correctamente el proveedor");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo deshabilitar el proveedor");
                }

                // Finaliza la edición antes de recargar la tabla
                SwingUtilities.invokeLater(() -> {
                    stopCellEditing();
                    if (listener != null)
                        listener.onProveedorActualizado();
                });
            } else {
                stopCellEditing();
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
                idProveedorActual = Integer.parseInt(value.toString());
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
}