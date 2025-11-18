package modelo.crudFactura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Conexion;

public class DaoFactura implements CrudFactura {

    @Override
    public List<Pedido> obtenerHistorialComprasFacturas(String idUsuario, String fechaInicio, String fechaFin) {
        List<Pedido> lista = new ArrayList<>();

        String sql = "SELECT p.id AS id_pedido, " +
                "p.fecha_ultima_actualizacion AS fecha, " +
                "pe.nombre AS estado, " +
                "f.total AS total, " +
                "p.id_factura " +
                "FROM pedido p " +
                "INNER JOIN pedido_estado pe ON p.id_estado_pedido = pe.id " +
                "INNER JOIN factura f ON p.id_factura = f.id " +
                "WHERE f.id_usuario = ? " +
                "AND p.fecha_ultima_actualizacion BETWEEN ? AND ?";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, idUsuario);
            ps.setString(2, fechaInicio + " 00:00:00");
            ps.setString(3, fechaFin + " 23:59:59");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pedido p = new Pedido();
                p.setIdPedido(rs.getInt("id_pedido"));
                p.setFecha(rs.getString("fecha"));
                p.setTotal(rs.getDouble("total"));
                p.setIdFactura(rs.getInt("id_factura"));
                lista.add(p);
            }

            return lista;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error (obtenerFacturaCompras)",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public List<ProductoDetalleFactura> obtenerDetallesCompraFacturaProductos(int idFactura) {
        List<ProductoDetalleFactura> lista = new ArrayList<>();
        String sql = "SELECT p.nombre, df.precioUnitario, df.cant, df.subtotal FROM det_factura df " +
                "INNER JOIN producto p ON df.id_prd = p.id " +
                "WHERE df.id_factura = ? AND df.id_prd IS NOT NULL;";

        try (
                Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idFactura);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProductoDetalleFactura pd = new ProductoDetalleFactura();
                pd.setNombreProducto(rs.getString("nombre"));
                pd.setPrecioUnitario(rs.getDouble("precioUnitario"));
                pd.setCantidad(rs.getInt("cant"));
                pd.setSubtotal(rs.getDouble("subtotal"));
                lista.add(pd);
            }
            return lista;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public List<ProductoDetalleFactura> obtenerDetallesCompraFacturaPromocion(int idFactura) {
        List<ProductoDetalleFactura> lista = new ArrayList<>();
        String sql = "SELECT pr.nombrePromocion, df.precioUnitario, df.cant, df.subtotal FROM det_factura df " +
                "INNER JOIN promociones pr ON df.id_prom = pr.idPromocion " +
                "WHERE df.id_factura = ? AND df.id_prom IS NOT NULL;";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idFactura);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProductoDetalleFactura pd = new ProductoDetalleFactura();
                pd.setNombreProducto(rs.getString("nombrePromocion"));
                pd.setPrecioUnitario(rs.getDouble("precioUnitario"));
                pd.setCantidad(rs.getInt("cant"));
                pd.setSubtotal(rs.getDouble("subtotal"));
                lista.add(pd);
            }
            return lista;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public List<ProductoDetalleFactura> obtenerDetallesCompra(int idFactura) {
        List<ProductoDetalleFactura> listaCompleta = new ArrayList<>();
        // Obtener productos de la factura
        List<ProductoDetalleFactura> productos = obtenerDetallesCompraFacturaProductos(idFactura);
        if (productos != null) {
            listaCompleta.addAll(productos);
        }
        // Obtener promociones de la factura
        List<ProductoDetalleFactura> promociones = obtenerDetallesCompraFacturaPromocion(idFactura);
        if (promociones != null) {
            listaCompleta.addAll(promociones);
        }
        return listaCompleta;
    }

    @Override
    public boolean existeFacturaEntreFechas(String idCliente, String fechaInicio, String fechaFin) {
        String sql = "SELECT COUNT(*) AS total " +
                "FROM pedido p " +
                "JOIN factura f ON p.id_factura = f.id " +
                "WHERE p.fecha_ultima_actualizacion BETWEEN ? AND ? " +
                "AND f.id_usuario = ?";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, fechaInicio + " 00:00:00");
            ps.setString(2, fechaFin + " 23:59:59");
            ps.setString(3, idCliente);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int total = rs.getInt("total");
                return total > 0; // true si hay al menos un pedido
            }
            return false;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(),
                    "Error de Consulta (existePedidoEntreFechas)", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

}