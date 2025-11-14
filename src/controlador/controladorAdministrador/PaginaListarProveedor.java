package controladorAdministrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.crudProveedor.Proveedor;
import modelo.crudProveedor.ProveedorDao;

public class PaginaListarProveedor implements ActionListener {
    private vista.vistaAdministrador.PaginaListarProveedores pListaPro;
    private ProveedorDao provDao = new ProveedorDao();

    public PaginaListarProveedor(vista.vistaAdministrador.PaginaListarProveedores p) {
        this.pListaPro = p;
        pListaPro.setVisible(true);
        cargarProveedores();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    private List<Proveedor> listarProveedores = new ArrayList<>();

    public void cargarProveedores() {
        DefaultTableModel modelo = (DefaultTableModel) pListaPro.tablaProveedores.getModel();
        modelo.setRowCount(0);

        listarProveedores = provDao.listarProveedorActivosInactivos();

        if (listarProveedores.isEmpty()) {
            // JOptionPane.showMessageDialog(null, "No hay listarProveedores inactivos");
            if (!pListaPro.noHayProvee.isVisible()) {
                pListaPro.noHayProvee.setVisible(true);
            }

            if (pListaPro.containInfo.isVisible()) {
                pListaPro.containInfo.setVisible(false);
            }
            return;
        } else {
            if (!pListaPro.containInfo.isVisible()) {
                pListaPro.containInfo.setVisible(true);
            }
            if (pListaPro.noHayProvee.isVisible()) {
                pListaPro.noHayProvee.setVisible(false);
            }
        }

        for (Proveedor proveedor : listarProveedores) {
            Object[] datos = {
                    proveedor.getNombre(),
                    proveedor.getDocumento(),
                    proveedor.getProducto(),
                    proveedor.getDireccion(),
                    proveedor.getTelefono(),
                    proveedor.getCorreo(),
                    proveedor.getEstadoVarchar()
            };
            modelo.addRow(datos);
        }
    }
}