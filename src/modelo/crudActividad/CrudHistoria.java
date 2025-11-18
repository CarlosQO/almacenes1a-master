package modelo.crudActividad;

import java.util.List;

public interface CrudHistoria<P> {
    public List<P> obtenerHistorialCompras(String idUsuario, String fechaInicio, String fechaFin);

    public List<ProductoDetalleFactura> obtenerDetallesCompra(int idFactura);

    public List<P> obtenerDetallesProductos(int idFactura);

    public List<P> obtenerDetallesPromocion(int idFactura);

    public boolean existePedidoEntreFechas(String idCliente, String fechaInicio, String fechaFin);
}
