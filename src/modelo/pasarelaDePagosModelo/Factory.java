
package modelo.pasarelaDePagosModelo;

import java.util.HashMap;
import java.util.Map;

public class Factory {
    private double saldo, totalrecibido;
    // tarjeta
    private String numeroTarjeta;
    private String fechaTarjeta, nombreDelTitularTarjeta, cvvTarjeta;
    // Consignacion
    private String numeroCuentaConsignacion, tipoDedocumentoConsignacion, bancoConsignacion, tipoCuentaConsignacion,
            nombreRemitenteConsignacion, numeroDocumentoConsignacion;
    // Billetera
    private String bancoBilletera, tipoDocumentoBilletera, numeroDocumentoBilletera;

    public Factory() {
    }

    public ProcesoDePago obtenerPago(TipoDePago tipopago) {
        Map<TipoDePago, ProcesoDePago> pagos = new HashMap<>();

        pagos.put(
                TipoDePago.TARJETA_CREDITO,
                new PagoTarjetaCredito(numeroTarjeta, cvvTarjeta, fechaTarjeta, nombreDelTitularTarjeta, saldo));

        pagos.put(
                TipoDePago.TARJETA_DEBITO,
                new PagoTarjetaDebito(numeroTarjeta, cvvTarjeta, fechaTarjeta, nombreDelTitularTarjeta, saldo));

        pagos.put(
                TipoDePago.CONSIGNACION,
                new PagoConsignacion(numeroCuentaConsignacion, tipoDedocumentoConsignacion, bancoConsignacion,
                        tipoCuentaConsignacion, nombreRemitenteConsignacion, numeroDocumentoConsignacion, saldo));

        pagos.put(
                TipoDePago.BILLETERA_ELECTRONICA,
                new PagoBilleteraElectronica(bancoBilletera, tipoDocumentoBilletera, numeroDocumentoBilletera, saldo));

        return pagos.get(tipopago);
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public double getTotalrecibido() {
        return totalrecibido;
    }

    public void setTotalrecibido(double totalrecibido) {
        this.totalrecibido = totalrecibido;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getCvvTarjeta() {
        return cvvTarjeta;
    }

    public void setCvvTarjeta(String cvvTarjeta) {
        this.cvvTarjeta = cvvTarjeta;
    }

    public String getFechaTarjeta() {
        return fechaTarjeta;
    }

    public void setFechaTarjeta(String fechaTarjeta) {
        this.fechaTarjeta = fechaTarjeta;
    }

    public String getNombreDelTitularTarjeta() {
        return nombreDelTitularTarjeta;
    }

    public void setNombreDelTitularTarjeta(String nombreDelTitularTarjeta) {
        this.nombreDelTitularTarjeta = nombreDelTitularTarjeta;
    }

    public String getNumeroCuentaConsignacion() {
        return numeroCuentaConsignacion;
    }

    public void setNumeroCuentaConsignacion(String numeroCuentaConsignacion) {
        this.numeroCuentaConsignacion = numeroCuentaConsignacion;
    }

    public String getTipoDedocumentoConsignacion() {
        return tipoDedocumentoConsignacion;
    }

    public void setTipoDedocumentoConsignacion(String tipoDedocumentoConsignacion) {
        this.tipoDedocumentoConsignacion = tipoDedocumentoConsignacion;
    }

    public String getBancoConsignacion() {
        return bancoConsignacion;
    }

    public void setBancoConsignacion(String bancoConsignacion) {
        this.bancoConsignacion = bancoConsignacion;
    }

    public String getTipoCuentaConsignacion() {
        return tipoCuentaConsignacion;
    }

    public void setTipoCuentaConsignacion(String tipoCuentaConsignacion) {
        this.tipoCuentaConsignacion = tipoCuentaConsignacion;
    }

    public String getNombreRemitenteConsignacion() {
        return nombreRemitenteConsignacion;
    }

    public void setNombreRemitenteConsignacion(String nombreRemitenteConsignacion) {
        this.nombreRemitenteConsignacion = nombreRemitenteConsignacion;
    }

    public String getNumeroDocumentoConsignacion() {
        return numeroDocumentoConsignacion;
    }

    public void setNumeroDocumentoConsignacion(String numeroDocumentoConsignacion) {
        this.numeroDocumentoConsignacion = numeroDocumentoConsignacion;
    }

    public String getBancoBilletera() {
        return bancoBilletera;
    }

    public void setBancoBilletera(String bancoBilletera) {
        this.bancoBilletera = bancoBilletera;
    }

    public String getTipoDocumentoBilletera() {
        return tipoDocumentoBilletera;
    }

    public void setTipoDocumentoBilletera(String tipoDocumentoBilletera) {
        this.tipoDocumentoBilletera = tipoDocumentoBilletera;
    }

    public String getNumeroDocumentoBilletera() {
        return numeroDocumentoBilletera;
    }

    public void setNumeroDocumentoBilletera(String numeroDocumentoBilletera) {
        this.numeroDocumentoBilletera = numeroDocumentoBilletera;
    }
}