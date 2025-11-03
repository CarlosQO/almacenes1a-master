package modelo.crudPromociones;

public class Promocion {
    public int idPromocion, idPrimerProducto, idSegundoProducto, porcentajeDescuento, cantidad;
    public String descripcionPromocion, nombrePromocion;
    public String rutaImagenPrimera, rutaImagenSegunda;
    public double total;

    public Promocion() {
    }

    public Promocion(int idPromocion, int idPrimerProducto, int idSegundoProducto, String descripcionPromocion,
            String nombrePromocion, int porcentajeDescuento, String rutaImagenPrimera, String rutaImagenSegunda,
            double total) {
        this.idPromocion = idPromocion;
        this.idPrimerProducto = idPrimerProducto;
        this.idSegundoProducto = idSegundoProducto;
        this.descripcionPromocion = descripcionPromocion;
        this.nombrePromocion = nombrePromocion;
        this.rutaImagenPrimera = rutaImagenPrimera;
        this.rutaImagenSegunda = rutaImagenSegunda;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdPromomocion() {
        return idPromocion;
    }

    public void setIdPromocion(int idPromocion) {
        this.idPromocion = idPromocion;
    }

    public int getIdPrimerProducto() {
        return idPrimerProducto;
    }

    public void setIdPrimerProducto(int idPrimerProducto) {
        this.idPrimerProducto = idPrimerProducto;
    }

    public int getIdSegundoProducto() {
        return idSegundoProducto;
    }

    public void setIdSegundoProducto(int idSegundoProducto) {
        this.idSegundoProducto = idSegundoProducto;
    }

    public String getDescripcionPromocion() {
        return descripcionPromocion;
    }

    public void setDescripcionPromocion(String descripcionPromocion) {
        this.descripcionPromocion = descripcionPromocion;
    }

    public String getNombrePromocion() {
        return nombrePromocion;
    }

    public void setNombrePromocion(String nombrePromocion) {
        this.nombrePromocion = nombrePromocion;
    }

    public String getRutaImagenPrimera() {
        return rutaImagenPrimera;
    }

    public void setRutaImagenPrimera(String rutaImagenPrimera) {
        this.rutaImagenPrimera = rutaImagenPrimera;
    }

    public String getRutaImagenSegunda() {
        return rutaImagenSegunda;
    }

    public void setRutaImagenSegunda(String rutaImagenSegunda) {
        this.rutaImagenSegunda = rutaImagenSegunda;
    }

    public int getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(int porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
