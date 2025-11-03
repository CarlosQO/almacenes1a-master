package vista.vistaCliente.tarjetas;

import static vista.componentes.RoundedPanel.resizeImage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import vista.componentes.ImagenRoundedPanel;
import vista.componentes.RoundedButton;
import vista.componentes.RoundedPanel;

public class TarjetaPromocion extends JPanel {
    // Panel principal redondeado
    public RoundedPanel panelPromocion;

    // Componentes internos
    public ImagenRoundedPanel imagen1, imagen2;
    public JLabel lblTitulo, lblDescripcion, lblDescuento, lblTotal;
    public RoundedButton btnComprarAhora, btnAgregarCarrito;

    // Datos
    private String nombrePromocion, descripcion;
    private int idPromocion, descuento;
    private String rutaImagen1, rutaImagen2;
    private double total;

    public TarjetaPromocion(int idPromocion, String nombrePromocion, String descripcion, int descuento,
            String rutaImagen1, String rutaImagen2, double total) {

        this.nombrePromocion = nombrePromocion;
        this.descripcion = descripcion;
        this.descuento = descuento;
        this.rutaImagen1 = rutaImagen1;
        this.rutaImagen2 = rutaImagen2;

        panelPromocion = new RoundedPanel(20, 0xFFFFFF);
        panelPromocion.setLayout(null);
        panelPromocion.setPreferredSize(new Dimension(800, 400));
        panelPromocion.setBackground(Color.WHITE);
        panelPromocion.setShadowSize(5);

        if (!rutaImagen2.equals("null")) {
            imagen1 = cargarImagenSegura(rutaImagen1, 250, 250, 40, 20);
            panelPromocion.add(imagen1);

            imagen2 = cargarImagenSegura(rutaImagen2, 250, 250, 350, 20);
            panelPromocion.add(imagen2);
        } else {
            imagen1 = cargarImagenSegura(rutaImagen1, 300, 250, 150, 20);
            panelPromocion.add(imagen1);
        }

        lblTitulo = new JLabel(nombrePromocion);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setBounds(50, 280, 900, 30);
        panelPromocion.add(lblTitulo);

        lblDescuento = new JLabel("-" + descuento + "%");
        lblDescuento.setFont(new Font("Arial", Font.BOLD, 20));
        lblDescuento.setForeground(Color.RED);
        lblDescuento.setBounds(550, 280, 200, 30);
        panelPromocion.add(lblDescuento);

        lblDescripcion = new JLabel(descripcion);
        lblDescripcion.setFont(new Font("Arial", Font.PLAIN, 16));
        lblDescripcion.setBounds(50, 310, 900, 40);
        panelPromocion.add(lblDescripcion);

        lblTotal = new JLabel("Total : " + total);
        lblTotal.setFont(new Font("Arial", Font.BOLD, 20));
        lblTotal.setBounds(50, 355, 900, 40);
        panelPromocion.add(lblTotal);

        btnComprarAhora = new RoundedButton("Comprar Ahora", new Color(240, 240, 240));
        btnComprarAhora.setBounds(280, 355, 150, 30);
        panelPromocion.add(btnComprarAhora);

        BufferedImage carritoImg = null;
        try {
            carritoImg = ImageIO.read(new File("src/Iconos/carrito.png"));
        } catch (IOException e) {
            e.printStackTrace();
            carritoImg = new BufferedImage(20, 20, BufferedImage.TYPE_INT_ARGB);
        }
        ImageIcon carritoIcon = resizeImage(carritoImg, 20, 20);
        btnAgregarCarrito = new RoundedButton("Agregar", carritoIcon, new Color(240, 240, 240));
        btnAgregarCarrito.setBounds(460, 355, 150, 30);
        panelPromocion.add(btnAgregarCarrito);
    }

    // Carga segura de ImagenRoundedPanel paar evitar errores
    private ImagenRoundedPanel cargarImagenSegura(String ruta, int ancho, int alto, int x, int y) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(ruta));
        } catch (IOException e) {
            e.printStackTrace();
            // Crear una imagen vacía si hay error
            img = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        }
        // Image imgRedimensionada = img.getScaledInstance(ancho, alto,
        // Image.SCALE_SMOOTH);
        ImagenRoundedPanel panelImg = new ImagenRoundedPanel(20, null, img);
        panelImg.setBounds(x, y, ancho, alto);
        return panelImg;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getNombrePromocion() {
        return nombrePromocion;
    }

    public void setNombrePromocion(String nombrePromocion) {
        this.nombrePromocion = nombrePromocion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public String getRutaImagen1() {
        return rutaImagen1;
    }

    public void setRutaImagen1(String rutaImagen1) {
        this.rutaImagen1 = rutaImagen1;
    }

    public String getRutaImagen2() {
        return rutaImagen2;
    }

    public void setRutaImagen2(String rutaImagen2) {
        this.rutaImagen2 = rutaImagen2;
    }

    public int getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(int idPromocion) {
        this.idPromocion = idPromocion;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tarjeta Promoción");
            frame.setLayout(null);
            frame.setSize(1000, 500);

            try {
                TarjetaPromocion tarjeta = new TarjetaPromocion(
                        1,
                        "Super Promo",
                        "Descripción de la promoción con todos los detalles importantes para el usuario.",
                        50,
                        "src/productos/CamisasFormalesHombre/camisa MangaLarga Blanca.jpg",
                        "null",
                        // "src/productos/CamisasFormalesHombre/camisa MangaLarga Blanca.jpg",
                        1200.00);

                tarjeta.panelPromocion.setBounds(10, 10, 650, 400);
                frame.add(tarjeta.panelPromocion);
            } catch (Exception e) {
                e.printStackTrace();
            }

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
