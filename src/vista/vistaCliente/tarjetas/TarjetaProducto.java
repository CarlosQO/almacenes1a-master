package vista.vistaCliente.tarjetas;

import static vista.componentes.RoundedPanel.*;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import vista.componentes.RoundedButton;
import vista.componentes.RoundedPanel;
import vista.componentes.ImagenRoundedPanel;

public class TarjetaProducto extends JPanel {
    private String imagen, nombreProducto, talla, descripcionProducto;
    private int identificadorTarjeta;
    private double precio;
    private RoundedPanel tarjetaCuerpo, detalle;
    private ImagenRoundedPanel containImagen;
    public RoundedButton compraInstanatnea, agregarAlCarrito;

    public TarjetaProducto(int identificadorTarjeta, String imagen, String nombreProducto, String talla,
            String descripcionProducto, double precio) throws IOException {
        // crear el cuerpo de la tarjeta
        this.imagen = imagen;
        this.nombreProducto = nombreProducto;
        this.talla = talla;
        this.descripcionProducto = descripcionProducto;
        this.identificadorTarjeta = identificadorTarjeta;
        this.precio = precio;

        tarjetaCuerpo = new RoundedPanel(20, 0xFFFFFF);
        tarjetaCuerpo.setShadowSize(1);
        tarjetaCuerpo.setBackground(Color.white);
        tarjetaCuerpo.setLayout(null);
        tarjetaCuerpo.setBounds(0, 0, 220, 330); // ocupa todo el panel padre

        // agregar al padre
        this.setLayout(null);
        this.setPreferredSize(new Dimension(220, 330));
        this.setSize(200, 295);
        setOpaque(false);

        BufferedImage originalCamisa = ImageIO.read(new File(imagen));
        Image imagenRedimensionada = originalCamisa.getScaledInstance(190, 160, Image.SCALE_SMOOTH);

        containImagen = new ImagenRoundedPanel(20, null, imagenRedimensionada);
        containImagen.setBounds(10, 10, 200, 170);
        tarjetaCuerpo.add(containImagen);

        // detalle
        // color de fondo informacion de cada producto
        Color colorFondoInformacion = new Color(180, 230, 255, 133);

        detalle = new RoundedPanel(20, 0xFFFFFF);
        detalle.setShadowSize(1);
        detalle.setBounds(10, 185, 200, 135);
        detalle.setBackground(colorFondoInformacion);
        detalle.setLayout(null); // Layout absoluto

        // Nombre del producto
        JLabel lblNombre = new JLabel(nombreProducto);
        lblNombre.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        lblNombre.setBounds(10, 10, 200, 15); // x, y, ancho, alto
        detalle.add(lblNombre);
        // Talla
        JLabel lblTalla = new JLabel("Talla: " + talla);
        lblTalla.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        lblTalla.setBounds(10, 30, 200, 15);
        detalle.add(lblTalla);
        // Descripción
        JTextArea lblDescripcion = new JTextArea("Descripcion: " + descripcionProducto);
        lblDescripcion.setFont(new Font("Times New Roman", Font.PLAIN, 13));
        lblDescripcion.setBounds(10, 45, 180, 30); // aumentar alto para que se vea todo
        lblDescripcion.setLineWrap(true); // salto de línea automático
        lblDescripcion.setWrapStyleWord(true); // cortar palabras completas
        lblDescripcion.setEditable(false); // solo lectura
        lblDescripcion.setHighlighter(null); // quita el seleccionar
        lblDescripcion.setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // quita el puntero de texto
        lblDescripcion.setOpaque(false); // fondo transparente
        detalle.add(lblDescripcion);

        // Precio
        JLabel lblPrecio = new JLabel("Precio: " + precio);
        lblPrecio.setFont(new Font("Times New Roman", Font.BOLD, 12));
        lblPrecio.setBounds(10, 80, 200, 20);
        detalle.add(lblPrecio);

        tarjetaCuerpo.add(detalle);
        // botones
        compraInstanatnea = new RoundedButton("Comprar Ahora", new Color(240, 240, 240));
        compraInstanatnea.setBounds(5, 100, 95, 30);
        compraInstanatnea.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        compraInstanatnea.setForeground(Color.BLACK); // solo si tu fondo es oscuro
        compraInstanatnea.setHorizontalAlignment(SwingConstants.LEFT);
        compraInstanatnea.setMargin(new Insets(0, 5, 0, 5));

        BufferedImage carritoImagenOriginal = ImageIO.read(new File("src/Iconos/carrito.png"));
        ImageIcon carritoImagen = resizeImage(carritoImagenOriginal, 20, 20);
        agregarAlCarrito = new RoundedButton("Agregar", carritoImagen, new Color(240, 240, 240));
        agregarAlCarrito.setBounds(104, 100, 93, 30);
        agregarAlCarrito.setForeground(Color.BLACK);
        agregarAlCarrito.setFont(new Font("Times New Roman", Font.PLAIN, 12));

        detalle.add(compraInstanatnea);
        detalle.add(agregarAlCarrito);

        this.add(tarjetaCuerpo);
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public int getIdentificadorTarjeta() {
        return identificadorTarjeta;
    }

    public void setIdentificadorTarjeta(int identificadorTarjeta) {
        this.identificadorTarjeta = identificadorTarjeta;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // Llamado botones
    public void setCompraListener(ActionListener listener) {
        compraInstanatnea.addActionListener(listener);
    }

    public void setAgregarCarritoListener(ActionListener listener) {
        agregarAlCarrito.addActionListener(listener);
    }

    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame("Prueba TarjetaProducto");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.yellow);
        frame.setSize(1300, 800);
        JPanel contenedor = new JPanel();
        contenedor.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 15)); // separación entre tarjetas
        contenedor.setBackground(Color.yellow);
        HashMap<Integer, JPanel> tarjetas = new HashMap<>();

        for (int i = 0; i < 2; i++) {
            TarjetaProducto tarjeta = new TarjetaProducto(
                    i,
                    "", // ruta imagen válida
                    "Producto " + (i + 1),
                    "M",
                    "Descripción corta llllljjkihboooooooooooooooooooooooooooooooooooooogvtfcf vb goooooooooooooooooooooooooooooooooooooooooooofcrdrcfgvbhhgfrdfghjiuygfcy jn"
                            + (i + 1),
                    500.0);
            tarjetas.put(i, tarjeta);
            contenedor.add(tarjeta);
        }

        frame.add(new JScrollPane(contenedor)); // con scroll si hay muchas
        frame.setVisible(true);
    }

}
