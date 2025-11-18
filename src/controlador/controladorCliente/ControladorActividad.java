package controladorCliente;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import modelo.crudActividad.DaoHistoria;
import modelo.crudActividad.ProductoDetalleFactura;
import vista.componentes.ScrollPersonalizado;
import vista.vistaCliente.PanelPrincipal;
import vista.vistaCliente.convertirPDF.ReportePedidos;
import vista.vistaCliente.tarjetas.FiltroTarjeta;
import vista.vistaCliente.tarjetas.TarjetasHistorial;

public class ControladorActividad implements ActionListener {
    private PanelPrincipal panelPrincipal;
    private String idUsuario;
    private FiltroTarjeta panelFiltroActividad;
    private DaoHistoria daoHistoria = new DaoHistoria();
    private ScrollPersonalizado scroll;

    public ControladorActividad(PanelPrincipal panelPrincipal, String idUsuario) {
        this.panelPrincipal = panelPrincipal;
        this.idUsuario = idUsuario;
        panelPrincipal.actividad.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelPrincipal.actividad) {
            panelPrincipal.panelCentroContenido.setVisible(true);
            panelPrincipal.panelCentroContenido.setBackground(Color.WHITE);
            panelPrincipal.panelCentroContenido.removeAll();

            panelFiltroActividad = new FiltroTarjeta("Reportes de Actividad", "Generar Reporte");
            panelFiltroActividad.setBounds(150, 10, 680, 180);
            panelPrincipal.panelCentroContenido.add(panelFiltroActividad);
            panelPrincipal.panelCentroContenido.revalidate();
            panelPrincipal.panelCentroContenido.repaint();

            panelFiltroActividad.buscar.addActionListener(eventoBuscar -> {
                if (panelFiltroActividad.validarFechas()) {
                    String inicio = panelFiltroActividad.getFechaInicio();
                    String fin = panelFiltroActividad.getFechaFin();

                    if (getValidarLaExistenciaDePedidos(inicio, fin)) {
                        caragraBoton(inicio, fin);
                        cargarTarjetasPedidos(inicio, fin);
                    } else {
                        JOptionPane.showMessageDialog(null, "No tiene pedidos registrados en esas fechas");
                    }
                }
            });
        }
    }

    public boolean getValidarLaExistenciaDePedidos(String fechaInicio, String fechaFin) {
        return daoHistoria.existePedidoEntreFechas(idUsuario, fechaInicio, fechaFin);
    };

    public void cargarTarjetasPedidos(String fechaInicio, String fechaFin) {
        // Panel interno (donde van las tarjetas)
        panelPrincipal.panelActividadCentrado = new JPanel();
        panelPrincipal.panelActividadCentrado.removeAll();
        panelPrincipal.panelActividadCentrado.setOpaque(true);
        panelPrincipal.panelActividadCentrado.setBackground(new Color(180, 230, 255));
        panelPrincipal.panelActividadCentrado.setLayout(null);

        int yTarjeta = 10, alturaExtra = 0;
        // Obtener pedidos
        List<modelo.crudActividad.Pedido> listaPedido = daoHistoria.obtenerHistorialCompras(idUsuario, fechaInicio,fechaFin);

        for (modelo.crudActividad.Pedido p : listaPedido) {
            List<ProductoDetalleFactura> productos = daoHistoria.obtenerDetallesCompra(p.getIdFactura());
            int tamañoPanelProductos = productos.size() * 30;
            TarjetasHistorial tarjeta = new TarjetasHistorial(p.getFecha(), tamañoPanelProductos, p.getTotal(),
                    p.getEstado());

            int y = 0;
            for (ProductoDetalleFactura pd : productos) {
                JLabel lblNombre = new JLabel(pd.getNombreProducto());
                lblNombre.setBounds(10, y, 150, 25);
                tarjeta.getPanelProductos().add(lblNombre);

                JLabel lblPrecio = new JLabel("$" + pd.getPrecioUnitario());
                lblPrecio.setBounds(190, y, 80, 25);
                tarjeta.getPanelProductos().add(lblPrecio);

                JLabel lblCantidad = new JLabel("x" + pd.getCantidad());
                lblCantidad.setBounds(290, y, 50, 25);
                tarjeta.getPanelProductos().add(lblCantidad);

                JLabel lblSubtotal = new JLabel("$" + pd.getSubtotal());
                lblSubtotal.setBounds(400, y, 100, 25);
                tarjeta.getPanelProductos().add(lblSubtotal);
                y += 30;
            }
            tarjeta.getPanelHistorial().setBounds(65, yTarjeta, 550, tamañoPanelProductos + 120);
            panelPrincipal.panelActividadCentrado.add(tarjeta.getPanelHistorial());
            yTarjeta += tamañoPanelProductos + 140;
            alturaExtra += y;
        }
        yTarjeta += alturaExtra;

        int numeroTarjetas = listaPedido.size();
        if (numeroTarjetas > 0 && numeroTarjetas > 3) {
            panelPrincipal.panelActividadCentrado.setBounds(0, 0, 680, yTarjeta);
            panelPrincipal.panelActividadCentrado.setPreferredSize(new Dimension(680, yTarjeta));

            scroll = new ScrollPersonalizado(panelPrincipal.panelActividadCentrado, "vertical", 680, 700);
            scroll.setBounds(150, 250, 680, 700);
            panelPrincipal.panelCentroContenido.add(scroll);
        } else {
            // Mostrar sin scroll
            panelPrincipal.panelActividadCentrado.setBounds(150, 240, 680, 700);
            panelPrincipal.panelCentroContenido.add(panelPrincipal.panelActividadCentrado);
        }
        panelPrincipal.panelCentroContenido.revalidate();
        panelPrincipal.panelCentroContenido.repaint();
    }

    public void caragraBoton(String fechaInicio, String fechaFin) {
        // Mostrar botón
        panelPrincipal.btnGenerarReporteDeactividad.setVisible(true);
        panelPrincipal.btnGenerarReporteDeactividad.setBounds(650, 200, 150, 30);
        panelPrincipal.panelCentroContenido.add(panelPrincipal.btnGenerarReporteDeactividad);

        panelPrincipal.btnGenerarReporteDeactividad.addActionListener(eveGenerar -> {
            generarReporte(fechaInicio, fechaFin);
        });
    }

    public void generarReporte(String fechaInicio, String fechaFin) {
        List<modelo.crudActividad.Pedido> listaPedidos = daoHistoria.obtenerHistorialCompras(idUsuario, fechaInicio,fechaFin);

        // 0btener productos de cada pedido en un Map
        Map<Integer, List<ProductoDetalleFactura>> mapaProductos = new HashMap<>();
        for (modelo.crudActividad.Pedido p : listaPedidos) {
            List<ProductoDetalleFactura> detalles = daoHistoria.obtenerDetallesCompra(p.getIdFactura());
            mapaProductos.put(p.getIdPedido(), detalles);
        }
        ReportePedidos.generar(fechaInicio, fechaFin, listaPedidos, mapaProductos);
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getIdUsuario() {
        return idUsuario;
    }

}