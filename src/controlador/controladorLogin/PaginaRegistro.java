package controladorLogin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import encriptar.Seguridad;
import modelo.crudDocumento.Documento;
import modelo.crudDocumento.DocumentoDao;
import modelo.crudUsuario.Usuario;
import modelo.crudUsuario.UsuarioDao;
import vista.vistaLoginRegistro.Login;
import vista.vistaLoginRegistro.Registro;

public class PaginaRegistro implements ActionListener {
    private Registro Registro;
    private DocumentoDao docDao = new DocumentoDao();
    private UsuarioDao uDao = new UsuarioDao();
    private Login l;
    private PaginaLogin pl;

    public PaginaRegistro(Registro r) {
        this.Registro = r;
        this.Registro.btnRegistrarse.addActionListener(this);
        r.iniciarSesion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                redirigirLogin(0);
            }
        });
        listarDocumento(r);
        r.setVisible(true);
        r.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == Registro.btnRegistrarse) {
            if (Registro.ValidarCampos()) {
                String documento = Registro.inputNumeroDoc.getText();
                if (uDao.buscarUsuario(documento) > 0) {
                    JOptionPane.showMessageDialog(null, "El usuario ya se encuentra registrado");
                    return;
                }
                if (uDao.buscarCorreo(Registro.inputCorreoElectronico.getText()) > 0) {
                    JOptionPane.showMessageDialog(null, "El correo ya se encuentra registrado");
                    return;
                }
                if (!ValidarCampos()) {
                    return;
                }

                String nombres = Registro.inputNombres.getText();
                String apellidos = Registro.inputApellido.getText();
                String correo = Registro.inputCorreoElectronico.getText();
                String telefono = Registro.inputTelefono.getText();
                String contrasena = new String(Registro.inputContrasena.getPassword());
                String confirmar = new String(Registro.inputConfirmarContrasena.getPassword());
                String contraseñaEncriptada = Seguridad.encriptar(contrasena);
                String direccion = Registro.inputDireccion.getText();
                int tipoDoc = rolSeleccionado(Registro.tipoCedula.getSelectedIndex());
                int idRol = 2;
                if (contrasena.equals(confirmar)) {
                    Usuario u = new Usuario();
                    u.setDocumento(documento);
                    u.setNombre(nombres);
                    u.setApellido(apellidos);
                    u.setIdTipoDoc(tipoDoc);
                    u.setCorreo(correo);
                    u.setContrasena(contraseñaEncriptada);
                    u.setDireccion(direccion);
                    u.setTelefono(telefono);
                    u.setIdRol(idRol);
                    int cantidad = uDao.Registrar(u);
                    if (cantidad == 1) {
                        int opcion = JOptionPane.showConfirmDialog(
                                null,
                                "Registro exitoso.\n¿Desea iniciar sesión?",
                                "Registro",
                                JOptionPane.YES_NO_OPTION);

                        // Limpia los campos del formulario
                        Registro.limpiarCampos();

                        // Verifica la opción seleccionada
                        redirigirLogin(opcion);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al registrar usuario");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Las contrasenas no coinciden");
                }
            } else {
                return;
            }
        }
    }

    private List<Documento> documentos = new ArrayList<>();

    private void listarDocumento(Registro r) {
        documentos = docDao.listar();
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        for (Documento doc : documentos) {
            model.addElement(doc.getNombre());
        }
        r.tipoCedula.setModel(model);
    }

    private int rolSeleccionado(int docSeleccionado) {
        Documento d = documentos.get(docSeleccionado);
        return d.getId();
    }

    public boolean ValidarCampos() {
        String nombres = Registro.inputNombres.getText();
        String apellidos = Registro.inputApellido.getText();
        String correo = Registro.inputCorreoElectronico.getText();
        String telefono = Registro.inputTelefono.getText();
        String contrasena = new String(Registro.inputContrasena.getPassword());
        String confirmar = new String(Registro.inputConfirmarContrasena.getPassword());
        String direccion = Registro.inputDireccion.getText();
        if (nombres.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || telefono.isEmpty() || contrasena.isEmpty()
                || confirmar.isEmpty() || direccion.isEmpty()) {
            System.out.println("Por favor, complete todos los campos.");
            return false;
        }
        return true;
    }

    public void redirigirLogin(int opcion) {
        if (opcion == 0) {
            l = new Login();
            pl = new PaginaLogin(l);
            Registro.setVisible(false);
        } else {
            return;
        }
    }
}
