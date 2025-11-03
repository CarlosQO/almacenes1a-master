package modelo.crudProveedorEmpresa;

public class ProveedorEmpresa {
    private int idProveedor;
    private String nombre;
    private String tipo;
    private String documento;
    private int metodoDePago;
    private String direccion;
    private String telefono;
    private String correo;
    private int estado;
    private String representanteNombre;
    private String representanteDocumento;
    private String representanteTelefono;
    private String representanteCorreo;

    public ProveedorEmpresa(int idProveedor, String tipo, String nombre, String documento, int metodoDePago,
            String direccion, String telefono, String correo, int estado, String representanteNombre,
            String representanteDocumento, String representanteTelefono, String representanteCorreo) {
        this.idProveedor = idProveedor;
        this.tipo = tipo;
        this.nombre = nombre;
        this.documento = documento;
        this.metodoDePago = metodoDePago;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.estado = estado;
        this.representanteNombre = representanteNombre;
        this.representanteDocumento = representanteDocumento;
        this.representanteTelefono = representanteTelefono;
        this.representanteCorreo = representanteCorreo;
    }

    public ProveedorEmpresa() {

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

    public int getIdProveedor() {
        return idProveedor;
    }

    public String getRepresentanteNombre() {
        return representanteNombre;
    }

    public String getRepresentanteDocumento() {
        return representanteDocumento;
    }

    public String getRepresentanteTelefono() {
        return representanteTelefono;
    }

    public String getRepresentanteCorreo() {
        return representanteCorreo;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public void setRepresentanteDocumento(String representanteDocumento) {
        this.representanteDocumento = representanteDocumento;
    }

    public void setRepresentanteNombre(String representanteNombre) {
        this.representanteNombre = representanteNombre;
    }

    public void setRepresentanteTelefono(String representanteTelefono) {
        this.representanteTelefono = representanteTelefono;
    }

    public void setRepresentanteCorreo(String representanteCorreo) {
        this.representanteCorreo = representanteCorreo;
    }
}
