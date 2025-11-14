package controladorAdministrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import controladorAdministrador.action.AccionesHabilitarProveedor;
import modelo.crudProveedor.Proveedor;
import modelo.crudProveedor.ProveedorDao;

public class PaginaHabilitarProveedor implements ActionListener {
    private vista.vistaAdministrador.PaginaHabilitarProveedor pHabiPro;
    private ProveedorDao provDao = new ProveedorDao();

    public PaginaHabilitarProveedor(vista.vistaAdministrador.PaginaHabilitarProveedor p) {
        this.pHabiPro = p;
        pHabiPro.setVisible(true);
        pHabiPro.buscar.addActionListener(this);
        cargarProveedoresInactivos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pHabiPro.buscar) {
            proveedorInactivoID = provDao.listarProveedorPorID(pHabiPro.busqueda.getText().trim().toString());
            if (proveedorInactivoID.isEmpty()) {
                cargarProveedoresInactivos();
                JOptionPane.showMessageDialog(null, "No se encontró ningún proveedor con ese NIT", "Error",
                        JOptionPane.ERROR_MESSAGE);
                proveedorInactivoID.clear();
                cargarProveedoresInactivos();
                return;
            }
            // validar que el proveedor tenga el estado activo
            if (proveedorInactivoID.get(0).getEstado() == 1) {
                JOptionPane.showMessageDialog(null,
                        "El proveedor con el Documento " + proveedorInactivoID.get(0).getDocumento()
                                + " ya se encuentra habilitado",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                proveedorInactivoID.clear();
                cargarProveedoresInactivos();
                return;
            }
            // validar que el proveedor tenga el estado pendiente
            if (proveedorInactivoID.get(0).getEstado() == 3) {
                JOptionPane.showMessageDialog(null,
                        "El proveedor con el Documento " + proveedorInactivoID.get(0).getDocumento()
                                + " se encuentra pendiente",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                proveedorInactivoID.clear();
                cargarProveedoresInactivos();
                return;
            }

            proveedorInactivo = proveedorInactivoID;
            cargarProveedoresInactivos();
        }
    }

    private List<Proveedor> proveedorInactivo = new ArrayList<>();
    private List<Proveedor> proveedorInactivoID = new ArrayList<>();

    public void cargarProveedoresInactivos() {
        DefaultTableModel modelo = (DefaultTableModel) pHabiPro.tablaProveInactivo.getModel();
        modelo.setRowCount(0);

        if (proveedorInactivoID.isEmpty()) {
            proveedorInactivo = provDao.listarProveedorInactivo();
        }

        if (proveedorInactivo.isEmpty()) {
            // JOptionPane.showMessageDialog(null, "No hay proveedorInactivo inactivos");
            if (!pHabiPro.noHayProvee.isVisible()) {
                pHabiPro.noHayProvee.setVisible(true);
            }

            if (pHabiPro.containInfo.isVisible()) {
                pHabiPro.containInfo.setVisible(false);
            }
            return;
        } else {
            if (!pHabiPro.containInfo.isVisible()) {
                pHabiPro.containInfo.setVisible(true);
            }
            if (pHabiPro.noHayProvee.isVisible()) {
                pHabiPro.noHayProvee.setVisible(false);
            }
        }

        for (Proveedor proveedor : proveedorInactivo) {
            Object[] datos = {
                    proveedor.getNombre(),
                    proveedor.getDocumento(),
                    proveedor.getProducto(),
                    proveedor.getMetodoPagoVarchar(),
                    proveedor.getTelefono(),
                    proveedor.getDocumento() // Este será usado por los botones
            };
            modelo.addRow(datos);
        }

        int totalCols = pHabiPro.tablaProveInactivo.getColumnCount();
        if (totalCols > 5) {
            pHabiPro.tablaProveInactivo.getColumnModel()
                    .getColumn(5)
                    .setCellRenderer(new AccionesHabilitarProveedor(() -> cargarProveedoresInactivos()));

            pHabiPro.tablaProveInactivo.getColumnModel()
                    .getColumn(5)
                    .setCellEditor(new AccionesHabilitarProveedor(() -> cargarProveedoresInactivos()));
            pHabiPro.tablaProveInactivo.setRowHeight(35);
        } else {
            System.out.println("La columna 'Acciones' no está disponible (total columnas: " + totalCols + ")");
        }
    }
}