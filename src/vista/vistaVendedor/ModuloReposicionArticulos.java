package vista.vistaVendedor;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controlador.controladorVendedor.controladorSolicitudesDeReposicion;
import vista.componentes.*;

public class ModuloReposicionArticulos extends JPanel {

    private JFrame ventana;
    private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    public JPanel panelTarjetas;
    private Header header;

    public ModuloReposicionArticulos(JFrame ventana) {
        this.ventana = ventana;
        setLayout(null);
        setBackground(Color.WHITE);
        setSize(1300, 700);

        // encabezado
        header = new Header();
        add(header);

        // Etiqueta “ALERTAS”
        lblTitulo = new JLabel("ALERTAS");
        lblTitulo.setFont(new Font("Arial", Font.PLAIN, 16));
        lblTitulo.setBounds(200, 80, 200, 30);
        add(lblTitulo);

        // Subtítulo “BAJO STOCK”
        lblSubtitulo = new JLabel("BAJO STOCK");
        lblSubtitulo.setFont(new Font("Arial", Font.BOLD, 26));
        lblSubtitulo.setBounds(200, 110, 300, 40);
        add(lblSubtitulo);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Módulo de Reposición");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 700);
        frame.setLocationRelativeTo(null);

        ModuloReposicionArticulos modulo = new ModuloReposicionArticulos(frame);
        frame.add(modulo);
        frame.setVisible(true);
    }
}
