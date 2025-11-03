package modelo.crudReposicion;

import java.util.List;

public interface CrudReposicion<T> {

    public List<T> listar();

    public boolean insertarReposicion(int idProducto, int cantidadReposicion);

    public boolean actualizarEstado(int idSolicitud);

}