package vista.vistaAdministrador;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import vista.componentes.FechasComponenteV2;
import vista.componentes.Header;
import vista.fuenteLetra.Fuente;

public class PaginaHistoricoVentas extends JFrame {
    private Container contenedor;
    private Header header;
    public FechasComponenteV2 containFechas;
    private JPanel containMayorTarjeta;
    public JPanel containInfo;
    private Fuente fuente = new Fuente();
    public JTable tablaVentas;
    public DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;
    private JLabel titulo;
    public JLabel fecha;

    public PaginaHistoricoVentas() {
        super("Historico de Ventas");
        /*
         * setLayout(null);
         * setLocationRelativeTo(null);
         */
        setSize(1300, 700);
        contenedor = getContentPane();
        contenedor.setLayout(null);
        contenedor.setBackground(Color.white);
        header = new Header();
        contenedor.add(header);
        containFechas = new FechasComponenteV2(15, 0xFFFFFFF, "Generar Histórico", "Histórico de Ventas");
        // containFechas.agregarBtnDescargarInforme("Descargar Historico");
        // containFechas.setBounds(10, header.getHeight() + 20, 850, 110); //con el
        // boton de descargar
        containFechas.setBounds(300, header.getHeight() + 20, 640, 110);
        contenedor.add(containFechas);

        // Contenedor mayor
        containMayorTarjeta = new JPanel();
        containMayorTarjeta.setBounds(50, containFechas.getY() + containFechas.getHeight() + 20, 1200, 400);
        containMayorTarjeta.setLayout(null);
        containMayorTarjeta.setBackground(Color.white);

        // Título
        titulo = new JLabel("Histórico de Ventas");
        titulo.setFont(fuente.fuente(2, true));
        titulo.setBounds(370, 10, 700, 40);
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
        String[] columnas = { "Fecha Venta", "Codigo", "Nombre Cliente", "Producto Vendido",
                "Precio Unitario", "Cantidad", "Subtotal", "Total", "Metodo Pago", "Estado Venta" };
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaVentas = new JTable(modeloTabla);
        configurarEncabezadosTabla();
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
        scrollTabla.setBounds(0, titulo.getY() + titulo.getHeight() + 10, containMayorTarjeta.getWidth(), 320);
        containInfo.add(scrollTabla);
        containMayorTarjeta.add(scrollTabla);

        contenedor.add(containMayorTarjeta);
    }

    private void configurarEncabezadosTabla() {
        // Permite saltos de línea en los nombres de las columnas
        tablaVentas.getTableHeader().setDefaultRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JLabel label = new JLabel("<html><center>" + value.toString().replace(" ", "<br>") + "</center></html>");
            label.setFont(fuente.fuente(5, true));
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setOpaque(true);
            label.setBackground(new Color(0xE0E0E0));
            label.setBorder(javax.swing.BorderFactory.createLineBorder(Color.BLACK));
            return label;
        });
    }

    public static void main(String[] args) {
        PaginaHistoricoVentas phv = new PaginaHistoricoVentas();
        phv.setLocationRelativeTo(null);
        phv.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        phv.setVisible(true);
    }
}
