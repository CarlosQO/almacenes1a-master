package modelo.crudPromociones;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Conexion;
import modelo.crudProducto.Producto;

public class PromocionDao implements CrudPromocion<Producto> {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public List mostrarPromociones() {
        List<Promocion> lista = new ArrayList<>();
        String sql = "SELECT * FROM promociones WHERE cantidad>0";

        try (
                Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();) {

            while (rs.next()) {
                Promocion promo = new Promocion();
                promo.setIdPromocion(rs.getInt("idPromocion"));
                promo.setIdPrimerProducto(rs.getInt("idPrimerProducto"));

                // Validar idSegunProducto
                int idSegundo = rs.getInt("idSegunProducto");
                if (rs.wasNull()) {
                    promo.setIdSegundoProducto(-1); // Guardar la palabra "null"
                } else {
                    promo.setIdSegundoProducto(idSegundo);
                }

                promo.setDescripcionPromocion(rs.getString("descripcion"));
                promo.setNombrePromocion(rs.getString("nombrePromocion"));
                promo.setPorcentajeDescuento(rs.getInt("porcentajeDescuento"));

                // Validar imagenSegunda
                String imagenSegunda = rs.getString("imagenSegunda");
                if (imagenSegunda == null || imagenSegunda.trim().isEmpty()) {
                    promo.setRutaImagenSegunda("null");
                } else {
                    promo.setRutaImagenSegunda(imagenSegunda);
                }

                promo.setRutaImagenPrimera(rs.getString("imagen"));
                promo.setTotal(rs.getDouble("precio"));

                lista.add(promo);
            }
            return lista;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error al consultar promociones", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public int RegistrarPromocion(Promocion p) {
        try {
            String Sql = "INSERT INTO promociones( idPrimerProducto, idSegunProducto, descripcion, nombrePromocion, porcentajeDescuento, precio, cantidad, imagen, imagenSegunda) VALUES (?,?,?,?,?,?,?,?,?)";
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(Sql);
            ps.setInt(1, p.getIdPrimerProducto());
            if (p.getIdSegundoProducto() == 0) {
                ps.setNull(2, 0);
            } else {
                ps.setInt(2, p.getIdSegundoProducto());
            }
            ps.setString(3, p.getDescripcionPromocion());
            ps.setString(4, p.getNombrePromocion());
            ps.setInt(5, p.getPorcentajeDescuento());
            ps.setDouble(6, p.getTotal());
            ps.setInt(7, p.getCantidad());
            ps.setString(8, p.getRutaImagenPrimera());
            if (p.getRutaImagenSegunda() == "") {
                ps.setString(9, null);
            } else {
                ps.setString(9, p.getRutaImagenSegunda());
            }

            int filasInsertadas = ps.executeUpdate();

            if (filasInsertadas > 0) {
                System.out.println(" Inserción exitosa en promociones");
                return 1;
            } else {
                System.out.println("️ No se insertó ningún registro");
            }
        } catch (Exception e) {
            System.out.println("Error en la consulta de registrar promocion " + e.toString());
        }
        return 0;
    }

    @Override
    public int numeroPromociones() {
        String sql = "SELECT COUNT(*) FROM promociones WHERE cantidad>0";

        try (
                Connection conexion = Conexion.getInstance().getConnection();
                PreparedStatement ps = conexion.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); // Retorna el número total de promociones
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error al contar promociones", JOptionPane.ERROR_MESSAGE);
            return 0; // Error al ejecutar la consulta
        }
        return 0; // Si no hay resultado
    }

    @Override
    public int cantidadEnStockPromociones(int idPromocion) {
        String sql = "SELECT cantidad FROM promociones WHERE idPromocion=?";
        try (
                Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);) {

            ps.setInt(1, idPromocion);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("cantidad");
            }
            return -1;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error al sacar numero promociones",
                    JOptionPane.ERROR_MESSAGE);
            return -1; // Error al ejecutar la consulta
        }
    }

    @Override
    public boolean validarStockPromocion(int idPromocion, int cantidadSolicitada) {
        String sql = "SELECT cantidad FROM promociones WHERE idPromocion = ?";
        try (
                Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPromocion);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int cantidadDisponible = rs.getInt("cantidad");
                return cantidadDisponible >= cantidadSolicitada;
            }
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error al validar stock de promoción",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public void disminuirStockPromocion(int idPromocion, int cantidadVendida) {
        String sql = "UPDATE promociones SET cantidad = cantidad - ? WHERE idPromocion = ?";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cantidadVendida);
            ps.setInt(2, idPromocion);

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas > 0) {
            } else {
                JOptionPane.showMessageDialog(null,
                        "No se encontró la promoción con el ID especificado.",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al disminuir el stock de la promoción: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public List<Integer> obtenerIdsProductosPromocion(int idPromocion) {
        List<Integer> idsProductos = new ArrayList<>();
        String sql = "SELECT idPrimerProducto, idSegunProducto FROM promociones WHERE idPromocion = ?";

        try (Connection conexion = Conexion.getInstance().getConnection();
                PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idPromocion);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int idPrimerProducto = rs.getInt("idPrimerProducto");
                int idSegunProducto = rs.getInt("idSegunProducto");

                // Solo agregar si el id es válido (> 0)
                if (idPrimerProducto > 0) {
                    idsProductos.add(idPrimerProducto);
                }

                if (idSegunProducto > 0) {
                    idsProductos.add(idSegunProducto);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error obteniendo productos de promoción",
                    JOptionPane.ERROR_MESSAGE);
        }

        return idsProductos;
    }

}
