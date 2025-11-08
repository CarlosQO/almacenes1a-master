package vista.vistaSupervisor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.InputStream;
import java.util.Calendar;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import vista.componentes.Header;
import vista.vistaSupervisor.componentes.PlaceholderSupport;
import vista.vistaSupervisor.componentes.RoundedPanel;

public class SeguimientoVendedor extends JFrame {

    private JPanel panelP, panelRO;
    private Font customFont;
    private JLabel lReOp, lAño, lMes;
    private JLabel lDocumento;
    private javax.swing.JTextField txtDocumento;
    private JTable tablaSV;
    private DefaultTableModel camposSV;
    private JScrollPane jSPane;
    private JComboBox<String> cBMes;
    private JComboBox<Integer> cBAño;
    private JButton btnBR;
    private Header header;

    public SeguimientoVendedor() {
        setTitle("Seguimiento de Vendedor");
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        panelP = new JPanel();
        panelP.setBackground(Color.white);
        panelP.setLayout(null);
        panelP.setBounds(0, 0, 1300, 700);

        try {
            InputStream is = ReportesOperativos.class.getResourceAsStream("/fonts/newCentury.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, is);
            is.close();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (Exception e) {
            e.printStackTrace();
        }

        header = new Header();

        panelP.add(header);

        panelRO = new RoundedPanel(16, Color.lightGray);
        panelRO.setLayout(null);
        panelRO.setBackground(Color.lightGray);
        panelRO.setBounds(225, 100, 850, 200);

        if (panelRO instanceof RoundedPanel) {
            ((RoundedPanel) panelRO).setShadowSize(1);
            ((RoundedPanel) panelRO).setShadowColor(new Color(0, 0, 0));
        }

        lReOp = new JLabel("Seguimiento de Vendedor");
        lReOp.setBounds(200, 10, 650, 50);
        panelRO.add(lReOp);

        if (customFont != null) {
            lReOp.setFont(customFont.deriveFont(Font.BOLD, 32f));
        } else {
            lReOp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 32));
        }

        // Documento (centrado junto a Año y Mes)
        lDocumento = new JLabel("Documento");
        lDocumento.setBounds(85, 70, 200, 20);
        lDocumento.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        panelRO.add(lDocumento);

        txtDocumento = new JTextField();
        txtDocumento.setBounds(85, 100, 200, 40);
        PlaceholderSupport.setPlaceholder(txtDocumento, "Ingrese el documento");
        panelRO.add(txtDocumento);

        lAño = new JLabel("Año");
        lAño.setBounds(325, 70, 200, 20);
        lAño.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        panelRO.add(lAño);

        int añoActual = Calendar.getInstance().get(Calendar.YEAR);
        cBAño = new JComboBox<>();
        for (int año = 2005; año <= 2100; año++) {
            cBAño.addItem(año);
        }
        cBAño.setSelectedItem(añoActual);
        cBAño.setBounds(325, 100, 200, 40);
        panelRO.add(cBAño);

        lMes = new JLabel("Mes");
        lMes.setBounds(565, 70, 200, 20);
        lMes.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        panelRO.add(lMes);

        // Nombres de los meses
        String[] meses = {
                "Enero", "Febrero", "Marzo", "Abril",
                "Mayo", "Junio", "Julio", "Agosto",
                "Septiembre", "Octubre", "Noviembre", "Diciembre"
        };

        cBMes = new JComboBox<>(meses);

        // Selecciona el mes actual por defecto
        int mesActual = Calendar.getInstance().get(Calendar.MONTH);
        cBMes.setSelectedIndex(mesActual);
        cBMes.setBounds(565, 100, 200, 40);
        panelRO.add(cBMes);

        btnBR = new JButton("Buscar");
        // Place Buscar button centered below the comboboxes
        btnBR.setBounds(565, 150, 200, 40);
        panelRO.add(btnBR);

        add(panelRO);

        camposSV = new DefaultTableModel();
        camposSV.addColumn("<html><center>Nombre<br>Vendedor</center></html>");
        camposSV.addColumn("<html><center>Documento</center></html>");
        camposSV.addColumn("<html><center>Pedidos<br>Despachados</center></html>");
        camposSV.addColumn("<html><center>Pedidos<br>Cancelados</center></html>");
        camposSV.addColumn("<html><center>Pedidos<br>Entregados</center></html>");
        camposSV.addColumn("<html><center>Pedidos no<br>Entregados</center></html>");

        tablaSV = new JTable(camposSV);

        JTableHeader headerTabla = tablaSV.getTableHeader();
        headerTabla.setPreferredSize(new Dimension(headerTabla.getWidth(), 50));
        headerTabla.setReorderingAllowed(false);
        headerTabla.setResizingAllowed(false);

        tablaSV.setRowHeight(40);
        camposSV.addRow(new Object[5]);

        if (customFont != null) {
            tablaSV.setFont(customFont.deriveFont(Font.PLAIN, 16f));
            headerTabla.setFont(customFont.deriveFont(Font.PLAIN, 18f));
        } else {
            tablaSV.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
            headerTabla.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        }

        // Renderer personalizado para las filas
        tablaSV.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                JLabel c = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                c.setBorder(new EmptyBorder(5, 10, 5, 10)); // margen interno

                if (isSelected) {
                    c.setBackground(new Color(184, 207, 229));
                } else if (row == 0) {
                    c.setBackground(Color.WHITE);
                } else {
                    c.setBackground(Color.cyan);
                }

                return c;
            }
        });

        // ScrollPane
        jSPane = new JScrollPane(tablaSV);
        jSPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jSPane.setBounds(200, 320, 900, 300);

        panelP.add(jSPane);

        add(panelP);
    }

    // Getters for controller
    public JButton getBtnBR() {
        return btnBR;
    }

    public JTextField getTxtDocumento() {
        return txtDocumento;
    }

    public JTable getTablaSV() {
        return tablaSV;
    }

    public JComboBox<Integer> getCBAño() {
        return cBAño;
    }

    public JComboBox<String> getCBMes() {
        return cBMes;
    }
}
