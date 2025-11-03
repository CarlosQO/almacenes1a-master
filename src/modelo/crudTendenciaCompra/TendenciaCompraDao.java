package modelo.crudTendenciaCompra;

import java.util.List;

import modelo.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TendenciaCompraDao implements CrudTendenciaCompra {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public List<Object[]> listarTendenciaCompra(int año, int mes) {
        List<Object[]> datos = new ArrayList<>();
        try {
            String sql = "SELECT "
                    + "p.id AS id_producto, "
                    + "p.nombre AS producto, "
                    + "p.precio AS precio_unitario, "
                    + "SUM(h.cantidadVenta) AS total_vendido, "
                    + "(SUM(h.cantidadVenta) * p.precio) AS valor_total, "
                    + "MONTH(h.fecha) AS mes, "
                    + "YEAR(h.fecha) AS anio "
                    + "FROM histoventas h "
                    + "INNER JOIN producto p ON h.idProducto = p.id "
                    + "WHERE YEAR(h.fecha) = ? "
                    + "AND MONTH(h.fecha) = ? "
                    + "GROUP BY p.id, p.nombre, p.precio, mes, anio "
                    + "ORDER BY total_vendido DESC";
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, año);
            ps.setInt(2, mes);
            rs = ps.executeQuery();
            while (rs.next()) {
                Object[] fila = new Object[5];
                fila[0] = rs.getString("id_producto");
                fila[1] = rs.getString("producto");
                fila[2] = rs.getString("precio_unitario");
                fila[3] = rs.getString("total_vendido");
                fila[4] = rs.getString("valor_Total");
                datos.add(fila);
            }
            return datos;
        } catch (Exception e) {
            System.out.println("Error al intentar obtener la tendencia de compras: " + e.toString());
        }
        return datos;
    }

}
