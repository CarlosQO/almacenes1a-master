package modelo.crudReposicion;

public class Reposicion {

    private String idUsuario;
    private int idProducto;
    private String nombreProducto;
    private String fecha;
    private int idSolicitudesRepo;

    public Reposicion(String idUsuario, int idProducto, String fecha, String nombreProducto) {

        this.idUsuario = idUsuario;
        this.idProducto = idProducto;
        this.fecha = fecha;
        this.nombreProducto = nombreProducto;
    }

    public Reposicion() {

    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getFecha() {
        return fecha;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setIdSolicitudesRepo(int idSolicitudesRepo) {
        this.idSolicitudesRepo = idSolicitudesRepo;
    }

    public int getIdSolicitudesRepo() {
        return idSolicitudesRepo;
    }
}
