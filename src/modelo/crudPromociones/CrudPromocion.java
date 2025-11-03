package modelo.crudPromociones;

import java.util.List;

public interface CrudPromocion<P> {
    public List<P> mostrarPromociones();

    public int RegistrarPromocion(Promocion p);

    public int numeroPromociones();

    public int cantidadEnStockPromociones(int idPromocion);

    public boolean validarStockPromocion(int idPromocion, int cantidadSolicitada);

    public void disminuirStockPromocion(int idPromocion, int cantidadVendida);

    public List<Integer> obtenerIdsProductosPromocion(int idPromocion);
}
