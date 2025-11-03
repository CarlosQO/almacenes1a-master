package modelo.crudCarrito;

public class ProductosCarrito {
    public String nombreProducto, imagen;
    public int idCarrito, idCliente, idProducto, cantidadProducto;
    public Double precioUnitarioProducto, subtotalPorProducto, total;

    public ProductosCarrito() {
    }

    public ProductosCarrito(String nombreProducto, String imagen, int idCarrito, int idCliente, int idProducto,
            int cantidadProducto, Double precioUnitarioProducto, Double subtotalPorProducto) {
        this.nombreProducto = nombreProducto;
        this.imagen = imagen;
        this.idCarrito = idCarrito;
        this.idCliente = idCliente;
        this.idProducto = idProducto;
        this.cantidadProducto = cantidadProducto;
        this.precioUnitarioProducto = precioUnitarioProducto;
        this.subtotalPorProducto = subtotalPorProducto;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombrePrpducto) {
        this.nombreProducto = nombrePrpducto;
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

    public int getCantidadProducto() {
        return cantidadProducto;
    }

    public void setCantidadProducto(int cantidadProducto) {
        this.cantidadProducto = cantidadProducto;
    }

    public Double getPrecioUnitarioProducto() {
        return precioUnitarioProducto;
    }

    public void setPrecioUnitarioProducto(Double precioUnitarioProducto) {
        this.precioUnitarioProducto = precioUnitarioProducto;
    }

    public Double getSubtotalPorProducto() {
        return subtotalPorProducto;
    }

    public void setSubtotalPorProducto(Double subtotalPorProducto) {
        this.subtotalPorProducto = subtotalPorProducto;
    }
}
