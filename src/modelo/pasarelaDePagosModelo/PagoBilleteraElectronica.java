package modelo.pasarelaDePagosModelo;
import javax.swing.JOptionPane;

public class PagoBilleteraElectronica implements ProcesoDePago{
    public String banco, tipoDocumento, numeroDocumento;
    public double monto, saldo;

    public PagoBilleteraElectronica(String banco, String tipoDocumento, String numeroDocumento, double saldo) {
        this.banco = banco;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.saldo = saldo;
    }

    public String getBanco() {return banco;}
    public void setBanco(String banco) {this.banco = banco;}

    public String getTipoDocumento() {return tipoDocumento;}
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento;}

    public String getNumeroDocumento() {return numeroDocumento;}
    public void setNumeroDocumento(String numeroDocumento) {this.numeroDocumento = numeroDocumento;}

    public double getSaldo() {return saldo;}
    public void setSaldo(double saldo) { this.saldo = saldo;}

    @Override
    public int pagar(double monto) {
        if(this.saldo>=monto){
            JOptionPane.showMessageDialog(null, "Pago con Billetera Electronica exitoso");
            return 1;
        }else{
            JOptionPane.showMessageDialog(null, "Saldo insuficiente");
            return -1;
        }
    }

    @Override
    public String factura() {
        return "METODO DE PAGO: Billetera Electronica";
    }
    
}
