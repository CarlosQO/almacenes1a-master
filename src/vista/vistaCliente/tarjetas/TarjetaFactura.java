package vista.vistaCliente.tarjetas;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import vista.componentes.RoundedButton;
import vista.componentes.RoundedPanel;

public class TarjetaFactura {
    private String fecha;
    private int tamañoPanelProductos;
    private double totalCompra;

    private RoundedPanel panelTarjetaFactura;
    private JLabel lblFecha, lblTotal, lblnombreProducto, lblCantidad, lblPrecio, lblSubtotal;
    private JPanel panelProductos;
    private RoundedButton btnDescargarFactura;

    public TarjetaFactura(String fecha, int tamañoPanelProductos, double totalCompra) {

        // Panel principal
        panelTarjetaFactura = new RoundedPanel(20, 0xFFFFFF);
        panelTarjetaFactura.setShadowSize(1);
        panelTarjetaFactura.setLayout(null);
        panelTarjetaFactura.setBackground(Color.WHITE);
        panelTarjetaFactura.setBorder(null);
        panelTarjetaFactura.setPreferredSize(new Dimension(550, tamañoPanelProductos + 120));

        // Fecha
        lblFecha = new JLabel("Fecha: " + fecha);
        lblFecha.setFont(new Font("Arial", Font.BOLD, 14));
        lblFecha.setBounds(20, 10, 300, 25);
        panelTarjetaFactura.add(lblFecha);

        // Panel de encabezados
        JPanel panelEncabezados = new JPanel();
        panelEncabezados.setLayout(null);
        panelEncabezados.setBackground(new Color(180, 230, 255, 133));
        panelEncabezados.setBounds(10, 45, 530, 30);
        panelTarjetaFactura.add(panelEncabezados);

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
        panelTarjetaFactura.add(panelProductos);

        JPanel panelLinea = new JPanel();
        panelLinea.setLayout(null);
        panelLinea.setBackground(Color.BLACK);
        panelLinea.setBounds(panelProductos.getX(),
                panelProductos.getY() + panelProductos.getHeight() + 5,
                panelProductos.getWidth() + 40,
                1);
        panelTarjetaFactura.add(panelLinea);

        // Total
        lblTotal = new JLabel("Total: $" + String.format("%.2f", totalCompra));
        lblTotal.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotal.setBounds(10, tamañoPanelProductos + 100, 200, 30);
        panelTarjetaFactura.add(lblTotal);

        // Botón descargar
        btnDescargarFactura = new RoundedButton("Descargar Factura", new Color(180, 230, 255, 133));
        btnDescargarFactura.setBounds(350, tamañoPanelProductos + 100, 180, 30);
        panelTarjetaFactura.add(btnDescargarFactura);
    }

    public JButton getBtnDescargarFactura() {
        return btnDescargarFactura;
    }

    public JPanel getPanelProductos() {
        return panelProductos;
    }

    public JPanel getTarjetafactura() {
        return panelTarjetaFactura;
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

    public static void main(String[] args) {

        // Datos de ejemplo
        String fecha = "2025-10-13";
        int tamañoPanelProductos = 90; // Ej: 3 productos * 30px cada uno
        double totalCompra = 150.00;

        // Crear tarjeta factura
        TarjetaFactura factura = new TarjetaFactura(fecha, tamañoPanelProductos, totalCompra);

        // EJEMPLO de productos (nombre, precio unitario, cantidad, subtotal)
        String[] nombres = { "Zapatos", "Camisa", "Pantalón" };
        double[] precios = { 50.00, 30.00, 20.00 };
        int[] cantidades = { 1, 2, 3 };

        int y = 0;
        for (int i = 0; i < nombres.length; i++) {
            JLabel lblNombre = new JLabel(nombres[i]);
            lblNombre.setBounds(10, y, 150, 25);
            factura.getPanelProductos().add(lblNombre);

            JLabel lblPrecio = new JLabel("$" + precios[i]);
            lblPrecio.setBounds(190, y, 80, 25);
            factura.getPanelProductos().add(lblPrecio);

            JLabel lblCantidad = new JLabel("x" + cantidades[i]);
            lblCantidad.setBounds(290, y, 50, 25);
            factura.getPanelProductos().add(lblCantidad);

            double subtotal = precios[i] * cantidades[i];
            JLabel lblSubtotal = new JLabel("$" + subtotal);
            lblSubtotal.setBounds(400, y, 100, 25);
            factura.getPanelProductos().add(lblSubtotal);

            y += 30; // espacio entre filas
        }

        // Crear ventana
        JFrame frame = new JFrame("Factura");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, tamañoPanelProductos + 550);
        frame.setLayout(null);

        // Agregar la tarjeta al frame
        factura.getTarjetafactura().setBounds(20, 20, 550, tamañoPanelProductos + 150);
        frame.add(factura.getTarjetafactura());

        // Mostrar ventana
        frame.setVisible(true);

        // Acción ejemplo para el botón
        factura.getBtnDescargarFactura().addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Factura descargada correctamente!");
        });
    }
}
