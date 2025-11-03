package modelo.crudMetodoDePago;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Conexion;

public class MetodoPagoDao {
    public List<MetodoPago> listarMetodosPago() {
        List<MetodoPago> metodos = new ArrayList<>();
        String sql = "SELECT id, nombre FROM metodo_pago";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MetodoPago mp = new MetodoPago();
                mp.setId(rs.getInt("id"));
                mp.setNombre(rs.getString("nombre"));
                metodos.add(mp);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error al cargar métodos de pago",
                    JOptionPane.ERROR_MESSAGE);
        }

        return metodos;
    }
}