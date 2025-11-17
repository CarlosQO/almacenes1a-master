package vista.vistaMiAjustes;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import vista.fuenteLetra.Fuente;

public class MiAjustes extends JFrame {
    private JLabel icono;
    private Container contain;
    private JLabel usuarioLabel;
    public JLabel actualizarDatos, cerrarSesion;
    private JLabel cerrar;
    public String usuario;
    Fuente fuente = new Fuente();

    public MiAjustes() {
        setTitle("Mis Ajustes");
        setSize(200, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
        setBackground(Color.white);
        contain = getContentPane();
        contain.setLayout(null);

        cerrar = new JLabel("X");
        cerrar.setFont(fuente.fuente(4, true));
        cerrar.setBounds(0, 5, 190, 20);
        cerrar.setHorizontalAlignment(JLabel.RIGHT);
        cerrar.setForeground(Color.black);
        cerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cerrar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
            }
        });
        contain.add(cerrar);

        try {
            ImageIcon img = new ImageIcon(getClass().getResource("/Iconos/user.png"));
            img = new ImageIcon(img.getImage().getScaledInstance(90, 90, java.awt.Image.SCALE_SMOOTH));
            icono = new JLabel(img);
            icono.setHorizontalAlignment(JLabel.CENTER);
            icono.setBounds(0, 10, 200, 90);
            contain.add(icono);
        } catch (Exception e) {
            System.out.println("Error al cargar la imagen en MiAjustes: " + e.getMessage());
        }
        usuarioLabel = new JLabel("");
        usuarioLabel.setFont(fuente.fuente(6, true));
        usuarioLabel.setHorizontalAlignment(JLabel.CENTER);
        usuarioLabel.setBounds(0, icono.getY() + icono.getHeight() + 5, 200, 20);
        contain.add(usuarioLabel);

        actualizarDatos = new JLabel("Actualizar Datos");
        actualizarDatos.setCursor(new Cursor(java.awt.Cursor.HAND_CURSOR));
        actualizarDatos.setFont(fuente.fuente(6, true));
        actualizarDatos.setBounds(10, usuarioLabel.getY() + usuarioLabel.getHeight() + 10, 100, 30);
        contain.add(actualizarDatos);

        cerrarSesion = new JLabel("Cerrar Sesion");
        cerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cerrarSesion.setFont(fuente.fuente(6, true));
        cerrarSesion.setBounds(10, actualizarDatos.getY() + 65, 200, 30);
        contain.add(cerrarSesion);
    }

    public void repintar() {
        revalidate();
        repaint();
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
        usuarioLabel.setText(usuario);
    }
    
}
