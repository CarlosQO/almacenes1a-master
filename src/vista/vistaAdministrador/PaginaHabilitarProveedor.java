package vista.vistaAdministrador;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;

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

        title = new JLabel("Aprobar proveedor");
        title.setFont(fuente.fuente(2, false));
        title.setBounds(100, header.getHeight() + header.getY() + 30, (int) title.getPreferredSize().getWidth(),
                40);
        contenedor.add(title);

        panelBusqueda = new RoundedPanel(20, 0x000000);
        panelBusqueda.setBounds(title.getX(), header.getHeight() + header.getY() + 100, 300, 50);
        panelBusqueda.setLayout(null);
        panelBusqueda.setShadowSize(1);

        busqueda = new JXTextField();
        limitarCaracteres(busqueda, 10);
        busqueda.setFont(fuente.fuente(3, false));
        busqueda.setBackground(null);
        busqueda.setOpaque(false);
        busqueda.setBorder(null);
        busqueda.setPrompt("Documento o NIT");
        busqueda.setPromptForeground(new Color(128, 128, 128));
        limitarCaracteres(busqueda, 20);
        busqueda.setBounds(1, 0, 250, 40);
        panelBusqueda.add(busqueda);

        contenedor.add(panelBusqueda);
    }

    public static void main(String[] args) {
        PaginaHabilitarProveedor ventana = new PaginaHabilitarProveedor();
        ventana.setVisible(true);
    }
}
