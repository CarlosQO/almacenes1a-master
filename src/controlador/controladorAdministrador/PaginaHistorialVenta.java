package controladorAdministrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import controladorAdministrador.PDF_Administrador.ReporteHistorialVentasPDF;
import modelo.crudHistorialVenta.HistorialVentaDao;

public class PaginaHistorialVenta implements ActionListener {

    public vista.vistaAdministrador.PaginaHistorialVenta paginaVista;
    private HistorialVentaDao histoDao = new HistorialVentaDao();
    // public
    // controlador.controladorAdministrador.PDF_Administrador.ReporteHistorialVentasPDF
    // ReporteVentasPDF;
    public ReporteHistorialVentasPDF ReporteVentasPDF;

    public PaginaHistorialVenta(vista.vistaAdministrador.PaginaHistorialVenta p) {
        this.paginaVista = p;
        paginaVista.setSize(1300, 700);
        paginaVista.setLocationRelativeTo(null);
        paginaVista.setResizable(false);
        paginaVista.setVisible(true);
        paginaVista.containFechas.generar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == paginaVista.containFechas.generar) {
            String fechaInicio = paginaVista.containFechas.getFechaInicio();
            String fechaFin = paginaVista.containFechas.getFechaFin();
            if (validarFechas(fechaInicio, fechaFin)) {
                cargarHistorialVentas(fechaInicio, fechaFin);
            } else {
                paginaVista.fecha.setText("");
                paginaVista.modificarTamañoFecha(205);
                if (paginaVista.containInfo.isVisible()) {
                    paginaVista.containInfo.setVisible(false);
                    return;
                }
            }
        }
        if (e.getSource() == paginaVista.containFechas.descargar) {
            ReporteVentasPDF = new ReporteHistorialVentasPDF();
            // Generar PDF con toda la información del informe
            ReporteVentasPDF.generalHistorialVentasPDF(
                    paginaVista.containFechas.getFechaInicio(), // Fecha inicio
                    paginaVista.containFechas.getFechaFin(),
                    lista);

            JOptionPane.showMessageDialog(null,
                    " Reporte PDF generado correctamente.\nSe guardó en la carpeta Descargas.");
        }
    }

    private List<Object[]> lista = new ArrayList<>();

    private void cargarHistorialVentas(String fechaInicio, String fechaFin) {
        DefaultTableModel modelo = (DefaultTableModel) paginaVista.tablaVentas.getModel();
        modelo.setRowCount(0); // reiniciar la tabla
        lista = histoDao.lista(fechaInicio, fechaFin);
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay historial de venta para esa fecha");
            if (paginaVista.containInfo.isVisible()) {
                paginaVista.containInfo.setVisible(false);
                paginaVista.modificarTamañoFecha(205);
            }
            return;
        }
        paginaVista.containFechas.agregarBtnDescargarInforme("Descargar Informe");
        paginaVista.modificarTamañoFecha(260);
        paginaVista.containFechas.revalidate();
        paginaVista.containFechas.repaint();
        paginaVista.containFechas.descargar.addActionListener(this);
        paginaVista.fecha.setText("Fecha Inicio: " + fechaInicio + "    Fecha fin: " + fechaFin);
        paginaVista.containInfo.setVisible(true);
        for (Object[] fila : lista) {
            Object[] datos = {
                    fila[0], // ID del producto
                    fila[1], // nombre del producto
                    fila[2], // cantidad de venta
                    fila[3], // valor unitario
                    fila[4] // valor total
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
