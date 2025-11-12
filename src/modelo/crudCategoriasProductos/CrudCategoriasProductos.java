package modelo.crudCategoriasProductos;
import java.util.List;

public interface CrudCategoriasProductos<C> {
    public List<C> cargarCategorias();
    public String buscarNombreCategoria(int idCategoria);
}
