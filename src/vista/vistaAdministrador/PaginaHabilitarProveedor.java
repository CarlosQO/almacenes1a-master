package vista.vistaAdministrador;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

import org.jdesktop.swingx.JXTextField;

import vista.componentes.Header;
import vista.componentes.RoundedJXButton;
import vista.fuenteLetra.Fuente;
import static vista.componentes.CamposConLimite.limitarCaracteres;

public class PaginaHabilitarProveedor extends JFrame {
    private JLabel title;
    private Container contenedor;
    private Header header;
    private Fuente fuente = new Fuente();
    public JXTextField busqueda;
    public RoundedJXButton buscar;
    private RoundedPanel panelBusqueda;
    public DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;
    public JPanel containInfo;
    public JTable tablaProveInactivo;
    public JLabel noHayProvee;

    public PaginaHabilitarProveedor() {
        super("Habilitar proveedor");
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

        title = new JLabel("Habilitar proveedor");
        title.setFont(fuente.fuente(2, false));
        title.setBounds(100, header.getHeight() + header.getY() + 30, (int) title.getPreferredSize().getWidth(),
                40);
        contenedor.add(title);

        noHayProvee = new JLabel();
        noHayProvee.setVisible(false);
        noHayProvee.setText("No hay proveedores por habilitar");
        noHayProvee.setFont(fuente.fuente(3, false));
        noHayProvee.setBounds(400, title.getHeight() + title.getY() + 150,
                (int) noHayProvee.getPreferredSize().getWidth() + 50,
                40);
        contenedor.add(noHayProvee);

        panelBusqueda = new RoundedPanel(20, 0x000000);
        panelBusqueda.setBounds(title.getX(), header.getHeight() + header.getY() + 100, 300, 50);
        panelBusqueda.setLayout(null);
        panelBusqueda.setShadowSize(1);

        busqueda = new JXTextField();
        busqueda.setPrompt("Documento o NIT");
        busqueda.setPromptForeground(new Color(128, 128, 128));
        busqueda.setFont(fuente.fuente(3, false));
        busqueda.setBackground(null);
        busqueda.setBorder(null);
        limitarCaracteres(busqueda, 10);
        busqueda.setBounds(8, 5, 270, 40);
        panelBusqueda.add(busqueda);

        buscar = new RoundedJXButton("Buscar");
        buscar.setFont(fuente.fuente(3, false));
        buscar.setBounds(panelBusqueda.getWidth() + panelBusqueda.getX() + 20, panelBusqueda.getY(), 200, 50);
        contenedor.add(buscar);
        contenedor.add(panelBusqueda);

        // Contenedor de información
        containInfo = new JPanel();
        containInfo.setLayout(null);
        containInfo.setBackground(Color.white);
        containInfo.setBounds(title.getX(), panelBusqueda.getHeight() + panelBusqueda.getY() + 20, 940 + 170, 370);

        String[] columnas = { "<html><p>Nombres<br>Proveedor</p></html>", "<html><p>Documento<br>NIT</p></html>",
                "<html><p>Producto/<br>Servicio</p></html>",
                "Medio de Pago", "Contacto", "Acciones" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column >= 0 && column < getColumnCount() && column == 5;
            }

        };
        tablaProveInactivo = new JTable(modeloTabla);
        tablaProveInactivo.getColumnModel().getColumn(1).setPreferredWidth(20);
        tablaProveInactivo.getColumnModel().getColumn(4).setPreferredWidth(10);
        tablaProveInactivo.getColumnModel().getColumn(5).setPreferredWidth(140);
        tablaProveInactivo.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 45));
        tablaProveInactivo.getTableHeader().setFont(fuente.fuente(5, true));
        tablaProveInactivo.getColumnModel().setColumnMargin(10);
        tablaProveInactivo.setRowSelectionAllowed(false); // No permite seleccionar filas
        tablaProveInactivo.setColumnSelectionAllowed(false); // No permite seleccionar columnas
        tablaProveInactivo.setCellSelectionEnabled(false);

        // Estilo visual
        tablaProveInactivo.getTableHeader().setReorderingAllowed(false);
        tablaProveInactivo.getTableHeader().setResizingAllowed(false);
        tablaProveInactivo.setRowHeight(30);
        tablaProveInactivo.setBackground(new Color(0xD7EEFF)); // color celeste suave
        tablaProveInactivo.setGridColor(Color.BLACK);

        // ScrollPane dentro de containInfo
        scrollTabla = new JScrollPane(tablaProveInactivo);
        scrollTabla.setBounds(0, 0, containInfo.getWidth(), containInfo.getHeight());
        containInfo.add(scrollTabla);

        contenedor.add(containInfo);

    }
}
