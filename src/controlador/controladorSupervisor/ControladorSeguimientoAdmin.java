package controladorSupervisor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

import modelo.crudSeguimientoAdministrador.SeguimientoAdmin;
import modelo.crudSeguimientoAdministrador.SeguimientoAdminDao;
import vista.vistaSupervisor.SeguimientoAdministrador;

public class ControladorSeguimientoAdmin {
    private SeguimientoAdministrador view;
    private SeguimientoAdminDao dao;

    public ControladorSeguimientoAdmin(SeguimientoAdministrador view) {
        this.view = view;
        this.dao = new SeguimientoAdminDao();

        this.view.getTablaSA().setDefaultEditor(Object.class, null);
        initController();
    }

    private void initController() {
        view.getBtnBR().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarReporte();
            }
        });
        // carga inicial
        cargarReporte();
    }

    private void cargarReporte() {
        int año = view.getCBAño().getItemAt(view.getCBAño().getSelectedIndex());
        int mesIndex = view.getCBMes().getSelectedIndex() + 1; // meses 1-12

        List<SeguimientoAdmin> datos = dao.listarPorMesAno(mesIndex, año, null);

        // mapear por dia
        Map<Integer, SeguimientoAdmin> mapa = new HashMap<>();
        for (SeguimientoAdmin sd : datos) {
            mapa.put(sd.getDia(), sd);
        }

        DefaultTableModel model = (DefaultTableModel) view.getTablaSA().getModel();
        // limpiar filas
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        model.addRow(new Object[5]);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, año);
        cal.set(Calendar.MONTH, mesIndex - 1);
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int d = 1; d <= daysInMonth; d++) {
            if (mapa.containsKey(d)) {
                SeguimientoAdmin sd = mapa.get(d);
                model.addRow(new Object[] { d, sd.getConexiones(), sd.getDuracion(), sd.getHoraIngreso(),
                        sd.getHoraSalida() });
            } else {
                model.addRow(new Object[] { d, 0, "00:00:00", "", "" });
            }
        }
    }
}
