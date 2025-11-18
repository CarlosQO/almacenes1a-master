package modelo.crudUsuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.swing.JOptionPane;

import encriptar.Seguridad;
import modelo.Conexion;

public class UsuarioDao implements CrudUsuario<Usuario> {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public List<Usuario> listar() {
        return null;
    }

    public int Registrar(Usuario u) {
        String sql = "INSERT INTO usuarios (documento, nombre, apellido, id_tipo_doc, correo, contrasena, direccion, telefono, id_rol) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getDocumento());
            ps.setString(2, u.getNombre());
            ps.setString(3, u.getApellido());
            ps.setInt(4, u.getIdTipoDoc());
            ps.setString(5, u.getCorreo());
            ps.setString(6, u.getContrasena());
            ps.setString(7, u.getDireccion());
            ps.setString(8, u.getTelefono());
            ps.setInt(9, u.getIdRol());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                return 1;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar usuario: " + e);
        }
        return 0;
    }

    @Override
    public int Actualizar(Usuario u) {
        String sql = "UPDATE usuarios SET nombre = ?, apellido = ?, telefono = ? WHERE documento = ?";
        try {
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, u.getNombre());
            ps.setString(2, u.getApellido());
            ps.setString(3, u.getTelefono());
            ps.setString(4, u.getDocumento());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                return 1;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar usuario: " + e);
        }
        return 0;
    }

    @Override
    public Usuario obtenerUsuario(String documento) {
        String sql = "SELECT * FROM usuarios WHERE documento = ?";
        try {
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, documento);
            rs = ps.executeQuery();
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setDocumento(rs.getString("documento"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setIdTipoDoc(rs.getInt("id_tipo_doc"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setContrasena(rs.getString("contrasena"));
                usuario.setDireccion(rs.getString("direccion"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setIdRol(rs.getInt("id_rol"));
                return usuario;
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró usuario con documento: " + documento);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener ese usuario: " + e);
        }
        return null;
    }

    @Override
    public int ActualizarRol(int rolID, String documento) {
        int columnAfectada = 0;
        String sql = "UPDATE usuarios SET id_rol = ? WHERE documento = ?";
        try {
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, rolID);
            ps.setString(2, documento);
            columnAfectada = ps.executeUpdate();
            if (columnAfectada > 0) {
                System.out.println("Rol actualizado con exito");
            }
            return columnAfectada;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al intentar actualizar el rol");
        }
        return columnAfectada;
    }

    @Override
    public int obtenerCantidadUsuario() {
        try {
            String sql = "SELECT COUNT(*) AS totalUsuarios FROM usuarios";
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("totalUsuarios");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener la cantidad de usuarios: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public int buscarUsuario(String documento) {
        try {
            String sql = "SELECT COUNT(*) AS totalUsuarios FROM usuarios WHERE documento = ?";
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, documento);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("totalUsuarios");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener la cantidad de usuarios: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public int buscarCorreo(String correo) {
        try {
            String sql = "SELECT COUNT(*) AS totalUsuarios FROM usuarios WHERE correo = ?";
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("totalUsuarios");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener la cantidad de usuarios por correo: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public Usuario validarCredencialesUsuario(String documento, String contrasenaIngresada) {
        String sql = "SELECT * FROM usuarios WHERE documento = ?";
        try {
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, documento);
            rs = ps.executeQuery();
            if (rs.next()) {
                String hashAlmacenado = rs.getString("contrasena");

                // Verificamos la contraseña ingresada contra el hash en BD
                if (Seguridad.verificar(contrasenaIngresada, hashAlmacenado)) {
                    Usuario usuario = new Usuario();
                    usuario.setDocumento(rs.getString("documento"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApellido(rs.getString("apellido"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setContrasena(hashAlmacenado);
                    usuario.setDireccion(rs.getString("direccion"));
                    usuario.setTelefono(rs.getString("telefono"));
                    usuario.setIdRol(rs.getInt("id_rol"));
                    return usuario;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al validar credenciales: " + e);
        }
        return null;
    }

}
