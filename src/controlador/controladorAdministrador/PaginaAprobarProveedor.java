package controladorAdministrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

import controladorAdministrador.action.AccionesRendererEditor;
import modelo.crudProveedor.Proveedor;
import modelo.crudProveedor.ProveedorDao;

public class PaginaAprobarProveedor implements ActionListener {
    private vista.vistaAdministrador.PaginaAprobarProveedor paginaAprobarProveedor;
    private ProveedorDao prDao = new ProveedorDao();

    public PaginaAprobarProveedor(vista.vistaAdministrador.PaginaAprobarProveedor paginaAprobarProveedor) {
        this.paginaAprobarProveedor = paginaAprobarProveedor;
        cargarProveedoresPendientes();
        paginaAprobarProveedor.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private List<Proveedor> proveedores = new ArrayList<>();

    private void cargarProveedoresPendientes() {
        DefaultTableModel modelo = (DefaultTableModel) paginaAprobarProveedor.tablaProveedores.getModel();
        modelo.setRowCount(0);

        proveedores = prDao.listarProveedorPendiente();

        if (proveedores.isEmpty()) {
            // JOptionPane.showMessageDialog(null, "No hay proveedores inactivos");
            if (!paginaAprobarProveedor.noHayProvee.isVisible()) {
                paginaAprobarProveedor.noHayProvee.setVisible(true);
            }

            if (paginaAprobarProveedor.containInfo.isVisible()) {
                paginaAprobarProveedor.containInfo.setVisible(false);
            }
            return;
        } else {
            if (!paginaAprobarProveedor.containInfo.isVisible()) {
                paginaAprobarProveedor.containInfo.setVisible(true);
            }
            if (paginaAprobarProveedor.noHayProvee.isVisible()) {
                paginaAprobarProveedor.noHayProvee.setVisible(false);
            }
        }

        for (Proveedor proveedor : proveedores) {
            Object[] datos = {
                    proveedor.getNombre(),
                    proveedor.getDocumento(),
                    proveedor.getProducto(),
                    proveedor.getDireccion(),
                    proveedor.getTelefono(),
                    proveedor.getCorreo(),
                    proveedor.getDocumento() // Este será usado por los botones
            };
            modelo.addRow(datos);
        }

        int totalCols = paginaAprobarProveedor.tablaProveedores.getColumnCount();
        if (totalCols > 6) {
            paginaAprobarProveedor.tablaProveedores.getColumnModel()
                    .getColumn(6)
                    .setCellRenderer(new AccionesRendererEditor(() -> cargarProveedoresPendientes()));

            paginaAprobarProveedor.tablaProveedores.getColumnModel()
                    .getColumn(6)
                    .setCellEditor(new AccionesRendererEditor(() -> cargarProveedoresPendientes()));

            paginaAprobarProveedor.tablaProveedores.setRowHeight(35);
        } else {
            System.out.println("La columna 'Acciones' no está disponible (total columnas: " + totalCols + ")");
        }
    }
}
