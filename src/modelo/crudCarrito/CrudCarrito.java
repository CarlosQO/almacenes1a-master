package modelo.crudCarrito;

import java.util.List;

public interface CrudCarrito<C> {
    // procesos
    public String limpiarCarrito(String idUsuario);

    public int facturaInsert(String idUsuario, int idMetodoPago, double total);

    public void detFacturaInsertProdcuto(int idFactura, int idProducto, int cantidad, double precioUnitario,
            double subtotal);

    public void detFacturaInsertPromocion(int idFactura, int idProducto, int cantidad, double precioUnitario,
            double subtotal);

    public void guardarFacturaConDetallesProductos(String idUsuario, int idMetodoPago, double total, List<C> productos);

    public void guardarFacturaConDetallesPromocion(int idFactura, double total, List<PromocionCarrito> promociones);

    public int encontrarCarritoActivo(String idCliente);

    public void registrarPedido(int idFactura);

    public void validarStockItemsCarrito(String idUsuario);

    // productos
    public List<C> mostrarProductosCarrito(String idUsuario);

    public int agregarProductosAlCarrito(int idProducto, String idUsuario, String imagen, int cantidad,
            double precioUnitario);

    public void aumentarCantidadProductoDelCarrito(int idProducto, String idUsuario, double precioUnitario);

    public void dismunirCantidadProductoDelCarrito(int idProducto, String idUsuario, double precioUnitario);

    public void eliminarProductoDelCarrito(int idProducto, String idUsuario);

    public int productoExisteEnCarrito(String idUsuario, int idProducto);

    public void actualizarCantidadProducto(String idCliente, int idProducto, int cantidad);

    // promociones
    public List<PromocionCarrito> mostarPromociones(String idUsuario);

    public int agregarPromocionAlCarrito(int idPromocion, String idUsuario, String imagen, int cantidad,
            double precioUnitario);

    public int promocionExisteEnCarrito(String idUsuario, int idPromocion);

    public void aumentarCantidadPromocionDelCarrito(int idPromocion, String idUsuario, double precioUnitario);

    public void disminuirCantidadPromocionDelCarrito(int idPromocion, String idUsuario, double precioUnitario);

    public void eliminarPromocionDelCarrito(int idPromocion, String idUsuario);

    public void actualizarCantidadPromocion(String idUsuario, int idPromocion, int cantidad);
}
