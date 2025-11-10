package modelo.crudMetodoDePago;

import java.util.List;

public interface CrudMetodosPago<M> {
    public List<MetodoPago> listarMetodosPago();

    public int buscarMetodoDePagoPorId(String nombreMetodoDePago);
}
