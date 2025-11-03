package controladorSupervisor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import modelo.crudPQRS.DaoPQRS;
import modelo.crudPQRS.Pqrs;
import vista.vistaSupervisor.VistaPQRS;

public class ControladorPQRS {

    private VistaPQRS vPqrs = new VistaPQRS();
    private DaoPQRS pqrsDao = new DaoPQRS();
    private DefaultTableModel camposPQRS = new DefaultTableModel();

    public ControladorPQRS(VistaPQRS vPqrs) {
        this.vPqrs = vPqrs;
        getListarProductos(vPqrs.getTabla());
        vPqrs.getTabla().setDefaultEditor(Object.class, null);
        vPqrs.getTabla().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                logicaTabla(me);
            }
        });
    }

    public ControladorPQRS() {

    }

    public void getListarProductos(JTable tabla) {
        camposPQRS = (DefaultTableModel) tabla.getModel();
        List<Pqrs> lista = pqrsDao.listar();
        Object[] object = new Object[4];

        for (int indice = 0; indice < lista.size(); indice++) {
            object[0] = lista.get(indice).getIdPqrs();
            object[1] = lista.get(indice).getNomUsuarioRemi() + " " + lista.get(indice).getApeUsuarioRemi();
            object[2] = lista.get(indice).getAsunto();
            object[3] = lista.get(indice).getFechaEnvio();
            camposPQRS.addRow(object);
        }
        vPqrs.getTabla().setModel(camposPQRS);
    }

    private void logicaTabla(MouseEvent me) {
        if (me.getClickCount() == 2) {
            int fila = vPqrs.getTabla().getSelectedRow();

            if (vPqrs.getTabla().getValueAt(fila, 0) != null) {
                int id = Integer.parseInt(vPqrs.getTabla().getValueAt(fila, 0).toString());
                List<Pqrs> consuPQRS = pqrsDao.listarPorID(id);
                JOptionPane.showMessageDialog(vPqrs, consuPQRS.get(0).getCuerpo(), consuPQRS.get(0).getAsunto(),
                        JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

}
