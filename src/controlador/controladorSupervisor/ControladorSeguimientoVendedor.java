package controladorSupervisor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import modelo.crudSeguimientoVendedor.CrudSeguimientoVendedor;
import modelo.crudSeguimientoVendedor.SeguimientoVen;
import modelo.crudSeguimientoVendedor.SeguimientoVenDao;
import vista.componentes.Validaciones;
import vista.vistaSupervisor.SeguimientoVendedor;
import vista.vistaSupervisor.componentes.PlaceholderSupport;

public class ControladorSeguimientoVendedor implements ActionListener {
    private SeguimientoVendedor view;
    private CrudSeguimientoVendedor dao;

    public ControladorSeguimientoVendedor(SeguimientoVendedor view) {
        this.view = view;
        this.dao = new SeguimientoVenDao();
        view.getBtnBR().addActionListener(this);
        view.getTablaSV().setDefaultEditor(Object.class, null);
    }

    private void cargar() {
        String documento = view.getTxtDocumento().getText().trim();

        // Si el campo está con el placeholder, se considera vacío
        if (PlaceholderSupport.isPlaceholderVisible(view.getTxtDocumento(), "Ingrese el documento")) {
            documento = "";
        }

        // Validación solo si el campo tiene algo
        if (!documento.isEmpty() && !Validaciones.validarCedula(documento)) {
            JOptionPane.showMessageDialog(view,
                    "El número de documento no es válido. Debe ser numérico y tener entre 10 y 11 dígitos.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int año = view.getCBAño().getItemAt(view.getCBAño().getSelectedIndex());
        int mes = view.getCBMes().getSelectedIndex() + 1;

        // Obtener los datos según los filtros
        List<SeguimientoVen> lista = dao.listarPorDocumentoAnoMes(
                documento.isEmpty() ? null : documento, año, mes);

        DefaultTableModel model = (DefaultTableModel) view.getTablaSV().getModel();
        model.setRowCount(0); // limpia la tabla
        model.addRow(new Object[6]);

        if (lista == null || lista.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                    "No se encontraron registros para los filtros seleccionados.",
                    "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Agregar los registros al JTable
        for (SeguimientoVen sv : lista) {
            model.addRow(new Object[] {
                    sv.getNombreVendedor(),
                    sv.getDocumento(),
                    sv.getPedidosDespachados(),
                    sv.getPedidosCancelados(),
                    sv.getPedidosEntregados(),
                    sv.getPedidosNoEntregados()
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == view.getBtnBR()) {
            cargar();
        }
    }
}
