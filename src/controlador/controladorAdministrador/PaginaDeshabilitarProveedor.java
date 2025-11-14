package controladorAdministrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import controladorAdministrador.action.AccionesDeshabilitarProveedor;
import modelo.crudProveedor.Proveedor;
import modelo.crudProveedor.ProveedorDao;

public class PaginaDeshabilitarProveedor implements ActionListener {
    private vista.vistaAdministrador.PaginaDeshabilitarProveedor pDeshaPro;
    private ProveedorDao provDao = new ProveedorDao();

    public PaginaDeshabilitarProveedor(vista.vistaAdministrador.PaginaDeshabilitarProveedor p) {
        this.pDeshaPro = p;
        pDeshaPro.setVisible(true);
        pDeshaPro.buscar.addActionListener(this);
        cargarProveedoresActivos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pDeshaPro.buscar) {
            proveedorInactivoID = provDao.listarProveedorPorID(pDeshaPro.busqueda.getText().trim().toString());
            if (proveedorInactivoID.isEmpty()) {
                cargarProveedoresActivos();
                JOptionPane.showMessageDialog(null, "No se encontró ningún proveedor con ese NIT", "Error",
                        JOptionPane.ERROR_MESSAGE);
                proveedorInactivoID.clear();
                return;
            }
            // validar que el proveedor tenga el estado activo
            if (proveedorInactivoID.get(0).getEstado() == 2) {
                JOptionPane.showMessageDialog(null,
                        "El proveedor con el Documento " + proveedorInactivoID.get(0).getDocumento()
                                + " se encuentra deshabilitado",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                proveedorInactivoID.clear();
                return;
            }
            // validar que el proveedor tenga el estado pendiente
            if (proveedorInactivoID.get(0).getEstado() == 3) {
                JOptionPane.showMessageDialog(null,
                        "El proveedor con el Documento " + proveedorInactivoID.get(0).getDocumento()
                                + " no se encuentra pendiente",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                proveedorInactivoID.clear();
                return;
            }

            proveedorInactivo = proveedorInactivoID;
            cargarProveedoresActivos();
        }
    }

    private List<Proveedor> proveedorInactivo = new ArrayList<>();
    private List<Proveedor> proveedorInactivoID = new ArrayList<>();

    public void cargarProveedoresActivos() {
        DefaultTableModel modelo = (DefaultTableModel) pDeshaPro.tablaProveInactivo.getModel();
        modelo.setRowCount(0);

        if (proveedorInactivoID.isEmpty()) {
            proveedorInactivo = provDao.listarProveedorActivos();
        }

        if (proveedorInactivo.isEmpty()) {
            // JOptionPane.showMessageDialog(null, "No hay proveedorInactivo inactivos");
            if (!pDeshaPro.noHayProvee.isVisible()) {
                pDeshaPro.noHayProvee.setVisible(true);
            }

            if (pDeshaPro.containInfo.isVisible()) {
                pDeshaPro.containInfo.setVisible(false);
            }
            return;
        } else {
            if (!pDeshaPro.containInfo.isVisible()) {
                pDeshaPro.containInfo.setVisible(true);
            }
            if (pDeshaPro.noHayProvee.isVisible()) {
                pDeshaPro.noHayProvee.setVisible(false);
            }
        }

        for (Proveedor proveedor : proveedorInactivo) {
            Object[] datos = {
                    proveedor.getNombre(),
                    proveedor.getDocumento(),
                    proveedor.getProducto(),
                    proveedor.getMetodoPagoVarchar(),
                    proveedor.getTelefono(),
                    proveedor.getDocumento() 
            };
            modelo.addRow(datos);
        }

        int totalCols = pDeshaPro.tablaProveInactivo.getColumnCount();
        if (totalCols > 5) {
            pDeshaPro.tablaProveInactivo.getColumnModel()
                    .getColumn(5)
                    .setCellRenderer(new AccionesDeshabilitarProveedor(() -> cargarProveedoresActivos()));

            pDeshaPro.tablaProveInactivo.getColumnModel()
                    .getColumn(5)
                    .setCellEditor(new AccionesDeshabilitarProveedor(() -> cargarProveedoresActivos()));
            pDeshaPro.tablaProveInactivo.setRowHeight(35);
        } else {
            System.out.println("La columna 'Acciones' no está disponible (total columnas: " + totalCols + ")");
        }
    }
}