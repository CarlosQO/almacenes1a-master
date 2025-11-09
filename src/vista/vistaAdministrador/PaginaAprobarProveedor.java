package vista.vistaAdministrador;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Container;
import vista.componentes.Header;
import vista.fuenteLetra.Fuente;

public class PaginaAprobarProveedor extends JFrame {
    private JLabel title;
    private Container contenedor;
    private Header header;
    public JPanel containInfo;
    private Fuente fuente = new Fuente();
    public JTable tablaProveedores;
    public DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;
    public JLabel noHayProvee;

    public PaginaAprobarProveedor() {
        super("Aprobar proveedor");
        setSize(1300, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        contenedor = getContentPane();
        contenedor.setLayout(null);
        contenedor.setBackground(Color.white);

        // Estilo del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Header
        header = new Header();
        contenedor.add(header);

        title = new JLabel("Aprobar proveedor");
        title.setFont(fuente.fuente(2, false));
        title.setBounds(100, header.getHeight() + header.getY() + 30, (int) title.getPreferredSize().getWidth(),
                40);
        contenedor.add(title);

        noHayProvee = new JLabel();
        noHayProvee.setVisible(false);
        noHayProvee.setText("No hay proveedores por aprobar");
        noHayProvee.setFont(fuente.fuente(3, false));
        noHayProvee.setBounds(400, title.getHeight() + title.getY() + 150,
                (int) noHayProvee.getPreferredSize().getWidth() + 50,
                40);
        contenedor.add(noHayProvee);

        // Contenedor de información
        containInfo = new JPanel();
        containInfo.setVisible(false);
        containInfo.setLayout(null);
        containInfo.setBackground(Color.white);
        containInfo.setBounds(title.getX(), title.getHeight() + title.getY() + 50, 940 + 170, 370);
        // Tabla dentro de containInfo
        String[] columnas = { "<html><p>Nombres<br>Completos</p></html>", "Cedula",
                "<html><p>Producto/<br>Servicio</p></html>",
                "Dirección", "Telefono", "Correo", "Acciones" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 0 && column < getColumnCount() && column == 6;
            }

        };
        tablaProveedores = new JTable(modeloTabla);
        tablaProveedores.getColumnModel().getColumn(1).setPreferredWidth(20);
        tablaProveedores.getColumnModel().getColumn(4).setPreferredWidth(10);
        tablaProveedores.getColumnModel().getColumn(6).setPreferredWidth(140);
        tablaProveedores.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 45));
        tablaProveedores.getTableHeader().setFont(fuente.fuente(5, true));
        tablaProveedores.getColumnModel().setColumnMargin(10);
        tablaProveedores.setRowSelectionAllowed(false); // No permite seleccionar filas
        tablaProveedores.setColumnSelectionAllowed(false); // No permite seleccionar columnas
        tablaProveedores.setCellSelectionEnabled(false);

        // Estilo visual
        tablaProveedores.getTableHeader().setReorderingAllowed(false);
        tablaProveedores.getTableHeader().setResizingAllowed(false);
        tablaProveedores.setRowHeight(30);
        tablaProveedores.setBackground(new Color(0xD7EEFF)); // color celeste suave
        tablaProveedores.setGridColor(Color.BLACK);

        // ScrollPane dentro de containInfo
        scrollTabla = new JScrollPane(tablaProveedores);
        scrollTabla.setBounds(0, 0, containInfo.getWidth(), containInfo.getHeight());
        containInfo.add(scrollTabla);

        contenedor.add(containInfo);
    }
}
