package modelo.pasarelaDePagosModelo;

import javax.swing.JOptionPane;

public class ProcesadorDePago {
    private ProcesoDePago estrategiaDePago;
    private boolean exitoEnPago = false;

    public void setEstrategiaDePago(ProcesoDePago estrategiaDePago) {
        this.estrategiaDePago = estrategiaDePago;
    }

    public void procesarPago(double monto) {
        exitoEnPago = false;
        if (estrategiaDePago != null) {
            if (estrategiaDePago.pagar(monto) == 1) {
                exitoEnPago = true;
            }
        } else {
            System.out.println(" No se ha definido una estrategia de pago.");
        }
        JOptionPane.showMessageDialog(null, "conectado a stratgy");
    }

    public boolean exitoEnPago() {
        return exitoEnPago;
    }

}