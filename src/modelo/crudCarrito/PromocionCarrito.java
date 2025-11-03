package modelo.crudCarrito;

public class PromocionCarrito {
    public String nombreProducto, imagen;
    public int idCarrito, idCliente, idPromocion, cantidadPromociones;
    public Double precioUnitarioPromocion, subtotalPorProducto, total;

    public PromocionCarrito() {
    }

    public PromocionCarrito(String nombreProducto, String imagen, int idCarrito, int idCliente, int idPromocion,
            int cantidadPromociones, Double precioUnitarioPromocion, Double subtotalPorProducto, Double total) {
        this.nombreProducto = nombreProducto;
        this.imagen = imagen;
        this.idCarrito = idCarrito;
        this.idCliente = idCliente;
        this.idPromocion = idPromocion;
        this.cantidadPromociones = cantidadPromociones;
        this.precioUnitarioPromocion = precioUnitarioPromocion;
        this.subtotalPorProducto = subtotalPorProducto;
        this.total = total;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(int idPromocion) {
        this.idPromocion = idPromocion;
    }

    public int getCantidadPromocion() {
        return cantidadPromociones;
    }

    public void setCantidadPromocion(int cantidadPromociones) {
        this.cantidadPromociones = cantidadPromociones;
    }

    public Double getPrecioUnitarioPromocion() {
        return precioUnitarioPromocion;
    }

    public void setPrecioUnitarioPromocion(Double precioUnitarioPromocion) {
        this.precioUnitarioPromocion = precioUnitarioPromocion;
    }

    public Double getSubtotalPorPromocion() {
        return subtotalPorProducto;
    }

    public void setSubtotalPorPromocion(Double subtotalPorProducto) {
        this.subtotalPorProducto = subtotalPorProducto;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

}
