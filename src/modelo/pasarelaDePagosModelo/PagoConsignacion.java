
package modelo.pasarelaDePagosModelo;

import javax.swing.JOptionPane;

public class PagoConsignacion implements ProcesoDePago {
    private String numeroCuenta, tipoDedocumento, banco, tipoCuenta, nombreRemitente, numeroDocumento;
    private double saldo;

    public PagoConsignacion(String numeroCuenta, String tipoDedocumento, String banco, String tipoCuenta,
            String nombreRemitente, String numeroDocumento, double saldo) {
        this.numeroCuenta = numeroCuenta;
        this.tipoDedocumento = tipoDedocumento;
        this.banco = banco;
        this.tipoCuenta = tipoCuenta;
        this.nombreRemitente = nombreRemitente;
        this.numeroDocumento = numeroDocumento;
        this.saldo = saldo;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public String getTipoDedocumento() {
        return tipoDedocumento;
    }

    public void setTipoDedocumento(String tipoDedocumento) {
        this.tipoDedocumento = tipoDedocumento;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getNombreRemitente() {
        return nombreRemitente;
    }

    public void setNombreRemitente(String nombreRemitente) {
        this.nombreRemitente = nombreRemitente;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
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
            JOptionPane.showMessageDialog(null, "Pago con Consignación exitoso");
            return 1;
        } else {
            JOptionPane.showMessageDialog(null, "Saldo insuficiente");
            return -1;
        }

    }

    @Override
    public String factura() {
        return "METODO DE PAGO: Consignación";
    }
}