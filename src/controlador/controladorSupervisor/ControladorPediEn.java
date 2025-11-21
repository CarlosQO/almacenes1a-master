package controladorSupervisor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modelo.crudPedidos.DaoPedido;
import modelo.crudPedidos.PedidoSupervisor;
import vista.vistaSupervisor.componentes.RoundedPanel;
import vista.vistaSupervisor.PedidosEntregados;

public class ControladorPediEn {

    private PedidosEntregados vpe = new PedidosEntregados();
    private JLabel lPEDesc;

    public ControladorPediEn(PedidosEntregados vpe) {
        this.vpe = vpe;
        crearPediEn();
    }

    public ControladorPediEn() {

    }

    private void crearPediEn() {

        DaoPedido rpdDao = new DaoPedido();

        // Obtener la lista y mostrar su contenido en consola
        List<PedidoSupervisor> lista = rpdDao.listarPE();
        if (lista == null || lista.isEmpty()) {
            JLabel vacio = new JLabel("No hay pedidos entregados.");
            vacio.setOpaque(false);
            vpe.getPanelPedido().add(vacio);
        } else {
            for (PedidoSupervisor r : lista) {
                // crear un panel por petición con layout claro
                JPanel cardPedido = new RoundedPanel(12, Color.lightGray);
                cardPedido.setBackground(Color.white);
                cardPedido.setLayout(new BorderLayout(10, 10));
                cardPedido.setMaximumSize(new Dimension(900, 100));
                cardPedido.setPreferredSize(new Dimension(900, 60));
                if (cardPedido instanceof RoundedPanel) {
                    ((RoundedPanel) cardPedido).setShadowSize(3);
                    ((RoundedPanel) cardPedido).setShadowColor(new Color(0, 0, 0, 90));
                }

                lPEDesc = new JLabel(
                        "<html>El pedido " + r.getId() + " fue entregado exitosamente </html>");
                lPEDesc.setPreferredSize(new Dimension(500, 300));
                lPEDesc.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));
                lPEDesc.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

                cardPedido.add(lPEDesc, BorderLayout.WEST);

                vpe.getPanelPedido().add(cardPedido);
                vpe.getPanelPedido().add(Box.createRigidArea(new Dimension(0, 8)));
            }
        }
    }
}
