package vista.vistaCliente.tarjetas;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import vista.componentes.RoundedButton;
import vista.componentes.RoundedPanel;
import vista.vistaCliente.Validaciones;

public class TarjetaPQRS {

    public JDialog dialogo;
    public RoundedPanel panelPQRS;
    public RoundedButton btnEnviar;
    public JTextField txtAsunto;
    public JTextArea txtPQRS;

    public TarjetaPQRS(JFrame frame) {
        // Crear JDialog modal
        dialogo = new JDialog(frame, "PQRS", true);
        dialogo.setLayout(null);
        dialogo.setBounds(200, 200, 300, 450);
        dialogo.getContentPane().setBackground(Color.WHITE);

        // Panel principal con bordes redondeados
        panelPQRS = new RoundedPanel(30, 0xFFFFFF);
        panelPQRS.setShadowSize(1);
        panelPQRS.setLayout(null);
        panelPQRS.setBounds(20, 20, 250, 380);
        panelPQRS.setBackground(new Color(180, 230, 255, 130));
        dialogo.add(panelPQRS);

        // Título
        JLabel lblTitulo = new JLabel("PQRS");
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 24));
        lblTitulo.setBounds(90, 10, 100, 30);
        panelPQRS.add(lblTitulo);

        // Label Asunto
        JLabel lblAsunto = new JLabel("Asunto*");
        lblAsunto.setBounds(30, 50, 200, 20);
        panelPQRS.add(lblAsunto);

        // Campo Asunto
        txtAsunto = new JTextField();
        txtAsunto.setBounds(30, 80, 200, 25);
        panelPQRS.add(txtAsunto);

        // Label PQRS
        JLabel lblPQRS = new JLabel("PQRS*");
        lblPQRS.setBounds(30, 120, 200, 20);
        panelPQRS.add(lblPQRS);

        // Área de texto con scroll
        txtPQRS = new JTextArea();
        txtPQRS.setLineWrap(true);
        txtPQRS.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(txtPQRS);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(30, 150, 200, 150);
        panelPQRS.add(scroll);

        // Botón Enviar
        btnEnviar = new RoundedButton("Enviar", new Color(255, 255, 255));
        btnEnviar.setBounds(70, 310, 100, 35);
        panelPQRS.add(btnEnviar);
    }

    public void mostrar() {
        dialogo.setVisible(true);
    }

    public boolean validarCampos() {
        String asunto = getTxtAsunto().getText().trim();
        String cuerpo = getTxtPQRS().getText().trim();
        boolean resultado = true;
        if (asunto.isEmpty() || cuerpo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            return false;
        }
        if (!Validaciones.validarMinimo50Caracteres(cuerpo)) {
            JOptionPane.showMessageDialog(null, "Minimo 50 caracteres");
            return false;
        }
        return true;
    }

    public JTextField getTxtAsunto() {
        return txtAsunto;
    }

    public void setTxtAsunto(JTextField txtAsunto) {
        this.txtAsunto = txtAsunto;
    }

    public JTextArea getTxtPQRS() {
        return txtPQRS;
    }

    public void setTxtPQRS(JTextArea txtPQRS) {
        this.txtPQRS = txtPQRS;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Ejemplo Tarjeta PQRS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        TarjetaPQRS tarjetaDialog = new TarjetaPQRS(frame);
        tarjetaDialog.mostrar();
    }
}
