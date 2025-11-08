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
    public JTable tablaVentas;
    public DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;

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

        // Contenedor de información
        containInfo = new JPanel();
        containInfo.setLayout(null);
        containInfo.setBackground(Color.white);
        containInfo.setBounds(title.getX(), title.getHeight() + title.getY() + 50, 940, 370);
        // Tabla dentro de containInfo
        String[] columnas = { "<html><p>Nombres<br>Completos</p></html>", "Cedula",
                "<html><p>Producto/<br>Servicio</p></html>",
                "Dirección", "Telefono", "Correo", "Acciones" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaVentas = new JTable(modeloTabla);
        tablaVentas.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 45));
        tablaVentas.getTableHeader().setFont(fuente.fuente(5, true));
        tablaVentas.getColumnModel().setColumnMargin(10);
        tablaVentas.setRowSelectionAllowed(false); // No permite seleccionar filas
        tablaVentas.setColumnSelectionAllowed(false); // No permite seleccionar columnas
        tablaVentas.setCellSelectionEnabled(false);

        // Estilo visual
        tablaVentas.getTableHeader().setReorderingAllowed(false);
        tablaVentas.getTableHeader().setResizingAllowed(false);
        tablaVentas.setRowHeight(30);
        tablaVentas.setBackground(new Color(0xD7EEFF)); // color celeste suave
        tablaVentas.setGridColor(Color.BLACK);

        // ScrollPane dentro de containInfo
        scrollTabla = new JScrollPane(tablaVentas);
        scrollTabla.setBounds(0, 0, containInfo.getWidth(), containInfo.getHeight());
        containInfo.add(scrollTabla);

        contenedor.add(containInfo);
    }
}
