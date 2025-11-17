package vista.componentes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controladorLogin.PaginaLogin;
import controladorMisAjuste.MiAjustesControlador;
import vista.vistaMiAjustes.MiAjustes;

public class Header extends JPanel {

    private JLabel lblLogo;
    private JLabel lblUser;
    MiAjustes miAjustes;
    MiAjustesControlador miAjustesControlador;
    PaginaLogin paginaLogin = new PaginaLogin();
    public static String usuario;

    public Header() {
        setBackground(new Color(207, 207, 207));
        setLayout(null);
        setBounds(0, 0, 1300, 80);

        // --- LOGO ---
        lblLogo = new JLabel();
        lblLogo.setBounds(10, 5, 80, 80);
        ImageIcon logoIcon = cargarImagen("/Iconos/logoAlmacen1A.png", 80, 80);
        if (logoIcon != null) {
            lblLogo.setIcon(logoIcon);
        }
        add(lblLogo);

        // --- ICONO DE USUARIO ---
        lblUser = new JLabel();
        lblUser.setBounds(1200, 10, 60, 60);
        ImageIcon userIcon = cargarImagen("/Iconos/user.png", 60, 60);
        if (userIcon != null) {
            lblUser.setIcon(userIcon);
        }
        lblUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        lblUser.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                miAjustes = new MiAjustes();
                miAjustes.setBounds(lblUser.getX(), lblUser.getY() + 190, 200, 250);
                miAjustesControlador = new MiAjustesControlador(miAjustes, PaginaLogin.usuario, PaginaLogin.documento);
            }
        });
        add(lblUser);
    }

    private ImageIcon cargarImagen(String path, int ancho, int alto) {
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            Image img = icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch (Exception e) {
            System.err.println("No se pudo cargar la imagen: " + path);
            return null;
        }
    }

    public void modificarUbicacionUser(int x) {
        lblUser.setBounds(x, 10, 60, 60);
        repaint();
        revalidate();
    }
}
