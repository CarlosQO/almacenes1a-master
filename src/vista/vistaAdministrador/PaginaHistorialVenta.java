package vista.vistaAdministrador;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import vista.componentes.FechasComponente;
import vista.componentes.Header;
import vista.fuenteLetra.Fuente;

public class PaginaHistorialVenta extends JFrame {

    private Container contenedor;
    private Header header;
    public FechasComponente containFechas;
    private JLabel titulo;
    public JLabel fecha;
    private JPanel containMayorTarjeta;
    public JPanel containInfo;
    private Fuente fuente = new Fuente();
    public JTable tablaVentas;
    public DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;

    public PaginaHistorialVenta() {
        super("Historial de Venta");
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

        // Contenedor de fechas
        containFechas = new FechasComponente(5, 0x000000, "Generar Reporte", "Historial de Venta");
        containFechas.setBounds(10, 120, 200, 210);
        contenedor.add(containFechas);

        // Contenedor mayor
        containMayorTarjeta = new JPanel();
        containMayorTarjeta.setBounds(280, 90, 940, 560);
        containMayorTarjeta.setLayout(null);
        containMayorTarjeta.setBackground(Color.white);

        // Título
        titulo = new JLabel("Historial de Ventas");
        titulo.setFont(fuente.fuente(2, true));
        titulo.setBounds(270, 10, 700, 40);
        containMayorTarjeta.add(titulo);

        // Contenedor de información
        containInfo = new JPanel();
        containInfo.setVisible(false);
        containInfo.setLayout(null);
        containInfo.setBackground(Color.white);
        containInfo.setBounds(2, 50, 940, 510);
        containMayorTarjeta.add(containInfo);
        // Agregar la fecha de la consulta
        fecha = new JLabel();
        fecha.setFont(fuente.fuente(4, true));
        fecha.setBounds(100, 20, 720, 40);
        containInfo.add(fecha);

        // Tabla dentro de containInfo
        String[] columnas = { "Id Producto", "Nombre del producto", "Cantidad de Venta", "Valor Unitario",
                "Valor Total" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaVentas = new JTable(modeloTabla);
        tablaVentas.getColumnModel().getColumn(0).setPreferredWidth(30);
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
        scrollTabla.setBounds(100, 80, 800, 390);
        containInfo.add(scrollTabla);

        contenedor.add(containMayorTarjeta);
    }

    public void modificarTamañoFecha(int Largo) {
        // 260 o 270 de largo
        // ocultar 210
        containFechas.setSize(new Dimension(200, Largo));
    }
}
