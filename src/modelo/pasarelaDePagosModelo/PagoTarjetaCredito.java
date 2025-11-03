package modelo.pasarelaDePagosModelo;

import javax.swing.JOptionPane;

public class PagoTarjetaCredito implements ProcesoDePago {
    private String numeroTarjeta, cvv;
    private String fecha, nombreDelTitular;
    private double saldo;

    public PagoTarjetaCredito(String numeroTarjeta, String cvv, String fecha, String nombreDelTitular, double saldo) {
        this.numeroTarjeta = numeroTarjeta;
        this.cvv = cvv;
        this.fecha = fecha;
        this.nombreDelTitular = nombreDelTitular;
        this.saldo = saldo;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public String getNombreDelTitular() {
        return nombreDelTitular;
    }

    public void setNombreDelTitular(String nombreDelTitular) {
        this.nombreDelTitular = nombreDelTitular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public int pagar(double monto) {
        if (this.saldo >= monto) {
            JOptionPane.showMessageDialog(null, "Pago con Tarjeta de Credito exitoso");
            return 1;
        } else {
            JOptionPane.showMessageDialog(null, "Saldo insuficiente");
            return -1;
        }
    }

    @Override
    public String factura() {
        return "METODO DE PAGO: Tarjeto de Credito";
    }
}
