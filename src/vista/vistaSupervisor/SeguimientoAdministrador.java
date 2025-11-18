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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import vista.componentes.Header;
import vista.vistaSupervisor.componentes.RoundedPanel;

public class SeguimientoAdministrador extends JFrame {

    private JPanel panelP, panelRO;
    private Font customFont;
    private JLabel lReOp, lAño, lMes;
    private JTable tablaSA;
    private DefaultTableModel camposSA;
    private JScrollPane jSPane;
    private JComboBox<String> cBMes;
    private JComboBox<Integer> cBAño;
    private JButton btnBR;
    private Header header;

    public SeguimientoAdministrador() {
        setTitle("Reportes Operativos");
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
        panelRO.setBounds(225, 100, 850, 150);

        if (panelRO instanceof RoundedPanel) {
            ((RoundedPanel) panelRO).setShadowSize(1);
            ((RoundedPanel) panelRO).setShadowColor(new Color(0, 0, 0));
        }

        lReOp = new JLabel("Seguimiento de Administrador");
        lReOp.setBounds(200, 0, 650, 50);
        panelRO.add(lReOp);

        if (customFont != null) {
            lReOp.setFont(customFont.deriveFont(Font.BOLD, 32f));
        } else {
            lReOp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 32));
        }

        lAño = new JLabel("Año");
        lAño.setBounds(30, 50, 350, 50);
        lAño.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        panelRO.add(lAño);

        int añoActual = Calendar.getInstance().get(Calendar.YEAR);
        cBAño = new JComboBox<>();
        for (int año = 2025; año <= añoActual; año++) {
            cBAño.addItem(año);
        }
        cBAño.setSelectedItem(añoActual);
        cBAño.setBounds(30, 90, 200, 40);
        panelRO.add(cBAño);

        lMes = new JLabel("Mes");
        lMes.setBounds(320, 50, 350, 50);
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
        cBMes.setBounds(320, 90, 200, 40);
        panelRO.add(cBMes);

        btnBR = new JButton("Buscar");
        btnBR.setBounds(600, 90, 200, 40);
        panelRO.add(btnBR);

        add(panelRO);

        camposSA = new DefaultTableModel();
        camposSA.addColumn("Dia");
        camposSA.addColumn("Conexiones");
        camposSA.addColumn("Duración");
        camposSA.addColumn("Hora conexión");
        camposSA.addColumn("Hora de desconexión");

        tablaSA = new JTable(camposSA);

        JTableHeader headerTabla = tablaSA.getTableHeader();
        headerTabla.setPreferredSize(new Dimension(headerTabla.getWidth(), 35));
        headerTabla.setReorderingAllowed(false);
        headerTabla.setResizingAllowed(false);

        tablaSA.setRowHeight(40);
        camposSA.addRow(new Object[5]);

        if (customFont != null) {
            tablaSA.setFont(customFont.deriveFont(Font.PLAIN, 16f));
            headerTabla.setFont(customFont.deriveFont(Font.PLAIN, 18f));
        } else {
            tablaSA.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
            headerTabla.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        }

        // Renderer personalizado para las filas
        tablaSA.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
        jSPane = new JScrollPane(tablaSA);
        jSPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jSPane.setBounds(200, 270, 900, 350);

        panelP.add(jSPane);

        add(panelP);
    }

    // Getters necesarios para el controlador
    public javax.swing.JComboBox<Integer> getCBAño() {
        return cBAño;
    }

    public javax.swing.JComboBox<String> getCBMes() {
        return cBMes;
    }

    public javax.swing.JButton getBtnBR() {
        return btnBR;
    }

    public javax.swing.JTable getTablaSA() {
        return tablaSA;
    }
}
