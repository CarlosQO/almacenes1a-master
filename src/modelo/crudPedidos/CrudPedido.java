package modelo.crudPedidos;

import java.util.List;

public interface CrudPedido<P> {
    public List<P> obtenerPedidos(String idUsuario);

    public List<P> obtenerDetallesProductos(int idFactura);

    public List<P> obtenerDetallesPromocion(int idFactura);

    public List<P> obtenerProductoPedidos(int idFactura);

    public void actualizaEstadoProductoEntregado(int idFactura);

    public void actualizaEstadoProductoNoEntregado(int idFactura);

    public List<EstadoPedido> getEstadosPedidos();

    public List<Pedido> getPedidosPorEstado(String estado);

    public boolean cambiarEstadoPedido(int idPedido, int idEstado);

    public List<PedidoSupervisor> listarPE();

    public List<PedidoSupervisor> listarPNoE();

}
