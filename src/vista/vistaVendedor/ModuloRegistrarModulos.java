package vista.vistaVendedor;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import vista.componentes.*;

public class ModuloRegistrarModulos extends javax.swing.JPanel {

    private JFrame ventana;
    private Header header;
    public String titulo;
    public Formulario formularioRegistro;

    public ModuloRegistrarModulos(JFrame frame, Map<Integer, String> categorias) {
        this.ventana = frame;
        this.titulo = "Registro de Productos";

        setLayout(null);
        setBackground(Color.WHITE);
        setSize(1300, 700);
        setVisible(true);

        // Header
        header = new Header();
        add(header);

        // Formulario
        Map<Integer, String> estado = new HashMap<>();
        formularioRegistro = new Formulario("Registro", 0, categorias, estado, 380, 100);
        // Solo agregamos el panelFormulario
        add(formularioRegistro.getPanelFormulario());
    }

    public boolean validarCampos() {
        return formularioRegistro.validarCampos();
    }
}
