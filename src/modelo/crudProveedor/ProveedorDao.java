package modelo.crudProveedor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Conexion;

public class ProveedorDao implements CrudProveedor<Proveedor> {

    @Override
    public List<Proveedor> listar() {
        List<Proveedor> datos = new ArrayList<>();
        String sql = "SELECT * FROM proveedor";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Proveedor pv = new Proveedor();
                pv.setId(rs.getInt(1));
                pv.setTipo(rs.getString(2));
                pv.setNombre(rs.getString(3));
                pv.setDocumento(rs.getString(4));
                pv.setMetodoDePago(rs.getInt(5));
                pv.setDireccion(rs.getString(6));
                pv.setTelefono(rs.getString(7));
                pv.setCorreo(rs.getString(8));
                pv.setEstado(rs.getInt(9));
                pv.setIdProducto(rs.getInt(10));
                datos.add(pv);
            }
        } catch (

        Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de Consulta", JOptionPane.ERROR_MESSAGE);
        }

        return datos;
    }

    @Override
    public int setAgregar(Proveedor p) {
        String sql = "INSERT INTO proveedor (tipo, nombre, documento, metodo_pago, direccion, telefono, correo, idProducto,estado) VALUES (?,?,?,?,?,?,?,?,?)";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, p.getTipo());
            ps.setString(2, p.getNombre());
            ps.setString(3, p.getDocumento());
            ps.setInt(4, p.getMetodoDePago());
            ps.setString(5, p.getDireccion());
            ps.setString(6, p.getTelefono());
            ps.setString(7, p.getCorreo());
            ps.setInt(8, p.getIdProducto());
            ps.setInt(9, p.getEstado());

            int affected = ps.executeUpdate();
            if (affected == 0) {
                return 0;
            }

            return affected;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de Inserción", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public int setActualizar(Proveedor p) {
        // No implementado aún
        return 0;
    }

    @Override
    public int setEliminar(int id) {
        // No implementado aún
        return 0;
    }

    @Override
    public boolean existeProveedorPorNit(String nit) {
        String sql = "SELECT COUNT(*) FROM proveedor WHERE documento = ?";
        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nit);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Si el conteo es > 0, ya existe
                }
            }
        } catch (Exception e) {
            System.err.println("Error verificando NIT existente: " + e.getMessage());
        }
        return false;
    }

}