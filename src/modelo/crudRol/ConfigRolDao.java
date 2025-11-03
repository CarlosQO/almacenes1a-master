package modelo.crudRol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import modelo.Conexion;

public class ConfigRolDao implements CrudRol<Rol> {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public List<Rol> listar() {
        List<Rol> lista = new ArrayList<>();
        String sql = "SELECT * FROM rol WHERE nombre <> 'Administrador'";
        try {
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Rol rol = new Rol();
                rol.setId_rol(rs.getInt(1));
                rol.setRol(rs.getString(2));
                lista.add(rol);
            }
        } catch (Exception e) {
            System.out.println("Error al listar roles: " + e);
        }
        return lista;
    }

    @Override
    public int setActualizarRol(int id, String rol) {
        int columnAfectada = 0;
        String SqlActualizar = "UPDATE rol SET rol = ? WHERE id_rol = ?";
        try {
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(SqlActualizar);
            ps.setString(1, rol);
            ps.setInt(2, id);
            columnAfectada = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al actualizar rol: " + e);
        }
        return columnAfectada;
    }
}
