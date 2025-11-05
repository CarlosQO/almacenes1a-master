package vista.vistaVendedor;

import java.awt.*;
import javax.swing.*;
import vista.componentes.RoundedButton;

public class TarjetasDeSolicitudesReposicion extends JPanel {
    private String nombreProducto;
    private String unidadesDisponibles;
    private int idProducto;
    private RoundedButton botonSolicitar;

    public TarjetasDeSolicitudesReposicion(int idProducto, String nombreProducto, String unidadesDisponibles) {
        this.nombreProducto = nombreProducto;
        this.unidadesDisponibles = unidadesDisponibles;

        // Construcción de la tarjeta
        setLayout(null);
        setBackground(new Color(173, 216, 230)); 
        setPreferredSize(new Dimension(480, 80));
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        // Label del nombre del producto
        JLabel lblNombre = new JLabel(nombreProducto);
        lblNombre.setFont(new Font("Serif", Font.BOLD, 20));
        lblNombre.setBounds(10, 5, 400, 30);
        add(lblNombre);
        
         // Label del id del producto
        JLabel lblIdProducto = new JLabel("Id Producto: "+idProducto);
        lblIdProducto.setFont(new Font("Serif", Font.PLAIN, 15));
        lblIdProducto.setBounds(10, 30, 200, 30);
        add(lblIdProducto);

        // Label de unidades disponibles
        JLabel lblUnidades = new JLabel(unidadesDisponibles + " unidades");
        lblUnidades.setFont(new Font("Serif", Font.PLAIN, 15));
        lblUnidades.setBounds(10, 55, 200, 20);
        add(lblUnidades);

        // Botón "Solicitar
        botonSolicitar = new RoundedButton("Solicitar", Color.WHITE);
        botonSolicitar.setFont(new Font("Serif", Font.PLAIN, 20));
        botonSolicitar.setForeground(Color.BLACK);
        botonSolicitar.setBounds(650, 15, 100, 30);
        add(botonSolicitar);
    }

    public RoundedButton getBotonSolicitar() {
        return botonSolicitar;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getUnidadesDisponibles() {
        return unidadesDisponibles;
    }

    public void setUnidadesDisponibles(String unidadesDisponibles) {
        this.unidadesDisponibles = unidadesDisponibles;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Solicitud de Reposición");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.add(new TarjetasDeSolicitudesReposicion(1, "Camisa Roja", "2"));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
