package modelo.crudProducto;

import java.util.List;

public interface CrudProducto<P> {
        public List<P> mostrarProductos();

        public List<P> listarProductoMenosVendidos();

        public List<P> listarProductoMasVendidos();

        public List<P> listarProductoNombreID();

        public int Registrar(Producto p);

        public int Actualizar(Producto p);

        public Producto obtenerProducto(int idProducto);

        public List<P> listarProductoMasVendidosPorFecha(String fechaInicio, String fechaFin);

        public List<P> listarProductoMenosVendidosPorFecha(String fechaInicio, String fechaFin);

        public List<P> listarStockActualPorProducto();

        public int setDismuirStock(int idProducto, int cantidad);

        public boolean getValidarStockProducto(int idProducto, int cantidad);

        public int stockProducto(int idProducto);

        public void guardarVenta(int idProducto, int cantidad);

        public List<P> listarProductosSinProveedor();

        // vendedor
        public boolean productoExiste(int id);

        public boolean registrarProducto(int id, String nombre, int cantidad, double precio, String descripcion,
                        String talla, String imagen, int idCategoria, int idEstado);

        public boolean actualizarDatosProducto(int id, String nombre, double precio, String descripcion, String talla,
                        String imagen, int idCategoria, int idEstado);

        public List<Producto> informacionDeUnProducto(int id);

        public int obtenerCantidadCategoriasConProductos();

        public void registrarProductoEnVentas(int idProducto, int cantidad);
}
