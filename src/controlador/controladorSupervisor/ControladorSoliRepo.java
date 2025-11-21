package controladorSupervisor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import modelo.crudReposicion.Reposicion;
import modelo.crudReposicion.ReposicionDao;
import vista.vistaSupervisor.componentes.RoundedPanel;
import vista.vistaSupervisor.SolicitudesReposiciones;

public class ControladorSoliRepo {
    private SolicitudesReposiciones vsr = new SolicitudesReposiciones();
    private JLabel lRepoDesc;
    private Font customFont;
    private ReposicionDao rpdDao = new ReposicionDao();

    public ControladorSoliRepo(SolicitudesReposiciones vsr) {
        this.vsr = vsr;
        this.customFont = vsr.getCustomFont();
        crearSoliRepo();
    }

    public ControladorSoliRepo() {

    }

    private void crearSoliRepo() {
        List<Reposicion> lista = rpdDao.listar();
        if (lista == null || lista.isEmpty()) {
            JLabel vacio = new JLabel("No hay reposiciones encontradas.");
            vacio.setOpaque(false);
            vsr.getPanelSoliContent().add(vacio);
        } else {
            for (Reposicion r : lista) {
                JPanel cardSoliRepo = new RoundedPanel(12, Color.white);
                cardSoliRepo.setBackground(Color.white);
                cardSoliRepo.setLayout(new BorderLayout(10, 10));
                cardSoliRepo.setMaximumSize(new Dimension(900, 80));
                cardSoliRepo.setPreferredSize(new Dimension(900, 80));
                if (cardSoliRepo instanceof RoundedPanel) {
                    ((RoundedPanel) cardSoliRepo).setShadowSize(3);
                    ((RoundedPanel) cardSoliRepo).setShadowColor(new Color(0, 0, 0, 90));
                }
                lRepoDesc = new JLabel(
                        "<html>El vendedor " + r.getIdUsuario() + " ha solicitado la reposicion del producto "
                                + r.getNombreProducto() + "</html>");
                lRepoDesc.setPreferredSize(new Dimension(500, 300));
                lRepoDesc.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 10));
                if (customFont != null) {
                    lRepoDesc.setFont(customFont.deriveFont(Font.BOLD, 18f));
                } else {
                    lRepoDesc.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
                }
                JPanel botones = new JPanel();
                botones.setOpaque(false);
                botones.setLayout(new BoxLayout(botones, BoxLayout.X_AXIS));

                // Crear componentes por fila para evitar compartir el mismo campo entre
                // tarjetas
                final JTextField txtCantidad = new JTextField();
                txtCantidad.setPreferredSize(new Dimension(100, 30));
                txtCantidad.setMaximumSize(new Dimension(100, 30));
                txtCantidad.setAlignmentY(Component.CENTER_ALIGNMENT);

                // Placeholder behaviour: show grey 'Cantidad' when empty
                final String placeholder = "Cantidad";
                txtCantidad.setText(placeholder);
                txtCantidad.setForeground(Color.GRAY);
                txtCantidad.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (txtCantidad.getText().equals(placeholder)) {
                            txtCantidad.setText("");
                            txtCantidad.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if (txtCantidad.getText().trim().isEmpty()) {
                            txtCantidad.setText(placeholder);
                            txtCantidad.setForeground(Color.GRAY);
                        }
                    }
                });

                final JButton btnReposicion = new JButton("Reposición");
                btnReposicion.setPreferredSize(new Dimension(100, 30));
                btnReposicion.setMaximumSize(new Dimension(100, 30));
                btnReposicion.setAlignmentY(Component.CENTER_ALIGNMENT);

                // Acción por fila: validar mínimo 5 y mostrar mensajes
                btnReposicion.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String texto = txtCantidad.getText().trim();
                        try {
                            int cantidad = Integer.parseInt(texto);
                            if (cantidad > 5) {
                                // Intentar insertar la reposición en la base de datos
                                final int idProducto = r.getIdProducto(); // Capturar el ID del producto
                                final int idSolicitud = r.getIdSolicitudesRepo(); // Capturar el ID de la solicitud
                                if (rpdDao.insertarReposicion(idProducto, cantidad)
                                        && rpdDao.actualizarEstado(idSolicitud)) {
                                    JOptionPane.showMessageDialog(vsr,
                                            "Se realizo correctamente la reposición");

                                    rpdDao.actualizarCantidadProduc(cantidad, idProducto);

                                    // Recargar la lista de solicitudes
                                    vsr.getPanelSoliContent().removeAll();
                                    crearSoliRepo();
                                    vsr.getPanelSoliContent().revalidate();
                                    vsr.getPanelSoliContent().repaint();
                                } else {
                                    JOptionPane.showMessageDialog(vsr,
                                            "Error al registrar la reposición en la base de datos",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(vsr,
                                        "Cantidad no valida",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(vsr,
                                    "Cantidad no valida",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                botones.add(txtCantidad);
                botones.add(Box.createRigidArea(new Dimension(24, 0)));
                botones.add(btnReposicion);
                botones.add(Box.createRigidArea(new Dimension(24, 0)));
                cardSoliRepo.add(lRepoDesc, BorderLayout.WEST);
                cardSoliRepo.add(botones, BorderLayout.EAST);
                vsr.getPanelSoliContent().add(cardSoliRepo);
                vsr.getPanelSoliContent().add(Box.createRigidArea(new Dimension(0, 8)));
            }
        }
    }
}
