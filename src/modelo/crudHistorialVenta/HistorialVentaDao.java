package modelo.crudHistorialVenta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelo.Conexion;

public class HistorialVentaDao implements CrudHistorialVenta {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public List<Object[]> lista(String fechaInicio, String FechaFin) {
        List<Object[]> datos = new ArrayList<>();
       try {
            String sql = "SELECT "
                    + "h.idHistoVenta, "
                    + "p.nombre AS producto, "
                    + "h.cantidadVenta, "
                    + "p.precio, "
                    + "(h.cantidadVenta * p.precio) AS subtotal, "
                    + "h.fecha "
                    + "FROM histoventas h "
                    + "JOIN producto p ON h.idProducto = p.id "
                    + "WHERE h.fecha BETWEEN ? AND ? "
                    + "ORDER BY h.fecha DESC;";
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, fechaInicio + " 00:00:00");
            ps.setString(2, FechaFin + " 23:59:59");

            rs = ps.executeQuery();
            while (rs.next()) {
                Object[] fila = new Object[6];
                fila[0] = rs.getInt("idHistoVenta");
                fila[1] = rs.getString("producto");
                fila[2] = rs.getInt("cantidadVenta");
                fila[3] = rs.getDouble("precio");
                fila[4] = rs.getDouble("subtotal");
                fila[5] = rs.getDate("fecha");
                datos.add(fila);
            }

        } catch (Exception e) {
            System.out.println("Error al intentar obtener el historial venta por fecha: " + e.toString());
        }
        return datos;
    }

    @Override
    public int registrarVenta(String idVendedor, int idPedido) {
        try {
            String sql = "INSERT INTO vendedorhistoventas (idVendedor, idPedido, fecha) VALUES (?,?,NOW())";
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, idVendedor);
            ps.setInt(2, idPedido);
            int filasInsertadas = ps.executeUpdate();

            if (filasInsertadas > 0) {
                System.out.println("Inserción exitosa en historiaVendedor");
                return 1;
            } else {
                System.out.println("No se insertó ningún registro");
            }
        } catch (Exception e) {
            System.out.println("Error en la consulta de registrar " + e.toString());
        }
        return 0;
    }
}