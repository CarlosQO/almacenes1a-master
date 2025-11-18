package modelo.crudFactura;

import java.util.List;

public interface CrudFactura {
    public List<Pedido> obtenerHistorialComprasFacturas(String idUsuario, String fechaInicio, String fechaFin);

    public List<ProductoDetalleFactura> obtenerDetallesCompraFacturaProductos(int idFactura);

    public List<ProductoDetalleFactura> obtenerDetallesCompraFacturaPromocion(int idFactura);

    public List<ProductoDetalleFactura> obtenerDetallesCompra(int idFactura);

    public boolean existeFacturaEntreFechas(String idCliente, String fechaInicio, String fechaFin);
}
