package vista.vistaCliente.tarjetas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import vista.componentes.RoundedButton;
import vista.componentes.RoundedPanel;

public class TarjetaSegumientoPedido {
    private String fecha;
    private int tamañoPanelProductos;
    private double totalCompra;

    private RoundedPanel panelTarjetaSeguimientoPedidos;
    private JPanel panelProductos;
    private JLabel lblFecha, lblTotal, lblnombreProducto, lblCantidad, lblPrecio, lblSubtotal;
    public RoundedButton btnEntregado, btnNoEntregado;

    public TarjetaSegumientoPedido(String fecha, int tamañoPanelProductos, double totalCompra) {
        // Panel principal
        panelTarjetaSeguimientoPedidos = new RoundedPanel(20, 0xFFFFFF);
        panelTarjetaSeguimientoPedidos.setShadowSize(1);
        panelTarjetaSeguimientoPedidos.setLayout(null);
        panelTarjetaSeguimientoPedidos.setBackground(Color.WHITE);
        panelTarjetaSeguimientoPedidos.setBorder(null);
        panelTarjetaSeguimientoPedidos.setPreferredSize(new Dimension(550, tamañoPanelProductos + 150));

        // Fecha
        lblFecha = new JLabel("Fecha: " + fecha);
        lblFecha.setFont(new Font("Arial", Font.BOLD, 14));
        lblFecha.setBounds(20, 10, 300, 25);
        panelTarjetaSeguimientoPedidos.add(lblFecha);

        // Panel de encabezados
        JPanel panelEncabezados = new JPanel();
        panelEncabezados.setLayout(null);
        panelEncabezados.setBackground(new Color(180, 230, 255, 133));
        panelEncabezados.setBounds(10, 45, 530, 30);
        panelTarjetaSeguimientoPedidos.add(panelEncabezados);

        // Labels de encabezado
        lblnombreProducto = new JLabel("Producto");
        lblnombreProducto.setForeground(Color.BLACK);
        lblnombreProducto.setFont(new Font("Arial", Font.BOLD, 12));
        lblnombreProducto.setBounds(10, 5, 150, 20);
        panelEncabezados.add(lblnombreProducto);

        lblPrecio = new JLabel("Precio");
        lblPrecio.setForeground(Color.BLACK);
        lblPrecio.setFont(new Font("Arial", Font.BOLD, 12));
        lblPrecio.setBounds(190, 5, 80, 20);
        panelEncabezados.add(lblPrecio);

        lblCantidad = new JLabel("Cantidad");
        lblCantidad.setForeground(Color.BLACK);
        lblCantidad.setFont(new Font("Arial", Font.BOLD, 12));
        lblCantidad.setBounds(290, 5, 80, 20);
        panelEncabezados.add(lblCantidad);

        lblSubtotal = new JLabel("Subtotal");
        lblSubtotal.setForeground(Color.BLACK);
        lblSubtotal.setFont(new Font("Arial", Font.BOLD, 12));
        lblSubtotal.setBounds(400, 5, 100, 20);
        panelEncabezados.add(lblSubtotal);

        // Panel productos
        panelProductos = new JPanel();
        panelProductos.setLayout(null);
        panelProductos.setBackground(Color.WHITE);
        panelProductos.setBounds(10, 75, 480, tamañoPanelProductos);
        panelTarjetaSeguimientoPedidos.add(panelProductos);

        JPanel panelLinea = new JPanel();
        panelLinea.setLayout(null);
        panelLinea.setBackground(Color.BLACK);
        panelLinea.setBounds(panelProductos.getX(),
                panelProductos.getY() + panelProductos.getHeight() + 5,
                panelProductos.getWidth() + 40,
                1);
        panelTarjetaSeguimientoPedidos.add(panelLinea);

        // Total
        lblTotal = new JLabel("Total: $" + String.format("%.2f", totalCompra));
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotal.setBounds(10, tamañoPanelProductos + 105, 200, 30);
        panelTarjetaSeguimientoPedidos.add(lblTotal);

        // Botón descargar
        btnEntregado = new RoundedButton("Entregado", new Color(76, 175, 80));
        btnEntregado.setBounds(240, tamañoPanelProductos + 105, 130, 30);
        panelTarjetaSeguimientoPedidos.add(btnEntregado);

        btnNoEntregado = new RoundedButton("No entregado", new Color(244, 67, 54));
        btnNoEntregado.setBounds(400, tamañoPanelProductos + 105, 130, 30);
        panelTarjetaSeguimientoPedidos.add(btnNoEntregado);

    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getTamañoPanelProductos() {
        return tamañoPanelProductos;
    }

    public void setTamañoPanelProductos(int tamañoPanelProductos) {
        this.tamañoPanelProductos = tamañoPanelProductos;
    }

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }

    public JPanel getPanelProductos() {
        return panelProductos;
    }

    public void setPanelProductos() {
        this.panelProductos = panelProductos;
    }

    public void setTarjetaSeguimientoPedido() {
        this.panelTarjetaSeguimientoPedidos = panelTarjetaSeguimientoPedidos;
    }

    public JPanel getTarjetaSeguimientoPedido() {
        return panelTarjetaSeguimientoPedidos;
    }

}