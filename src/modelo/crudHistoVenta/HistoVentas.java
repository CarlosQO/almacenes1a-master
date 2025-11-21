package modelo.crudHistoVenta;
import java.sql.Timestamp;

public class HistoVentas {

    private int idHistoVenta;
    private int cantidadVenta;
    private int idProducto;
    private Timestamp fecha; // DATETIME

    // Constructor vacío
    public HistoVentas() {
    }

    // Constructor completo
    public HistoVentas(int idHistoVenta, int cantidadVenta, int idProducto, Timestamp fecha) {
        this.idHistoVenta = idHistoVenta;
        this.cantidadVenta = cantidadVenta;
        this.idProducto = idProducto;
        this.fecha = fecha;
    }

    // Getters y Setters
    public int getIdHistoVenta() {
        return idHistoVenta;
    }

    public void setIdHistoVenta(int idHistoVenta) {
        this.idHistoVenta = idHistoVenta;
    }

    public int getCantidadVenta() {
        return cantidadVenta;
    }

    public void setCantidadVenta(int cantidadVenta) {
        this.cantidadVenta = cantidadVenta;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }
}
