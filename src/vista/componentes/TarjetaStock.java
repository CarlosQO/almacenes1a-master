package vista.componentes;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import vista.vistaAdministrador.RoundedPanel;

public class TarjetaStock extends RoundedPanel {

    private JLabel lblTitulo;
    private JPanel containContenido;
    private JScrollPane scrollPane;

    public TarjetaStock(int radius, int borderHex,String titulo) {
        super(radius, borderHex);
        
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(200, 260));
        setShadowSize(2);

        //  Título superior
        lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitulo.setForeground(Color.BLACK);
        lblTitulo.setBorder(new EmptyBorder(0, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);

        //  Panel de contenido (donde se agregan las filas)
        containContenido = new JPanel();
        containContenido.setLayout(new GridLayout(0, 2, 5, 5)); // nombre / cantidad
        containContenido.setOpaque(false);

        //  Scroll para el contenido
        scrollPane = new JScrollPane(containContenido);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);
    }

    //  Método para limpiar el contenido
    public void limpiarContenido() {
        containContenido.removeAll();
        containContenido.revalidate();
        containContenido.repaint();
    }

    //  Método para agregar una fila (nombre, cantidad)
    public void agregarFila(String nombre, String cantidad) {
        JLabel lblNombre = new JLabel(nombre);
        JLabel lblCantidad = new JLabel(cantidad, SwingConstants.RIGHT);

        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblCantidad.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        containContenido.add(lblNombre);
        containContenido.add(lblCantidad);
    }
    /*public static void main(String[] args) {
        // Estilo visual (opcional)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Crear ventana principal
        JFrame frame = new JFrame("Ejemplo TarjetaStock");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        frame.getContentPane().setBackground(new Color(240, 240, 240));

        // Crear tarjeta con título
        TarjetaStock tarjeta = new TarjetaStock(10,0xFFFFFF,"Camisas");

        // Agregar productos simulados
        for (int i = 1; i <= 30; i++) {
            tarjeta.agregarFila("Camisa modelo " + i, (10 + i) + " und");
        }

        // Agregar la tarjeta al frame
        frame.add(tarjeta);

        // Mostrar la ventana
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }*/
}
