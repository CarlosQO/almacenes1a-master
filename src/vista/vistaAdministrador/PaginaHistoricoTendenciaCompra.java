package vista.vistaAdministrador;

import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import vista.componentes.Header;
import vista.fuenteLetra.Fuente;
import vista.componentes.FechasContainMesAño;

public class PaginaHistoricoTendenciaCompra extends JFrame {

    private Container contenedor;
    private Header header;
    public JLabel fecha;
    public FechasContainMesAño containFechas; // Nuevo componente de año y mes
    public RoundedPanel containInfo, containMayorTarjeta;
    public JLabel tituloHistorico;
    private Fuente fuente = new Fuente();
    public DefaultTableModel modeloTabla;
    public JTable tablaTendencia;
    private JScrollPane scrollTabla;

    public PaginaHistoricoTendenciaCompra() {
        super("Histórico de Tendencia de Compra");
        contenedor = getContentPane();
        contenedor.setLayout(null);
        contenedor.setBackground(Color.white);

        // HEADER
        header = new Header();
        contenedor.add(header);

        // COMPONENTE DE FECHAS (reemplazado por PanelHistorico)
        containFechas = new FechasContainMesAño();
        containFechas.setBackground(new Color(0xF8F9FB));
        containFechas.setBounds(10, 120, 200, 180);
        contenedor.add(containFechas);
        // CONTENEDOR MAYOR DE TARJETAS
        containMayorTarjeta = new RoundedPanel(20, 0xF8F9FB);
        containMayorTarjeta.setBounds(280, 90, 940, 560);
        containMayorTarjeta.setLayout(null);
        containMayorTarjeta.setBackground(new Color(0xF8F9FB));
        containMayorTarjeta.setShadowSize(1);

        // CONTENEDOR DE TARJETAS
        containInfo = new RoundedPanel(20, 0xF8F9FB);
        containInfo.setLayout(null);
        containInfo.setBackground(new Color(0xF8F9FB));
        containInfo.setShadowSize(1);
        containInfo.setBounds(2, 50, 940, 510);
        containMayorTarjeta.add(containInfo);
        //agregar la fecha de la consulta
        fecha = new JLabel();
        fecha.setFont(fuente.fuente(4, true));
        fecha.setBounds(100, 20, 720, 40);
        containInfo.add(fecha);

        // TÍTULO
        tituloHistorico = new JLabel("Histórico de Tendencia de Compra");
        tituloHistorico.setFont(fuente.fuente(2, true));
        tituloHistorico.setBounds(180, 10, 700, 40);
        containMayorTarjeta.add(tituloHistorico);
     
        //  Tabla dentro de containInfo
        String[] columnas = {"Id Producto","Producto", "Cantidad Venta","Valor Und","Valor Total"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaTendencia = new JTable(modeloTabla);
        tablaTendencia.getColumnModel().getColumn(0).setPreferredWidth(30);
        tablaTendencia.getTableHeader().setPreferredSize(new java.awt.Dimension(0, 45));
        tablaTendencia.getTableHeader().setFont(fuente.fuente(5, true));
        tablaTendencia.getColumnModel().setColumnMargin(10);

        tablaTendencia.setRowSelectionAllowed(false);   // No permite seleccionar filas
        tablaTendencia.setColumnSelectionAllowed(false); // No permite seleccionar columnas
        tablaTendencia.setCellSelectionEnabled(false);

        // Estilo visual
        tablaTendencia.getTableHeader().setReorderingAllowed(false);
        tablaTendencia.getTableHeader().setResizingAllowed(false);
        tablaTendencia.setRowHeight(30);
        tablaTendencia.setBackground(new Color(0xD7EEFF)); // color celeste suave
        tablaTendencia.setGridColor(Color.BLACK);

        // ScrollPane dentro de containInfo
        scrollTabla = new JScrollPane(tablaTendencia);
        scrollTabla.setBounds(80, 80, 800, 390);
        containInfo.add(scrollTabla);
        
        
        contenedor.add(containMayorTarjeta);
    }

    // MAIN de prueba
    public static void main(String[] args) {
        PaginaHistoricoTendenciaCompra frame = new PaginaHistoricoTendenciaCompra();
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
