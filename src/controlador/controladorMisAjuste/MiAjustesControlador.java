package controladorMisAjuste;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import controladorLogin.PaginaLogin;
import modelo.crudAdministradorSesion.AdministradorDao;
import modelo.crudUsuario.Usuario;
import modelo.crudUsuario.UsuarioDao;
import vista.vistaLoginRegistro.Login;
import vista.vistaMiAjustes.ActualizarPerfil;
import vista.vistaMiAjustes.MiAjustes;
import vista.vistaCliente.PanelPrincipal;

public class MiAjustesControlador implements ActionListener {
    private String usuario, documento;
    private int rol, idSesionAdmin;
    private MiAjustes ajusteV;
    private Login l;
    private PaginaLogin pl;
    private AdministradorDao aDao = new AdministradorDao();
    private PanelPrincipal panelPrincipal;
    private ActualizarPerfil actualizarPerfil;
    private ActualizarPerfilControlador actualizarPerfilControlador;
    private UsuarioDao uDao = new UsuarioDao();

    public MiAjustesControlador(MiAjustes ajusteV, String usuario, String documento, int rol, int idSesionAdmin) {
        this.ajusteV = ajusteV;
        this.usuario = usuario;
        this.documento = documento;
        this.rol = rol;
        this.idSesionAdmin = idSesionAdmin;
        ajusteV.setVisible(true);
        ajusteV.setUsuario(usuario);
        ajusteV.repintar();
        ajusteV.actualizarDatos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                actualizarPerfil = new ActualizarPerfil();
                actualizarPerfilControlador = new ActualizarPerfilControlador(actualizarPerfil, documento);
                actualizarPerfil.mostrarComoModal(actualizarPerfil, "Actualizar Perfil");
                // capturar la ventana al salir
                if (actualizarPerfil.isVisible() == false || actualizarPerfil.isShowing() == false
                        || actualizarPerfil.isActive() == false) {
                    Usuario usuario = uDao.obtenerUsuario(documento);
                    ajusteV.setUsuario(usuario.getNombre() + " " + usuario.getApellido());
                    ajusteV.repintar();
                    ajusteV.repaint();
                    ajusteV.revalidate();
                }
            }
        });
        ajusteV.cerrarSesion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int opcion = JOptionPane.showConfirmDialog(null,
                        "¿Desea cerrar sesión?", "Cierrar sesión",
                        JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    redirigirLogin();
                } else {
                    return;
                }
            }
        });
    }

    public MiAjustesControlador(MiAjustes ajusteV, String usuario, String documento, int rol,
            PanelPrincipal panelPrincipal) {
        this.ajusteV = ajusteV;
        this.usuario = usuario;
        this.documento = documento;
        this.rol = rol;
        this.panelPrincipal = panelPrincipal;
        panelPrincipal.btnCerrarSesion.addActionListener(this);
        panelPrincipal.btnActualizarInfo.addActionListener(this);
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getSource() == panelPrincipal.btnCerrarSesion) {
            int opcion = JOptionPane.showConfirmDialog(null,
                    "¿Desea cerrar sesión?", "Cierrar sesión",
                    JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                redirigirLogin();
            } else {
                return;
            }
        }
        if (e.getSource() == panelPrincipal.btnActualizarInfo) {
            actualizarPerfil = new ActualizarPerfil();
            actualizarPerfilControlador = new ActualizarPerfilControlador(actualizarPerfil, documento);
            actualizarPerfil.mostrarComoModal(actualizarPerfil, "Actualizar Perfil");
            // capturar la ventana al salir

        }
    }

    public void redirigirLogin() {

        if (rol == 1) {
            aDao.registrarHoraSalida(idSesionAdmin);
        }
        // Cerrar TODAS las ventanas abiertas
        for (java.awt.Window w : java.awt.Window.getWindows()) {
            w.dispose();
        }

        // Volver a la pantalla de login
        l = new Login();
        pl = new PaginaLogin(l);
        l.setVisible(true);
        l.setLocationRelativeTo(null);
    }

}