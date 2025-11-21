package vista.componentes;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

public class OjoLabel extends JLabel {

    private ImageIcon iconoCerrado;
    private ImageIcon iconoAbierto;

    public OjoLabel(JPasswordField campoClave) {

        iconoCerrado = new ImageIcon(getClass().getResource("/Iconos/ojoCerrado.png"));
        iconoAbierto = new ImageIcon(getClass().getResource("/Iconos/ojoAbierto.png"));

        setIcon(iconoCerrado);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                setIcon(iconoAbierto);
                mostrarClave(campoClave);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setIcon(iconoCerrado);
                ocultarClave(campoClave);
            }
        });
    }

    private void mostrarClave(JPasswordField pass) {
        pass.setEchoChar((char) 0); // Muestra el texto

    }

    private void ocultarClave(JPasswordField pass) {
        pass.setEchoChar('•');
    }
}
