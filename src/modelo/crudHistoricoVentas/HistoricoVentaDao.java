package modelo.crudHistoricoVentas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelo.Conexion;

public class HistoricoVentaDao implements CrudHistoricoVenta {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public List<Object[]> lista(String fechaInicio, String fechaFin) {
        List<Object[]> datos = new ArrayList<>();

        try {
            String sql = """
                        SELECT
                            f.fecha AS fecha_venta,
                            ped.id AS Codigo,
                            u.documento AS documento_usuario,
                            CONCAT(u.nombre, ' ', u.apellido) AS nombre_cliente,
                            u.correo,
                            p.id AS idProducto,
                            p.nombre AS producto,
                            c.nombre AS categoria,
                            df.cant AS cantidadVenta,
                            p.precio AS precio_unitario,
                            (df.cant * p.precio) AS subtotal,
                            f.total AS TotalVenta,
                            mp.nombre AS metodo_pago,
                            pe.nombre AS estado_pedido
                        FROM factura f
                        INNER JOIN usuarios u ON f.id_usuario = u.documento
                        INNER JOIN det_factura df ON df.id_factura = f.id
                        INNER JOIN producto p ON df.id_prd = p.id
                        LEFT JOIN categoria c ON p.id_categoria = c.id
                        LEFT JOIN metodo_pago mp ON f.id_metodo_pago = mp.id
                        LEFT JOIN pedido ped ON ped.id_factura = f.id
                        LEFT JOIN pedido_estado pe ON ped.id_estado_pedido = pe.id
                        WHERE f.fecha BETWEEN ? AND ?
                        ORDER BY f.fecha DESC
                    """;

            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            rs = ps.executeQuery();

            while (rs.next()) {
                Object[] fila = new Object[14];
                fila[0] = rs.getString("fecha_venta");
                fila[1] = rs.getString("Codigo");
                fila[2] = rs.getString("documento_usuario");
                fila[3] = rs.getString("nombre_cliente");
                fila[4] = rs.getString("correo");
                fila[5] = rs.getString("idProducto");
                fila[6] = rs.getString("producto");
                fila[7] = rs.getString("categoria");
                fila[8] = rs.getInt("cantidadVenta");
                fila[9] = rs.getDouble("precio_unitario");
                fila[10] = rs.getDouble("subtotal");
                fila[11] = rs.getDouble("TotalVenta");
                fila[12] = rs.getString("metodo_pago");
                fila[13] = rs.getString("estado_pedido");
                datos.add(fila);
            }

        } catch (Exception e) {
            System.out.println("Error al listar el histórico de ventas: " + e.getMessage());
        }
        return datos;
    }

}