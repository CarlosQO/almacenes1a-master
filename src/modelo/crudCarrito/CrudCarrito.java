package modelo.crudCarrito;

import java.util.List;

public interface CrudCarrito<C> {
    // procesos
    public String limpiarCarrito(int idUsuario);

    public int facturaInsert(int idUsuario, int idMetodoPago, double total);

    public void detFacturaInsertProdcuto(int idFactura, int idProducto, int cantidad, double precioUnitario,
            double subtotal);

    public void detFacturaInsertPromocion(int idFactura, int idProducto, int cantidad, double precioUnitario,
            double subtotal);

    public void guardarFacturaConDetallesProductos(int idUsuario, int idMetodoPago, double total, List<C> productos);

    public void guardarFacturaConDetallesPromocion(int idFactura, double total, List<PromocionCarrito> promociones);

    public int encontrarCarritoActivo(int idCliente);

    public void registrarPedido(int idFactura);

    public void validarStockItemsCarrito(int idUsuario);

    // productos
    public List<C> mostrarProductosCarrito(int idUsuario);

    public int agregarProductosAlCarrito(int idProducto, int idUsuario, String imagen, int cantidad,
            double precioUnitario);

    public void aumentarCantidadProductoDelCarrito(int idProducto, int idUsuario, double precioUnitario);

    public void dismunirCantidadProductoDelCarrito(int idProducto, int idUsuario, double precioUnitario);

    public void eliminarProductoDelCarrito(int idProducto, int idUsuario);

    public int productoExisteEnCarrito(int idUsuario, int idProducto);

    public void actualizarCantidadProducto(int idCliente, int idProducto, int cantidad);

    // promociones
    public List<PromocionCarrito> mostarPromociones(int idUsuario);

    public int agregarPromocionAlCarrito(int idPromocion, int idUsuario, String imagen, int cantidad,
            double precioUnitario);

    public int promocionExisteEnCarrito(int idUsuario, int idPromocion);

    public void aumentarCantidadPromocionDelCarrito(int idPromocion, int idUsuario, double precioUnitario);

    public void disminuirCantidadPromocionDelCarrito(int idPromocion, int idUsuario, double precioUnitario);

    public void eliminarPromocionDelCarrito(int idPromocion, int idUsuario);

    public void actualizarCantidadPromocion(int idUsuario, int idPromocion, int cantidad);
}
