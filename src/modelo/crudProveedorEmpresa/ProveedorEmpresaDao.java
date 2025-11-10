package modelo.crudProveedorEmpresa;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import javax.swing.JOptionPane;

import modelo.Conexion;
import modelo.crudProveedor.CrudProveedor;

public class ProveedorEmpresaDao implements CrudProveedor<ProveedorEmpresa> {

    @Override
    public List<ProveedorEmpresa> listar() {
        List<ProveedorEmpresa> datos = new ArrayList<>();
        String sql = "SELECT p.*, pe.representante_nombre,pe.representante_documento,pe.representante_telefono,pe.representante_correo FROM proveedor p INNER JOIN proveedor_empresa pe on p.id = pe.id_proveedor;";

        try (
                Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ProveedorEmpresa pEmpresa = new ProveedorEmpresa();
                pEmpresa.setIdProveedor(rs.getInt(1));
                pEmpresa.setTipo(rs.getString(2));
                pEmpresa.setNombre(rs.getString(3));
                pEmpresa.setDocumento(rs.getString(4));
                pEmpresa.setMetodoDePago(rs.getInt(5));
                pEmpresa.setDireccion(rs.getString(6));
                pEmpresa.setTelefono(rs.getString(7));
                pEmpresa.setCorreo(rs.getString(8));
                pEmpresa.setEstado(rs.getInt(9));
                pEmpresa.setRepresentanteNombre(rs.getString(10));
                pEmpresa.setRepresentanteDocumento(rs.getString(11));
                pEmpresa.setRepresentanteTelefono(rs.getString(12));
                pEmpresa.setRepresentanteCorreo(rs.getString(13));
                datos.add(pEmpresa);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de Consulta", JOptionPane.ERROR_MESSAGE);
        }

        return datos;
    }

    @Override
    public int setAgregar(ProveedorEmpresa p) {
        // Asumimos que la fila en 'proveedor' ya fue insertada y tenemos el id
        String sql = "INSERT INTO proveedor_empresa (id_proveedor, representante_nombre, representante_documento, representante_telefono, representante_correo) VALUES (?,?,?,?,?)";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, p.getIdProveedor());
            ps.setString(2, p.getRepresentanteNombre());
            ps.setString(3, p.getRepresentanteDocumento());
            ps.setString(4, p.getRepresentanteTelefono());
            ps.setString(5, p.getRepresentanteCorreo());

            return ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de Inserción",
                    JOptionPane.ERROR_MESSAGE);
        }

        return 0;
    }

    @Override
    public int setActualizar(ProveedorEmpresa p) {
        /*
         * String sql =
         * "UPDATE producto SET nombre=?, precio=?, cantidad_stock=? WHERE id=?";
         * 
         * try (
         * Connection con = Conexion.getInstance().getConnection();
         * PreparedStatement ps = con.prepareStatement(sql)) {
         * ps.setString(1, p.getNombre());
         * ps.setDouble(2, p.getPrecio());
         * ps.setInt(3, p.getCantidad());
         * ps.setInt(4, p.getId());
         * return ps.executeUpdate();
         * } catch (Exception e) {
         * JOptionPane.showMessageDialog(null, e.toString(), "Error de Actualizacion",
         * JOptionPane.ERROR_MESSAGE);
         * }
         */
        return 0;
    }

    @Override
    public int setEliminar(int id) {
        /*
         * String sql = "DELETE FROM producto WHERE id = ?";
         * 
         * try (
         * Connection con = Conexion.getInstance().getConnection();
         * PreparedStatement ps = con.prepareStatement(sql)) {
         * ps.setInt(1, id);
         * return ps.executeUpdate();
         * } catch (Exception e) {
         * JOptionPane.showMessageDialog(null, e.toString(), "Error de Eliminación",
         * JOptionPane.ERROR_MESSAGE);
         * }
         */
        return 0;
    }

    @Override
    public List<ProveedorEmpresa> listarProveedorPendiente() {
        return null;
    }

    @Override
    public List<ProveedorEmpresa> listarProveedorActivos() {
        return null;
    }

    @Override
    public List<ProveedorEmpresa> listarProveedorInactivo() {
        return null;
    }

}