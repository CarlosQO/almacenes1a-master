package modelo.crudSeguimientoVendedor;

public class SeguimientoVen {
    private String nombreVendedor;
    private String documento;
    private int pedidosNoEntregados;
    private int pedidosEntregados;
    private int pedidosCancelados;
    private int pedidosDespachados;

    public SeguimientoVen() {
    }

    public String getNombreVendedor() {
        return nombreVendedor;
    }

    public void setNombreVendedor(String nombreVendedor) {
        this.nombreVendedor = nombreVendedor;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getPedidosNoEntregados() {
        return pedidosNoEntregados;
    }

    public void setPedidosNoEntregados(int pedidosNoEntregados) {
        this.pedidosNoEntregados = pedidosNoEntregados;
    }

    public int getPedidosEntregados() {
        return pedidosEntregados;
    }

    public void setPedidosEntregados(int pedidosEntregados) {
        this.pedidosEntregados = pedidosEntregados;
    }

    public int getPedidosCancelados() {
        return pedidosCancelados;
    }

    public void setPedidosCancelados(int pedidosCancelados) {
        this.pedidosCancelados = pedidosCancelados;
    }

    public int getPedidosDespachados() {
        return pedidosDespachados;
    }

    public void setPedidosDespachados(int pedidosDespachados) {
        this.pedidosDespachados = pedidosDespachados;
    }
}
