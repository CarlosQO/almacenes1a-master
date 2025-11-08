package controladorAdministrador.action;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import modelo.crudProveedor.ProveedorDao;
import vista.componentes.RoundedJXButton;

public class AccionesRendererEditor extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private final JPanel panel;
    private final RoundedJXButton btnAprobar;
    private final RoundedJXButton btnRechazar;
    private int idProveedorActual;

    public AccionesRendererEditor() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false); // 🔹 Hace el panel completamente transparente
        panel.setBorder(null); // 🔹 Sin borde

        btnAprobar = new RoundedJXButton("Aprobar");
        btnRechazar = new RoundedJXButton("Rechazar");

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
            ProveedorDao proveedorDao = new ProveedorDao();
            int confirm = JOptionPane.showConfirmDialog(null,
                    "¿Desea aprobar al proveedor con Documento " + idProveedorActual + "?",
                    "Confirmar Aprobación",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                proveedorDao.CambiarEstado(idProveedorActual, 1);
                JOptionPane.showMessageDialog(null, "Proveedor aprobado correctamente.");
            }
            stopCellEditing();
        });

        // Evento botón Rechazar
        btnRechazar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(null,
                    "¿Desea rechazar al proveedor con ID " + idProveedorActual + "?",
                    "Confirmar Rechazo",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, "Proveedor rechazado correctamente.");
            }
            stopCellEditing();
        });
    }

    // Renderizado normal (sin eventos)
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        panel.setOpaque(false); // 🔹 Siempre transparente
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
        panel.setOpaque(false); // 🔹 También transparente cuando se edita
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return idProveedorActual;
    }
}
