package controladorLogin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import controladorVendedor.ControladorVendedor;
import controladorAdministrador.PaginaPrincipal;
import controladorCliente.ControladorPrincipalCliente;
import controladorSupervisor.ContraladorVistaSuper;
import modelo.crudAdministradorSesion.AdministradorDao;
import modelo.crudUsuario.Usuario;
import modelo.crudUsuario.UsuarioDao;
import vista.vistaAdministrador.PrincipalAdministradorVista;
import vista.vistaLoginRegistro.Login;
import vista.vistaLoginRegistro.Registro;
import vista.vistaSupervisor.VistaSupervisor;
import vista.vistaVendedor.VistaVendedor;
import vista.vistaCliente.PanelPrincipal;

public class PaginaLogin implements ActionListener {
    private Login login;
    private UsuarioDao uDao = new UsuarioDao();
    private Registro r;
    private PaginaRegistro pr;
    private AdministradorDao aDao = new AdministradorDao();
    public static String usuario, documento;
    public static int rol, idSesionAdmin;

    public PaginaLogin() {
    }

    public PaginaLogin(Login l) {
        this.login = l;
        l.btnIniciarSesion.addActionListener(this);
        l.registrarse.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                redirigirLogin(0);
            }
        });
        l.setVisible(true);
        l.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login.btnIniciarSesion) {
            if (!login.validarCampos()) {
                return;
            }
            if (!ValidarCampos()) {
                return;
            }
            String documento = login.inputNumero.getText();
            // validar que el usuario con ese documento exista
            int cantidad = uDao.buscarUsuario(documento);
            if (cantidad == 0) {
                int opcion = JOptionPane.showConfirmDialog(
                        null,
                        "El usuario no se encuentra registrado.\n¿Desea registrarse?",
                        "Registro",
                        JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    redirigirLogin(0);
                }
                return;
            }
            String contraseña = new String(login.inputContra.getPassword());
            Usuario existe = uDao.validarCredencialesUsuario(documento, contraseña);
            if (existe != null) {
                try {
                    // redirigir segun el rol
                    this.usuario = existe.getNombre() + " " + existe.getApellido();
                    this.documento = existe.getDocumento();
                    this.rol = existe.getIdRol();

                    regirigirSegunElRol(existe.getIdRol());
                } catch (IOException e1) {
                    System.out.println("Error al redirigir: " + e1.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Contraseña Incorrecta, intente nuevamente",
                        "Error de autenticación", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    public boolean ValidarCampos() {
        String documento = login.inputNumero.getText();
        String contraseña = login.inputContra.getPassword().toString();
        if (documento.isEmpty() || contraseña.isEmpty()) {
            System.out.println("Por favor, complete todos los campos.");
            return false;
        }
        return true;
    }

    private void regirigirSegunElRol(int idRol) throws IOException {
        switch (idRol) {
            case 1:
                PrincipalAdministradorVista v = new PrincipalAdministradorVista();
                PaginaPrincipal c = new PaginaPrincipal(v, login.inputNumero.getText());
                v.setResizable(false);
                int idSesion = aDao.registrarHoraIngreso(login.inputNumero.getText());
                this.idSesionAdmin = idSesion;
                v.setVisible(true);
                v.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                login.setVisible(false);
                configurarCierreVentana(v, idSesion);
                break;
            case 2:
                PanelPrincipal paginaCliente = new PanelPrincipal();
                ControladorPrincipalCliente con = new ControladorPrincipalCliente(paginaCliente,
                        login.inputNumero.getText());
                paginaCliente.setResizable(false);
                paginaCliente.setVisible(true);
                paginaCliente.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                login.setVisible(false);
                configurarCierreVentana(paginaCliente, 0);

                break;
            case 3:
                VistaVendedor vista = new VistaVendedor();
                ControladorVendedor cv = new ControladorVendedor(vista);
                vista.setResizable(false);
                vista.setVisible(true);
                vista.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

                login.setVisible(false);
                configurarCierreVentana(vista, 0);
                System.out.println("Vendedor");
                break;
            case 4:
                VistaSupervisor vs = new VistaSupervisor();
                ContraladorVistaSuper cs = new ContraladorVistaSuper(vs);
                vs.setResizable(false);
                vs.setVisible(true);
                vs.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                login.setVisible(false);
                configurarCierreVentana(vs, 0);
                break;
            default:
                System.out.println("Rol no reconocido");
                break;
        }
    }

    public void redirigirLogin(int opcion) {
        if (opcion == 0) {
            r = new Registro();
            pr = new PaginaRegistro(r);
            login.setVisible(false);
        } else {
            return;
        }
    }

    private void configurarCierreVentana(JFrame ventanaSecundaria, int idSesion) {
        login.setVisible(false);

        javax.swing.SwingUtilities.invokeLater(() -> {
            ventanaSecundaria.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    int opcion = JOptionPane.showConfirmDialog(
                            ventanaSecundaria,
                            "¿Desea cerrar sesión?",
                            "Confirmar cierre de sesión",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);

                    if (opcion == JOptionPane.YES_OPTION) {
                        // Cierra la ventana secundaria
                        if (idSesion > 0) {
                            aDao.registrarHoraSalida(idSesion);
                        }
                        ventanaSecundaria.setVisible(false);
                        login.limpiarCampos();
                        login.setVisible(true);
                    } else {
                        // Evita que la ventana se cierre si elige "No"
                        ventanaSecundaria.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    }
                }
            });
        });
    }
}
