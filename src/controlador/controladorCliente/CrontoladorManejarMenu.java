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

    public static void main(String[] args) throws IOException {
        PanelPrincipal menu = new PanelPrincipal();
        menu.setVisible(true);
        menu.setSize(1300, 700);
        ControladorCatalogo c = new ControladorCatalogo(menu);
        ControladorActividad ca = new ControladorActividad(menu);
        ControladorHistorial ch = new ControladorHistorial(menu);
        ControladorSeguimiento cs = new ControladorSeguimiento(menu);
        ControladorPQRS cpqrs = new ControladorPQRS(menu);
        CrontoladorManejarMenu ccerrar = new CrontoladorManejarMenu(menu);
    }

}
