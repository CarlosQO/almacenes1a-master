package modelo.crudEstadosProdcutos;
import java.util.List;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Conexion;

public class DaoEstadosProductos implements CrudEstadosProductos<EstadosProductos>{

    @Override
    public List<EstadosProductos> mostrarEstados() {
        List<EstadosProductos> listaEstados = new ArrayList<>();
        String sql = "SELECT id, nombre FROM producto_estado";

        try (Connection con = Conexion.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                EstadosProductos estado = new EstadosProductos();
                estado.setIdEstado(rs.getInt("id"));
                estado.setNombreEstado(rs.getString("nombre"));
                listaEstados.add(estado);
            }
            return listaEstados;
        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error al cargar estados", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
    }
    
}

