package vista.vistaAdministrador;

import java.awt.Color;
import java.awt.Container;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import vista.componentes.Header;
import vista.componentes.Tarjetas;
import vista.fuenteLetra.Fuente;

public class PaginaPromociones extends JFrame {
    private Container contenedor;
    private Header header;
    private JLabel titulo;
    public JButton regresar;
    public RoundedPanel contenedorImagen;
    public Tarjetas tarjeta;
    private vista.fuenteLetra.Fuente f = new Fuente();

    public PaginaPromociones() throws IOException {
        contenedor = getContentPane();
        contenedor.setLayout(null);
        contenedor.setBackground(Color.WHITE);

        header = new Header();
        contenedor.add(header);

        // añadir el contenedor para el titulo y las tarjetas
        contenedorImagen = new RoundedPanel(20, 0xF8F9FB);
        contenedorImagen.setLayout(null);
        contenedorImagen.setBackground(new Color(0xF8F9FB));
        contenedorImagen.setShadowSize(1);
        contenedorImagen.setBounds(180, 90, 940, 560);
        contenedor.add(contenedorImagen);
        // añadir el titulo
        titulo = new JLabel("Productos menos vendidos");
        titulo.setFont(f.fuente(2, false));
        titulo.setBounds(180, 10, (int) titulo.getPreferredSize().getWidth(),
                (int) titulo.getPreferredSize().getHeight());
        contenedorImagen.add(titulo);

    }
}
