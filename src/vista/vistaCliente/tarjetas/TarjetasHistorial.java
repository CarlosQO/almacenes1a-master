package vista.vistaCliente.tarjetas;

import javax.swing.*;
import java.awt.*;
import vista.componentes.RoundedPanel;

public class TarjetasHistorial {
    private String fecha, estado;
    private int tamañoPanelProductos;
    private double totalCompra;

    private RoundedPanel panelHistorial;
    private JPanel panelProductos;
    private JLabel lblFecha, lblTotal, lblEstado, lblnombreProducto, lblCantidad, lblPrecio, lblSubtotal;

    public TarjetasHistorial(String fecha, int tamañoPanelProductos, double totalCompra, String estado) {

        // Panel principal
        panelHistorial = new RoundedPanel(20, 0xFFFFFF);
        panelHistorial.setShadowSize(1);
        panelHistorial.setLayout(null);
        panelHistorial.setBackground(Color.WHITE);
        panelHistorial.setBorder(null);
        panelHistorial.setPreferredSize(new Dimension(550, tamañoPanelProductos + 120));

        // Fecha
        lblFecha = new JLabel("Fecha: " + fecha);
        lblFecha.setFont(new Font("Arial", Font.BOLD, 14));
        lblFecha.setBounds(20, 10, 300, 25);
        panelHistorial.add(lblFecha);

        // info
        // Panel de encabezados (títulos de columnas)
        JPanel panelEncabezados = new JPanel();
        panelEncabezados.setLayout(null);
        panelEncabezados.setBackground(new Color(180, 230, 255, 133)); // Azul tipo Windows 10
        panelEncabezados.setBounds(10, 45, 530, 30);
        panelHistorial.add(panelEncabezados);

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
        panelHistorial.add(panelProductos);

        JPanel panelLinea = new JPanel();
        panelLinea.setLayout(null);
        panelLinea.setBackground(Color.BLACK);
        panelLinea.setBounds(panelProductos.getX(),
                panelProductos.getY() + panelProductos.getHeight() + 5,
                panelProductos.getWidth() + 40,
                1);
        panelHistorial.add(panelLinea);

        // estado
        lblEstado = new JLabel("Estado: " + estado);
        lblEstado.setFont(new Font("Arial", Font.BOLD, 14));
        lblEstado.setBounds(10, tamañoPanelProductos + 85, 200, 30);
        panelHistorial.add(lblEstado);

        // Total
        lblTotal = new JLabel("Total: $" + String.format("%.2f", totalCompra));
        lblTotal.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotal.setBounds(200, tamañoPanelProductos + 85, 200, 30);
        panelHistorial.add(lblTotal);

    }

    public JPanel getPanelHistorial() {
        return panelHistorial;
    }

    public void setPanelHistorial(RoundedPanel panelHistorial) {
        this.panelHistorial = panelHistorial;
    }

    public JPanel getPanelProductos() {
        return panelProductos;
    }

    public void setPanelProductos(JPanel panelProductos) {
        this.panelProductos = panelProductos;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public static void main(String[] args) {

        // Datos de ejemplo
        String fecha = "2025-10-13";
        int tamañoPanelProductos = 90; // Ej: 3 productos * 30px cada uno
        double totalCompra = 150.00;

        // Crear tarjeta historial
        TarjetasHistorial historial = new TarjetasHistorial(fecha, tamañoPanelProductos, totalCompra, "disponible");

        // EJEMPLO de productos (nombre, precio unitario, cantidad, subtotal)
        String[] nombres = { "Zapatos", "Camisa", "Pantalón" };
        double[] precios = { 50.00, 30.00, 20.00 };
        int[] cantidades = { 1, 2, 3 };

        int y = 0;
        for (int i = 0; i < nombres.length; i++) {
            JLabel lblNombre = new JLabel(nombres[i]);
            lblNombre.setBounds(10, y, 150, 25);
            historial.getPanelProductos().add(lblNombre);

            JLabel lblPrecio = new JLabel("$" + precios[i]);
            lblPrecio.setBounds(170, y, 80, 25);
            historial.getPanelProductos().add(lblPrecio);

            JLabel lblCantidad = new JLabel("x" + cantidades[i]);
            lblCantidad.setBounds(260, y, 50, 25);
            historial.getPanelProductos().add(lblCantidad);

            double subtotal = precios[i] * cantidades[i];
            JLabel lblSubtotal = new JLabel("$" + subtotal);
            lblSubtotal.setBounds(320, y, 100, 25);
            historial.getPanelProductos().add(lblSubtotal);

            y += 30; // espacio entre filas
        }

        // Crear ventana
        JFrame frame = new JFrame("Historial de Compra");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, tamañoPanelProductos + 250);
        frame.setLayout(null);

        // Agregar la tarjeta al frame
        historial.getPanelHistorial().setBounds(20, 20, 550, tamañoPanelProductos + 120);
        frame.add(historial.getPanelHistorial());

        frame.setVisible(true);
    }
}
