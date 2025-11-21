package controladorAdministrador.action;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

import modelo.crudProveedor.Proveedor;
import modelo.crudProveedor.ProveedorDao;
import vista.componentes.RoundedJXButton;

public class AccionesHabilitarProveedor extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private final JPanel panel;
    private final RoundedJXButton btnAprobar;
    private long idProveedorActual;
    private AccionProveedorListener listener;
    private String documentoSegundoProveedor;
    private String nombreSegundoProveedor;
    ProveedorDao proveedorDao = new ProveedorDao();

    public AccionesHabilitarProveedor(AccionProveedorListener listener) {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setBorder(null);
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnAprobar = new RoundedJXButton("Habilitar");
        btnAprobar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAprobar.setBounds(0, 3, 140, 28);
        btnAprobar.setBaseColor(new Color(46, 204, 113)); // Verde
        btnAprobar.setBorderColor(Color.BLACK);

        panel.add(btnAprobar);

        // Evento botón Aprobar
        btnAprobar.addActionListener(e -> {

            int confirm = JOptionPane.showConfirmDialog(null,
                    "¿Desea Habilitar al proveedor con Documento " + idProveedorActual + "?",
                    "Confirmar Habilitación",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                String documentoProveedorActual = Long.toString(idProveedorActual);
                if (procesoParaHabilitarYdeshabilitarProveedor(documentoProveedorActual)) {
                    int opcion = JOptionPane.showConfirmDialog(null,
                            "Ya existe un proveedor asociado a este producto.\n"
                                    + "Proveedor: " + nombreSegundoProveedor + "\n"
                                    + "Documento: " + documentoSegundoProveedor + "\n"
                                    + "¿Desea deshabilitar este proveedor y habilitar el nuevo?",
                            "Proveedor Asociado Encontrado",
                            JOptionPane.YES_NO_OPTION);
                    if (opcion == JOptionPane.YES_OPTION) {
                        int resultadoDeshabilitarAnterior = proveedorDao
                                .CambiarEstado(Integer.parseInt(documentoSegundoProveedor), 2);
                        int resultadoHabilitarNuevo = proveedorDao
                                .CambiarEstado(idProveedorActual, 1);
                        if (resultadoDeshabilitarAnterior != 0 && resultadoHabilitarNuevo != 0) {
                            JOptionPane.showMessageDialog(null,
                                    "Se Deshabilito correctamente el proveedor asociado y se habilitó el nuevo.");
                        } else {
                            JOptionPane.showMessageDialog(null, "No se pudo deshabilitar el proveedor asociado");
                        }
                        SwingUtilities.invokeLater(() -> {
                            stopCellEditing();
                            if (listener != null)
                                listener.onProveedorActualizado();
                        });
                    }
                } else {
                    int result = proveedorDao.CambiarEstado(idProveedorActual, 1);
                    if (result != 0) {
                        JOptionPane.showMessageDialog(null, "Se Habilito correctamente el proveedor");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo habilitar el proveedor");
                    }
                    SwingUtilities.invokeLater(() -> {
                        stopCellEditing();
                        if (listener != null)
                            listener.onProveedorActualizado();
                    });
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
        if (value != null || value != "") {
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

    private boolean procesoParaHabilitarYdeshabilitarProveedor(String documentoProveedorActual) {
        List<Proveedor> proveedores = proveedorDao.listarProveedorPorID(documentoProveedorActual);
        if (proveedores.isEmpty()) {
            return false;
        }
        int idProducto = proveedores.get(0).getIdProducto();
        Proveedor segundoProveedor = proveedorDao.validarProductoAsociadoAProveedor(idProducto);
        if (segundoProveedor == null) {
            return false;
        }
        nombreSegundoProveedor = segundoProveedor.getNombre();
        documentoSegundoProveedor = segundoProveedor.getDocumento();

        return true;
    }
}