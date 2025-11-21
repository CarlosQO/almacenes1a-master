package vista.vistaSupervisor;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vista.componentes.Header;
import vista.vistaSupervisor.componentes.RoundedPanel;

public class VistaSupervisor extends JFrame {

    private JPanel panelP, panelHeader, panelVS, panelBtns, panelProve, panelSeguiVen, panelSeguiAdm,
            panelPediEn, panelPediNoEn, panelPQRS, panelSoliRepo, panelRepor;
    private JLabel lTitulo, lProveedores, lPreRePro, lSeguimientos, lPedidos, lSolicitud, lReportes;
    private Font customFont;
    private Header header;

    public VistaSupervisor() {
        setTitle("Vista Supervisor");
        setSize(1300, 700);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        panelP = new JPanel();
        panelP.setLayout(null);
        panelP.setBackground(Color.white);
        panelP.setBounds(0, 0, 1300, 700);
        add(panelP);

        try {
            InputStream is = VistaSupervisor.class.getResourceAsStream("/fonts/newCentury.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, is);
            is.close();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (Exception e) {
            e.printStackTrace();
        }

        panelVS = new RoundedPanel(30, Color.lightGray);
        panelVS.setLayout(null);

        lTitulo = new JLabel("Almacenes 1A");
        lTitulo.setFont(customFont.deriveFont(Font.PLAIN, 32));
        lTitulo.setBounds(40, 130, 600, 50);
        panelVS.add(lTitulo);

        header = new Header();
        header.setBackground(Color.white);
        panelHeader = new RoundedPanel(16, Color.lightGray);
        panelHeader.setBackground(Color.white);
        panelHeader.setLayout(null);

        if (panelHeader instanceof RoundedPanel) {
            ((RoundedPanel) panelHeader).setShadowSize(15);
            ((RoundedPanel) panelHeader).setShadowColor(Color.white);
            ((RoundedPanel) panelHeader).setRoundAllCorners(2);
        }
        if (panelVS instanceof RoundedPanel) {
            ((RoundedPanel) panelVS).setShadowSize(15);
            ((RoundedPanel) panelVS).setShadowColor(new Color(0, 0, 0));
        }

        header.setBounds(20, 20, 1110, header.getHeight() - 5);
        header.modificarUbicacionUser(1020);
        panelHeader.add(header);
        panelHeader.setBounds(0, 0, 1150, 115);
        panelVS.setBounds(70, 20, 1150, 620);
        panelVS.setBackground(new Color(248, 250, 254));
        panelVS.add(panelHeader);

        panelP.add(panelVS);

        panelBtns = new RoundedPanel(16, Color.lightGray);
        if (panelBtns instanceof RoundedPanel) {
            ((RoundedPanel) panelBtns).setShadowSize(1);
            ((RoundedPanel) panelBtns).setShadowColor(new Color(0, 0, 0));
        }
        panelBtns.setLayout(null);
        panelBtns.setBounds(35, 210, 1080, 380);
        panelBtns.setBackground(Color.white);

        pProveedores();

        pSeguimientos();

        pPedidos();

        pSolicitud();

        pReportes();

        panelVS.add(panelBtns);

    }

    public JPanel getPanelProve() {
        return panelProve;
    }

    public JPanel getPanelSeguiVen() {
        return panelSeguiVen;
    }

    public JPanel getPanelSeguiAdm() {
        return panelSeguiAdm;
    }

    public JPanel getPanelPediEn() {
        return panelPediEn;
    }

    public JPanel getPanelPediNoEn() {
        return panelPediNoEn;
    }

    public JPanel getPanelPQRS() {
        return panelPQRS;
    }

    public JPanel getPanelSoliRepo() {
        return panelSoliRepo;
    }

    public JPanel getPanelRepor() {
        return panelRepor;
    }

    private void pProveedores() {
        lProveedores = new JLabel("Proveedores");
        lProveedores.setFont(customFont.deriveFont(Font.PLAIN, 28));
        lProveedores.setBounds(60, 10, 600, 50);
        panelBtns.add(lProveedores);
        panelProve = new RoundedPanel(16, Color.lightGray);
        if (panelProve instanceof RoundedPanel) {
            ((RoundedPanel) panelProve).setShadowSize(1);
            ((RoundedPanel) panelProve).setShadowColor(new Color(0, 0, 0));
        }
        panelProve.setLayout(null);
        panelProve.setBounds(50, 60, 320, 140);
        panelProve.setBackground(Color.white);

        lPreRePro = new JLabel("Pre registro Proveedor");
        lPreRePro.setFont(customFont.deriveFont(Font.PLAIN, 18));
        lPreRePro.setBounds(70, panelProve.getHeight() - 50, 600, 50);
        panelProve.add(lPreRePro);

        panelBtns.add(panelProve);
    }

    public void pSeguimientos() {
        lSeguimientos = new JLabel("Seguimientos");
        lSeguimientos.setFont(customFont.deriveFont(Font.PLAIN, 28));
        lSeguimientos.setBounds(430, 10, 600, 50);
        panelBtns.add(lSeguimientos);
        panelSeguiVen = new RoundedPanel(16, Color.lightGray);
        if (panelSeguiVen instanceof RoundedPanel) {
            ((RoundedPanel) panelSeguiVen).setShadowSize(1);
            ((RoundedPanel) panelSeguiVen).setShadowColor(new Color(0, 0, 0));
        }
        panelSeguiVen.setLayout(null);
        panelSeguiVen.setBounds(430, 60, 260, 140);
        panelSeguiVen.setBackground(Color.white);

        lSeguimientos = new JLabel("<html><center>Seguimiento Vendedores</center></html>");
        lSeguimientos.setFont(customFont.deriveFont(Font.PLAIN, 18));
        lSeguimientos.setBounds(35, panelSeguiVen.getHeight() - 70, 200, 80);
        panelSeguiVen.add(lSeguimientos);
        panelBtns.add(panelSeguiVen);

        panelSeguiAdm = new RoundedPanel(16, Color.lightGray);
        if (panelSeguiAdm instanceof RoundedPanel) {
            ((RoundedPanel) panelSeguiAdm).setShadowSize(1);
            ((RoundedPanel) panelSeguiAdm).setShadowColor(new Color(0, 0, 0));
        }
        panelSeguiAdm.setLayout(null);
        panelSeguiAdm.setBounds(720, 60, 260, 140);
        panelSeguiAdm.setBackground(Color.white);

        lSeguimientos = new JLabel("<html><center>Seguimiento Administrador</center></html>");
        lSeguimientos.setFont(customFont.deriveFont(Font.PLAIN, 18));
        lSeguimientos.setBounds(35, panelSeguiAdm.getHeight() - 70, 200, 80);
        panelSeguiAdm.add(lSeguimientos);

        panelBtns.add(panelSeguiAdm);
    }

    private void pPedidos() {
        lPedidos = new JLabel("Pedidos");
        lPedidos.setFont(customFont.deriveFont(Font.PLAIN, 28));
        lPedidos.setBounds(60, 205, 600, 50);
        panelBtns.add(lPedidos);
        panelPediEn = new RoundedPanel(16, Color.lightGray);
        if (panelPediEn instanceof RoundedPanel) {
            ((RoundedPanel) panelPediEn).setShadowSize(1);
            ((RoundedPanel) panelPediEn).setShadowColor(new Color(0, 0, 0));
        }
        panelPediEn.setLayout(null);
        panelPediEn.setBounds(50, 250, 150, 120);
        panelPediEn.setBackground(Color.white);

        lPreRePro = new JLabel("<html><center>Pedidos Entregados</center></html>");

        lPreRePro.setFont(customFont.deriveFont(Font.PLAIN, 18));
        lPreRePro.setBounds(25, panelPediEn.getHeight() - 50, 100, 50);
        panelPediEn.add(lPreRePro);

        panelBtns.add(panelPediEn);
        panelPediNoEn = new RoundedPanel(16, Color.lightGray);
        if (panelPediNoEn instanceof RoundedPanel) {
            ((RoundedPanel) panelPediNoEn).setShadowSize(1);
            ((RoundedPanel) panelPediNoEn).setShadowColor(new Color(0, 0, 0));
        }
        panelPediNoEn.setLayout(null);
        panelPediNoEn.setBounds(220, 250, 150, 120);
        panelPediNoEn.setBackground(Color.white);

        lPreRePro = new JLabel("<html><center>Pedidos no Entregados</center></html>");

        lPreRePro.setFont(customFont.deriveFont(Font.PLAIN, 18));
        lPreRePro.setBounds(25, panelPediNoEn.getHeight() - 50, 100, 50);
        panelPediNoEn.add(lPreRePro);

        panelBtns.add(panelPediNoEn);
    }

    private void pSolicitud() {
        lSolicitud = new JLabel("Solicitud");
        lSolicitud.setFont(customFont.deriveFont(Font.PLAIN, 28));
        lSolicitud.setBounds(430, 205, 600, 50);
        panelBtns.add(lSolicitud);
        panelPQRS = new RoundedPanel(16, Color.lightGray);
        if (panelPQRS instanceof RoundedPanel) {
            ((RoundedPanel) panelPQRS).setShadowSize(1);
            ((RoundedPanel) panelPQRS).setShadowColor(new Color(0, 0, 0));
        }
        panelPQRS.setLayout(null);
        panelPQRS.setBounds(430, 250, 150, 120);
        panelPQRS.setBackground(Color.white);

        lPreRePro = new JLabel("<html><center>Bandeja de entrada PQRS</center></html>");

        lPreRePro.setFont(customFont.deriveFont(Font.PLAIN, 18));
        lPreRePro.setBounds(15, panelPQRS.getHeight() - 50, 120, 50);
        panelPQRS.add(lPreRePro);

        panelBtns.add(panelPQRS);
        panelSoliRepo = new RoundedPanel(16, Color.lightGray);
        if (panelSoliRepo instanceof RoundedPanel) {
            ((RoundedPanel) panelSoliRepo).setShadowSize(1);
            ((RoundedPanel) panelSoliRepo).setShadowColor(new Color(0, 0, 0));
        }
        panelSoliRepo.setLayout(null);
        panelSoliRepo.setBounds(610, 250, 150, 120);
        panelSoliRepo.setBackground(Color.white);

        lPreRePro = new JLabel("<html><center>Solicitud Reposicion</center></html>");

        lPreRePro.setFont(customFont.deriveFont(Font.PLAIN, 18));
        lPreRePro.setBounds(25, panelSoliRepo.getHeight() - 50, 100, 50);
        panelSoliRepo.add(lPreRePro);

        panelBtns.add(panelSoliRepo);
    }

    private void pReportes() {
        lReportes = new JLabel("Reportes");
        lReportes.setFont(customFont.deriveFont(Font.PLAIN, 28));
        lReportes.setBounds(800, 205, 600, 50);
        panelBtns.add(lReportes);
        panelRepor = new RoundedPanel(16, Color.lightGray);
        if (panelRepor instanceof RoundedPanel) {
            ((RoundedPanel) panelRepor).setShadowSize(1);
            ((RoundedPanel) panelRepor).setShadowColor(new Color(0, 0, 0));
        }
        panelRepor.setLayout(null);
        panelRepor.setBounds(800, 250, 150, 120);
        panelRepor.setBackground(Color.white);

        lPreRePro = new JLabel("<html><center>Reportes Operativos</center></html>");

        lPreRePro.setFont(customFont.deriveFont(Font.PLAIN, 18));
        lPreRePro.setBounds(15, panelRepor.getHeight() - 50, 120, 50);
        panelRepor.add(lPreRePro);

        panelBtns.add(panelRepor);
    }
}
