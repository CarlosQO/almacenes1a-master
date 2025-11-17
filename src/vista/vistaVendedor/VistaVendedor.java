package vista.vistaVendedor;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.InputStream;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vista.componentes.*;
import vista.vistaVendedor.componentes.RoundedPanel;

public class VistaVendedor extends JFrame {

    private JPanel panelP, panelBtn, panelRecepcionOrdenes, panelActuProducto, panelSolicitudReposicion,
            panelRegistroProducto;
    private JLabel lTitulo, lRecepcionOrdenes, lIRO, lBajoStock, lIBS, lSolicitudReposicion, lISR, lRegistroProducto,
            lIRP;
    private Font customFont;

    private Header header;

    public VistaVendedor() {
        setTitle("Vista Vendedor");
        setSize(1300, 700);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        panelP = new JPanel();
        panelP.setLayout(null);
        panelP.setBackground(Color.white);
        panelP.setBounds(0, 0, 1300, 700);
        add(panelP);

        try {
            InputStream is = VistaVendedor.class.getResourceAsStream("/fonts/newCentury.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, is);
            is.close();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (Exception e) {
            e.printStackTrace();
        }

        lTitulo = new JLabel("<html><center>A l m a c e n e s 1 A</center></html>");
        lTitulo.setFont(customFont.deriveFont(Font.PLAIN, 62));
        lTitulo.setBounds(400, 80, 480, lTitulo.getFont().getSize() * 2);
        panelP.add(lTitulo);

        header = new Header();
        // header = new Header().headerCopiar(new Color(255, 255, 255, 255), 1300);
        header.setBounds(0, 0, header.getWidth(), header.getHeight());

        panelP.add(header);

        panelBtn = new JPanel();
        panelBtn.setBounds(250, 280, 800, 300);
        panelBtn.setOpaque(false);
        panelBtn.setLayout(new GridLayout(2, 2, 25, 25));

        panelRecepcionOrdenes = new RoundedPanel(20, Color.cyan);
        panelRecepcionOrdenes.setLayout(new GridLayout(1, 2, 0, 0));
        panelRecepcionOrdenes.setBackground(Color.white);
        removerSombras(panelRecepcionOrdenes);
        panelRecepcionOrdenes.add(agregarImagenALabel(lIRO, "/Iconos/Ícono de lista de verificación.png", 180, 130));
        lRecepcionOrdenes = new JLabel("<html>Recepción de Ordenes</html>");
        lRecepcionOrdenes.setFont(customFont.deriveFont(Font.PLAIN, 28));
        panelRecepcionOrdenes.add(lRecepcionOrdenes);

        panelActuProducto = new RoundedPanel(20, Color.cyan);
        panelActuProducto.setLayout(new GridLayout(1, 2, 0, 0));
        panelActuProducto.setBackground(Color.white);
        removerSombras(panelActuProducto);
        panelActuProducto.add(agregarImagenALabel(lISR, "/Iconos/reposicionIcono.png", 120, 120));
        lBajoStock = new JLabel("<html>Actualización de Productos</html>");
        lBajoStock.setFont(customFont.deriveFont(Font.PLAIN, 28));
        panelActuProducto.add(lBajoStock);

        panelSolicitudReposicion = new RoundedPanel(20, Color.cyan);
        panelSolicitudReposicion.setLayout(new GridLayout(1, 2, 0, 0));
        panelSolicitudReposicion.setBackground(Color.white);
        removerSombras(panelSolicitudReposicion);
        panelSolicitudReposicion.add(agregarImagenALabel(lIBS, "/Iconos/barraCodigo.png", 130, 80));
        lSolicitudReposicion = new JLabel("<html>Solicitud de reposición de artículos</html>");
        lSolicitudReposicion.setFont(customFont.deriveFont(Font.PLAIN, 28));
        panelSolicitudReposicion.add(lSolicitudReposicion);

        panelRegistroProducto = new RoundedPanel(20, Color.cyan);
        panelRegistroProducto.setLayout(new GridLayout(1, 2, 0, 0));
        panelRegistroProducto.setBackground(Color.white);
        removerSombras(panelRegistroProducto);
        panelRegistroProducto.add(agregarImagenALabel(lIRP, "/Iconos/registroProducto.png", 90, 90));
        lRegistroProducto = new JLabel("<html>Registro de Productos</html>");
        lRegistroProducto.setFont(customFont.deriveFont(Font.PLAIN, 28));
        panelRegistroProducto.add(lRegistroProducto);

        panelBtn.add(panelRecepcionOrdenes);
        panelBtn.add(panelActuProducto);
        panelBtn.add(panelSolicitudReposicion);
        panelBtn.add(panelRegistroProducto);
        panelP.add(panelBtn);

    }

    public JPanel getPanelRegistroProductos() {
        return panelRegistroProducto;
    }

    public JPanel getPanelSolicitudReposicion() {
        return panelSolicitudReposicion;
    }

    public JPanel getPanelBajoStock() {
        return panelActuProducto;
    }

    public JPanel getPanelRecepcionOrdenes() {
        return panelRecepcionOrdenes;
    }

    private void removerSombras(JPanel panel) {
        if (panel instanceof RoundedPanel) {
            ((RoundedPanel) panel).setShadowSize(1);
            ((RoundedPanel) panel).setShadowColor(new Color(0, 0, 0));
        }
    }

    private JLabel agregarImagenALabel(JLabel label, String ruta, int ancho, int alto) {

        ImageIcon originalIcon = new ImageIcon(getClass().getResource(ruta));
        Image originalImage = originalIcon.getImage();

        Image resizedImage = originalImage.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        label = new JLabel(resizedIcon);
        label.setBounds(50, 50, ancho, alto);
        return label;
    }

}
