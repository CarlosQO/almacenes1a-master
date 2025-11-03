package modelo.crudVendedor;

import java.util.List;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import modelo.Conexion;

public class VendedorDao implements CrudVendedor<Vendedor> {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public List<Vendedor> listar() {
        return null;
    }

    @Override
    public Vendedor buscarVendedor(String idVendedor) {
        String sql = "SELECT * FROM vendedores WHERE idVendedor = ?";
        try {
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, idVendedor);
            rs = ps.executeQuery();
            if (rs.next()) {
                Vendedor v = new Vendedor();
                v.setIdVendedor(rs.getString("idVendedor"));
                v.setIdUsuario(rs.getString("idUsuario"));
                v.setNumeroDeVenta(rs.getInt("numeroVenta"));
                v.setEstado(rs.getString("estado"));
                return v;
            } else {
                System.out.println("No se encontró vendedor con documento: " + idVendedor);
            }
        } catch (Exception e) {
            System.out.println("Error al obtener ese vendedor: " + e);
        }
        return null;
    }

    @Override
    public int actualizarEstado(String idVendedor, String estado) {
        try {
            String sql = "UPDATE vendedores SET estado = ? WHERE idVendedor = ?";
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, estado);
            ps.setString(2, idVendedor);
            int columnaAfectada = ps.executeUpdate();
            if (columnaAfectada > 0) {
                System.out.println("Estado del vendedor actualizado con exito: " + estado);
            }
            return columnaAfectada;
        } catch (Exception e) {
            System.out.println("Error al intentar actualizar el estado del vendedor");
        }
        return 0;
    }

    @Override
    public int agregarVendedor(Vendedor v) {
        try {
            String sql = "INSERT INTO vendedores(idVendedor, idUsuario, numeroVenta, estado) VALUES (?,?,?,?)";
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, v.getIdVendedor());
            ps.setString(2, v.getIdUsuario());
            ps.setInt(3, v.getNumeroDeVenta());
            ps.setString(4, v.getEstado());
            int filasInsertadas = ps.executeUpdate();

            if (filasInsertadas > 0) {
                System.out.println(" Inserción exitosa en vendedor");
                return 1;
            } else {
                System.out.println("️ No se insertó ningún registro de vendedor");
            }
        } catch (Exception e) {
            System.out.println("Error en la consulta de registrar vendedor " + e.toString());
        }
        return 0;
    }
}
