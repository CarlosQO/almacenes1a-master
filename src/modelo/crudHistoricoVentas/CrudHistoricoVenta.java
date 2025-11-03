package modelo.crudHistoricoVentas;

import java.util.List;

public interface CrudHistoricoVenta {
    public List<Object[]> lista(String fechaInicio, String FechaFin);
}
