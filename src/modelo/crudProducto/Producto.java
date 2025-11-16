package modelo.crudProducto;

public class Producto {
    private int id, idCategoria, idEstado, cantidad;
    private String nombre, talla, imagen, descripcion, fechaRegistro, categoria, estado;
    private Double precio;

    public Producto() {
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Producto(int id, int idCategoria, int idEstado, int cantidad, String nombre, String talla, String imagen,
            String descripcion, Double precio) {
        this.id = id;
        this.idCategoria = idCategoria;
        this.idEstado = idEstado;
        this.cantidad = cantidad;
        this.nombre = nombre;
        this.talla = talla;
        this.imagen = imagen;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    public Producto(int id, String nombre, int cantidad, Double precio, String descripcion, String talla,
            String imagen, int idCategoria, int idEstado, String fechaRegistro, int idProveedor) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.descripcion = descripcion;
        this.talla = talla;
        this.imagen = imagen;
        this.idCategoria = idCategoria;
        this.idEstado = idEstado;
        this.fechaRegistro = fechaRegistro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
