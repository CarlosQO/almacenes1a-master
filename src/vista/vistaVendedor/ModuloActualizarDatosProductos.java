package vista.vistaVendedor;

import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import vista.componentes.*;

public class ModuloActualizarDatosProductos extends JPanel {
    private JFrame ventana;
    private Header header;
    public String titulo;
    public Formulario formularioActualizar;
    public RoundedPanel panelBuscarProducto;
    public JTextField txtBuscar;
    public RoundedButton btnBuscar;
    public int idProducto;

    public ModuloActualizarDatosProductos(JFrame ventana, Map<Integer, String> categorias, Map<Integer, String> estado) {
        this.ventana = ventana;
        this.titulo = "Actualizar de Productos";

        setLayout(null);
        setBackground(Color.WHITE);
        setSize(1300, 700);

        // Header
        header = new Header();
        add(header);

        // panel bsucar
        panelBuscarProducto = new RoundedPanel(30, 0xC7D9E8);
        panelBuscarProducto.setShadowSize(1);
        panelBuscarProducto.setLayout(null);
        panelBuscarProducto.setBackground(new Color(240, 248, 250));
        panelBuscarProducto.setBounds(100, 100, 300, 200);

        JLabel lblBuscar = new JLabel("Buscar producto");
        lblBuscar.setFont(new Font("Times New Roman", Font.BOLD, 18));
        lblBuscar.setBounds(20, 20, 150, 30);
        panelBuscarProducto.add(lblBuscar);

        JLabel lblText = new JLabel("Ingrese el id del producto:");
        lblText.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        lblText.setBounds(20, 60, 200, 30);
        panelBuscarProducto.add(lblText);

        txtBuscar = new JTextField();
        txtBuscar.setBounds(20, 90, 200, 30);
        panelBuscarProducto.add(txtBuscar);

        btnBuscar = new RoundedButton("Buscar", new Color(51, 122, 255));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFont(new Font("Times New Roman", Font.BOLD, 16));
        btnBuscar.setBounds(20, 140, 150, 30);
        panelBuscarProducto.add(btnBuscar);

        add(panelBuscarProducto);
    }

    public boolean validarCampos() {
        return formularioActualizar.validarCampos();
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crear frame principal
            JFrame ventana = new JFrame("Registro de Productos");
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setSize(1300, 700);
            ventana.setLocationRelativeTo(null);
            ventana.setLayout(null);

            // Crear mapa de categorías
            Map<Integer, String> categorias = new HashMap<>();
            categorias.put(1, "Flores");
            categorias.put(2, "Plantas de interior");
            categorias.put(3, "Cactus");

            // Crear módulo de registro
            ModuloActualizarDatosProductos moduloRegistro = new ModuloActualizarDatosProductos(ventana, categorias, categorias);
            ventana.add(moduloRegistro);

            ventana.setVisible(true);
        });
    }
}
