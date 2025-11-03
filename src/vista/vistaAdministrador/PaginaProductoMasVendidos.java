package vista.vistaAdministrador;

import java.awt.Color;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JLabel;

import vista.componentes.FechasComponente;
import vista.componentes.Header;
import vista.fuenteLetra.Fuente;

public class PaginaProductoMasVendidos extends JFrame {

    private Container contenedor;
    private Header header;

    public FechasComponente containFechas;
    public RoundedPanel containTarjetas, containMayorTarjeta;
    public JLabel tituloMasVendidos;
    private Fuente fuente = new Fuente();

    public PaginaProductoMasVendidos() {
        super("Productos más vendidos");
        contenedor = getContentPane();
        contenedor.setLayout(null);
        contenedor.setBackground(Color.white);

        // HEADE
        header = new Header();
        contenedor.add(header);

        // COMPONENTE DE FECHAS
        containFechas = new FechasComponente(5, 0x000000, "Generar Reporte", "Productos Mas Vendidos");
        containFechas.setBounds(10, 120, 200, 210);
        contenedor.add(containFechas);

        // CONTENEDOR MAYOR DE TARJETAS
        containMayorTarjeta = new RoundedPanel(20, 0xF8F9FB);
        containMayorTarjeta.setBounds(280, 90, 940, 560);
        containMayorTarjeta.setLayout(null);
        containMayorTarjeta.setBackground(new Color(0xF8F9FB));
        containMayorTarjeta.setShadowSize(1);
        containMayorTarjeta.setVisible(false);

        // CONTENEDOR DE TARJETAS
        containTarjetas = new RoundedPanel(20, 0xF8F9FB);
        containTarjetas.setLayout(null);
        containTarjetas.setBackground(new Color(0xF8F9FB));
        containTarjetas.setShadowSize(1);
        containTarjetas.setBounds(2, 50, 940, 510);
        containMayorTarjeta.add(containTarjetas);

        contenedor.add(containMayorTarjeta);

        // TÍTULO
        tituloMasVendidos = new JLabel("Productos más vendidos");
        tituloMasVendidos.setFont(fuente.fuente(2, true));
        tituloMasVendidos.setBounds(180, 10, 700, 40);
        containMayorTarjeta.add(tituloMasVendidos);
    }
}
