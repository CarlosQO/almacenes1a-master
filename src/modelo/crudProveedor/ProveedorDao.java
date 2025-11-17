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
        String sql = "INSERT INTO proveedor (tipo, nombre, documento, metodo_pago, direccion, telefono, correo,idProducto, estado) VALUES (?,?,?,?,?,?,?,?,?)";

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

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
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
    public List<Proveedor> listarProveedorPendiente() {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT proveedor.*, producto.nombre AS nombreProduc FROM proveedor INNER JOIN producto ON proveedor.idProducto = producto.id WHERE proveedor.estado = 3";
        try {
            Connection con = Conexion.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setId(rs.getInt(1));
                proveedor.setTipo(rs.getString(2));
                proveedor.setNombre(rs.getString(3));
                proveedor.setDocumento(rs.getString(4));
                proveedor.setMetodoDePago(rs.getInt(5));
                proveedor.setDireccion(rs.getString(6));
                proveedor.setTelefono(rs.getString(7));
                proveedor.setCorreo(rs.getString(8));
                proveedor.setEstado(rs.getInt("estado"));
                proveedor.setProducto(rs.getString("nombreProduc"));
                proveedores.add(proveedor);
            }
            return proveedores;
        } catch (Exception e) {
            System.out.println("Error al listar proveedores pendientes: " + e.getMessage());
        }
        return proveedores;
    }

    @Override
    public List<Proveedor> listarProveedorActivos() {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT proveedor.*, producto.nombre AS nombreProduc, metodo_pago.nombre AS metodoPago FROM proveedor INNER JOIN producto ON proveedor.idProducto = producto.id INNER JOIN metodo_pago ON proveedor.metodo_pago = metodo_pago.id WHERE proveedor.estado = 1";
        try {
            Connection con = Conexion.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setId(rs.getInt(1));
                proveedor.setTipo(rs.getString(2));
                proveedor.setNombre(rs.getString(3));
                proveedor.setDocumento(rs.getString(4));
                proveedor.setMetodoDePago(rs.getInt(5));
                proveedor.setDireccion(rs.getString(6));
                proveedor.setTelefono(rs.getString(7));
                proveedor.setCorreo(rs.getString(8));
                proveedor.setEstado(rs.getInt("estado"));
                proveedor.setProducto(rs.getString("nombreProduc"));
                proveedor.setMetodoPagoVarchar(rs.getString("metodoPago"));
                proveedores.add(proveedor);
            }
            return proveedores;
        } catch (Exception e) {
            System.out.println("Error al listar proveedores activos: " + e.getMessage());
        }
        return proveedores;
    }

    @Override
    public List<Proveedor> listarProveedorInactivo() {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT proveedor.*, producto.nombre AS nombreProduc, metodo_pago.nombre AS metodoPago FROM proveedor INNER JOIN producto ON proveedor.idProducto = producto.id INNER JOIN metodo_pago ON proveedor.metodo_pago = metodo_pago.id WHERE proveedor.estado = 2";
        try {
            Connection con = Conexion.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setId(rs.getInt(1));
                proveedor.setTipo(rs.getString(2));
                proveedor.setNombre(rs.getString(3));
                proveedor.setDocumento(rs.getString(4));
                proveedor.setMetodoDePago(rs.getInt(5));
                proveedor.setDireccion(rs.getString(6));
                proveedor.setTelefono(rs.getString(7));
                proveedor.setCorreo(rs.getString(8));
                proveedor.setEstado(rs.getInt("estado"));
                proveedor.setProducto(rs.getString("nombreProduc"));
                proveedor.setMetodoPagoVarchar(rs.getString("metodoPago"));
                proveedores.add(proveedor);
            }
            return proveedores;
        } catch (Exception e) {
            System.out.println("Error al listar proveedores inactivos: " + e.getMessage());
        }
        return proveedores;
    }

    @Override
    public int CambiarEstado(int id, int estado) {
        String sql = "UPDATE proveedor SET estado = ? WHERE documento = ?";
        try {
            Connection con = Conexion.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, estado);
            ps.setInt(2, id);
            return ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error al cambiar el estado del proveedor: " + e.getMessage());
        }
        return 0;
    }

    @Override
    public List<Proveedor> listarProveedorPorID(String documento) {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT proveedor.*, producto.nombre AS nombreProduc, metodo_pago.nombre AS metodoPago FROM proveedor INNER JOIN producto ON proveedor.idProducto = producto.id INNER JOIN metodo_pago ON proveedor.metodo_pago = metodo_pago.id WHERE proveedor.documento = ?";
        try {
            Connection con = Conexion.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, documento);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setId(rs.getInt(1));
                proveedor.setTipo(rs.getString(2));
                proveedor.setDocumento(rs.getString("documento"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedor.setMetodoDePago(rs.getInt(5));
                proveedor.setDireccion(rs.getString(6));
                proveedor.setTelefono(rs.getString(7));
                proveedor.setCorreo(rs.getString(8));
                proveedor.setEstado(rs.getInt("estado"));
                proveedor.setProducto(rs.getString("nombreProduc"));
                proveedor.setMetodoPagoVarchar(rs.getString("metodoPago"));
                proveedores.add(proveedor);
            }
            return proveedores;
        } catch (Exception e) {
            System.out.println("Error al listar proveedor por ID: " + e.getMessage());
        }
        return proveedores;
    }

    @Override
    public List<Proveedor> listarProveedorActivosInactivos() {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT proveedor.*, producto.nombre AS nombreProduc, metodo_pago.nombre AS metodoPago,  proveedor_estado.nombre AS proveedorEstadoVarchar FROM proveedor INNER JOIN producto ON proveedor.idProducto = producto.id INNER JOIN metodo_pago ON proveedor.metodo_pago = metodo_pago.id INNER JOIN proveedor_estado ON proveedor.estado = proveedor_estado.id WHERE proveedor.estado IN (1, 2)";
        try {
            Connection con = Conexion.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setId(rs.getInt(1));
                proveedor.setTipo(rs.getString(2));
                proveedor.setNombre(rs.getString(3));
                proveedor.setDocumento(rs.getString(4));
                proveedor.setMetodoDePago(rs.getInt(5));
                proveedor.setDireccion(rs.getString(6));
                proveedor.setTelefono(rs.getString(7));
                proveedor.setCorreo(rs.getString(8));
                proveedor.setEstado(rs.getInt(9));
                proveedor.setProducto(rs.getString("nombreProduc"));
                proveedor.setMetodoPagoVarchar(rs.getString("metodoPago"));
                proveedor.setEstadoVarchar(rs.getString("proveedorEstadoVarchar"));
                proveedores.add(proveedor);
            }
            return proveedores;
        } catch (Exception e) {
            System.out.println("Error al listar proveedores activos e inactivos: " + e.getMessage());
        }
        return proveedores;
    }

    @Override
    public boolean existeProveedorPorNit(String nit) {
        String sql = "SELECT COUNT(*) FROM proveedor WHERE documento = ?";
        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nit);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (Exception e) {
            System.err.println("Error verificando NIT existente: " + e.getMessage());
        }
        return false;
    }
}