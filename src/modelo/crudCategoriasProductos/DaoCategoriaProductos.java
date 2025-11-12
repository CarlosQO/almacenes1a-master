package modelo.crudCategoriasProductos;
import java.util.List;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import modelo.Conexion;

public class DaoCategoriaProductos implements CrudCategoriasProductos<Categoria> {
    
    @Override
    public List<Categoria> cargarCategorias() {
        List<Categoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categoria";

        try (
            Connection con = Conexion.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                Categoria c = new Categoria();
                c.setIdCategoria(rs.getInt("id"));
                c.setNombreCategoria(rs.getString("nombre"));
                lista.add(c);
            }
            return lista;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error al cargar categorias", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    } 

    @Override
    public String buscarNombreCategoria(int idCategoria) {
        String sql = "SELECT nombre FROM categoria WHERE id = ?";
        
        try (
            Connection con = Conexion.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql)
        ) {
            ps.setInt(1, idCategoria);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getString("nombre");
            } else {
                return null; // No se encontró la categoría
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error al buscar categoría", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
}