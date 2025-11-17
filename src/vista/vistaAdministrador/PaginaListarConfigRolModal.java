package vista.vistaAdministrador;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import vista.componentes.Validaciones;

public class PaginaListarConfigRolModal extends JFrame {
    public JTextField numeCedula;
    public JButton buscar, guardarCambios;
    private Container container;
    private JPanel confiRolContainer, headerRol;
    private FlowLayout miflow;
    public JTextField numeId, numeNombres, numeApellidos, numeTipoRol;
    public JComboBox tipoDeRol;

    public PaginaListarConfigRolModal() {
        container = getContentPane();
        miflow = new FlowLayout();
        container.setLayout(miflow);
    }

    public void listarConfiRol() {
        // Limpiar antes de agregar
        limpiar();
        confiRolContainer = new JPanel();
        confiRolContainer.setLayout(null);
        confiRolContainer.setPreferredSize(new Dimension(320, 380));
        confiRolContainer.setBackground(Color.WHITE);
        confiRolContainer.setBounds(10, 0, 320, 380);
        // añadir la cabecera con el titulo de configurar Rol
        headerRol = new JPanel();
        headerRol.setLayout(null);
        headerRol.setBounds(0, 0, (int) confiRolContainer.getPreferredSize().getWidth(), 40);
        JLabel confiRol = new JLabel("Configurar Rol");
        confiRol.setFont(fuente(true));
        confiRol.setBounds(90, 10, (int) confiRol.getPreferredSize().getWidth() + 20,
                (int) confiRol.getPreferredSize().getHeight());
        headerRol.add(confiRol);
        confiRolContainer.add(headerRol);

        // añadir el campo para ingresar cedula y el boton buscar
        JLabel cedula = new JLabel("N° de Cédula:");
        cedula.setFont(fuente(false));
        cedula.setBounds(10, 65, (int) cedula.getPreferredSize().getWidth(),
                (int) cedula.getPreferredSize().getHeight());
        confiRolContainer.add(cedula);
        numeCedula = new JTextField();
        numeCedula.setBounds(120, 60, 190, 30);
        numeCedula.setFont(fuente(false));
        confiRolContainer.add(numeCedula);
        buscar = new JButton("Buscar");
        buscar.setFont(fuente(false));
        buscar.setBackground(Color.WHITE);
        buscar.setFocusable(false);
        buscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        buscar.setBounds(120, 100, 100, 30);
        confiRolContainer.add(buscar);

        // añadir el campo de id, nombres, apellidos, tipo rol, los JLabel no editables
        // y el boton
        // guardar cambios
        JLabel id = new JLabel("ID:");
        id.setFont(fuente(false));
        id.setBounds(10, 150, (int) id.getPreferredSize().getWidth() + 10,
                (int) id.getPreferredSize().getHeight());
        confiRolContainer.add(id);
        numeId = new JTextField();
        numeId.setBounds(120, 150, 190, 30);
        numeId.setFont(fuente(false));
        numeId.setEditable(false);
        confiRolContainer.add(numeId);

        JLabel nombres = new JLabel("Nombres:");
        nombres.setFont(fuente(false));
        nombres.setBounds(10, 190, (int) nombres.getPreferredSize().getWidth() + 10,
                (int) nombres.getPreferredSize().getHeight());
        confiRolContainer.add(nombres);
        numeNombres = new JTextField();
        numeNombres.setBounds(120, 190, 190, 30);
        numeNombres.setFont(fuente(false));
        numeNombres.setEditable(false);
        confiRolContainer.add(numeNombres);

        JLabel apellidos = new JLabel("Apellidos:");
        apellidos.setFont(fuente(false));
        apellidos.setBounds(10, 230, (int) apellidos.getPreferredSize().getWidth() + 10,
                (int) apellidos.getPreferredSize().getHeight());
        numeApellidos = new JTextField();
        numeApellidos.setBounds(120, 230, 190, 30);
        numeApellidos.setFont(fuente(false));
        numeApellidos.setEditable(false);
        confiRolContainer.add(numeApellidos);

        confiRolContainer.add(apellidos);
        JLabel tipoRol = new JLabel("Tipo Rol:");
        tipoRol.setFont(fuente(false));
        tipoRol.setBounds(10, 270, (int) tipoRol.getPreferredSize().getWidth() + 10,
                (int) tipoRol.getPreferredSize().getHeight());
        confiRolContainer.add(tipoRol);
        tipoDeRol = new JComboBox<String>();
        tipoDeRol.setBounds(120, 270, 190, 30);
        tipoDeRol.setFont(fuente(false));
        tipoDeRol.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confiRolContainer.add(tipoDeRol);

        guardarCambios = new JButton("Guardar Cambios");
        guardarCambios.setFont(fuente(false));
        guardarCambios.setBackground(Color.WHITE);
        guardarCambios.setFocusable(false);
        guardarCambios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        guardarCambios.setBounds(70, 330, 200, 30);
        confiRolContainer.add(guardarCambios);

        container.add(confiRolContainer);
        // Ajustar tamaño del frame para que coincida con el panel
        // this.setSize(340, 420);
    }

    public void mostrarComoModal(JFrame parent, String m) {
        JDialog dialog = new JDialog(parent, m, true);
        dialog.setContentPane(this.getContentPane());
        dialog.setSize(this.getSize());
        dialog.setLocationRelativeTo(parent);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    }

    public void limpiar() {
        container.removeAll();
        container.repaint();
        container.revalidate();
    }

    public Font fuente(boolean b) {
        try {
            if (b) {
                Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/newCentury.ttf"))
                        .deriveFont(Font.BOLD, 20);
                return customFont;
            } else {
                Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/newCentury.ttf"))
                        .deriveFont(Font.BOLD, 15);
                return customFont;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se logro cargar la fuente de las letras");
            return UIManager.getFont("Label.font");
        }
    }

    public void limpiarCampos() {
        // numeId, numeNombres, numeApellidos, numeTipoRol;
        numeId.setText("");
        numeNombres.setText("");
        numeApellidos.setText("");
        tipoDeRol.removeAllItems();
    }

    public boolean validaciones() {
        String cedula = numeCedula.getText();
        if (cedula.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "El campo de cédula no debe estar vacío", "Cédula inválida",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (!Validaciones.validarCedula(cedula)) {
            JOptionPane.showMessageDialog(null,
                    "La cédula no es válida, debe ser numérica y tener 9 o 10 dígitos", "Cédula inválida",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}