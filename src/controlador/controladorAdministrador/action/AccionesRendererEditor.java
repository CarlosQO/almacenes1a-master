package controladorAdministrador.action;

import java.awt.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

import modelo.crudProveedor.Proveedor;
import modelo.crudProveedor.ProveedorDao;
import vista.componentes.RoundedJXButton;

public class AccionesRendererEditor extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private final JPanel panel;
    private final RoundedJXButton btnAprobar;
    private final RoundedJXButton btnRechazar;
    private long idProveedorActual;
    private AccionProveedorListener listener;
    ProveedorDao proveedorDao = new ProveedorDao();

    public AccionesRendererEditor(AccionProveedorListener listener) {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setBorder(null);

        btnAprobar = new RoundedJXButton("Aprobar");
        btnRechazar = new RoundedJXButton("Rechazar");
        btnAprobar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRechazar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnAprobar.setBounds(0, 3, 90, 28);
        btnAprobar.setBaseColor(new Color(46, 204, 113)); // Verde
        btnAprobar.setBorderColor(Color.BLACK);

        btnRechazar.setBounds(100, 3, 120, 28);
        btnRechazar.setBaseColor(new Color(231, 76, 60)); // Rojo
        btnRechazar.setBorderColor(Color.BLACK);

        panel.add(btnAprobar);
        panel.add(btnRechazar);

        // Evento botón Aprobar
        btnAprobar.addActionListener(e -> {

            int confirm = JOptionPane.showConfirmDialog(null,
                    "¿Desea aprobar al proveedor con Documento " + idProveedorActual + "?",
                    "Confirmar Aprobación",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String documentoString = Long.toString(idProveedorActual);
                if (validarQueExistaProveedorAsociadoAlProducto(documentoString)) {
                    int result = proveedorDao.CambiarEstado(idProveedorActual, 2);
                    if (result != 0) {
                        JOptionPane.showMessageDialog(null,
                                "Ya existe un proveedor asociado a este producto.\nSe actualizó correctamente el estado del proveedor a Inhabilitado.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo actualizar el estado del proveedor");
                    }
                } else {
                    int result = proveedorDao.CambiarEstado(idProveedorActual, 1);
                    if (result != 0) {
                        JOptionPane.showMessageDialog(null,
                                "Se actualizó correctamente el estado del proveedor a Habilitado.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo actualizar el estado del proveedor");
                    }
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

        // Evento botón Rechazar
        btnRechazar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null,
                    "¿Desea rechazar al proveedor con ID " + idProveedorActual + "?",
                    "Confirmar Rechazo",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {

                int result = proveedorDao.CambiarEstado(idProveedorActual, 4);

                if (result != 0) {
                    JOptionPane.showMessageDialog(null, "Se Rechazo correctamente el proveedor");
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo actualizar el estado del proveedor");
                }

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
                idProveedorActual = Long.parseLong(value.toString());
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

    private boolean validarQueExistaProveedorAsociadoAlProducto(String documentoProveedorActual) {

        List<Proveedor> proveedores = proveedorDao.listarProveedorPorID(documentoProveedorActual);

        if (proveedores.isEmpty()) {
            return false;
        }

        int idProducto = proveedores.get(0).getIdProducto();

        return proveedorDao.validarProductoAsociadoAProveedor(idProducto) != null;
    }
}