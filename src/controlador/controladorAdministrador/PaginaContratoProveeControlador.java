package controladorAdministrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import controladorAdministrador.action.AccionesContratoProveedor;
import modelo.crudProveedor.Proveedor;
import modelo.crudProveedor.ProveedorDao;
import vista.vistaAdministrador.PaginaGenerarContrato;

public class PaginaContratoProveeControlador implements ActionListener {
    private PaginaGenerarContrato paginaGenerarContrato = new PaginaGenerarContrato();
    private ProveedorDao proveedorDao = new ProveedorDao();

    public PaginaContratoProveeControlador(PaginaGenerarContrato paGene) {
        this.paginaGenerarContrato = paGene;
        paGene.setLocationRelativeTo(null);
        paGene.setVisible(true);
        paGene.btnBuscarPro.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == paginaGenerarContrato.btnBuscarPro) {
            if (!paginaGenerarContrato.validarCampos()) {
                return;
            }

            String DocPro = paginaGenerarContrato.contratoDoc.getText();
            proveedor = proveedorDao.listarProveedorPorID(DocPro);

            if (proveedor.isEmpty()) {
                JOptionPane.showMessageDialog(null, "El proveedor no existe", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            cargarProveedores();
        }
    }

    private List<Proveedor> proveedor = new ArrayList<>();

    private void cargarProveedores() {
        DefaultTableModel modelo = (DefaultTableModel) paginaGenerarContrato.tablaVentas.getModel();
        modelo.setRowCount(0);

        for (Proveedor proveedor : proveedor) {
            Object[] datos = {
                    proveedor.getTipo(),
                    proveedor.getNombre(),
                    proveedor.getDocumento(),
                    proveedor.getDocumento()
            };
            modelo.addRow(datos);
        }
        int totalCols = paginaGenerarContrato.tablaVentas.getColumnCount();
        if (totalCols > 3) {
            paginaGenerarContrato.tablaVentas.getColumnModel()
                    .getColumn(3)
                    .setCellRenderer(new AccionesContratoProveedor());

            paginaGenerarContrato.tablaVentas.getColumnModel()
                    .getColumn(3)
                    .setCellEditor(new AccionesContratoProveedor());
            paginaGenerarContrato.tablaVentas.setRowHeight(35);
        } else {
            System.out.println("La columna 'Acciones' no está disponible (total columnas: " + totalCols + ")");
        }
    }

    public static void main(String[] args) {
        PaginaContratoProveeControlador p = new PaginaContratoProveeControlador(new PaginaGenerarContrato());
    }
}