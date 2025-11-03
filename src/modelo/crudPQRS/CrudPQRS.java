package modelo.crudPQRS;

import java.util.List;

public interface CrudPQRS<P> {
    public boolean enviarPQRS(int idUusario, String asunto, String cuerpo);

    public String obtenerCorreo(int idUsuario);

    public List<P> listar();

    public List<P> listarPorID(int id);
}
