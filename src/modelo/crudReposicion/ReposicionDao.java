package modelo.crudReposicion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Conexion;

public class ReposicionDao implements CrudReposicion<Reposicion> {

    @Override
    public List<Reposicion> listar() {
        List<Reposicion> datos = new ArrayList<>();
        String sql = "SELECT sr.id_solicitudes_repo as id_solicitud, sr.id_usuario, sr.id_producto, sr.fecha, prod.nombre FROM solicitudes_reposicion sr join producto prod on sr.id_producto = prod.id WHERE sr.estado_reposicion = 'Pendiente'";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Reposicion rp = new Reposicion();
                rp.setIdSolicitudesRepo(rs.getInt("id_solicitud"));
                rp.setIdUsuario(rs.getString("id_usuario"));
                rp.setIdProducto(rs.getInt("id_producto"));
                rp.setFecha(rs.getString("fecha"));
                rp.setNombreProducto(rs.getString("nombre"));

                datos.add(rp);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de Consulta", JOptionPane.ERROR_MESSAGE);
        }

        return datos;
    }

    @Override
    public boolean insertarReposicion(int idProducto, int cantidadReposicion) {
        Connection con = null;
        try {
            con = Conexion.getInstance().getConnection();
            con.setAutoCommit(false); // Iniciar transacción

            // Insertar en la tabla reposicion
            String sqlReposicion = "INSERT INTO reposicion (idProducto, cantidadReposicion, fecha) VALUES (?, ?, CURRENT_TIMESTAMP)";
            try (PreparedStatement ps = con.prepareStatement(sqlReposicion)) {
                ps.setInt(1, idProducto);
                ps.setInt(2, cantidadReposicion);
                ps.executeUpdate();
            }

            actualizarEstado(idProducto);

            con.commit(); // Confirmar transacción
            return true;
        } catch (Exception e) {
            try {
                if (con != null) {
                    con.rollback(); // Revertir en caso de error
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(null, e.toString(), "Error al registrar reposición",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean actualizarEstado(int idSolicitud) {
        String sql = "UPDATE `solicitudes_reposicion` SET `estado_reposicion`= 'Repuesto' WHERE id_solicitudes_repo = ?;";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idSolicitud);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error al actualizar estado", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
