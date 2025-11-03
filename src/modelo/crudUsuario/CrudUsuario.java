package modelo.crudUsuario;

import java.util.List;

public interface CrudUsuario<U> {
    public List<U> listar();

    public int Registrar(Usuario u);

    public int Actualizar(Usuario u);

    public Usuario obtenerUsuario(String documento);

    public int ActualizarRol(int rolID, String documento);

    public int obtenerCantidadUsuario();

    public int buscarUsuario(String documento);

    public int buscarCorreo(String correo);

    public Usuario validarCredencialesUsuario(String documento, String contrasena);
}
