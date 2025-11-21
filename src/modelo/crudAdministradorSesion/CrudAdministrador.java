package modelo.crudAdministradorSesion;

import java.util.List;

public interface CrudAdministrador {
    public int registrarHoraIngreso(String idAdministrador);

    public int registrarHoraSalida(int idSesion);

    public List<Object> lista();
}
