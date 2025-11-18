package controladorMisAjuste;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import modelo.crudUsuario.Usuario;
import modelo.crudUsuario.UsuarioDao;
import vista.vistaMiAjustes.ActualizarPerfil;

public class ActualizarPerfilControlador implements ActionListener {
    private ActualizarPerfil actualizarPerfil;
    private UsuarioDao uDao = new UsuarioDao();
    private String documento;

    public ActualizarPerfilControlador(ActualizarPerfil actualizarPerfil, String documento) {
        this.actualizarPerfil = actualizarPerfil;
        this.documento = documento;
        llenarCampos();
        actualizarPerfil.guardarCambios.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == actualizarPerfil.guardarCambios) {
            // validaciones
            if (!validarCamposVacios()) {
                return;
            }
            if (!actualizarPerfil.validarCampos()) {
                return;
            }
            // campos
            String nombre = actualizarPerfil.numeNombres.getText();
            String apellido = actualizarPerfil.numeApellidos.getText();
            String telefono = actualizarPerfil.numeTelefono.getText();
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setApellido(apellido);
            usuario.setTelefono(telefono);
            usuario.setDocumento(documento);
            int filas = uDao.Actualizar(usuario);
            if (filas > 0) {
                JOptionPane.showMessageDialog(null, "Perfil actualizado con exito");
                // Cerrar el dialog que contiene esta vista
                java.awt.Window window = javax.swing.SwingUtilities.getWindowAncestor(actualizarPerfil);
                if (window != null) {
                    window.dispose();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar perfil");
                return;
            }
        }
    }

    private Usuario usuario = new Usuario();

    public void llenarCampos() {
        usuario = uDao.obtenerUsuario(documento);
        actualizarPerfil.llenarCampos(usuario.getDocumento(), usuario.getNombre(), usuario.getApellido(),
                usuario.getTelefono());
    }

    public boolean validarCamposVacios() {
        if (actualizarPerfil.numeNombres.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre no puede estar vacio");
            return false;
        }
        if (actualizarPerfil.numeApellidos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El apellido no puede estar vacio");
            return false;
        }
        if (actualizarPerfil.numeTelefono.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El telefono no puede estar vacio");
            return false;
        }
        return true;
    }
}
