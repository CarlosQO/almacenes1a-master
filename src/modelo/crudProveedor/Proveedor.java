package modelo.crudProveedor;

public class Proveedor {
    private int id;
    private String nombre;
    private String tipo;
    private String documento;
    private int metodoDePago;
    private String direccion;
    private String telefono;
    private String correo;
    private int estado;
    private String producto;

    public Proveedor(int id, String tipo, String nombre, String documento, int metodoDePago, String direccion,
            String telefono, String correo, int estado) {
        this.id = id;
        this.tipo = tipo;
        this.nombre = nombre;
        this.documento = documento;
        this.metodoDePago = metodoDePago;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.estado = estado;
    }

    public Proveedor() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setMetodoDePago(int metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getDocumento() {
        return documento;
    }

    public int getEstado() {
        return estado;
    }

    public int getMetodoDePago() {
        return metodoDePago;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getTipo() {
        return tipo;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }
}
