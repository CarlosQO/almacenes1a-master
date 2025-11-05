package modelo.crudSolicitudesReposicion;
import java.util.List;

public interface CrudSolicitudesReposicion<S> {
    public List<S> mostrarProductosConBajostock();
    public void enviarSolicitud(int idVendedor, int idProducto);
    public boolean productoConSolicitud(int idProducto);
}
