package modelo.crudSolicitudesReposicion;

import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Conexion;

public class DaoSolicitudesReposicion implements CrudSolicitudesReposicion<SolicitudesReposicion>{

   @Override 
    public List<SolicitudesReposicion> mostrarProductosConBajostock() {
        List<SolicitudesReposicion> info = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE cantidad <= 5 AND id_estado = 1";

        try (
            Connection con = Conexion.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) { 
                SolicitudesReposicion sr = new SolicitudesReposicion();
                int idProducto = rs.getInt("id");

                sr.setId(idProducto);
                sr.setCantidad(rs.getInt("cantidad"));
                sr.setNombre(rs.getString("nombre"));
                sr.setPrecio(rs.getDouble("precio"));

                // Verificar si el producto ya tiene solicitud enviada
                boolean solicitudExiste = productoConSolicitud(idProducto);
                sr.setSolitudEnviada(solicitudExiste);

                info.add(sr);
            }
            return info;    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de Consulta (mostrarProductosConBajostock)", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }


    @Override
    public void enviarSolicitud(int idVendedor, int idProducto) {
        String sql = "INSERT INTO solicitudes_reposicion (id_usuario, id_producto, fecha) VALUES (?, ?, NOW())";

        try (Connection conn = Conexion.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idVendedor); //  el id del vendedor activo
            ps.setInt(2, idProducto);

            ps.executeUpdate();
            System.out.println("Solicitud de reposición enviada correctamente.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de Consulta (Enviar Solicitud)", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public boolean productoConSolicitud(int idProducto) {
        String sql = "SELECT 1 FROM solicitudes_reposicion WHERE id_producto = ? LIMIT 1";

        try (Connection conn = Conexion.getInstance().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idProducto);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true; // ya tiene una solicitud registrada
            }

        }  catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de Consulta (Buscar producto Solicitud)", JOptionPane.ERROR_MESSAGE);
        }

        return false; // No existe solicitud para ese producto
    }

    
}
