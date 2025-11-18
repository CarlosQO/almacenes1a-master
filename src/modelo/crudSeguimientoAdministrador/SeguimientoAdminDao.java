package modelo.crudSeguimientoAdministrador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Conexion;

public class SeguimientoAdminDao implements CrudSeguimientoAdmin {

    public List<SeguimientoAdmin> listarPorMesAno(int mes, int ano) {
        List<SeguimientoAdmin> lista = new ArrayList<>();
        String sql = "SELECT DAY(fecha) AS dia, "
                + "COUNT(*) AS conexiones, "
                + "COALESCE(SEC_TO_TIME(SUM(TIME_TO_SEC(tiempoConexion))), '00:00:00') AS duracion_sum, "
                + "COALESCE(TIME(MIN(horaIngreso)), '') AS hora_ingreso, "
                + "COALESCE(TIME(MAX(horaSalida)), '') AS hora_salida "
                + "FROM administradorsesion "
                + "WHERE MONTH(fecha) = ? AND YEAR(fecha) = ?";

        sql += " GROUP BY DAY(fecha) ORDER BY dia";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, mes);
            ps.setInt(2, ano);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    SeguimientoAdmin sd = new SeguimientoAdmin();
                    sd.setDia(rs.getInt("dia"));
                    sd.setConexiones(rs.getInt("conexiones"));
                    sd.setDuracion(rs.getString("duracion_sum"));
                    sd.setHoraIngreso(rs.getString("hora_ingreso"));
                    sd.setHoraSalida(rs.getString("hora_salida"));
                    lista.add(sd);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error al consultar sesiones", JOptionPane.ERROR_MESSAGE);
        }

        return lista;
    }
}
