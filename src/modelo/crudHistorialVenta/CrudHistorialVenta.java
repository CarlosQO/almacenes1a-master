package modelo.crudHistorialVenta;

import java.util.List;

public interface CrudHistorialVenta {
    public List<Object[]> lista(String fechaInicio, String FechaFin);
}
