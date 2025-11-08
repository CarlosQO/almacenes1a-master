package controladorAdministrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import controladorAdministrador.action.AccionesRendererEditor;
import modelo.crudProveedor.Proveedor;
import modelo.crudProveedor.ProveedorDao;

public class PaginaAprobarProveedor implements ActionListener {
    private vista.vistaAdministrador.PaginaAprobarProveedor paginaAprobarProveedor;
    private ProveedorDao prDao = new ProveedorDao();

    public PaginaAprobarProveedor(vista.vistaAdministrador.PaginaAprobarProveedor paginaAprobarProveedor) {
        this.paginaAprobarProveedor = paginaAprobarProveedor;
        cargarProveedoresInactivos();
        paginaAprobarProveedor.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private List<Proveedor> proveedores = new ArrayList<>();

    private void cargarProveedoresInactivos() {
        // Tomar el modelo actual (NO crear uno nuevo)
        DefaultTableModel modelo = (DefaultTableModel) paginaAprobarProveedor.tablaProveedores.getModel();
        modelo.setRowCount(0); // Limpia filas pero mantiene el diseño original

        proveedores = prDao.listarProveedorInactivo();

        if (proveedores.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay proveedores inactivos");
            paginaAprobarProveedor.containInfo.setVisible(false);
            return;
        }

        paginaAprobarProveedor.containInfo.setVisible(true);

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

        // Aplica el renderer/editor SOLO a la columna "Acciones" (sin tocar el resto
        // del modelo)
        int colAcciones = paginaAprobarProveedor.tablaProveedores.getColumnCount() - 1; // Última columna

        paginaAprobarProveedor.tablaProveedores.getColumnModel()
                .getColumn(colAcciones)
                .setCellRenderer(new AccionesRendererEditor());

        paginaAprobarProveedor.tablaProveedores.getColumnModel()
                .getColumn(colAcciones)
                .setCellEditor(new AccionesRendererEditor());

        // Mejora visual (opcional)
        paginaAprobarProveedor.tablaProveedores.setRowHeight(35);
    }

}
