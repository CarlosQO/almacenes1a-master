package vista.vistaVendedor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

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

import vista.vistaVendedor.componentes.Header;

public class RecepcionDeOrdenes extends JFrame {

    private JPanel header, panelP;
    private JLabel lRecepOrden, lEstadoOrden;
    private Font customFont;
    private JComboBox<String> cBEO;
    private JTable tablaOrdenes;
    private DefaultTableModel camposOrdenes;
    private JScrollPane jSPane;

    public RecepcionDeOrdenes() {
        setTitle("Recepción de Órdenes");
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        panelP = new JPanel();
        panelP.setBackground(Color.white);
        panelP.setLayout(null);
        panelP.setBounds(0, 0, 1300, 700);

        header = new Header().headerCopiar(Color.WHITE, 1300);
        panelP.add(header);

        try {
            InputStream is = RecepcionDeOrdenes.class.getResourceAsStream("/fonts/newCentury.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, is);
            is.close();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (Exception e) {
            e.printStackTrace();
        }

        lRecepOrden = new JLabel("Recepción de Órdenes");
        lRecepOrden.setBounds(150, 60, 800, 100);

        panelP.add(lRecepOrden);

        lEstadoOrden = new JLabel("Estado de la Orden:");
        lEstadoOrden.setBounds(150, 150, 350, 50);
        lEstadoOrden.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        add(lEstadoOrden);

        if (customFont != null) {
            lRecepOrden.setFont(customFont.deriveFont(Font.PLAIN, 36f));
            lEstadoOrden.setFont(customFont.deriveFont(Font.PLAIN, 22f));
        } else {
            lRecepOrden.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 36));
            lEstadoOrden.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
        }

        cBEO = new JComboBox<>();
        cBEO.setBounds(380, 155, 200, 35);
        add(cBEO);

        camposOrdenes = new DefaultTableModel();
        camposOrdenes.addColumn("ID Orden");
        camposOrdenes.addColumn("Cliente");
        camposOrdenes.addColumn("Productos");
        camposOrdenes.addColumn("Estado");

        tablaOrdenes = new JTable(camposOrdenes);

        JTableHeader headerTabla = tablaOrdenes.getTableHeader();
        headerTabla.setPreferredSize(new Dimension(headerTabla.getWidth(), 35));
        headerTabla.setReorderingAllowed(false);
        headerTabla.setResizingAllowed(false);

        tablaOrdenes.setRowHeight(40);

        if (customFont != null) {
            tablaOrdenes.setFont(customFont.deriveFont(Font.PLAIN, 18f));
            headerTabla.setFont(customFont.deriveFont(Font.PLAIN, 20f));
        } else {
            tablaOrdenes.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));
            headerTabla.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
        }

        // Renderer personalizado para las filas
        tablaOrdenes.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                if (column == 4) { // Columna de acciones
                    JPanel panel = new JPanel();
                    panel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0));
                    panel.setBackground(isSelected ? new Color(184, 207, 229) : row == 0 ? Color.WHITE : Color.cyan);

                    if (row > 0) { // Solo mostrar botones después de la primera fila
                        javax.swing.JButton btnCancelar = new javax.swing.JButton("Cancelar");
                        javax.swing.JButton btnEntregar = new javax.swing.JButton("Entregar");

                        btnCancelar.setPreferredSize(new Dimension(90, 30));
                        btnEntregar.setPreferredSize(new Dimension(90, 30));

                        panel.add(btnCancelar);
                        panel.add(btnEntregar);
                    }

                    return panel;
                }

                JLabel c = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                c.setBorder(new EmptyBorder(5, 10, 5, 10)); // margen interno

                // Si es la columna de acciones (columna 4) y el valor está vacío, ocultar el
                // contenido
                if (column == 4 && (value == null || value.toString().isEmpty())) {
                    c.setText("");
                    c.setBackground(table.getBackground());
                } else if (isSelected) {
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
        jSPane = new JScrollPane(tablaOrdenes);
        jSPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jSPane.setBounds(150, 220, 1000, 400);

        panelP.add(jSPane);
        add(panelP);

    }

    public JComboBox<String> getCBEO() {
        return cBEO;
    }

    public DefaultTableModel getModeloTabla() {
        return camposOrdenes;
    }

    public JTable getTablaOrdenes() {
        return tablaOrdenes;
    }

    public void setColumnAccionesVisible(boolean visible) {
        if (visible) {
            if (camposOrdenes.findColumn("Acciones") == -1) {
                camposOrdenes.addColumn("Acciones");
            }
        } else {
            int columnIndex = camposOrdenes.findColumn("Acciones");
            if (columnIndex != -1) {
                camposOrdenes.setColumnCount(4); // Elimina la última columna del modelo
            }
        }
    }
}
