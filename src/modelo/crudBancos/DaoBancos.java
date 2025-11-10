package modelo.crudBancos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Conexion;

public class DaoBancos implements CrudBanco {
    @Override
    public List<Banco> listarBancos() {
        List<Banco> lista = new ArrayList<>();
        String sql = "SELECT idBanco, nombreBanco FROM bancos";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Banco banco = new Banco();
                banco.setIdBanco(rs.getInt("idBanco"));
                banco.setNombreBanco(rs.getString("nombreBanco"));
                lista.add(banco);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al listar bancos: " + e.getMessage());
        }

        return lista;
    }
}
