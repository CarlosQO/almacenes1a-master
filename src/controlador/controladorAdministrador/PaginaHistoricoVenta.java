package controladorAdministrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import controladorAdministrador.PDF_Administrador.HistoricoVentaPDF;
import modelo.crudHistoricoVentas.HistoricoVentaDao;
import vista.vistaAdministrador.PaginaHistoricoVentas;

public class PaginaHistoricoVenta implements ActionListener {

    private PaginaHistoricoVentas pHistoricoVentas;
    private HistoricoVentaDao histoDao = new HistoricoVentaDao();

    public PaginaHistoricoVenta(PaginaHistoricoVentas pHistoricoVentas) {
        this.pHistoricoVentas = pHistoricoVentas;
        pHistoricoVentas.setVisible(true);
        pHistoricoVentas.containFechas.generar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pHistoricoVentas.containFechas.generar) {
            String fechaInicio = pHistoricoVentas.containFechas.getFechaInicio();
            String fechaFin = pHistoricoVentas.containFechas.getFechaFin();
            if (validarFechas(fechaInicio, fechaFin)) {
                pHistoricoVentas.ConfigurarTamaño(1);
                cargarHistoricoVenta(fechaInicio, fechaFin);
            } else {
                pHistoricoVentas.fecha.setText("");
                if (pHistoricoVentas.containInfo.isVisible()) {
                    pHistoricoVentas.ConfigurarTamaño(2);
                    pHistoricoVentas.containInfo.setVisible(false);
                    return;
                }
            }
        }
        if (e.getSource() == pHistoricoVentas.containFechas.descargar) {
            HistoricoVentaPDF historicoVentaPDF = new HistoricoVentaPDF();
            // Generar PDF con toda la información del informe
            historicoVentaPDF.generalHistoricoVentasPDF(
                    pHistoricoVentas.containFechas.getFechaInicio(), // Fecha inicio
                    pHistoricoVentas.containFechas.getFechaFin(),
                    HistoVenta);
            JOptionPane.showMessageDialog(null,
                    " Reporte PDF generado correctamente.\nSe guardó en la carpeta Descargas.");
        }
    }

    private List<Object[]> HistoVenta = new ArrayList<>();

    private void cargarHistoricoVenta(String fechaInicio, String fechaFin) {
        DefaultTableModel modelo = (DefaultTableModel) pHistoricoVentas.tablaVentas.getModel();
        modelo.setRowCount(0); // reiniciar la tabla
        HistoVenta = histoDao.lista(fechaInicio, fechaFin);
        if (HistoVenta.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay historial de venta para esa fecha");
            if (pHistoricoVentas.containInfo.isVisible()) {
                pHistoricoVentas.ConfigurarTamaño(2);
                pHistoricoVentas.containInfo.setVisible(false);
            }
            return;
        }
        pHistoricoVentas.containFechas.agregarBtnDescargarInforme("Descargar Historico");
        pHistoricoVentas.containFechas.revalidate();
        pHistoricoVentas.containFechas.repaint();
        pHistoricoVentas.containFechas.descargar.addActionListener(this);
        pHistoricoVentas.fecha.setText("Fecha Inicio: " + fechaInicio + "    Fecha fin: " + fechaFin);
        pHistoricoVentas.containInfo.setVisible(true);
        for (Object[] fila : HistoVenta) {
            System.out.println(fila[0] + " " + fila[1] + " " + fila[2] + " " + fila[3] + " " + fila[4] + " " + fila[5]
                    + " " + fila[6] + " " + fila[7] + " " + fila[8] + " " + fila[9]);
            Object[] datos = {
                    fila[0], // ID del producto
                    fila[1], // nombre del producto
                    fila[3], // cantidad de venta
                    fila[6], // valor unitario
                    fila[9], // valor total
                    fila[8], // valor total
                    fila[10], // valor total
                    fila[11], // valor total
                    fila[12], // valor total
                    fila[13] // valor total
            };
            modelo.addRow(datos);
        }
    }

    public boolean validarFechas(String fechaInicioStr, String fechaFinStr) {
        // Validar que se haya llenado la fecha de inicio
        if (fechaInicioStr == null || fechaInicioStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe llenar el campo de la fecha de inicio");
            return false;
        }

        // Validar que se haya llenado la fecha de fin
        if (fechaFinStr == null || fechaFinStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe llenar el campo de la fecha fin");
            return false;
        }

        try {
            // Formato esperado de las fechas (por ejemplo: 2025-10-15)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date fechaInicio = sdf.parse(fechaInicioStr);
            Date fechaFin = sdf.parse(fechaFinStr);
            Date hoy = new Date(); // fecha actual

            // Validar que ninguna de las fechas sea posterior al día actual
            if (fechaInicio.after(hoy)) {
                JOptionPane.showMessageDialog(null, "La fecha de inicio no puede ser superior al día de hoy");
                return false;
            }
            if (fechaFin.after(hoy)) {
                JOptionPane.showMessageDialog(null, "La fecha fin no puede ser superior al día de hoy");
                return false;
            }

            // Validar que la fecha fin no sea anterior a la fecha inicio
            if (fechaFin.before(fechaInicio)) {
                JOptionPane.showMessageDialog(null, "La fecha fin no puede ser anterior a la fecha de inicio");
                return false;
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Use el formato yyyy-MM-dd");
            return false;
        }

        return true;
    }
}
