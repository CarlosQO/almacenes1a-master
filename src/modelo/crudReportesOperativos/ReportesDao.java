package modelo.crudReportesOperativos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Conexion;

public class ReportesDao implements CrudReportesOperativos {

    @Override
    public List<ReportesOpe> listar() {
        List<ReportesOpe> lista = new ArrayList<>();
        String sql = "SELECT " +
                "    CONCAT('Venta de productos - Factura #', f.id) AS Concepto, " +
                "    f.total AS Monto, " +
                "    f.fecha AS Fecha, " +
                "    mp.nombre AS Medio_de_Pago " +
                "FROM factura f " +
                "INNER JOIN metodo_pago mp ON f.id_metodo_pago = mp.id " +
                "ORDER BY f.fecha DESC";
        Connection cn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            cn = Conexion.getInstance().getConnection();
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                ReportesOpe reporte = new ReportesOpe();
                reporte.setConcepto(rs.getString("Concepto"));
                reporte.setMonto(rs.getDouble("Monto"));
                reporte.setFecha(rs.getDate("Fecha"));
                reporte.setMedioPago(rs.getString("Medio_de_Pago"));
                lista.add(reporte);
            }
        } catch (Exception e) {
            System.out.println("Error al listar reportes operativos: " + e.getMessage());
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (cn != null)
                    cn.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar conexiones: " + e.getMessage());
            }
        }

        return lista;
    }

}