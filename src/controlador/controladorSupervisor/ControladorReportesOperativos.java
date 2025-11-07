package controlador.controladorSupervisor;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.swing.table.DefaultTableModel;

import modelo.crudReportesOperativos.ReportesDao;
import modelo.crudReportesOperativos.ReportesOpe;
import vista.vistaSupervisor.ReportesOperativos;

public class ControladorReportesOperativos {
    private ReportesOperativos vista;
    private ReportesDao reportesDao;
    private DefaultTableModel modelo;

    public ControladorReportesOperativos(ReportesOperativos vista) {
        this.vista = vista;
        this.reportesDao = new ReportesDao();
        this.modelo = (DefaultTableModel) vista.getTablaRO().getModel();
        iniciar();
    }

    private void iniciar() {
        // Configurar el action listener para el botón de búsqueda
        vista.getBtnBR().addActionListener(e -> cargarTabla());

        // Cargar datos iniciales
        cargarTabla();
    }

    private void cargarTabla() {
        // Limpiar tabla
        modelo.setRowCount(0);

        // Obtener mes y año seleccionados en la vista
        Integer añoSeleccionado = (Integer) vista.getCBAño().getSelectedItem();
        int mesSeleccionado = vista.getCBMes().getSelectedIndex(); // 0-based (enero=0)

        // Formateador de moneda (opcional)
        NumberFormat formatoMoneda = NumberFormat
                .getCurrencyInstance(new Locale.Builder().setLanguage("es").setRegion("CO").build());

        // Obtener datos y llenar tabla aplicando filtro por mes/año
        List<ReportesOpe> lista = reportesDao.listar();
        Calendar cal = Calendar.getInstance();
        for (ReportesOpe reporte : lista) {
            if (reporte.getFecha() == null)
                continue;
            cal.setTime(reporte.getFecha());
            int añoRow = cal.get(Calendar.YEAR);
            int mesRow = cal.get(Calendar.MONTH);

            if (añoRow == (añoSeleccionado != null ? añoSeleccionado : añoRow) && mesRow == mesSeleccionado) {
                Object[] fila = new Object[] {
                        "", // ID column (hidden)
                        reporte.getConcepto(),
                        formatoMoneda.format(reporte.getMonto()),
                        reporte.getFecha(),
                        reporte.getMedioPago()
                };
                modelo.addRow(fila);
            }
        }
    }
}