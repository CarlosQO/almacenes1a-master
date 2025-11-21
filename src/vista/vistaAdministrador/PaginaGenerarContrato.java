package vista.vistaAdministrador;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import vista.componentes.Header;
import vista.componentes.RoundedButton;
import vista.fuenteLetra.Fuente;
import static vista.componentes.CamposConLimite.limitarCaracteres;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import vista.componentes.Validaciones;

public class PaginaGenerarContrato extends JFrame {
    private Container contenedor;
    public JXTextField contratoDoc;
    public RoundedButton btnBuscarPro;
    public DefaultTableModel modeloTabla;
    public JTable tablaVentas;
    private JLabel titulo;
    private Fuente fuente = new Fuente();
    private Header header;
    private RoundedPanel conteTitle;
    private JTextArea contrato;
    public JScrollPane scrollContrato;

    public PaginaGenerarContrato() {
        super("Generar Contrato");
        setSize(1300, 700);
        setResizable(false);
        setBackground(Color.white);
        contenedor = this.getContentPane();
        contenedor.setLayout(null);
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Header
        header = new Header();
        contenedor.add(header);

        titulo = new JLabel("Contrato de Proveedores");
        titulo.setBounds(80, header.getHeight() + 50, 700, 40);
        titulo.setFont(fuente.fuente(2, true));
        contenedor.add(titulo);

        conteTitle = new RoundedPanel(20, 0xF8F9FB);
        conteTitle.setLayout(null);
        conteTitle.setBackground(header.getBackground());
        conteTitle.setShadowSize(1);
        conteTitle.setBounds(titulo.getX(), titulo.getHeight() + titulo.getY() + 20, 230, 46);
        contratoDoc = new JXTextField();
        contratoDoc.setFont(fuente.fuente(4, false));
        limitarCaracteres(contratoDoc, 10);
        contratoDoc.setPrompt("Documento o NIT");
        contratoDoc.setPromptForeground(Color.gray);
        contratoDoc.setBackground(header.getBackground());
        contratoDoc.setBorder(null);
        contratoDoc.setForeground(Color.black);
        contratoDoc.setBounds(8, 5, 200, 35);
        conteTitle.add(contratoDoc);
        contenedor.add(conteTitle);

        btnBuscarPro = new RoundedButton("Generar Contrato", new Color(0x6FDEFF));
        btnBuscarPro.setBounds(conteTitle.getX() + conteTitle.getWidth() + 20, conteTitle.getY() + 3, 200, 40);
        btnBuscarPro.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscarPro.setFont(fuente.fuente(4, true));
        contenedor.add(btnBuscarPro);

        // Tabla dentro de containInfo
        String[] columnas = { "Tipo de Proveedor", "Nombre del Proveedor", "Documento o NIT",
                "Acciones" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 0 && column < getColumnCount() && column == 3;
            }
        };
        tablaVentas = new JTable(modeloTabla);
        tablaVentas.getTableHeader().setFont(fuente.fuente(5, true));
        tablaVentas.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 45));
        tablaVentas.getColumnModel().setColumnMargin(10);

        tablaVentas.setRowSelectionAllowed(false); // No permite seleccionar filas
        tablaVentas.setColumnSelectionAllowed(false); // No permite seleccionar columnas
        tablaVentas.setCellSelectionEnabled(false);

        // Estilo visual
        tablaVentas.getTableHeader().setReorderingAllowed(false);
        tablaVentas.getTableHeader().setResizingAllowed(false);
        tablaVentas.setRowHeight(35);
        tablaVentas.setBackground(new Color(0xD7EEFF)); // color celeste suave
        tablaVentas.setGridColor(Color.BLACK);
        JScrollPane scroll = new JScrollPane(tablaVentas);
        scroll.setBounds(conteTitle.getX(), conteTitle.getY() + conteTitle.getHeight() + 30, 978, 82);
        contenedor.add(scroll);

        contrato = new JTextArea();
        contrato.setEditable(false);
        contrato.setLineWrap(true);
        contrato.setWrapStyleWord(true);
        contrato.setFont(fuente.fuente(4, false));
        scrollContrato = new JScrollPane(contrato);
        scrollContrato.setBounds(scroll.getX(), scroll.getY() + scroll.getHeight() + 30, 1100, 250);
        scrollContrato.setVisible(false);
        contenedor.add(scrollContrato);
    }

    public void cargarContrato(String contra) {
        contrato.setText(contra);
        contrato.setCaretPosition(0);
        scrollContrato.setVisible(true);
        repaint();
        revalidate();
    }

    public boolean validarCampos() {
        if (!Validaciones.validarCedula(contratoDoc.getText())) {
            JOptionPane.showMessageDialog(null, "El Documento o NIT no es valido, debe ser numerico", "Error",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}
