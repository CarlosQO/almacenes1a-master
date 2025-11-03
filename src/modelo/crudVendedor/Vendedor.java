package modelo.crudVendedor;

public class Vendedor {
    private String idVendedor, idUsuario, estado;
    private int numeroDeVenta;

    public Vendedor() {
    }

    public Vendedor(String idVendedor, String idUsuario, String estado, int numeroDeVenta) {
        this.idVendedor = idVendedor;
        this.idUsuario = idUsuario;
        this.estado = estado;
        this.numeroDeVenta = numeroDeVenta;
    }

    public String getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(String idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getNumeroDeVenta() {
        return numeroDeVenta;
    }

    public void setNumeroDeVenta(int numeroDeVenta) {
        this.numeroDeVenta = numeroDeVenta;
    }

}
