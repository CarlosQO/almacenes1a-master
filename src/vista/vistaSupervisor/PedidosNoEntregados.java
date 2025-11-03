package vista.vistaSupervisor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import vista.componentes.Header;
import vista.vistaSupervisor.componentes.RoundedPanel;

public class PedidosNoEntregados extends JFrame {

    private JPanel panelP, panelPedido;
    private RoundedPanel panelPNoE;
    private JLabel lPedidoNoE;
    private Header header;

    public PedidosNoEntregados() {

        setTitle("Pedidos Entregados");
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        panelP = new JPanel();
        panelP.setBackground(Color.white);
        panelP.setLayout(null);
        panelP.setBounds(0, 0, 1300, 700);

        header = new Header();

        panelP.add(header);

        // Crear el contenedor interior que alojará múltiples panelSoli en columna
        panelPedido = new JPanel();
        panelPedido.setLayout(new BoxLayout(panelPedido, BoxLayout.Y_AXIS));
        panelPedido.setOpaque(false); // dejamos que el RoundedPanel exterior pinte el fondo

        lPedidoNoE = new JLabel("Pedidos No Entregados");
        lPedidoNoE.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        lPedidoNoE.setBounds(500, 140, 800, 80);
        panelP.add(lPedidoNoE);

        // Envolver en un JScrollPane
        JScrollPane scroll = new JScrollPane(panelPedido);
        scroll.setBounds(30, 60, 940, 390);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // Hacer transparente el viewport para que se vea el fondo redondeado
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);

        // Panel exterior con esquinas redondeadas que contendrá al JScrollPane
        panelPNoE = new RoundedPanel(20, Color.LIGHT_GRAY);
        panelPNoE.setShadowSize(1);
        panelPNoE.setLayout(null);
        panelPNoE.setBackground(Color.white);
        panelPNoE.setBounds(150, 150, 1000, 480);
        panelPNoE.add(scroll, BorderLayout.CENTER);

        panelP.add(panelPNoE);

        add(panelP);

    }

    public JPanel getPanelPedidoNoE() {
        return panelPedido;
    }

}
