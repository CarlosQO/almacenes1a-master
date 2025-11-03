package modelo.crudSeguimientoVendedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Conexion;

public class SeguimientoVenDao implements CrudSeguimientoVendedor {

    @Override
    public List<SeguimientoVen> listarTodos() {
        List<SeguimientoVen> lista = new ArrayList<>();

        String sql = "SELECT u.nombre AS Nombre_Vendedor, "
                + "u.documento AS Documento, "
                + "SUM(CASE WHEN pe.nombre = 'Pendiente' THEN 1 ELSE 0 END) AS Pedidos_No_Entregados, "
                + "SUM(CASE WHEN pe.nombre = 'Entregado' THEN 1 ELSE 0 END) AS Pedidos_Entregados, "
                + "SUM(CASE WHEN pe.nombre = 'Cancelado' THEN 1 ELSE 0 END) AS Pedidos_Cancelados, "
                + "COUNT(p.id) AS Pedidos_Despachados "
                + "FROM vendedores v "
                + "INNER JOIN usuarios u ON v.idUsuario = u.documento "
                + "LEFT JOIN vendedorhistoventas vh ON vh.idVendedor = v.idVendedor "
                + "LEFT JOIN pedido p ON vh.idPedido = p.id "
                + "LEFT JOIN pedido_estado pe ON p.id_estado_pedido = pe.id "
                + "GROUP BY u.nombre, u.documento ORDER BY u.nombre";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SeguimientoVen sv = new SeguimientoVen();
                sv.setNombreVendedor(rs.getString("Nombre_Vendedor"));
                sv.setDocumento(rs.getString("Documento"));
                sv.setPedidosNoEntregados(rs.getInt("Pedidos_No_Entregados"));
                sv.setPedidosEntregados(rs.getInt("Pedidos_Entregados"));
                sv.setPedidosCancelados(rs.getInt("Pedidos_Cancelados"));
                sv.setPedidosDespachados(rs.getInt("Pedidos_Despachados"));
                lista.add(sv);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error al consultar seguimiento vendedor",
                    JOptionPane.ERROR_MESSAGE);
        }

        return lista;
    }

    @Override
    public List<SeguimientoVen> listarPorDocumentoAnoMes(String documento, int ano, int mes) {
        List<SeguimientoVen> lista = new ArrayList<>();

        String sql = "SELECT u.nombre AS Nombre_Vendedor, "
                + "u.documento AS Documento, "
                + "SUM(CASE WHEN pe.nombre = 'Pendiente' AND MONTH(p.fecha)=? AND YEAR(p.fecha)=? THEN 1 ELSE 0 END) AS Pedidos_No_Entregados, "
                + "SUM(CASE WHEN pe.nombre = 'Entregado' AND MONTH(p.fecha)=? AND YEAR(p.fecha)=? THEN 1 ELSE 0 END) AS Pedidos_Entregados, "
                + "SUM(CASE WHEN pe.nombre = 'Cancelado' AND MONTH(p.fecha)=? AND YEAR(p.fecha)=? THEN 1 ELSE 0 END) AS Pedidos_Cancelados, "
                + "SUM(CASE WHEN p.id IS NOT NULL AND MONTH(p.fecha)=? AND YEAR(p.fecha)=? THEN 1 ELSE 0 END) AS Pedidos_Despachados "
                + "FROM vendedores v "
                + "INNER JOIN usuarios u ON v.idUsuario = u.documento "
                + "LEFT JOIN vendedorhistoventas vh ON vh.idVendedor = v.idVendedor "
                + "LEFT JOIN pedido p ON vh.idPedido = p.id "
                + "LEFT JOIN pedido_estado pe ON p.id_estado_pedido = pe.id";

        if (documento != null && !documento.trim().isEmpty()) {
            sql += " WHERE u.documento = ?";
        }

        sql += " GROUP BY u.nombre, u.documento ORDER BY u.nombre";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            // set month/year parameters repeated where used
            ps.setInt(1, mes);
            ps.setInt(2, ano);
            ps.setInt(3, mes);
            ps.setInt(4, ano);
            ps.setInt(5, mes);
            ps.setInt(6, ano);
            ps.setInt(7, mes);
            ps.setInt(8, ano);

            if (documento != null && !documento.trim().isEmpty()) {
                ps.setString(9, documento.trim());
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SeguimientoVen sv = new SeguimientoVen();
                    sv.setNombreVendedor(rs.getString("Nombre_Vendedor"));
                    sv.setDocumento(rs.getString("Documento"));
                    sv.setPedidosNoEntregados(rs.getInt("Pedidos_No_Entregados"));
                    sv.setPedidosEntregados(rs.getInt("Pedidos_Entregados"));
                    sv.setPedidosCancelados(rs.getInt("Pedidos_Cancelados"));
                    sv.setPedidosDespachados(rs.getInt("Pedidos_Despachados"));
                    lista.add(sv);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error al consultar seguimiento vendedor",
                    JOptionPane.ERROR_MESSAGE);
        }

        return lista;
    }
}
