package controladorCliente;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import modelo.crudFactura.DaoFactura;
import modelo.crudFactura.ProductoDetalleFactura;
import modelo.crudPedidos.Pedido;
import vista.componentes.ScrollPersonalizado;
import vista.vistaCliente.PanelPrincipal;
import vista.vistaCliente.convertirPDF.FacturaPDF;
import vista.vistaCliente.tarjetas.FiltroTarjeta;
import vista.vistaCliente.tarjetas.TarjetaFactura;

public class ControladorHistorial implements ActionListener {
    private PanelPrincipal panelPrincipal;
    private static int idUsuario = 1002;
    private FiltroTarjeta panelFiltroHistorial;
    private DaoFactura daoFactura = new DaoFactura();
    private Pedido pedido;
    private ProductoDetalleFactura produto;
    private ScrollPersonalizado scrollHistorial;

    public ControladorHistorial(PanelPrincipal panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
        panelPrincipal.historialDeComprasOpcion.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelPrincipal.historialDeComprasOpcion) {
            panelPrincipal.panelMenu.setVisible(false);
            panelPrincipal.panelCentroContenido.setVisible(true);
            panelPrincipal.panelCentroContenido.setBackground(null);
            panelPrincipal.panelCentroContenido.removeAll();

            panelFiltroHistorial = new FiltroTarjeta("Historial de Compra", "Buscar");
            panelFiltroHistorial.setBounds(150, 10, 680, 180);
            panelPrincipal.panelCentroContenido.add(panelFiltroHistorial);
            panelPrincipal.panelCentroContenido.revalidate();
            panelPrincipal.panelCentroContenido.repaint();

            panelFiltroHistorial.buscar.addActionListener(eventoBuscar -> {
                if (panelFiltroHistorial.validarFechas()) {
                    String inicio = panelFiltroHistorial.getFechaInicio();
                    String fin = panelFiltroHistorial.getFechaFin();

                    if (getValidarLaExistenciaDePedidos(inicio, fin)) {
                        cargarTarjetasPedidos(inicio, fin);
                    } else {
                        JOptionPane.showMessageDialog(null, "No tiene pedidos registrados en esas fechas");
                    }
                }
            });
        }
    }

    public boolean getValidarLaExistenciaDePedidos(String fechaInicio, String fechaFin) {
        return daoFactura.existeFacturaEntreFechas(idUsuario, fechaInicio, fechaFin);
    };

    public void cargarTarjetasPedidos(String fechaInicio, String fechaFin) {
        // Panel interno (donde van las tarjeta
        panelPrincipal.panelHistorialCentrado = new JPanel();
        panelPrincipal.panelHistorialCentrado.removeAll();
        panelPrincipal.panelHistorialCentrado.setBackground(new Color(180, 230, 255, 133));
        panelPrincipal.panelHistorialCentrado.setLayout(null);

        int yTarjeta = 10, alturaExtra = 0;
        // Obtener pedidos
        List<modelo.crudFactura.Pedido> listaPedido = daoFactura.obtenerHistorialComprasFacturas(idUsuario, fechaInicio,
                fechaFin);

        for (modelo.crudFactura.Pedido p : listaPedido) {
            List<ProductoDetalleFactura> productos = daoFactura.obtenerDetallesCompra(p.getIdFactura());
            int tamañoPanelProductos = productos.size() * 30;
            TarjetaFactura tarjeta = new TarjetaFactura(p.getFecha(), tamañoPanelProductos, p.getTotal());

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
            tarjeta.getTarjetafactura().setBounds(65, yTarjeta, 550, tamañoPanelProductos + 150);
            panelPrincipal.panelHistorialCentrado.add(tarjeta.getTarjetafactura());

            tarjeta.getBtnDescargarFactura().addActionListener(eGenerarFactura -> {
                generarFactura(fechaInicio, fechaFin, p.getIdFactura(), productos, p.getTotal());
            });

            yTarjeta += tamañoPanelProductos + 200;
            alturaExtra += y;
        }
        yTarjeta += alturaExtra;

        int numeroTarjetas = listaPedido.size();
        if (numeroTarjetas > 0 && numeroTarjetas > 1) {
            panelPrincipal.panelHistorialCentrado.setBounds(0, 0, 680, yTarjeta);
            panelPrincipal.panelHistorialCentrado.setPreferredSize(new Dimension(680, yTarjeta));
            scrollHistorial = new ScrollPersonalizado(panelPrincipal.panelHistorialCentrado, "vertical", 680, 700);
            scrollHistorial.setBounds(150, 200, 680, 700);
            panelPrincipal.panelCentroContenido.add(scrollHistorial);
        } else {
            // Mostrar sin scroll
            panelPrincipal.panelHistorialCentrado.setBounds(150, 240, 680, 700);
            panelPrincipal.panelCentroContenido.add(panelPrincipal.panelHistorialCentrado);
        }
        panelPrincipal.panelCentroContenido.revalidate();
        panelPrincipal.panelCentroContenido.repaint();
    }

    public void generarFactura(String fechaInicio, String fechaFin, int idFactura,
            List<ProductoDetalleFactura> productosDeFactura, double totalFactura) {
        FacturaPDF.generar(fechaInicio, fechaFin, idFactura, productosDeFactura, totalFactura);
    }

    public static void main(String[] args) throws IOException {
        PanelPrincipal menu = new PanelPrincipal();
        menu.setVisible(true);
        menu.setSize(1300, 700);
        ControladorCatalogo c = new ControladorCatalogo(menu);
        ControladorActividad ca = new ControladorActividad(menu);
        ControladorHistorial ch = new ControladorHistorial(menu);
        ControladorSeguimiento cs = new ControladorSeguimiento(menu);
        ControladorPQRS cpqrs = new ControladorPQRS(menu);
        CrontoladorManejarMenu ccerrar = new CrontoladorManejarMenu(menu);
    }
}
