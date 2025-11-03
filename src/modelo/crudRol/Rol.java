package modelo.crudRol;

public class Rol {
    private int id_rol;
    private String rol;

    public Rol(int id_rol, String rol) {
        this.id_rol = id_rol;
        this.rol = rol;
    }

    public Rol() {
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
