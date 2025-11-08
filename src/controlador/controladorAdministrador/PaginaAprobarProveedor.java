package controladorAdministrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modelo.crudProveedor.Proveedor;
import modelo.crudProveedor.ProveedorDao;

public class PaginaAprobarProveedor implements ActionListener {
    private vista.vistaAdministrador.PaginaAprobarProveedor paginaAprobarProveedor;
    private ProveedorDao prDao = new ProveedorDao();

    public PaginaAprobarProveedor(vista.vistaAdministrador.PaginaAprobarProveedor paginaAprobarProveedor) {
        this.paginaAprobarProveedor = paginaAprobarProveedor;
        paginaAprobarProveedor.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private List<Proveedor> proveedores = new ArrayList<>();

    private void cargarProveedoresInactivos() {
        DefaultTableModel modelo = (DefaultTableModel) paginaAprobarProveedor.tablaProveedores.getModel();
        modelo.setRowCount(0); // reiniciar la tabla
        proveedores = prDao.listarProveedorInactivo();
        if (proveedores.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay historial de venta para esa fecha");
            if (paginaAprobarProveedor.containInfo.isVisible()) {
                paginaAprobarProveedor.containInfo.setVisible(false);
            }
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
                    
            };
            modelo.addRow(datos);
        }
    }
}
