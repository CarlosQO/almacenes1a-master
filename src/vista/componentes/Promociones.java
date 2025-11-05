package vista.componentes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

import javax.swing.BorderFactory;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.JTextComponent;

import vista.fuenteLetra.Fuente;
import vista.vistaAdministrador.RoundedPanel;

import vista.componentes.Validaciones;

public class Promociones extends JFrame {

    private Container container;
    private JLabel titlePromocion;
    public JTextField nombreProm, urlImagen;
    public JTextArea descripcionProm;

    public JComboBox descuento;
    public JComboBox productos;
    public RoundedPanel btnPublicar;
    public JTextField cantidad, PrecioTotal;

    private static Fuente fuente = new Fuente();

    public int idProductoSeleccionado;
    public String urlImagenSeleccionada;
    public int idSegundoProductoSeleccionado;

    public Promociones() {
        super("Promocion");

        container = getContentPane();

        container.setLayout(null);
        container.setBackground(new Color(0xE0E0E0));

        // agregar el titulo
        titlePromocion = new JLabel("Promoción");
        titlePromocion.setFont(fuente.fuente(3, true));
        titlePromocion.setForeground(Color.black);
        titlePromocion.setBounds(10, 5, (int) titlePromocion.getPreferredSize().getWidth() + 40,
                (int) titlePromocion.getPreferredSize().getHeight());
        container.add(titlePromocion);

        // agregar el JLabel de nombre de la promocion
        nombreProm = new JTextField();
        nombreProm.setBackground(Color.white);
        limitarCaracteres(nombreProm, 15);
        nombreProm.setFont(fuente.fuente(5, false));
        nombreProm.setBounds(10, (int) titlePromocion.getHeight() + 15, 270, 40);
        nombreProm.setBorder(crearTituloSinLinea("Nombre de la promoción", new Color(0x787878)));
        container.add(nombreProm);

        // agregar el porcentaje de descuento
        JPanel fondoDescuento = new JPanel();
        fondoDescuento.setBackground(Color.white);
        fondoDescuento.setLayout(null);
        fondoDescuento.setBounds(nombreProm.getX(), nombreProm.getY() + 50, nombreProm.getWidth() - 80, 40);
        JLabel textDescuento = new JLabel("Porcentaje de descuento");
        textDescuento.setFont(fuente.fuente(5, false));
        textDescuento.setBounds(0, 5, fondoDescuento.getWidth(), 30);
        fondoDescuento.add(textDescuento);
        container.add(fondoDescuento);
        descuento = new JComboBox<>(new Integer[] { 20, 30, 50 });
        descuento.setBounds(nombreProm.getWidth() - 70, nombreProm.getY() + 50, 80, 40);
        descuento.setBackground(Color.white);
        descuento.setBorder(null);
        descuento.setFont(fuente.fuente(5, false));
        container.add(descuento);

        // agregar la descripcion
        descripcionProm = new JTextArea();
        descripcionProm.setLineWrap(true);
        descripcionProm.setWrapStyleWord(true);
        descripcionProm.setBounds(nombreProm.getWidth() + 20, nombreProm.getY(), 200, 90);
        descripcionProm.setFont(fuente.fuente(5, false));
        descripcionProm.setBorder(crearTituloSinLinea("Descripción", new Color(0x787878)));
        limitarCaracteres(descripcionProm, 55);
        container.add(descripcionProm);

        // Agregar la url de la imagen
        urlImagen = new JTextField();
        urlImagen.setBackground(Color.white);
        urlImagen.setFont(fuente.fuente(5, false));
        urlImagen.setBounds(nombreProm.getX(), descuento.getY() + 50, nombreProm.getWidth(), 40);
        urlImagen.setBorder(crearTituloSinLinea("Imagen", new Color(0x787878)));
        urlImagen.setEditable(false);
        container.add(urlImagen);

        // Agregar el comboBox para los productos
        productos = new JComboBox<String>();
        productos.setBackground(Color.white);
        productos.setBorder(null);
        productos.setBounds(descripcionProm.getX(), descuento.getY() + 50, descripcionProm.getWidth(), 40);
        container.add(productos);

        // Agregar el btn de publicar
        btnPublicar = new RoundedPanel(10, 0xE0E0E0);
        btnPublicar.setLayout(null);
        btnPublicar.setShadowSize(1);
        btnPublicar.setBackground(new Color(0x75A6F3));
        btnPublicar.setBounds(340, productos.getY() + 50, 150, 40);
        JLabel textBtnPublicar = new JLabel("Publicar");
        textBtnPublicar.setFont(fuente.fuente(4, true));
        textBtnPublicar.setBounds(30, 5, 100, 30);
        textBtnPublicar.setForeground(Color.white);
        btnPublicar.add(textBtnPublicar, BorderLayout.CENTER);
        btnPublicar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnPublicar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPublicar.setBackground(new Color(0x4D90F0));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPublicar.setBackground(new Color(0x75A6F3));
            }
        });
        container.add(btnPublicar);

        // Agregar el campo de cantidad
        cantidad = new JTextField();
        cantidad.setBackground(Color.white);
        cantidad.setFont(fuente.fuente(5, false));
        cantidad.setBorder(crearTituloSinLinea("Cantidad a Promocionar", new Color(0x787878)));
        cantidad.setBounds(nombreProm.getX(), btnPublicar.getY(), 170, 40);
        cantidad.setHorizontalAlignment(JTextField.CENTER);
        limitarCaracteres(cantidad, 7);
        container.add(cantidad);

        // Agregar el campo de precioTotal
        PrecioTotal = new JTextField();
        PrecioTotal.setEditable(false);
        PrecioTotal.setFont(fuente.fuente(5, false));
        PrecioTotal.setHorizontalAlignment(JTextField.CENTER);
        PrecioTotal.setBackground(Color.white);
        PrecioTotal.setBounds(cantidad.getWidth() + 20, cantidad.getY(), 130, 40);
        PrecioTotal.setBorder(crearTituloSinLinea("Precio Total", new Color(0x787878)));
        container.add(PrecioTotal);

    }

    public static Border crearTituloSinLinea(String titulo, Color colorTexto) {
        TitledBorder borde = BorderFactory.createTitledBorder(
                BorderFactory.createEmptyBorder(),
                titulo,
                TitledBorder.LEFT,
                TitledBorder.TOP);
        borde.setTitleColor(colorTexto);
        borde.setTitleFont(fuente.fuente(6, true));
        return borde;
    }

    public static void limitarCaracteres(JTextComponent componente, int limite) {
        componente.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (componente.getText().length() >= limite) {
                    e.consume();
                    java.awt.Toolkit.getDefaultToolkit().beep();
                }
            }
        });
    }

    public void limpiarCampos() {
        nombreProm.setText("");
        descripcionProm.setText("");
        urlImagen.setText("");
        urlImagenSeleccionada = "";
        cantidad.setText("");
        idSegundoProductoSeleccionado = 0;
    }

    public boolean validarCampos() {
        if (!Validaciones.validarNombrePromocion(nombreProm.getText())) {
            JOptionPane.showMessageDialog(null,
                    "El nombre de la promocion no es valido, debe tener entre 10 y 15 letras, ademas debe tener mas del 50% de letras");
            return false;
        }
        if (!Validaciones.validarDescripcion(descripcionProm.getText())) {
            JOptionPane.showMessageDialog(null,
                    "La descripcion de la promocion no es valida, debe tener entre 20 y 55 letras, ademas debe tener mas del 50% de letras");
            return false;
        }
        if (!Validaciones.validarCantidadPromocionar(cantidad.getText())) {
            JOptionPane.showMessageDialog(null,
                    "La cantidad de productos a promocionar debe ser mayor a 0 o no debe contener letras");
            return false;
        }
        return true;
    }
}
