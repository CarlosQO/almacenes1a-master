package controladorCliente;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import modelo.crudPedidos.DaoPedido;
import modelo.crudPedidos.Pedido;
import modelo.crudPedidos.ProductoDetalle;
import vista.componentes.ScrollPersonalizado;
import vista.vistaCliente.PanelPrincipal;
import vista.vistaCliente.tarjetas.TarjetaSegumientoPedido;

public class ControladorSeguimiento implements ActionListener {
    private PanelPrincipal panelPrincipal;
    private String idUsuario;
    private DaoPedido daoPedido = new DaoPedido();
    private ScrollPersonalizado scrollSeguimiento;

    public ControladorSeguimiento(PanelPrincipal panelPrincipal, String idUsuario) {
        this.panelPrincipal = panelPrincipal;
        this.idUsuario = idUsuario;
        // Solo escuchamos el evento de seguimiento, pedidos lo maneja
        // CrontoladorManejarMenu
        panelPrincipal.seguimientoOpcion.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelPrincipal.seguimientoOpcion) {
            // Limpiar y preparar el panel antes de cargar
            panelPrincipal.panelMenu.setVisible(false);
            panelPrincipal.panelCentroContenido.setVisible(true);
            panelPrincipal.panelCentroContenido.removeAll();
            panelPrincipal.panelCentroContenido.revalidate();
            panelPrincipal.panelCentroContenido.repaint();

            // Cargar el contenido
            cargarPedidos();
        }
    }

    public void cargarPedidos() {
        // panelSeguimientoCentrado
        panelPrincipal.panelSeguimientoCentrado = new JPanel();
        panelPrincipal.panelSeguimientoCentrado.removeAll();
        panelPrincipal.panelSeguimientoCentrado.setBackground(new Color(180, 230, 255));
        panelPrincipal.panelSeguimientoCentrado.setLayout(null);

        int yTarjeta = 10, alturaExtra = 0;
        // Obtener pedidos
        List<Pedido> listaPedido = daoPedido.obtenerPedidos(idUsuario);
        if (listaPedido.size() > 0) {
            for (Pedido p : listaPedido) {
                List<ProductoDetalle> productos = daoPedido.obtenerProductoPedidos(p.getIdFactura());
                int tamañoPanelProductos = productos.size() * 30;
                TarjetaSegumientoPedido tarjeta = new TarjetaSegumientoPedido(p.getFecha(), tamañoPanelProductos,
                        p.getTotal());

                int y = 0;
                for (ProductoDetalle pd : productos) {
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

                tarjeta.getTarjetaSeguimientoPedido().setBounds(65, yTarjeta, 550, tamañoPanelProductos + 150);
                panelPrincipal.panelSeguimientoCentrado.add(tarjeta.getTarjetaSeguimientoPedido());
                yTarjeta += tamañoPanelProductos + 200;
                alturaExtra += y;

                tarjeta.btnEntregado.addActionListener(evenEntregado -> {
                    cambiarEstadoPedidoEntregado(p.getIdFactura());
                    limpiarPanelSeguimiento();
                    cargarPedidos();
                    JOptionPane.showMessageDialog(null, "Pedido Entregado");
                });

                tarjeta.btnNoEntregado.addActionListener(evenEntregado -> {
                    cambiarEstadoPedidoNoEntregado(p.getIdFactura());
                    limpiarPanelSeguimiento();
                    cargarPedidos();
                    JOptionPane.showMessageDialog(null, "Pedido No Entregado");
                });
            }
            yTarjeta += alturaExtra;
            int numeroTarjetas = listaPedido.size();
            panelPrincipal.panelSeguimientoCentrado.setPreferredSize(new Dimension(680, yTarjeta));
            panelPrincipal.panelSeguimientoCentrado.setBounds(0, 0, 680, yTarjeta);

            if (numeroTarjetas > 0 && numeroTarjetas > 4) {
                scrollSeguimiento = new ScrollPersonalizado(panelPrincipal.panelSeguimientoCentrado, "vertical", 680,700);
                scrollSeguimiento.setBounds(170, 0, 680, 700);
                panelPrincipal.panelCentroContenido.add(scrollSeguimiento);
            } else {
                // Mostrar sin scroll
                panelPrincipal.panelSeguimientoCentrado.setBounds(170, 0, 680, 700);
                panelPrincipal.panelCentroContenido.add(panelPrincipal.panelSeguimientoCentrado);
            }
        } else {
            JLabel mensaje = new JLabel("No tiene pedidos en estado Pendientes");
            mensaje.setBounds(160, 200, 500, 40);
            mensaje.setFont(new Font("Arial", Font.BOLD, 20));
            panelPrincipal.panelSeguimientoCentrado.setBounds(170, 0, 680, 700);
            panelPrincipal.panelSeguimientoCentrado.add(mensaje);
            panelPrincipal.panelCentroContenido.add(panelPrincipal.panelSeguimientoCentrado);
        }

        panelPrincipal.panelCentroContenido.revalidate();
        panelPrincipal.panelCentroContenido.repaint();
    }

    public void cambiarEstadoPedidoEntregado(int idFactura) {
        daoPedido.actualizaEstadoProductoEntregado(idFactura);
    }

    public void cambiarEstadoPedidoNoEntregado(int idFactura) {
        daoPedido.actualizaEstadoProductoNoEntregado(idFactura);
    }

    private void limpiarPanelSeguimiento() {
        try {
            // Eliminar el contenido del panel central (incluye scroll si existía)
            panelPrincipal.panelCentroContenido.removeAll();
            // Limpiar el panel específico donde están las tarjetas
            panelPrincipal.panelSeguimientoCentrado.removeAll();
            // Refrescar la interfaz
            panelPrincipal.panelSeguimientoCentrado.revalidate();
            panelPrincipal.panelSeguimientoCentrado.repaint();
            panelPrincipal.panelCentroContenido.revalidate();
            panelPrincipal.panelCentroContenido.repaint();
        } catch (Exception e) {
            System.out.println("Error al limpiar panel: " + e);
        }
    }
    
    public String getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
}
