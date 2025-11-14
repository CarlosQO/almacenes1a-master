package controladorCliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import vista.vistaCliente.PanelPrincipal;

public class CrontoladorManejarMenu implements ActionListener {
    private PanelPrincipal panelPrincipal;

    public CrontoladorManejarMenu(PanelPrincipal panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
        panelPrincipal.btnPerfil.addActionListener(this);
        panelPrincipal.pedidos.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelPrincipal.btnPerfil) {
            boolean estaVisiblePerfil = panelPrincipal.menuPerfil.isVisible();
            if (estaVisiblePerfil) {
                panelPrincipal.menuPerfil.setVisible(false);
            } else {
                panelPrincipal.menuPerfil.setVisible(true);
            }
        }
        if (e.getSource() == panelPrincipal.pedidos) {
            boolean estaVisiblePedidos = panelPrincipal.panelMenu.isVisible();
            if (estaVisiblePedidos) {
                panelPrincipal.panelMenu.setVisible(false);
            } else {
                panelPrincipal.panelMenu.setVisible(true);
            }
        }
    }

}
