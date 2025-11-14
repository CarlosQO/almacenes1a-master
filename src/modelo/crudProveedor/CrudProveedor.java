package modelo.crudProveedor;

import java.util.List;

public interface CrudProveedor<T> {
    public List<T> listar();

    public int setAgregar(T tr);

    public int setActualizar(T tr);

    public int setEliminar(int id);

    public List<T> listarProveedorPendiente();

    public List<T> listarProveedorActivos();

    public List<T> listarProveedorInactivo();

    public List<T> listarProveedorPorID(String documento);

    public List<T> listarProveedorActivosInactivos();

    public int CambiarEstado(int id, int estado);
    public boolean existeProveedorPorNit(String nit);
}
