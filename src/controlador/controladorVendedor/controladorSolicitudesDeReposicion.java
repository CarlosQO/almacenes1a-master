package controladorVendedor;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modelo.crudSolicitudesReposicion.*;
import vista.componentes.ScrollPersonalizado;
import vista.vistaVendedor.*;
import java.awt.Dimension;

public class controladorSolicitudesDeReposicion implements ActionListener {

    private int idVendedor;
    private DaoSolicitudesReposicion daoSolicitudesReposicion;
    private ModuloReposicionArticulos vistaModuloSolicitudes;
    private ScrollPersonalizado scrollSolicitudes;
    private JFrame frame;

    public controladorSolicitudesDeReposicion(JFrame frame, int idVendedor) {
        this.idVendedor = idVendedor;
        this.daoSolicitudesReposicion = new DaoSolicitudesReposicion();
        this.frame = frame;
        cargarModuloReposicion();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public void cargarModuloReposicion() {
        frame.setSize(1300, 700);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        vistaModuloSolicitudes = new ModuloReposicionArticulos(frame);
        frame.add(vistaModuloSolicitudes);
        frame.setVisible(true);

        vistaModuloSolicitudes.panelTarjetas = new JPanel();
        vistaModuloSolicitudes.panelTarjetas.setLayout(null);
        vistaModuloSolicitudes.panelTarjetas.setBackground(new Color(245, 245, 245));
        vistaModuloSolicitudes.panelTarjetas.setBounds(200, 150, 900, 450);
        vistaModuloSolicitudes.add(vistaModuloSolicitudes.panelTarjetas);
        cargarSolicitudes(vistaModuloSolicitudes.panelTarjetas);
    }

    public List<SolicitudesReposicion> getListarSolicitudesReposiicion() {
        List<SolicitudesReposicion> listaSolicitudes = new ArrayList<>();
        try {
            listaSolicitudes = daoSolicitudesReposicion.mostrarProductosConBajostock();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null, e.toString(), "Error al obtener solicitudes de reposición", JOptionPane.ERROR_MESSAGE);
        }
        return listaSolicitudes;
    }

    public void cargarSolicitudes(JPanel panelSolicitudes) {
        panelSolicitudes.removeAll();
        panelSolicitudes.setLayout(null);

        List<SolicitudesReposicion> lista = getListarSolicitudesReposiicion();
        JPanel contenedorTarjetas = new JPanel(null);
        contenedorTarjetas.setBackground(panelSolicitudes.getBackground());

        int y = 20;
        for (SolicitudesReposicion sr : lista) {
            TarjetasDeSolicitudesReposicion tarjeta = new TarjetasDeSolicitudesReposicion(
                    sr.getId(), sr.getNombre(), String.valueOf(sr.getCantidad()));
            tarjeta.setBounds(50, y, 800, 80);

            if (sr.isSolitudEnviada()) {
                tarjeta.getBotonSolicitar().setText("Enviada");
                tarjeta.getBotonSolicitar().setEnabled(false);
            } else {
                tarjeta.getBotonSolicitar().addActionListener(ev -> {
                    try {
                        daoSolicitudesReposicion.enviarSolicitud(idVendedor, sr.getId());
                        tarjeta.getBotonSolicitar().setText("Enviada");
                        tarjeta.getBotonSolicitar().setEnabled(false);
                        JOptionPane.showMessageDialog(null,
                                "Solicitud enviada correctamente para el producto: " + sr.getNombre(),
                                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error al enviar solicitud: " + ex.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                });
            }

            contenedorTarjetas.add(tarjeta);
            y += 90;
        }

        contenedorTarjetas.setPreferredSize(new Dimension(1150, y + 40));
        contenedorTarjetas.setBounds(0, 0, 900, y + 40);

        if (getListarSolicitudesReposiicion().size() > 4) {
            scrollSolicitudes = new ScrollPersonalizado(contenedorTarjetas, "vertical", 1150, 450);
            scrollSolicitudes.setBounds(0, 10, 900, 450);
            panelSolicitudes.add(scrollSolicitudes);
        } else {
            panelSolicitudes.add(contenedorTarjetas);
        }

        panelSolicitudes.revalidate();
        panelSolicitudes.repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Módulo Solicitudes de Reposición");
        controladorSolicitudesDeReposicion controlador = new controladorSolicitudesDeReposicion(frame, 1002);
    }
}