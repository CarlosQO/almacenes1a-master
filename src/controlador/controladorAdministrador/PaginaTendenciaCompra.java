package controladorAdministrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import controladorAdministrador.PDF_Administrador.TendenciaCompraPDF;
import modelo.crudTendenciaCompra.TendenciaCompraDao;
import vista.vistaAdministrador.PaginaHistoricoTendenciaCompra;

public class PaginaTendenciaCompra implements ActionListener {
    private PaginaHistoricoTendenciaCompra pTendencia;
    private TendenciaCompraDao tendenciaDao = new TendenciaCompraDao();

    public PaginaTendenciaCompra(PaginaHistoricoTendenciaCompra pTendencia) {
        this.pTendencia = pTendencia;
        pTendencia.containFechas.generar.addActionListener(this);
        pTendencia.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pTendencia.containFechas.generar) {
            String mes = pTendencia.containFechas.getMesSeleccionado();
            String año = pTendencia.containFechas.getAñoSeleccionado();
            if (!validarFechas(mes, año)) {
                System.out.println("Por favor seleccione un mes y un año");
                pTendencia.modificarTamaño(1);
                if (pTendencia.containInfo.isVisible()) {
                    pTendencia.containInfo.setVisible(false);
                }
                return;
            }
            listaTendenciaCompra = tendenciaDao.listarTendenciaCompra(Integer.parseInt(año),
                    pTendencia.containFechas.obtenerNumeroMes());
            if (listaTendenciaCompra.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "No hay resultados para esa mes y año, por favor seleccione otro mes", "",
                        JOptionPane.INFORMATION_MESSAGE);
                pTendencia.modificarTamaño(1);
                if (pTendencia.containInfo.isVisible()) {
                    pTendencia.containInfo.setVisible(false);
                }
                return;
            }
            cargarTendenciaCompra();
            pTendencia.modificarTamaño(2);
            pTendencia.containFechas.agregarBtnDescargarInforme("Descargar Histórico");
            pTendencia.containFechas.revalidate();
            pTendencia.containFechas.repaint();
            pTendencia.containFechas.descargar.addActionListener(this);
            pTendencia.containInfo.setVisible(true);
        }
        if (e.getSource() == pTendencia.containFechas.descargar) {
            TendenciaCompraPDF tendenciaCompraPDF = new TendenciaCompraPDF();
            tendenciaCompraPDF.generalHistoricoTendenciaCompraPDF(pTendencia.containFechas.getMesSeleccionado(),
                    pTendencia.containFechas.getAñoSeleccionado(), listaTendenciaCompra);

            JOptionPane.showMessageDialog(null,
                    " Reporte PDF generado correctamente.\nSe guardó en la carpeta Descargas.");
        }
    }

    private List<Object[]> listaTendenciaCompra = new ArrayList<>();

    private boolean validarFechas(String mes, String año) {
        if (mes.isEmpty() || año.isEmpty())
            return false;
        return true;
    }

    private void cargarTendenciaCompra() {
        DefaultTableModel modelo = (DefaultTableModel) pTendencia.tablaTendencia.getModel();
        modelo.setRowCount(0); // reiniciar la tabla
        for (Object[] fila : listaTendenciaCompra) {
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
}
