package modelo.crudFactura;

import java.util.List;

public interface CrudFactura {
    public List<Pedido> obtenerHistorialComprasFacturas(int idUsuario, String fechaInicio, String fechaFin);

    public List<ProductoDetalleFactura> obtenerDetallesCompraFacturaProductos(int idFactura);

    public List<ProductoDetalleFactura> obtenerDetallesCompraFacturaPromocion(int idFactura);

    public List<ProductoDetalleFactura> obtenerDetallesCompra(int idFactura);

    public boolean existeFacturaEntreFechas(int idCliente, String fechaInicio, String fechaFin);
}
