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

public class PedidosEntregados extends JFrame {

    private JPanel panelP, panelPedido;
    private RoundedPanel panelPE;
    private JLabel lPedidoE;
    private Header header;

    public PedidosEntregados() {

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

        lPedidoE = new JLabel("Pedidos Entregados");
        lPedidoE.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));
        lPedidoE.setBounds(500, 140, 800, 80);
        panelP.add(lPedidoE);

        // Envolver en un JScrollPane
        JScrollPane scroll = new JScrollPane(panelPedido);
        scroll.setBounds(30, 60, 940, 390);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // Hacer transparente el viewport para que se vea el fondo redondeado
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);

        // Panel exterior con esquinas redondeadas que contendrá al JScrollPane
        panelPE = new RoundedPanel(20, Color.lightGray);
        panelPE.setShadowSize(1);
        panelPE.setLayout(null);
        panelPE.setBackground(Color.white);
        panelPE.setBounds(150, 150, 1000, 480);
        panelPE.add(scroll, BorderLayout.CENTER);

        panelP.add(panelPE);

        add(panelP);
    }

    public JPanel getPanelPedido() {
        return panelPedido;
    }

}
