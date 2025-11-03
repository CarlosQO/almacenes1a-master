package modelo.crudPedidos;

import java.sql.Timestamp;

public class PedidoSupervisor {
    private int id;
    private int idEstadoPedido;
    private Timestamp fechaUltimaModi;

    public PedidoSupervisor(int id, int idEstadoPedido, Timestamp fechaUltimaModi) {
        this.id = id;
        this.idEstadoPedido = idEstadoPedido;
        this.fechaUltimaModi = fechaUltimaModi;
    }

    public PedidoSupervisor() {

    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdEstadoPedido(int idEstadoPedido) {
        this.idEstadoPedido = idEstadoPedido;
    }

    public void setFechaUltimaModi(Timestamp fechaUltimaModi) {
        this.fechaUltimaModi = fechaUltimaModi;
    }

    public int getId() {
        return id;
    }

    public int getIdEstadoPedido() {
        return idEstadoPedido;
    }

    public Timestamp getFechaUltimaModi() {
        return fechaUltimaModi;
    }
}
