package controladorVendedor;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import vista.vistaVendedor.ModuloActualizarDatosProductos;
import vista.vistaVendedor.ModuloRegistrarModulos;
import vista.vistaVendedor.ModuloReposicionArticulos;
import vista.vistaVendedor.RecepcionDeOrdenes;
import vista.vistaVendedor.VistaVendedor;

import controladorVendedor.controladorRecepcionOrdenes;

public class ControladorVendedor implements MouseListener {

    private VistaVendedor vv = new VistaVendedor();

    public ControladorVendedor(VistaVendedor vv) {
        this.vv = vv;
        vv.getPanelRecepcionOrdenes().addMouseListener(this);
        vv.getPanelRecepcionOrdenes().setCursor(new Cursor(Cursor.HAND_CURSOR));
        vv.getPanelBajoStock().addMouseListener(this);
        vv.getPanelBajoStock().setCursor(new Cursor(Cursor.HAND_CURSOR));
        vv.getPanelSolicitudReposicion().addMouseListener(this);
        vv.getPanelSolicitudReposicion().setCursor(new Cursor(Cursor.HAND_CURSOR));
        vv.getPanelRegistroProductos().addMouseListener(this);
        vv.getPanelRegistroProductos().setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == vv.getPanelRecepcionOrdenes()) {
            recepcionOrdenes();
        }
        if (e.getSource() == vv.getPanelRegistroProductos()) {
            registroProducto();
        }
        if (e.getSource() == vv.getPanelBajoStock()) {
            bajoStock();
        }
        if (e.getSource() == vv.getPanelSolicitudReposicion()) {
            solicitudReposicion();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {

        colorEn((JPanel) e.getSource());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        colorSa((JPanel) e.getSource());

    }

    private void recepcionOrdenes() {

        vv.setVisible(false);
        RecepcionDeOrdenes vro = new RecepcionDeOrdenes();
        new controladorRecepcionOrdenes(vro);
        vro.setVisible(true);

        vro.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                vv.setVisible(true);
            }
        });
        vv.setVisible(false);
    }

    private void registroProducto() {
        JFrame rp = new JFrame("Registro de Productos");
        ControladorRegistrarProductos conRegisProd = new ControladorRegistrarProductos(rp, 1);
        ModuloRegistrarModulos moduloRegistro = new ModuloRegistrarModulos(rp, conRegisProd.getListarCatgorias());
        rp.setVisible(true);
        configurarCierreVentana(rp);
    }

    private void bajoStock() {

        JFrame ap = new JFrame("Actualizar de Productos");
        ControladorActualizarProductos conActualizarProd = new ControladorActualizarProductos(ap, 1);
        ModuloActualizarDatosProductos moduloActualizarProductos = new ModuloActualizarDatosProductos(ap,
                conActualizarProd.getListarCatgorias(), conActualizarProd.getListarEstado());
        ap.setVisible(true);
        configurarCierreVentana(ap);

    }

    private void solicitudReposicion() {
        JFrame sr = new JFrame("Solicitudes de Reposición");
        sr.setVisible(true);

        ModuloReposicionArticulos moduloReposicionArticulos = new ModuloReposicionArticulos(sr);
        controladorSolicitudesDeReposicion controladorSolicitudesDeReposicion = new controladorSolicitudesDeReposicion(
                sr, 1);
        configurarCierreVentana(sr);

    }

    private void colorSa(JPanel vs) {
        vs.setBackground(Color.white);
    }

    private void colorEn(JPanel vs) {
        vs.setBackground(new Color(240, 240, 240));
    }

    private void configurarCierreVentana(JFrame ventanaSecundaria) {
        vv.setVisible(false);

        javax.swing.SwingUtilities.invokeLater(() -> {
            ventanaSecundaria.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    vv.setVisible(true);
                }
            });
        });
    }
}
