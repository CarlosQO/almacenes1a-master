package modelo.crudInformeGlobal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelo.Conexion;

public class InformeGlobalDao implements CrudInformesGlobal {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public int totalPedidosFechas(String fechaInicio, String fechaFin) {
        try {
            String sql = "SELECT COUNT(*) AS total_pedidos FROM pedido WHERE fecha_ultima_actualizacion BETWEEN ? AND ?";
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error al intentar obtener el total de pedidos " + e.toString());
        }
        return 0;
    }

    @Override
    public String metodoDePagoFecha(String fechaInicio, String fechaFin) {
        try {
            String sql = "SELECT mp.nombre AS metodo_pago FROM factura f INNER JOIN metodo_pago mp ON f.id_metodo_pago = mp.id WHERE f.fecha BETWEEN ? AND ? GROUP BY mp.nombre ORDER BY COUNT(*) DESC LIMIT 1;";
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("Error al intentar obtener el metodo de pago fecha " + e.toString());
        }
        return "";
    }

    @Override
    public List<String> totalVentaYingresosGenerados(String fechaInicio, String fechaFin) {
        List<String> resul = new ArrayList<>();
        try {
            String sql = "SELECT "
                    + "COALESCE(SUM(h.cantidadVenta), 0) AS total_unidades_vendidas, "
                    + "COALESCE(SUM(h.cantidadVenta * p.precio), 0) AS total_ganancias "
                    + "FROM histoVentas h "
                    + "INNER JOIN producto p ON h.idProducto = p.id "
                    + "WHERE h.fecha BETWEEN ? AND ?";
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            rs = ps.executeQuery();
            if (rs.next()) {
                resul.add(rs.getString(1));
                resul.add(rs.getString(2));
                return resul;
            }
        } catch (Exception e) {
            System.out.println(
                    "Error al intentar obtener la cantidad de venta y la ganancias por fechas " + e.toString());
        }
        return resul;
    }

    @Override
    public String tendenciaDeCompraFecha(String fechaInicio, String fechaFin) {
        try {
            String sql = "SELECT "
                    + "c.nombre AS categoria_mas_vendida "
                    + "FROM histoventas h "
                    + "INNER JOIN producto p ON h.idProducto = p.id "
                    + "INNER JOIN categoria c ON p.id_categoria = c.id "
                    + "WHERE h.fecha BETWEEN ? AND ? "
                    + "GROUP BY c.id, c.nombre "
                    + "ORDER BY SUM(h.cantidadVenta) DESC "
                    + "LIMIT 1";
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("categoria_mas_vendida");
            }
        } catch (Exception e) {
            System.out.println("Error al intentar obtener la tendencia de compra por fecha " + e.toString());
        }
        return "";
    }
}