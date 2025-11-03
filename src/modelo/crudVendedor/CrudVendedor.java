package modelo.crudVendedor;

import java.util.List;

public interface CrudVendedor<c> {
    public List<c> listar();

    public Vendedor buscarVendedor(String idVendedor);

    public int actualizarEstado(String idVendedor, String estado);

    public int agregarVendedor(Vendedor v);
}
