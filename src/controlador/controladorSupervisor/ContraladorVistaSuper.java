package controladorSupervisor;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import vista.vistaSupervisor.PedidosEntregados;
import vista.vistaSupervisor.PedidosNoEntregados;
import vista.vistaSupervisor.PreRegistroProveedor;
import vista.vistaSupervisor.ReportesOperativos;
import vista.vistaSupervisor.SeguimientoAdministrador;
import vista.vistaSupervisor.SeguimientoVendedor;
import vista.vistaSupervisor.SolicitudesReposiciones;
import vista.vistaSupervisor.VistaPQRS;
import vista.vistaSupervisor.VistaSupervisor;

public class ContraladorVistaSuper implements MouseListener {

    private VistaSupervisor vs = new VistaSupervisor();

    public ContraladorVistaSuper(VistaSupervisor vs) {
        this.vs = vs;
        vs.setVisible(true);
        vs.setLocationRelativeTo(null);
        vs.getPanelProve().addMouseListener(this);
        vs.getPanelProve().setCursor(new Cursor(Cursor.HAND_CURSOR));
        vs.getPanelSeguiVen().addMouseListener(this);
        vs.getPanelSeguiVen().setCursor(new Cursor(Cursor.HAND_CURSOR));
        vs.getPanelSeguiAdm().addMouseListener(this);
        vs.getPanelSeguiAdm().setCursor(new Cursor(Cursor.HAND_CURSOR));
        vs.getPanelPediEn().addMouseListener(this);
        vs.getPanelPediEn().setCursor(new Cursor(Cursor.HAND_CURSOR));
        vs.getPanelPediNoEn().addMouseListener(this);
        vs.getPanelPediNoEn().setCursor(new Cursor(Cursor.HAND_CURSOR));
        vs.getPanelPQRS().addMouseListener(this);
        vs.getPanelPQRS().setCursor(new Cursor(Cursor.HAND_CURSOR));
        vs.getPanelSoliRepo().addMouseListener(this);
        vs.getPanelSoliRepo().setCursor(new Cursor(Cursor.HAND_CURSOR));
        vs.getPanelRepor().addMouseListener(this);
        vs.getPanelRepor().setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public ContraladorVistaSuper() {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == vs.getPanelProve()) {
            preReProVista();
        }
        if (e.getSource() == vs.getPanelPQRS()) {
            PQRS();
        }
        if (e.getSource() == vs.getPanelPediEn()) {
            pedidoEntregado();
        }
        if (e.getSource() == vs.getPanelPediNoEn()) {
            pedidoNoEntregado();
        }
        if (e.getSource() == vs.getPanelSoliRepo()) {
            soliReposiciones();
        }
        if (e.getSource() == vs.getPanelRepor()) {
            reportesOperativos();
        }
        if (e.getSource() == vs.getPanelSeguiAdm()) {
            seguimientoAdministrador();
        }
        if (e.getSource() == vs.getPanelSeguiVen()) {
            seguimientoVendedor();
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

    private void preReProVista() {
        vs.setVisible(false);
        PreRegistroProveedor vprp = new PreRegistroProveedor();
        new ControladorProve(vprp);
        vprp.setVisible(true);
        configurarCierreVentana(vprp);
    }

    private void seguimientoVendedor() {
        vs.setVisible(false);
        SeguimientoVendedor vsv = new SeguimientoVendedor();
        new ControladorSeguimientoVendedor(vsv);
        vsv.setVisible(true);
        configurarCierreVentana(vsv);
    }

    private void seguimientoAdministrador() {
        vs.setVisible(false);
        SeguimientoAdministrador vsa = new SeguimientoAdministrador();
        new ControladorSeguimientoAdmin(vsa);
        vsa.setVisible(true);
        configurarCierreVentana(vsa);
    }

    private void reportesOperativos() {
        vs.setVisible(false);
        ReportesOperativos vro = new ReportesOperativos();
        new ControladorReportesOperativos(vro);
        vro.setVisible(true);
        configurarCierreVentana(vro);
    }

    private void PQRS() {
        vs.setVisible(false);
        VistaPQRS vpqrs = new VistaPQRS();
        new ControladorPQRS(vpqrs);
        vpqrs.setVisible(true);
        configurarCierreVentana(vpqrs);
    }

    private void pedidoEntregado() {
        vs.setVisible(false);
        PedidosEntregados vpe = new PedidosEntregados();
        new ControladorPediEn(vpe);
        vpe.setVisible(true);
        configurarCierreVentana(vpe);
    }

    private void pedidoNoEntregado() {
        vs.setVisible(false);
        PedidosNoEntregados vpne = new PedidosNoEntregados();
        new ControladorPediNoEn(vpne);
        vpne.setVisible(true);
        configurarCierreVentana(vpne);
    }

    private void soliReposiciones() {
        vs.setVisible(false);
        SolicitudesReposiciones vsr = new SolicitudesReposiciones();
        new ControladorSoliRepo(vsr);
        vsr.setVisible(true);
        configurarCierreVentana(vsr);
    }

    private void configurarCierreVentana(JFrame ventanaSecundaria) {
        vs.setVisible(false);

        javax.swing.SwingUtilities.invokeLater(() -> {
            ventanaSecundaria.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    vs.setVisible(true);
                }
            });
        });
    }

    private void colorSa(JPanel vs) {
        vs.setBackground(Color.white);
    }

    private void colorEn(JPanel vs) {
        vs.setBackground(new Color(240, 240, 240));
    }

}
