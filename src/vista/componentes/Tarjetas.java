package vista.componentes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import org.jdesktop.swingx.JXPanel;

import modelo.crudProducto.Producto;
import vista.fuenteLetra.Fuente;
import vista.vistaAdministrador.RoundedPanel;

public class Tarjetas extends RoundedPanel {

    public RoundedPanel containDetalle;
    public JLabel nombreCamisa, cantidad, precio;
    public JLabel talla, cantidadNum, precioNum;
    public JXPanel imgPanel;
    public RoundedPanel promocionar;
    private Fuente fuente = new Fuente();

    public Tarjetas(int radius, int borderHex) {
        super(radius, borderHex);
    }

    public Tarjetas(int radius, int borderHex, int idProd, String imagen, String nomCami, String tallaCami,
            String cantiCami,
            String precioCami) throws IOException {
        super(radius, borderHex);

        // Leer imagen original
        BufferedImage imgRounded = ImageIO.read(new File(imagen));

        // Crear panel de imagen con bordes redondeados
        imgPanel = new JXPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Dibujar imagen dentro de una forma redondeada
                Shape clip = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20);

                g2.setClip(clip);
                g2.drawImage(imgRounded, 0, 0, getWidth(), getHeight(), null);
                g2.dispose();
            }
        };
        imgPanel.setBounds(5, 5, 170, 170);
        imgPanel.setOpaque(false); // Transparente
        this.add(imgPanel);

        // agregar el detalle
        containDetalle = new RoundedPanel(20, 0xF8F9FB);
        containDetalle.setShadowSize(1);
        containDetalle.setLayout(null);
        containDetalle.setBounds(5, 175, 170, 70);
        containDetalle.setBackground(new Color(0xDDF6FD));

        // agregar nombreCamisa
        nombreCamisa = new JLabel(nomCami);
        nombreCamisa.setFont(fuente.fuente(6, true));
        nombreCamisa.setBounds(10, 2, 120, 20);
        containDetalle.add(nombreCamisa);

        // agregar cantidad
        cantidad = new JLabel("Cantidad de ventas:");
        cantidad.setFont(fuente.fuente(6, true));
        cantidad.setBounds(10, 15, 120, 20);
        containDetalle.add(cantidad);

        // agregar precio
        precio = new JLabel("Precio:");
        precio.setFont(fuente.fuente(6, true));
        precio.setBounds(10, 28, 80, 20);
        containDetalle.add(precio);

        // agregar talla
        talla = new JLabel(tallaCami);
        talla.setFont(fuente.fuente(6, true));
        talla.setBounds(140, 2, 80, 20);
        containDetalle.add(talla);

        // agregar cantidadNum
        cantidadNum = new JLabel(cantiCami);
        cantidadNum.setFont(fuente.fuente(6, true));
        cantidadNum.setBounds(140, 15, 80, 20);
        containDetalle.add(cantidadNum);

        // agregar precioNum
        precioNum = new JLabel(precioCami);
        precioNum.setFont(fuente.fuente(6, true));
        precioNum.setBounds(110, 28, 80, 20);
        containDetalle.add(precioNum);

        // agregar boton promocionar
        promocionar = new RoundedPanel(20, 0x76A7F2);
        promocionar.setForeground(Color.white);
        promocionar.setLayout(null);
        promocionar.setShadowSize(1);
        promocionar.setBackground(new Color(0x76A7F2));
        promocionar.setBounds(25, 45, 120, 20);
        promocionar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        promocionar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                promocionar.setBackground(new Color(0x5A8DD4));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                promocionar.setBackground(new Color(0x76A7F2));
            }
        });
        JLabel textPromo = new JLabel("Promocionar");
        textPromo.setFont(fuente.fuente(6, true));
        textPromo.setForeground(Color.white);
        textPromo.setBounds(25, 0, 120, 20);
        promocionar.add(textPromo);
        containDetalle.add(promocionar);

        this.add(containDetalle);
    }

    private Producto producto;

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Producto getProducto() {
        return producto;
    }

}
