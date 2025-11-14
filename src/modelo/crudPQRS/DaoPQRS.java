package modelo.crudPQRS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Conexion;

public class DaoPQRS implements CrudPQRS<Pqrs> {
    @Override
    public boolean enviarPQRS(String idUsuario, String asunto, String cuerpo) {
        String sql = "INSERT INTO pqrs (correo, asunto, contenido, id_usuario_remitente) VALUES (?, ?, ?, ?)";
        try (
                Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);) {
            // Obtener el correo del usuario
            String correo = obtenerCorreo(idUsuario);
            if (correo == null) {
                JOptionPane.showMessageDialog(null, "No se encontró correo del usuario", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }

            ps.setString(1, correo);
            ps.setString(2, asunto);
            ps.setString(3, cuerpo);
            ps.setString(4, idUsuario);

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error al enviar PQRS", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
public String obtenerCorreo(String idUsuario) {
    String sql = "SELECT correo FROM usuarios WHERE documento = ?";
    try (
        Connection con = Conexion.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
    ) {
        ps.setString(1, idUsuario);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                // Usuario encontrado, devolver correo
                return rs.getString("correo");
            } else {
                // Usuario NO encontrado
                JOptionPane.showMessageDialog(
                    null,
                    "No se encontró un usuario con el documento: " + idUsuario,
                    "Usuario no encontrado",
                    JOptionPane.WARNING_MESSAGE
                );
            }
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.toString(), "Error al obtener correo", JOptionPane.ERROR_MESSAGE);
    }
    return null;
}

    @Override
    public List<Pqrs> listar() {
        List<Pqrs> datos = new ArrayList<>();
        String sql = "SELECT pqrs.*, u.nombre, u.apellido FROM pqrs join usuarios u on documento = id_usuario_remitente ORDER BY fecha_envio asc";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Pqrs pd = new Pqrs();
                pd.setIdPqrs(rs.getInt(1));
                pd.setFechaEnvio(rs.getTimestamp(2));
                pd.setCorreo(rs.getString(3));
                pd.setAsunto(rs.getString(4));
                pd.setCuerpo(rs.getString(5));
                pd.setIdUsuarioRemitente(rs.getInt(6));
                pd.setNomUsuarioRemi(rs.getString(7));
                pd.setApeUsuarioRemi(rs.getString(8));
                datos.add(pd);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de Consulta", JOptionPane.ERROR_MESSAGE);
        }
        return datos;
    }

    @Override
    public List<Pqrs> listarPorID(int id) {
        List<Pqrs> datos = new ArrayList<>();
        String sql = "SELECT asunto, contenido FROM pqrs where id_pqrs = ?";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Pqrs pd = new Pqrs();
                    pd.setAsunto(rs.getString(1));
                    pd.setCuerpo(rs.getString(2));
                    datos.add(pd);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString(), "Error de Consulta", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de Consulta", JOptionPane.ERROR_MESSAGE);
        }
        return datos;
    }
}
