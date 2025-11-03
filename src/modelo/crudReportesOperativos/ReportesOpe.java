package modelo.crudReportesOperativos;

import java.sql.Date;

public class ReportesOpe {
    private String concepto;
    private double monto;
    private Date fecha;
    private String medioPago;

    public ReportesOpe() {
    }

    public ReportesOpe(String concepto, double monto, Date fecha, String medioPago) {
        this.concepto = concepto;
        this.monto = monto;
        this.fecha = fecha;
        this.medioPago = medioPago;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
    }
}