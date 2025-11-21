package modelo.crudHistoVenta;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import modelo.Conexion;

public class DaoHistoVenta implements CrudHistoVenta {
    @Override
    public void registradorVenta(int idProducto, int cantidad) {
    String sql = "INSERT INTO histoventas (cantidadVenta, idProducto, fecha) VALUES (?, ?, NOW())";
    
    try (
        Connection con = Conexion.getInstance().getConnection();
        PreparedStatement ps = con.prepareStatement(sql);
    ) {
        ps.setInt(1, cantidad);
        ps.setInt(2, idProducto);

        ps.executeUpdate();

    } catch (Exception e) {
        JOptionPane.showMessageDialog(
            null, 
            e.toString(), 
            "Error de Consulta (Guardar Historial de Venta)",
            JOptionPane.ERROR_MESSAGE
        );
    }
}
}