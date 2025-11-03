package modelo.crudSeguimientoVendedor;

import java.util.List;

public interface CrudSeguimientoVendedor {
    /**
     * Listar todos los registros sin filtrado.
     */
    public List<SeguimientoVen> listarTodos();

    /**
     * Listar agregados por vendedor filtrando por documento (opcional), año y mes.
     * If documento is null or empty, it will aggregate for all vendedores for the
     * given month/year.
     */
    public List<SeguimientoVen> listarPorDocumentoAnoMes(String documento, int ano, int mes);
}
