
package modelo.crudPedidos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Conexion;

public class DaoPedido implements CrudPedido {

    @Override
    public List<Pedido> obtenerPedidos(String idUsuario) {
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
                "AND p.id_estado_pedido = 1";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pedido p = new Pedido();
                p.setIdPedido(rs.getInt("id_pedido"));
                p.setFecha(rs.getString("fecha")); // fecha_ultima_actualizacion
                p.setEstado(rs.getString("estado"));
                p.setTotal(rs.getDouble("total"));
                p.setIdFactura(rs.getInt("id_factura"));
                lista.add(p);
            }
            return lista;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error (obtenerHistorialCompras)",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }

    }

    @Override
    public List<ProductoDetalle> obtenerDetallesProductos(int idFactura) {
        List<ProductoDetalle> lista = new ArrayList<>();
        String sql = "SELECT p.nombre, df.precioUnitario, df.cant, df.subtotal FROM det_factura df " +
                "INNER JOIN producto p ON df.id_prd = p.id WHERE df.id_factura = ?";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idFactura);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProductoDetalle pd = new ProductoDetalle();
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
    public List<ProductoDetalle> obtenerDetallesPromocion(int idFactura) {
        List<ProductoDetalle> lista = new ArrayList<>();
        String sql = "SELECT pr.nombrePromocion, df.precioUnitario, df.cant, df.subtotal FROM det_factura df " +
                "INNER JOIN promociones pr ON df.id_prom = pr.idPromocion " +
                "WHERE df.id_factura = ? AND df.id_prom IS NOT NULL;";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idFactura);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProductoDetalle pd = new ProductoDetalle();
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
    public List<ProductoDetalle> obtenerProductoPedidos(int idFactura) {
        List<ProductoDetalle> listaCompleta = new ArrayList<>();
        // Obtener productos de la factura
        List<ProductoDetalle> productos = obtenerDetallesProductos(idFactura);
        if (productos != null) {
            listaCompleta.addAll(productos);
        }
        // Obtener promociones de la factura
        List<ProductoDetalle> promociones = obtenerDetallesPromocion(idFactura);
        if (promociones != null) {
            listaCompleta.addAll(promociones);
        }
        return listaCompleta;
    }

    @Override
    public void actualizaEstadoProductoEntregado(int idFactura) {
        String sql = "UPDATE pedido SET id_estado_pedido = 2 WHERE id_factura = ?";
        try (
                Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(1, idFactura);
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error (actualizacion de estado)",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actualizaEstadoProductoNoEntregado(int idFactura) {
        String sql = "UPDATE pedido SET id_estado_pedido = 3 WHERE id_factura = ?";
        try (
                Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(1, idFactura);
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error (actualizacion de estado)",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public List<EstadoPedido> getEstadosPedidos() {
        List<EstadoPedido> datos = new ArrayList<>();
        String sql = "SELECT * FROM pedido_estado";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                EstadoPedido estado = new EstadoPedido();
                estado.setId(rs.getInt(1));
                estado.setEstado(rs.getString(2));
                datos.add(estado);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de Consulta", JOptionPane.ERROR_MESSAGE);
        }

        return datos;
    }

    @Override
    public List<Pedido> getPedidosPorEstado(String estado) {
        List<Pedido> datos = new ArrayList<>();
        String sql = "SELECT p.id, u.nombre, u.apellido, prod.nombre AS producto, ep.nombre as estado FROM pedido p JOIN factura f ON p.id_factura = f.id JOIN usuarios u ON f.id_usuario = u.documento JOIN det_factura df ON p.id_factura = df.id JOIN producto prod ON df.id_prd = prod.id JOIN pedido_estado ep ON p.id_estado_pedido = ep.id WHERE ep.nombre = ?";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, estado);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Pedido pd = new Pedido();
                pd.setIdPedido(rs.getInt("id"));
                pd.setCliente(rs.getString("u.nombre") + " " + rs.getString("u.apellido"));
                pd.setProductos(rs.getString("producto"));
                pd.setEstado(rs.getString("estado"));
                datos.add(pd);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de Consulta", JOptionPane.ERROR_MESSAGE);
        }

        return datos;
    }

    @Override
    public boolean cambiarEstadoPedido(int idPedido, int idEstado) {

        String sql = "UPDATE pedido SET id_estado_pedido = ? WHERE id = ?";
        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEstado);
            ps.setInt(2, idPedido);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de Actualización", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public List<PedidoSupervisor> listarPE() {
        List<PedidoSupervisor> datos = new ArrayList<>();
        String sql = "SELECT * FROM pedido where id_estado_pedido = 1";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PedidoSupervisor pd = new PedidoSupervisor();
                pd.setId(rs.getInt(1));
                pd.setIdEstadoPedido(rs.getInt(2));
                pd.setFechaUltimaModi(rs.getTimestamp(3));
                datos.add(pd);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de Consulta", JOptionPane.ERROR_MESSAGE);
        }

        return datos;
    }

    @Override
    public List<PedidoSupervisor> listarPNoE() {
        List<PedidoSupervisor> datos = new ArrayList<>();
        String sql = "SELECT * FROM pedido where id_estado_pedido = 3";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PedidoSupervisor pd = new PedidoSupervisor();
                pd.setId(rs.getInt(1));
                pd.setIdEstadoPedido(rs.getInt(2));
                pd.setFechaUltimaModi(rs.getTimestamp(3));
                datos.add(pd);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de Consulta", JOptionPane.ERROR_MESSAGE);
        }

        return datos;
    }
}