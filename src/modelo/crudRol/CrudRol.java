package modelo.crudRol;

import java.util.List;

public interface CrudRol<T> {
    public List<T> listar();
    public int setActualizarRol(int id, String rol);
}
