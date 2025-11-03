package vista.vistaSupervisor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

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

public class VistaPQRS extends JFrame {
    private JPanel panelP;
    private JLabel lPQRS;
    private Font customFont;
    private JTable tablaPQRS;
    private DefaultTableModel camposPQRS;
    private JScrollPane jSPane;
    private Header header;

    public VistaPQRS() {
        setTitle("PQRS");
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        panelP = new JPanel();
        panelP.setBackground(Color.white);
        panelP.setLayout(null);
        panelP.setBounds(0, 0, 1300, 700);

        header = new Header();
        panelP.add(header);

        try {
            InputStream is = VistaPQRS.class.getResourceAsStream("/fonts/newCentury.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, is);
            is.close();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (Exception e) {
            e.printStackTrace();
        }

        lPQRS = new JLabel("Bandeja de entrada");
        if (customFont != null) {
            lPQRS.setFont(customFont.deriveFont(Font.PLAIN, 56f));
        } else {
            lPQRS.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 56));
        }
        lPQRS.setBounds(150, 100, 800, 80);

        panelP.add(lPQRS);

        camposPQRS = new DefaultTableModel();
        camposPQRS.addColumn("ID");
        camposPQRS.addColumn("Nombre Usuario");
        camposPQRS.addColumn("Asunto");
        camposPQRS.addColumn("Fecha Envio");

        tablaPQRS = new JTable(camposPQRS);

        JTableHeader headerTabla = tablaPQRS.getTableHeader();
        headerTabla.setPreferredSize(new Dimension(headerTabla.getWidth(), 35));
        headerTabla.setReorderingAllowed(false);
        headerTabla.setResizingAllowed(false);

        tablaPQRS.setRowHeight(40);
        camposPQRS.addRow(new Object[4]);

        tablaPQRS.getColumnModel().getColumn(0).setMinWidth(0);
        tablaPQRS.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaPQRS.getColumnModel().getColumn(0).setWidth(0);

        if (customFont != null) {
            tablaPQRS.setFont(customFont.deriveFont(Font.PLAIN, 18f));
            headerTabla.setFont(customFont.deriveFont(Font.PLAIN, 20f));
        } else {
            tablaPQRS.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
            headerTabla.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        }

        // Renderer personalizado para las filas
        tablaPQRS.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
        jSPane = new JScrollPane(tablaPQRS);
        jSPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jSPane.setBounds(150, 220, 1000, 400);

        panelP.add(jSPane);
        add(panelP);

    }

    public JTable getTabla() {
        return tablaPQRS;
    }

}
