package modelo.crudSeguimientoAdministrador;

import java.util.List;

/**
 * Interfaz CRUD/contrato para las operaciones de seguimiento de administrador.
 */
public interface CrudSeguimientoAdmin {

    /**
     * Lista los agregados por día para un mes y año indicados. Si idAdministrador
     * es null o vacío, no filtra por administrador.
     *
     * @param mes mes (1-12)
     * @param ano año (ej. 2025)
     * @return lista de agregados por día
     */
    public List<SeguimientoAdmin> listarPorMesAno(int mes, int ano);

}
