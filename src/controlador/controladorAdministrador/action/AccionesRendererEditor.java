import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

import modelo.crudProveedor.ProveedorDao;

public class AccionesRendererEditor extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {
    private JPanel panel;
    private JButton btnAprobar;
    private JButton btnRechazar;
    private int idProveedorActual;

    public AccionesRendererEditor() {
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));

        btnAprobar = new JButton("Aprobar");
        btnRechazar = new JButton("Rechazar");

        btnAprobar.setBackground(new Color(46, 204, 113));
        btnRechazar.setBackground(new Color(231, 76, 60));
        btnAprobar.setForeground(Color.WHITE);
        btnRechazar.setForeground(Color.WHITE);

        btnAprobar.setFocusPainted(false);
        btnRechazar.setFocusPainted(false);

        panel.add(btnAprobar);
        panel.add(btnRechazar);

        // Eventos de los botones
        btnAprobar.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Proveedor aprobado: " + idProveedorActual);
            ProveedorDao proveedorDao = new ProveedorDao();
            proveedorDao.CambiarEstado(idProveedorActual, 1);
            stopCellEditing();
        });

        btnRechazar.addActionListener(e -> {
            JOptionPane.showMessageDialog(null, "Proveedor rechazado: " + idProveedorActual);

            stopCellEditing();
        });
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
            int row, int column) {
        return panel;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value != null) {
            idProveedorActual = Integer.parseInt(value.toString());
        }
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return idProveedorActual;
    }
}
