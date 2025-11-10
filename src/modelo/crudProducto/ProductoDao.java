package modelo.crudProducto;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import modelo.Conexion;

public class ProductoDao implements CrudProducto<Producto> {

    private Connection con;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public List<Producto> listarProductoMenosVendidos() {
        List<Producto> produc = new ArrayList<>();
        String sql = "SELECT p.id, p.nombre, v.cantidadVentas AS cantidad, p.precio, p.descripcion, p.talla, p.imagen, p.fecha_registro, p.id_categoria, p.id_estado FROM producto p INNER JOIN ventas v ON p.id = v.idProducto ORDER BY cantidad ASC LIMIT 10";
        try {
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setCantidad(rs.getInt(3));
                p.setPrecio(rs.getDouble(4));
                p.setDescripcion(rs.getString(5));
                p.setTalla(rs.getString(6));
                p.setImagen(rs.getString(7));
                p.setFechaRegistro(rs.getString(8));
                p.setIdCategoria(rs.getInt(9));
                p.setIdEstado(rs.getInt(10));
                produc.add(p);
            }
            return produc;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al extraer los productos de la bd");
        }
        return produc;
    }

    @Override
    public List<Producto> listarProductoMasVendidos() {
        List<Producto> produc = new ArrayList<>();
        String sql = "SELECT p.id, p.nombre, v.cantidadVentas AS cantidad, p.precio, p.descripcion, p.talla, p.imagen, p.fecha_registro, p.id_categoria, p.id_estado FROM producto p INNER JOIN ventas v ON p.id = v.idProducto ORDER BY cantidad DESC LIMIT 10";
        try {
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setCantidad(rs.getInt(3));
                p.setPrecio(rs.getDouble(4));
                p.setDescripcion(rs.getString(5));
                p.setTalla(rs.getString(6));
                p.setImagen(rs.getString(7));
                p.setFechaRegistro(rs.getString(8));
                p.setIdCategoria(rs.getInt(9));
                p.setIdEstado(rs.getInt(10));
                produc.add(p);
            }
            return produc;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al extraer los productos de la bd");
        }
        return produc;
    }

    @Override
    public int Registrar(Producto p) {
        return 0;
    }

    @Override
    public int Actualizar(Producto p) {
        return 0;
    }

    @Override
    public Producto obtenerProducto(int idProducto) {
        try {
            String sql = "SELECT * FROM producto WHERE id = ?";
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, idProducto);
            rs = ps.executeQuery();
            if (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setCantidad(rs.getInt(3));
                p.setPrecio(rs.getDouble(4));
                p.setDescripcion(rs.getString(5));
                p.setTalla(rs.getString(6));
                p.setImagen(rs.getString(7));
                p.setFechaRegistro(rs.getString(8));
                p.setIdCategoria(rs.getInt(9));
                p.setIdEstado(rs.getInt(10));
                return p;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener el producto: " + e);
        }
        return null;
    }

    @Override
    public List<Producto> mostrarProductos() {
        List<Producto> info = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE cantidad>0 AND id_estado=1 ORDER BY id_categoria ASC";
        try (
                Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setTalla(rs.getString("talla"));
                p.setImagen(rs.getString("imagen"));
                p.setPrecio(rs.getDouble("precio"));
                p.setIdCategoria(rs.getInt("id_categoria"));
                info.add(p);
            }
            return info;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de Consulta", JOptionPane.ERROR_MESSAGE);
            return null;
        }

    }

    @Override
    public List<Producto> listarProductoNombreID() {
        List<Producto> produc = new ArrayList<>();
        try {
            String sql = "SELECT id, nombre FROM producto";
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt(1));
                p.setNombre(rs.getString(2));
                produc.add(p);
            }
            return produc;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public List<Producto> listarProductoMasVendidosPorFecha(String fechaInicio, String fechaFin) {
        List<Producto> listProduc = new ArrayList<>();
        try {
            String sql = "SELECT p.id, p.nombre, p.talla, p.imagen, SUM(h.cantidadVenta) AS total_vendidos FROM producto p INNER JOIN histoventas h ON p.id = h.idProducto WHERE h.fecha BETWEEN ? AND ? GROUP BY p.id, p.nombre, p.talla ORDER BY total_vendidos DESC LIMIT 10";
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setTalla(rs.getString(3));
                p.setImagen(rs.getString(4));
                p.setCantidad(rs.getInt(5));
                listProduc.add(p);
            }
            return listProduc;
        } catch (Exception e) {
            System.out.println("Error al intentar extraer los 10 producto mas vendidos filtro");
        }
        return listProduc;
    }

    @Override
    public List<Producto> listarProductoMenosVendidosPorFecha(String fechaInicio, String fechaFin) {
        List<Producto> listProduc = new ArrayList<>();
        try {
            String sql = "SELECT p.id, p.nombre, p.talla, p.imagen, SUM(h.cantidadVenta) AS total_vendidos FROM producto p INNER JOIN histoventas h ON p.id = h.idProducto WHERE h.fecha BETWEEN ? AND ? GROUP BY p.id, p.nombre, p.talla ORDER BY total_vendidos ASC LIMIT 10";
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, fechaInicio);
            ps.setString(2, fechaFin);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setTalla(rs.getString(3));
                p.setImagen(rs.getString(4));
                p.setCantidad(rs.getInt(5));
                listProduc.add(p);
            }
            return listProduc;
        } catch (Exception e) {
            System.out.println("Error al intentar extraer los 10 producto menos vendidos filtro");
        }
        return listProduc;
    }

    @Override
    public List<Producto> listarStockActualPorProducto() {
        List<Producto> listStock = new ArrayList<>();
        try {
            String sql = "SELECT p.id, p.nombre, p.cantidad, c.nombre AS categoria, e.nombre AS estado FROM producto p INNER JOIN categoria c ON p.id_categoria = c.id INNER JOIN producto_estado e ON p.id_estado = e.id ORDER BY c.nombre, p.nombre";
            con = Conexion.getInstance().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt(1));
                p.setNombre(rs.getString(2));
                p.setCantidad(rs.getInt(3));
                p.setCategoria(rs.getString(4));
                p.setEstado(rs.getString(5));
                listStock.add(p);
            }
            return listStock;
        } catch (Exception e) {
            System.out.println("Error al obtener los productos con sus stock " + e.toString());
        }
        return listStock;
    }

    // Cuando el cliente realiza una compra este metodo actualiza el stock de los
    // productos comprados
    // En el contolador se le aplica a los productos nesesarios
    @Override
    public int setDismuirStock(int idProducto, int cantidad) {
        String sql = "UPDATE producto SET cantidad=cantidad-? WHERE id=?";
        try (
                Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(1, cantidad);
            ps.setInt(2, idProducto);
            return ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de Consulta(Disminuir cantidad)",
                    JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }

    @Override
    public boolean getValidarStockProducto(int idProducto, int cantidadCarrito) {
        int stockProducto = 0;
        int stockPromociones = 0;

        String sqlStock = "SELECT cantidad FROM producto WHERE id = ?";
        String sqlPromociones = "SELECT SUM(cantidad) AS totalPromociones "
                + "FROM promociones "
                + "WHERE (idPrimerProducto = ? OR idSegunProducto = ?) "
                + "AND cantidad > 0";

        try (Connection con = Conexion.getInstance().getConnection()) {
            // obtener la cantidad del producto primer consulta
            try (PreparedStatement ps1 = con.prepareStatement(sqlStock)) {
                ps1.setInt(1, idProducto);
                ResultSet rs1 = ps1.executeQuery();
                if (rs1.next()) {
                    stockProducto = rs1.getInt("cantidad");
                }
            }

            // obtner cantidad del promocion segunda consulta
            try (PreparedStatement ps2 = con.prepareStatement(sqlPromociones)) {
                ps2.setInt(1, idProducto);
                ps2.setInt(2, idProducto);
                ResultSet rs2 = ps2.executeQuery();
                if (rs2.next()) {
                    stockPromociones = rs2.getInt("totalPromociones");
                }
            }

            // validar stock
            int stockDisponible = stockProducto - stockPromociones;

            return stockDisponible >= cantidadCarrito;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(),
                    "Error al validar stock del producto", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public int stockProducto(int idProducto) {
        int stockProducto = 0;
        int stockPromociones = 0;

        String sqlProducto = "SELECT cantidad FROM producto WHERE id = ?";
        String sqlPromociones = "SELECT SUM(cantidad) AS totalPromociones FROM promociones "
                + "WHERE (idPrimerProducto = ? OR idSegunProducto = ?) "
                + "AND cantidad > 0";

        try (Connection con = Conexion.getInstance().getConnection()) {

            // Obtener la cantidad total del producto
            try (PreparedStatement ps1 = con.prepareStatement(sqlProducto)) {
                ps1.setInt(1, idProducto);
                ResultSet rs1 = ps1.executeQuery();
                if (rs1.next()) {
                    stockProducto = rs1.getInt("cantidad");
                }
            }

            // Obtener la cantidad comprometida en promociones
            try (PreparedStatement ps2 = con.prepareStatement(sqlPromociones)) {
                ps2.setInt(1, idProducto);
                ps2.setInt(2, idProducto);
                ResultSet rs2 = ps2.executeQuery();
                if (rs2.next()) {
                    stockPromociones = rs2.getInt("totalPromociones");
                }
            }

            // Calcular el stock disponible real
            int stockDisponible = stockProducto - stockPromociones;

            // Evitar valores negativos
            if (stockDisponible < 0) {
                stockDisponible = 0;
            }

            return stockDisponible;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(),
                    "Error al consultar stock del producto", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }

    @Override
    public void guardarVenta(int idProducto, int cantidad) {
        // String sql = "INSERT INTO ventas (idProducto, cantidadVentas) VALUES (?, ?)";
        String sql = "UPDATE ventas SET cantidadVentas = cantidadVentas + ? WHERE idProducto = ?";
        try (
                Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(2, idProducto);
            ps.setInt(1, cantidad);
            ps.executeUpdate();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de Consulta(Guardar Historia de venta)",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public List<Producto> listarProductosSinProveedor() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT id, nombre FROM producto WHERE id_proveedor IS NULL";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                productos.add(p);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error al cargar productos", JOptionPane.ERROR_MESSAGE);
        }

        return productos;
    }

    @Override
    public boolean asignarProveedor(int idProducto, int idProveedor) {
        String sql = "UPDATE producto SET id_proveedor = ? WHERE id = ?";

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProveedor);
            ps.setInt(2, idProducto);

            int result = ps.executeUpdate();
            return result > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error al asignar proveedor", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean registrarProducto(int id, String nombre, int cantidad, double precio, String descripcion,
            String talla, String imagen, int idCategoria, int idEstado) {

        try {
            // Primero verificamos si el producto ya existe por id
            if (productoExiste(id)) {
                JOptionPane.showMessageDialog(null, "El producto con este ID ya existe.", "Registro de producto",
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }
            // Si no existe, insertamos
            String sql = "INSERT INTO producto (id, nombre, cantidad, precio, descripcion, talla, imagen, id_categoria, id_estado) "
                    +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection con = Conexion.getInstance().getConnection();
                    PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.setString(2, nombre);
                ps.setInt(3, cantidad);
                ps.setDouble(4, precio);
                ps.setString(5, descripcion);
                ps.setString(6, talla);
                ps.setString(7, imagen);
                ps.setInt(8, idCategoria);
                ps.setInt(9, idEstado);

                ps.executeUpdate();
                JOptionPane.showMessageDialog(null, "Producto registrado correctamente. ", "Registro de producto",
                        JOptionPane.INFORMATION_MESSAGE);
                return true;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de Consulta (Registro de producto)",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public boolean productoExiste(int id) {
        try {
            String sql = "SELECT 1 FROM producto WHERE id = ? LIMIT 1";

            try (Connection con = Conexion.getInstance().getConnection();
                    PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next(); // true si el producto existe
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de Consulta (Verificación de producto)",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public List<Producto> informacionDeUnProducto(int id) {
        List<Producto> info = new ArrayList<>();
        String sql = """
                SELECT p.id, p.nombre, p.descripcion, p.talla, p.imagen, p.precio,
                       c.id AS categoria_id, c.nombre AS categoria_nombre,
                       e.id AS estado_id, e.nombre AS estado_nombre
                FROM producto p
                JOIN categoria c ON p.id_categoria = c.id
                JOIN producto_estado e ON p.id_estado = e.id
                WHERE p.id = ?
                LIMIT 1
                """;

        try (Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id"));
                p.setNombre(rs.getString("nombre"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setTalla(rs.getString("talla"));
                p.setImagen(rs.getString("imagen"));
                p.setPrecio(rs.getDouble("precio"));

                // Datos de categoría
                p.setIdCategoria(rs.getInt("categoria_id"));
                p.setCategoria(rs.getString("categoria_nombre"));

                // Datos de estado
                p.setIdEstado(rs.getInt("estado_id"));
                p.setEstado(rs.getString("estado_nombre"));

                info.add(p);
            }

            return info;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(),
                    "Error de Consulta (Información de producto)", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    @Override
    public boolean actualizarDatosProducto(int id, String nombre, double precio, String descripcion, String talla,
            String imagen, int idCategoria, int idEstado) {

        try {
            // Primero verificamos si el producto existe
            if (!productoExiste(id)) {
                JOptionPane.showMessageDialog(null, "El producto con ID " + id + " no existe.",
                        "Actualización de producto", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            // Si existe, realizamos la actualización
            String sql = "UPDATE producto SET nombre = ?, precio = ?, descripcion = ?, talla = ?, imagen = ?, id_categoria = ?, id_estado = ? WHERE id = ?";

            try (Connection con = Conexion.getInstance().getConnection();
                    PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setString(1, nombre);
                ps.setDouble(2, precio);
                ps.setString(3, descripcion);
                ps.setString(4, talla);
                ps.setString(5, imagen);
                ps.setInt(6, idCategoria);
                ps.setInt(7, idEstado);
                ps.setInt(8, id);

                int filasActualizadas = ps.executeUpdate();

                if (filasActualizadas > 0) {
                    JOptionPane.showMessageDialog(null, "Producto actualizado correctamente.",
                            "Actualización de producto", JOptionPane.INFORMATION_MESSAGE);
                    return true;
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo actualizar el producto.",
                            "Actualización de producto", JOptionPane.WARNING_MESSAGE);
                    return false;
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error de Consulta (Actualización de producto)",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // cantidad
    @Override
    public int obtenerCantidadCategoriasConProductos() {
        String sql = "SELECT COUNT(DISTINCT id_categoria) AS total FROM producto";
        int total = 0;

        try (
                Connection con = Conexion.getInstance().getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();) {
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.toString(),
                    "Error al obtener cantidad de categorías con productos", JOptionPane.ERROR_MESSAGE);
        }

        return total;
    }

}
