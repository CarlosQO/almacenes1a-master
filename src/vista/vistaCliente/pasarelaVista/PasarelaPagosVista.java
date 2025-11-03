package vista.vistaCliente.pasarelaVista;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class PasarelaPagosVista {
    public JDialog dialogo;
    JLabel titulo;
    public JButton btnTarjetaCredito, btnTarjetaDebito, btnConsignacion, btnBilletera;

    public PasarelaPagosVista(JFrame frame) {
        dialogo = new JDialog(frame, "Método de pago", true); // modal
        dialogo.setLayout(null);
        dialogo.setBounds(200, 200, 300, 280);
        dialogo.getContentPane().setBackground(Color.WHITE);

        titulo = new JLabel("Elige tu método de pago:");
        titulo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        titulo.setBounds(10, 10, 280, 30);
        dialogo.add(titulo);

        btnTarjetaDebito = crearBoton("Tarjeta débito", 50);
        btnTarjetaCredito = crearBoton("Tarjeta crédito", 91);
        btnConsignacion = crearBoton("Consignación", 132);
        btnBilletera = crearBoton("Billetera Electrónica", 173);
    }

    private JButton crearBoton(String texto, int y) {
        JButton btn = new JButton(texto);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBackground(new Color(230, 240, 255));
        btn.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        btn.setBorderPainted(false);
        btn.setBounds(10, y, 260, 40);
        dialogo.add(btn);
        return btn;
    }

    public JDialog getDialogo() {
        return dialogo;
    }

}