package modelo.crudAdministradorSesion;

public interface CrudAdministrador {
    public int registrarHoraIngreso(String idAdministrador);

    public int registrarHoraSalida(int idSesion);
}
