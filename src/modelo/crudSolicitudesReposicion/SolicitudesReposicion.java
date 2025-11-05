package modelo.crudSolicitudesReposicion;

public class SolicitudesReposicion {
    public int id, cantidad;
    public String nombre;
    public double precio;
    public boolean solitudEnviada;

    public SolicitudesReposicion() {
    }

    public SolicitudesReposicion(int id, String nombre, int cantidad,double precio, boolean solitudEnviada) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad=cantidad;
        this.precio = precio;
        this.solitudEnviada = solitudEnviada;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    } 

    public boolean isSolitudEnviada() {
        return solitudEnviada;
    }

    public void setSolitudEnviada(boolean solitudEnviada) {
        this.solitudEnviada = solitudEnviada;
    }
}
