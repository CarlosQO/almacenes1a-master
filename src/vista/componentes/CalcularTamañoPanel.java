
package vista.componentes;

public class CalcularTamañoPanel {

    public CalcularTamañoPanel() {
    }

    public int calcularAltoPanel(int numElementos, int columnas, int altoElemento, int separacionY, int padding) {
        int filas = (int) Math.ceil(numElementos / (double) columnas);
        return filas * altoElemento + (filas - 1) * separacionY + padding;
    }

    public int calcularAnchoPanel(int numElementos, int anchoElemento, int separacionX, int padding) {
        int columnas = numElementos;
        return columnas * anchoElemento + (columnas - 1) * separacionX + padding;
    }

}