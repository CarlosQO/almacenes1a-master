package vista.vistaCliente.tarjetas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import static vista.componentes.RoundedPanel.resizeImage;

public class TarjetasProductoCarrito extends JPanel {
    private JPanel miniTarjetaCuerpo;
    private String nombreProducto, imagenProducto;
    private int idProducto, cantidad;
    private double valorUnitario, subTotal;
    public JButton btnAumentarCantidad, btnDisminuirCantidad;

    public TarjetasProductoCarrito(int idProducto, String imagenProducto, String nombreProducto, int cantidad,
            double valorUnitario, double subTotal) throws IOException {
        this.idProducto = idProducto; // <- Aquí está el error
        this.imagenProducto = imagenProducto; // También falta asignar correctamente
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.valorUnitario = valorUnitario;
        this.subTotal = subTotal;
        this.setLayout(null);
        this.setBounds(0, 0, 280, 100);

        miniTarjetaCuerpo = new JPanel();
        miniTarjetaCuerpo.setLayout(null);
        miniTarjetaCuerpo.setBounds(0, 0, 280, 100);
        miniTarjetaCuerpo.setBackground(Color.WHITE);

        // Imagen
        BufferedImage imagenOriginal = ImageIO.read(new File(imagenProducto));
        ImageIcon imagen = resizeImage(imagenOriginal, 80, 80);
        JLabel lblImagen = new JLabel(imagen);
        lblImagen.setLayout(null);
        lblImagen.setBounds(10, 10, 78, 78);
        miniTarjetaCuerpo.add(lblImagen);

        // Detalel
        JLabel lblNombreProducto = new JLabel(nombreProducto);
        lblNombreProducto.setLayout(null);
        lblNombreProducto.setBounds(100, 8, 150, 20);
        lblNombreProducto.setFont(new Font("Times New Roman", Font.PLAIN, 14));

        JLabel lblCantidad = new JLabel("Cantidad: " + cantidad);
        lblCantidad.setLayout(null);
        lblCantidad.setBounds(100, 30, 70, 20);
        lblCantidad.setFont(new Font("Times New Roman", Font.PLAIN, 12));

        JLabel lblPrecioUnitario = new JLabel("Valor Und: " + valorUnitario);
        lblPrecioUnitario.setBounds(100, 50, 100, 10);
        lblPrecioUnitario.setFont(new Font("Times New Roman", Font.PLAIN, 12));

        JLabel lblSubtotal = new JLabel("Subtotal: " + subTotal);
        lblSubtotal.setBounds(100, 60, 100, 20);
        lblSubtotal.setFont(new Font("Times New Roman", Font.PLAIN, 12));

        // Botones: // Botón aumentar
        btnAumentarCantidad = new JButton("+");
        btnAumentarCantidad.setBackground(new Color(50, 205, 50));
        btnAumentarCantidad.setForeground(Color.WHITE); // Texto blanco
        btnAumentarCantidad.setLayout(null);
        btnAumentarCantidad.setBounds(240, 35, 22, 22);
        btnAumentarCantidad.setFocusPainted(false);
        btnAumentarCantidad.setBorderPainted(false);
        btnAumentarCantidad.setMargin(new Insets(2, 2, 0, 5));
        miniTarjetaCuerpo.add(btnAumentarCantidad);

        // Botón disminuir
        btnDisminuirCantidad = new JButton("-");
        btnDisminuirCantidad.setBackground(Color.RED);
        btnDisminuirCantidad.setForeground(Color.WHITE);
        btnDisminuirCantidad.setLayout(null);
        btnDisminuirCantidad.setBounds(240, 60, 22, 22);
        btnDisminuirCantidad.setFocusPainted(false);
        btnDisminuirCantidad.setMargin(new Insets(3, 4, 0, 5));
        btnDisminuirCantidad.setBorderPainted(false);
        miniTarjetaCuerpo.add(btnDisminuirCantidad);

        miniTarjetaCuerpo.add(lblNombreProducto);
        miniTarjetaCuerpo.add(lblCantidad);
        miniTarjetaCuerpo.add(lblPrecioUnitario);
        miniTarjetaCuerpo.add(lblSubtotal);

        this.add(miniTarjetaCuerpo);
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(String imagen) {
        this.imagenProducto = imagen;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    // Para el botón de aumentar
    public void setAumentarCantidadListener(ActionListener listener) {
        btnAumentarCantidad.addActionListener(listener);
    }

    // Para el botón de disminuir
    public void setDisminuirCantidadListener(ActionListener listener) {
        btnDisminuirCantidad.addActionListener(listener);
    }

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Prueba TarjetaProducto");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.yellow);
        frame.setSize(500, 500);
        JPanel contenedor = new JPanel();
        contenedor.setLayout(null);
        contenedor.setBounds(0, 0, 500, 500);
        contenedor.setBackground(Color.yellow);
        TarjetasProductoCarrito tc = new TarjetasProductoCarrito(1,
                "src/productos/CamisasFormalesHombre/camisa MangaLarga Blanca.jpg", "Producto 1", 22, 20.0, 40.0);
        frame.add(tc);
        frame.setVisible(true);

    }

}
