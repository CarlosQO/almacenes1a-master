package controladorAdministrador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import modelo.crudProducto.Producto;
import modelo.crudProducto.ProductoDao;
import vista.componentes.TarjetaFiltro;

public class PaginaProducMasVendidos implements ActionListener {

    public vista.vistaAdministrador.PaginaProductoMasVendidos paginaMasVendidos;
    public ProductoDao proDao = new ProductoDao();

    public PaginaProducMasVendidos(vista.vistaAdministrador.PaginaProductoMasVendidos paMasVendi) {
        this.paginaMasVendidos = paMasVendi;

        // Configurar ventana
        paginaMasVendidos.setSize(1300, 700);
        paginaMasVendidos.setLocationRelativeTo(null);
        paginaMasVendidos.setResizable(false);
        paginaMasVendidos.setVisible(true);

        // Registrar listener en el botón de generar reporte
        if (paginaMasVendidos.containFechas != null) {
            paginaMasVendidos.containFechas.getGenerarReporte().addActionListener(this);
        }
    }

    private List<Producto> prodMasVendi = new ArrayList<>();

    @Override
    public void actionPerformed(ActionEvent e) {
        // Verificar que el evento venga del botón
        if (e.getSource() == paginaMasVendidos.containFechas.getGenerarReporte()) {

            String fechaInicio = paginaMasVendidos.containFechas.getFechaInicio();
            String fechaFin = paginaMasVendidos.containFechas.getFechaFin();

            if (!validarFechas(fechaInicio, fechaFin)) {
                return;
            }

            prodMasVendi = proDao.listarProductoMasVendidosPorFecha(fechaInicio, fechaFin);
            if (prodMasVendi.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay productos vendidos en esa fechas");
            } else {
                // JOptionPane.showMessageDialog(null, "si hay productos");
                paginaMasVendidos.containMayorTarjeta.setVisible(true);

                paginaMasVendidos.containTarjetas.removeAll();
                paginaMasVendidos.containTarjetas.repaint();
                paginaMasVendidos.containTarjetas.revalidate();
                // Carga los productos en un hilo aparte
                new SwingWorker<Void, TarjetaFiltro>() {
                    @Override
                    protected Void doInBackground() throws Exception {

                        int ejeX = 10, ejeY = 10;
                        int ranking = 1;
                        for (Producto p : prodMasVendi) {
                            String ranString = Integer.toString(ranking);
                            // Crear tarjeta con imagen + datos
                            TarjetaFiltro tarjeta = new TarjetaFiltro(
                                    20,
                                    0xDCD6D6,
                                    p.getImagen(),
                                    p.getNombre(),
                                    p.getTalla(),
                                    Integer.toString(p.getCantidad()),
                                    ranString);

                            tarjeta.setLayout(null);
                            tarjeta.setShadowSize(1);
                            tarjeta.setBackground(Color.white);
                            tarjeta.setBounds(ejeX, ejeY, 180, 240);
                            tarjeta.add(tarjeta.containDetalle);
                            tarjeta.setProducto(p);
                            ranking++;
                            ejeX += 185;
                            if (ejeX > 925) {
                                ejeX = 10;
                                ejeY += 245;
                            }
                            publish(tarjeta);
                        }
                        return null;
                    }

                    @Override
                    protected void process(List<TarjetaFiltro> tarjetas) {
                        for (TarjetaFiltro t : tarjetas) {
                            paginaMasVendidos.containTarjetas.add(t);
                        }
                        paginaMasVendidos.revalidate();
                        paginaMasVendidos.repaint();
                    }

                }.execute();
                paginaMasVendidos.repaint();
                paginaMasVendidos.containTarjetas.repaint();
                paginaMasVendidos.containTarjetas.revalidate();
            }

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
            sdf.setLenient(false); // evita aceptar fechas inválidas como 2025-02-30

            // Parsear las fechas
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
