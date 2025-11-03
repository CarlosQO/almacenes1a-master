package modelo.crudPedidos;

public class EstadoPedido {
    private int id;
    private String estado;

    public EstadoPedido(int id, String estado) {
        this.id = id;
        this.estado = estado;
    }

    public EstadoPedido() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public String getEstado() {
        return estado;
    }

}
