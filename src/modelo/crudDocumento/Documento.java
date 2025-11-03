package modelo.crudDocumento;

public class Documento {
    private int id;
    private String nombre;

    public Documento(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Documento() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
