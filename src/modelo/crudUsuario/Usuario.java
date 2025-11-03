package modelo.crudUsuario;

public class Usuario {
    private String documento;
    private String nombre;
    private String apellido;
    private int id_tipo_doc;
    private String correo;
    private String contrasena;
    private String direccion;
    private String telefono;
    private int id_rol;

    public Usuario(String documento, String nombre, String apellido, int id_tipo_doc, String correo, String contrasena,
            String direccion, String telefono, int id_rol) {
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.id_tipo_doc = id_tipo_doc;
        this.correo = correo;
        this.contrasena = contrasena;
        this.direccion = direccion;
        this.telefono = telefono;
        this.id_rol = id_rol;
    }

    public Usuario() {
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getIdTipoDoc() {
        return id_tipo_doc;
    }

    public void setIdTipoDoc(int id_tipo_doc) {
        this.id_tipo_doc = id_tipo_doc;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getIdRol() {
        return id_rol;
    }

    public void setIdRol(int id_rol) {
        this.id_rol = id_rol;
    }
}
