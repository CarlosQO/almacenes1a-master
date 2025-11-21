package modelo.crudCarrito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import modelo.Conexion;

public class DaoCarrito implements CrudCarrito<ProductosCarrito> {

    // herramientas
    @Override
    public String limpiarCarrito(String idUsuario) {
        int idCarrito = encontrarCarritoActivo(idUsuario);

        if (idCarrito == -1) {
            return "No se encontró un carrito activo para el usuario.";
        }
        // borrar solo los detalles pero dejo el carrito activo para crear uno nuevo
        String sql = "DELETE FROM det_carrito WHERE id_carrito = ?";
        try (Connection conexion = Conexion.getInstance().getConnection();
                PreparedStatement psDetalle = conexion.prepareStatement(sql)) {
            psDetalle.setInt(1, idCarrito);
            psDetalle.executeUpdate();
            return "Carrito limpiado correctamente.";
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error al limpiar carrito", JOptionPane.ERROR_MESSAGE);
            return "Error al limpiar carrito.";
        }
    }

    @Override
    public int facturaInsert(String idUsuario, int idMetodoPago, double total) {
        String sql = "INSERT INTO factura (id_usuario, id_metodo_pago, fecha, total) VALUES (?, ?, NOW(), ?)";

        try (
                Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);) {
            ps.setString(1, idUsuario);
            ps.setInt(2, idMetodoPago);
            ps.setDouble(3, total);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // retorna el id autogenerado
            }
            return -1;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error al guardar factura", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    @Override
    public void detFacturaInsertProdcuto(int idFactura, int idProducto, int cantidad, double precioUnitario,
            double subtotal) {
        String sql = "INSERT INTO det_factura (id_prd, cant, id_factura, precioUnitario, subtotal) VALUES (?, ?, ?, ?,?)";

        try (
                Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(1, idProducto);
            ps.setInt(2, cantidad);
            ps.setInt(3, idFactura);
            ps.setDouble(4, precioUnitario);
            ps.setDouble(5, subtotal);
            ps.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error al guardar detalle de factura producto",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void detFacturaInsertPromocion(int idFactura, int idPromocion, int cantidad, double precioUnitario,
            double subtotal) {
        String sql = "INSERT INTO det_factura (id_prom, cant, id_factura, precioUnitario, subtotal) VALUES (?, ?, ?, ?, ?)";

        try (
                Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(1, idPromocion);
            ps.setInt(2, cantidad);
            ps.setInt(3, idFactura);
            ps.setDouble(4, precioUnitario);
            ps.setDouble(5, subtotal);
            ps.executeUpdate();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error al guardar detalle de factura promocion",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void guardarFacturaConDetallesProductos(String idUsuario, int idMetodoPago, double total,
            List<ProductosCarrito> productos) {
        int idFactura = facturaInsert(idUsuario, idMetodoPago, total);
        if (idFactura != -1) {
            for (ProductosCarrito p : productos) {
                detFacturaInsertProdcuto(
                        idFactura, p.getIdProducto(),
                        p.getCantidadProducto(), p.getPrecioUnitarioProducto(),
                        p.getSubtotalPorProducto());
            }
        }
    }

    @Override
    public void guardarFacturaConDetallesPromocion(int idFactura, double total,
            List<modelo.crudCarrito.PromocionCarrito> promociones) {
        if (idFactura != -1) {
            for (modelo.crudCarrito.PromocionCarrito p : promociones) {
                detFacturaInsertProdcuto(
                        idFactura, p.getIdPromocion(),
                        p.getCantidadPromocion(), p.getPrecioUnitarioPromocion(),
                        p.getSubtotalPorPromocion());
            }
        }
    }

    @Override
    public int encontrarCarritoActivo(String idCliente) {
        String sql = "SELECT id FROM carrito WHERE id_usuario = ?";

        try (
                Connection conexion = Conexion.getInstance().getConnection();
                PreparedStatement ps = conexion.prepareStatement(sql);) {
            ps.setString(1, idCliente);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Devuelve el id del carrito encontrado
                int id_encontrado = rs.getInt("id");
                return id_encontrado;
            } else {
                // No se encontró carrito para ese usuario
                return -1;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error buscando carrito activo",
                    JOptionPane.ERROR_MESSAGE);
            return -1; // En caso de error también devolvemos -1
        }
    }

    @Override
    public void registrarPedido(int idFactura) {
        String sql = "INSERT INTO pedido (id_estado_pedido, fecha_ultima_actualizacion, id_factura) VALUES (?, NOW(), ?)";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, 1); // estado 1 para pendiente
            ps.setInt(2, idFactura); // Factura asociada
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Pedido registrado correctamente.");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar el pedido: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override // por si no hay un item(produc promocion) sin stock, se elimine del carrito
    public void validarStockItemsCarrito(String idUsuario) {
        String sqlProductos = "SELECT d.id_producto, p.cantidad, p.id_estado FROM det_carrito d " +
                "INNER JOIN carrito c ON d.id_carrito = c.id " +
                "INNER JOIN producto p ON d.id_producto = p.id " +
                "WHERE c.id_usuario = ?";

        String sqlPromociones = "SELECT d.idPromocion, pr.cantidad " +
                "FROM det_carrito d " +
                "INNER JOIN carrito c ON d.id_carrito = c.id " +
                "INNER JOIN promociones pr ON d.idPromocion = pr.idPromocion " +
                "WHERE c.id_usuario = ?";

        try (Connection conexion = Conexion.getInstance().getConnection()) {

            // VALIDAR PRODUCTOS +
            try (PreparedStatement psProd = conexion.prepareStatement(sqlProductos)) {
                psProd.setString(1, idUsuario);
                ResultSet rsProd = psProd.executeQuery();

                while (rsProd.next()) {
                    int idProducto = rsProd.getInt("id_producto");
                    int estado = rsProd.getInt("id_estado");

                    // Se elimina solo si el estado es 2 (inactivo)
                    if (estado == 2) {
                        eliminarProductoDelCarrito(idProducto, idUsuario);
                    }
                }
            }

            // VALIDAR PROMOCIONES
            try (PreparedStatement psPromo = conexion.prepareStatement(sqlPromociones)) {
                psPromo.setString(1, idUsuario);
                ResultSet rsPromo = psPromo.executeQuery();

                while (rsPromo.next()) {
                    int idPromocion = rsPromo.getInt("idPromocion");
                    int stock = rsPromo.getInt("cantidad");
                    // Se elimina solo si el stock está agotado
                    if (stock <= 0) {
                        eliminarPromocionDelCarrito(idPromocion, idUsuario);
                    }
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error validando stock del carrito",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // productos
    @Override
    public List<ProductosCarrito> mostrarProductosCarrito(String idUsuario) {
        List<ProductosCarrito> info = new ArrayList<>();
        String sql = "SELECT c.id_usuario, d.id_producto, d.cantidad, d.imagen, d.id_carrito, d.subtotal, " +
                "p.nombre AS nombre_producto, p.precio AS precio_producto FROM carrito c " +
                "INNER JOIN det_carrito d ON c.id = d.id_carrito " +
                "INNER JOIN producto p ON d.id_producto = p.id " +
                "WHERE c.id_usuario = ?";

        try (
                Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setString(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ProductosCarrito carrito = new ProductosCarrito();
                carrito.setIdCliente(rs.getString("id_usuario"));
                carrito.setIdProducto(rs.getInt("id_producto"));
                carrito.setNombreProducto(rs.getString("nombre_producto"));
                carrito.setCantidadProducto(rs.getInt("cantidad"));
                carrito.setImagen(rs.getString("imagen"));
                carrito.setIdCarrito(rs.getInt("id_carrito"));
                carrito.setPrecioUnitarioProducto(rs.getDouble("precio_producto"));
                carrito.setSubtotalPorProducto(rs.getDouble("subtotal"));
                info.add(carrito);
            }
            return info;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de Consulta(lista)", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public int agregarProductosAlCarrito(int idProducto, String idUsuario, String imagen, int cantidad,
            double precioUnitario) {
        int validarExistencia = productoExisteEnCarrito(idUsuario, idProducto);

        // Si el producto ya está en el carrito, solo actualizamos cantidad y subtotal
        if (validarExistencia == 2) {
            aumentarCantidadProductoDelCarrito(idProducto, idUsuario, precioUnitario);
            return 2; // 2 = producto actualizado
        }

        // Buscar carrito activo del usuario
        int idCarrito = encontrarCarritoActivo(idUsuario);

        // Si no tiene carrito, creamos uno nuevo
        if (idCarrito == -1) {
            String sqlNuevoCarrito = "INSERT INTO carrito(id_usuario) VALUES (?)";
            try (
                    Connection conexion = Conexion.getInstance().getConnection();
                    PreparedStatement ps = conexion.prepareStatement(sqlNuevoCarrito,
                            PreparedStatement.RETURN_GENERATED_KEYS);) {
                ps.setString(1, idUsuario);
                int respuesta = ps.executeUpdate();

                if (respuesta > 0) {
                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        idCarrito = rs.getInt(1); // Guardamos el nuevo id del carrito
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString(), "Error creando nuevo carrito",
                        JOptionPane.ERROR_MESSAGE);
                return 0;
            }
        }

        // Insertar producto en detalle del carrito
        String sqlDetCarrito = "INSERT INTO det_carrito(id_producto, cantidad, imagen, precioUnitario, subtotal, id_carrito)"
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (
                Connection conDetCarrito = Conexion.getInstance().getConnection();
                PreparedStatement psDetCarrito = conDetCarrito.prepareStatement(sqlDetCarrito);) {

            psDetCarrito.setInt(1, idProducto);
            psDetCarrito.setInt(2, cantidad);
            psDetCarrito.setString(3, imagen);
            psDetCarrito.setDouble(4, precioUnitario);
            psDetCarrito.setDouble(5, cantidad * precioUnitario);
            psDetCarrito.setInt(6, idCarrito);

            int respuestaDet = psDetCarrito.executeUpdate();

            if (respuestaDet > 0) {
                return 1; // 1 = producto agregado nuevo
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error insertando en det_carrito productos",
                    JOptionPane.ERROR_MESSAGE);
        }
        return 0; // 0 = error general
    }

    @Override
    public int productoExisteEnCarrito(String idUsuario, int idProducto) {
        String sql = "SELECT COUNT(*) FROM det_carrito d " +
                "INNER JOIN carrito c ON d.id_carrito = c.id " +
                "WHERE c.id_usuario = ? AND d.id_producto = ?";

        try (
                Connection conexion = Conexion.getInstance().getConnection();
                PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, idUsuario);
            ps.setInt(2, idProducto);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int cantidad = rs.getInt(1);
                if (cantidad > 0) {
                    return 2; // El producto ya existe en el carrito
                } else {
                    return 1; // No existe el producto en el carrito
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de validación productos",
                    JOptionPane.ERROR_MESSAGE);
            return 0; // Error al ejecutar la consulta
        }
        return 0; // Si no hay resulyado
    }

    @Override
    public void dismunirCantidadProductoDelCarrito(int idProducto, String idUsuario, double precioUnitario) {
        int idCarrito = encontrarCarritoActivo(idUsuario); // Obtiene el id del carrito
        if (idCarrito == -1) {
            JOptionPane.showMessageDialog(null, "No se encontró carrito activo para el usuario." + idUsuario, "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener la cantidad actual del producto
        String sqlCantidad = "SELECT cantidad FROM det_carrito WHERE id_carrito = ? AND id_producto = ?";
        try (Connection conexion = Conexion.getInstance().getConnection();
                PreparedStatement psCantidad = conexion.prepareStatement(sqlCantidad)) {

            psCantidad.setInt(1, idCarrito);
            psCantidad.setInt(2, idProducto);

            ResultSet rs = psCantidad.executeQuery();

            if (rs.next()) {
                int cantidadActual = rs.getInt("cantidad");

                if (cantidadActual > 1) {
                    // Decrementar cantidad y subtotal
                    String sqlUpdate = "UPDATE det_carrito SET cantidad = cantidad - 1, subtotal = subtotal - ? WHERE id_carrito = ? AND id_producto = ?";
                    try (PreparedStatement psUpdate = conexion.prepareStatement(sqlUpdate)) {
                        psUpdate.setDouble(1, precioUnitario);
                        psUpdate.setInt(2, idCarrito);
                        psUpdate.setInt(3, idProducto);
                        int filasActualizadas = psUpdate.executeUpdate();

                        System.out.println("Filas actualizadas al disminuir: " + filasActualizadas); // Depuración
                    }
                } else {
                    // Si la cantidad es 1, eliminar producto
                    eliminarProductoDelCarrito(idProducto, idUsuario);
                }
            } else {
                System.out.println("Producto no encontrado en carrito para disminuir."); // Depuración
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error disminuyendo producto", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void eliminarProductoDelCarrito(int idProducto, String idUsuario) {
        String sqlDelete = "DELETE d FROM det_carrito d " +
                "INNER JOIN carrito c ON d.id_carrito = c.id " +
                "WHERE c.id_usuario = ? AND d.id_producto = ?";

        try (
                Connection conexion = Conexion.getInstance().getConnection();
                PreparedStatement ps = conexion.prepareStatement(sqlDelete);) {
            ps.setString(1, idUsuario);
            ps.setInt(2, idProducto);
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error eliminando producto del carrito",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void aumentarCantidadProductoDelCarrito(int idProducto, String idUsuario, double precioUnitario) {
        String sqlUpdate = "UPDATE det_carrito d " +
                "INNER JOIN carrito c ON d.id_carrito = c.id " +
                "SET d.cantidad = d.cantidad + 1, d.subtotal = d.subtotal + ? " +
                "WHERE c.id_usuario = ? AND d.id_producto = ?";
        try (
                Connection conexion = Conexion.getInstance().getConnection();
                PreparedStatement ps = conexion.prepareStatement(sqlUpdate);) {
            ps.setDouble(1, precioUnitario);
            ps.setString(2, idUsuario);
            ps.setInt(3, idProducto);

            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error actualizando producto existente",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actualizarCantidadProducto(String idCliente, int idProducto, int cantidad) {
        // SQL para actualizar la cantidad y el subtotal según la cantidad nueva
        String sqlUpdate = "UPDATE det_carrito d " +
                "INNER JOIN carrito c ON d.id_carrito = c.id " +
                "SET d.cantidad = ?, d.subtotal = ? * d.precioUnitario " +
                "WHERE c.id_usuario = ? AND d.id_producto = ?";

        try (
                Connection conexion = Conexion.getInstance().getConnection();
                PreparedStatement ps = conexion.prepareStatement(sqlUpdate);) {
            ps.setInt(1, cantidad); // Cantidad nueva
            ps.setInt(2, cantidad); // Multiplicamos cantidad * precio_unitario
            ps.setString(3, idCliente); // ID del usuario
            ps.setInt(4, idProducto); // ID del producto

            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error actualizando cantidad del producto",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // promociones
    @Override
    public List<PromocionCarrito> mostarPromociones(String idUsuario) {
        List<PromocionCarrito> infoPromo = new ArrayList<>();
        String sql = "SELECT c.id_usuario, d.idPromocion, d.cantidad, d.imagen, d.id_carrito, d.subtotal, " +
                "p.nombrePromocion AS nombre_producto, p.precio AS precio_producto " +
                "FROM carrito c " +
                "INNER JOIN det_carrito d ON c.id = d.id_carrito " +
                "INNER JOIN promociones p ON d.idPromocion = p.idPromocion " +
                "WHERE c.id_usuario = ?";

        try (
                Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setString(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PromocionCarrito promocion = new PromocionCarrito();
                promocion.setIdCliente(rs.getString("id_usuario"));
                promocion.setIdPromocion(rs.getInt("idPromocion"));
                promocion.setNombreProducto(rs.getString("nombre_producto"));
                promocion.setCantidadPromocion(rs.getInt("cantidad"));
                promocion.setImagen(rs.getString("imagen"));
                promocion.setIdCarrito(rs.getInt("id_carrito"));
                promocion.setPrecioUnitarioPromocion(rs.getDouble("precio_producto"));
                promocion.setSubtotalPorPromocion(rs.getDouble("subtotal"));
                infoPromo.add(promocion);
            }
            return infoPromo;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de Consulta promocion(lista)",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public int agregarPromocionAlCarrito(int idPromocion, String idUsuario, String imagen, int cantidad, double precioUnitario) {
        int validarExistencia = promocionExisteEnCarrito(idUsuario, idPromocion);

        // Si la promoción ya está en el carrito, solo actualizamos cantidad y subtotal
        if (validarExistencia == 2) {
            aumentarCantidadPromocionDelCarrito(idPromocion, idUsuario, precioUnitario);
            return 2; // 2 = promoción actualizada
        }

        // Buscar carrito activo del usuario
        int idCarrito = encontrarCarritoActivo(idUsuario);

        // Si no tiene carrito, creamos uno nuevo
        if (idCarrito == -1) {
            String sqlNuevoCarrito = "INSERT INTO carrito(id_usuario) VALUES (?)";
            try (
                    Connection conexion = Conexion.getInstance().getConnection();
                    PreparedStatement ps = conexion.prepareStatement(sqlNuevoCarrito,
                            PreparedStatement.RETURN_GENERATED_KEYS);) {
                ps.setString(1, idUsuario);
                int respuesta = ps.executeUpdate();

                if (respuesta > 0) {
                    ResultSet rs = ps.getGeneratedKeys();
                    if (rs.next()) {
                        idCarrito = rs.getInt(1); // Guardamos el nuevo id del carrito
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.toString(), "Error creando nuevo carrito promo",
                        JOptionPane.ERROR_MESSAGE);
                return 0;
            }
        }

        // Insertar promoción en detalle del carrito
        String sqlDetCarrito = "INSERT INTO det_carrito(idPromocion, cantidad, imagen, precioUnitario, subtotal, id_carrito)"
                +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (
                Connection conDetCarrito = Conexion.getInstance().getConnection();
                PreparedStatement psDetCarrito = conDetCarrito.prepareStatement(sqlDetCarrito);) {

            psDetCarrito.setInt(1, idPromocion);
            psDetCarrito.setInt(2, cantidad);
            psDetCarrito.setString(3, imagen);
            psDetCarrito.setDouble(4, precioUnitario);
            psDetCarrito.setDouble(5, cantidad * precioUnitario);
            psDetCarrito.setInt(6, idCarrito);

            int respuestaDet = psDetCarrito.executeUpdate();

            if (respuestaDet > 0) {
                return 1; // 1 = promoción agregada nueva
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error insertando en det_carrito promo",
                    JOptionPane.ERROR_MESSAGE);
        }
        return 0; // 0 = error general
    }

    @Override
    public int promocionExisteEnCarrito(String idUsuario, int idPromocion) {
        String sql = "SELECT COUNT(*) FROM det_carrito d " +
                "INNER JOIN carrito c ON d.id_carrito = c.id " +
                "WHERE c.id_usuario = ? AND d.idPromocion = ?";

        try (
                Connection conexion = Conexion.getInstance().getConnection();
                PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, idUsuario);
            ps.setInt(2, idPromocion);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int cantidad = rs.getInt(1);
                if (cantidad > 0) {
                    return 2; // La promoción ya existe en el carrito
                } else {
                    return 1; // No existe la promoción en el carrito
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de validación promocion",
                    JOptionPane.ERROR_MESSAGE);
            return 0; // Error al ejecutar la consulta
        }
        return 0; // Si no hay resultado
    }

    @Override
    public void aumentarCantidadPromocionDelCarrito(int idPromocion, String idUsuario, double precioUnitario) {
        String sqlUpdate = "UPDATE det_carrito d " +
                "INNER JOIN carrito c ON d.id_carrito = c.id " +
                "SET d.cantidad = d.cantidad + 1, d.subtotal = d.subtotal + ? " +
                "WHERE c.id_usuario = ? AND d.idPromocion = ?";
        try (
                Connection conexion = Conexion.getInstance().getConnection();
                PreparedStatement ps = conexion.prepareStatement(sqlUpdate);) {
            ps.setDouble(1, precioUnitario);
            ps.setString(2, idUsuario);
            ps.setInt(3, idPromocion);

            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error actualizando producto existente",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void disminuirCantidadPromocionDelCarrito(int idPromocion, String idUsuario, double precioUnitario) {
        int idCarrito = encontrarCarritoActivo(idUsuario); // Obtiene el id del carrito
        if (idCarrito == -1) {
            JOptionPane.showMessageDialog(null, "No se encontró carrito activo para el usuario." + idUsuario, "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener la cantidad actual de la promoción
        String sqlCantidad = "SELECT cantidad FROM det_carrito WHERE id_carrito = ? AND idPromocion = ?";
        try (Connection conexion = Conexion.getInstance().getConnection();
                PreparedStatement psCantidad = conexion.prepareStatement(sqlCantidad)) {

            psCantidad.setInt(1, idCarrito);
            psCantidad.setInt(2, idPromocion);

            ResultSet rs = psCantidad.executeQuery();

            if (rs.next()) {
                int cantidadActual = rs.getInt("cantidad");

                if (cantidadActual > 1) {
                    // Decrementar cantidad y subtotal
                    String sqlUpdate = "UPDATE det_carrito SET cantidad = cantidad - 1, subtotal = subtotal - ? WHERE id_carrito = ? AND idPromocion = ?";
                    try (PreparedStatement psUpdate = conexion.prepareStatement(sqlUpdate)) {
                        psUpdate.setDouble(1, precioUnitario);
                        psUpdate.setInt(2, idCarrito);
                        psUpdate.setInt(3, idPromocion);
                        int filasActualizadas = psUpdate.executeUpdate();

                        System.out.println("Filas actualizadas al disminuir: " + filasActualizadas); // Depuración
                    }
                } else {
                    // Si la cantidad es 1, eliminar promoción
                    eliminarPromocionDelCarrito(idPromocion, idUsuario);
                }
            } else {
                System.out.println("Promoción no encontrada en carrito para disminuir."); // Depuración
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error disminuyendo promoción",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void eliminarPromocionDelCarrito(int idPromocion, String idUsuario) {
        String sqlDelete = "DELETE d FROM det_carrito d " +
                "INNER JOIN carrito c ON d.id_carrito = c.id " +
                "WHERE c.id_usuario = ? AND d.idPromocion = ?";

        try (
                Connection conexion = Conexion.getInstance().getConnection();
                PreparedStatement ps = conexion.prepareStatement(sqlDelete);) {
            ps.setString(1, idUsuario);
            ps.setInt(2, idPromocion);
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error eliminando promoción del carrito",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void actualizarCantidadPromocion(String idUsuario, int idPromocion, int cantidad) {
        String sql = "UPDATE det_carrito d " +
                "INNER JOIN carrito c ON d.id_carrito = c.id " +
                "SET d.cantidad = ?, d.subtotal = ? * d.precioUnitario " +
                "WHERE c.id_usuario = ? AND d.idPromocion = ?";

        try (Connection conexion = Conexion.getInstance().getConnection();
                PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, cantidad);
            ps.setInt(2, cantidad); // recalcula subtotal
            ps.setString(3, idUsuario);
            ps.setInt(4, idPromocion);

            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error actualizando cantidad de promoción",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}