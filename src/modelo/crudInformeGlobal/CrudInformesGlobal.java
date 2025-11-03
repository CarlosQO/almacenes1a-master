package modelo.crudInformeGlobal;

import java.util.List;

public interface CrudInformesGlobal {
    public int totalPedidosFechas(String fechaInicio, String fechaFin);
    public List<String> totalVentaYingresosGenerados(String fechaInicio, String fechaFin);
    public String metodoDePagoFecha(String fechaInicio, String fechaFin);
    public String tendenciaDeCompraFecha(String fechaInicio, String fechaFin);
}
