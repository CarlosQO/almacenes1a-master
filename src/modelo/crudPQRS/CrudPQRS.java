package modelo.crudPQRS;

import java.util.List;

public interface CrudPQRS<P> {
    public boolean enviarPQRS(String idUusario, String asunto, String cuerpo);

    public String obtenerCorreo(String idUsuario);

    public List<P> listar();

    public List<P> listarPorID(int id);
}
