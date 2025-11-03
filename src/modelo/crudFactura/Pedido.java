package modelo.crudFactura;

public class Pedido {
    private int idPedido;
    private String fecha;
    private double total;
    private int idFactura;

    public Pedido() {
    }

    public Pedido(int idPedido, String fecha, double total, int idFactura) {
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.total = total;
        this.idFactura = idFactura;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }
}
