package controladorSupervisor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import modelo.crudSeguimientoVendedor.CrudSeguimientoVendedor;
import modelo.crudSeguimientoVendedor.SeguimientoVen;
import modelo.crudSeguimientoVendedor.SeguimientoVenDao;
import vista.vistaSupervisor.SeguimientoVendedor;

public class ControladorSeguimientoVendedor {
    private SeguimientoVendedor view;
    private CrudSeguimientoVendedor dao;

    public ControladorSeguimientoVendedor(SeguimientoVendedor view) {
        this.view = view;
        this.dao = new SeguimientoVenDao();
        initController();
    }

    private void initController() {
        view.getBtnBR().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargar();
            }
        });
        // carga inicial
        cargar();
    }

    private void cargar() {
        String documento = view.getTxtDocumento().getText().trim();
        if (documento.isEmpty()) {
            documento = null;
        }

        int año = view.getCBAño().getItemAt(view.getCBAño().getSelectedIndex());
        int mes = view.getCBMes().getSelectedIndex() + 1;

        List<SeguimientoVen> lista = dao.listarPorDocumentoAnoMes(documento, año, mes);

        DefaultTableModel model = (DefaultTableModel) view.getTablaSV().getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        model.addRow(new Object[6]);

        for (SeguimientoVen sv : lista) {
            model.addRow(new Object[] { sv.getNombreVendedor(), sv.getDocumento(), sv.getPedidosDespachados(),
                    sv.getPedidosCancelados(), sv.getPedidosEntregados(), sv.getPedidosNoEntregados() });
        }
    }
}
