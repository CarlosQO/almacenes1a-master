package modelo.crudAdministradorSesion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Conexion;

public class AdministradorDao implements CrudAdministrador {
    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public int registrarHoraIngreso(String idAdministrador) {
        String sql = "INSERT INTO administradorsesion " +
                "(idAdministrador, fecha, horaIngreso) " +
                "VALUES (?, CURDATE(), CURTIME())";
        try {
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, idAdministrador);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("Error al registrar hora de ingreso del administrador: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public int registrarHoraSalida(int idSesion) {
        String sql = "UPDATE administradorsesion SET horaSalida = NOW(), tiempoConexion = TIMEDIFF(NOW(), horaIngreso) WHERE idSesion = ?";
        try {
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idSesion);
            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al registrar hora de salida del administrador: " + e.getMessage());
        }
        return -1;
    }

    @Override
    public List<Object> lista() {
        List<Object> admin = new ArrayList<>();
        String sql = "SELECT * FROM usuarios WHERE id_rol = 1";
        try {
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                admin.add(rs.getObject(1));
                admin.add(rs.getObject(2));
                admin.add(rs.getObject(3));
                admin.add(rs.getObject(4));
                admin.add(rs.getObject(5));
                admin.add(rs.getObject(6));
                admin.add(rs.getObject(7));
                admin.add(rs.getObject(8));
                admin.add(rs.getObject(9));
            }
            return admin;
        } catch (Exception e) {
            System.out.println("Error al listar administradores: " + e.getMessage());
        }
        return admin;
    }

}
