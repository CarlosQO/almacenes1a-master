package vista.vistaCliente.pasarelaVista;

import java.awt.Font;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.List;

public class CamposDeLasTarjetas {
    private Font fuenteLabel = new Font("Arial", Font.BOLD, 13);

    /** Agrega el campo para número de tarjeta al panel */
    public JTextField agregarCampoNumeroTarjeta(JPanel panel, int x, int y) {
        JLabel lbl = new JLabel("Número de tarjeta*");
        lbl.setFont(fuenteLabel);
        lbl.setBounds(x, y, 160, 20);
        panel.add(lbl);

        JTextField txt = new JTextField();
        txt.setName("txtNumeroTarjeta");
        txt.setBounds(x + 200, y, 205, 25);
        txt.setBorder(null);
        panel.add(txt);
        return txt;
    }

    /** Agrega el campo para CVV */
    public JTextField agregarCampoCVV(JPanel panel, int x, int y) {
        JLabel lbl = new JLabel("Código de seguridad (CVV)*");
        lbl.setFont(fuenteLabel);
        lbl.setBounds(x, y, 180, 20);
        panel.add(lbl);

        JTextField txt = new JTextField();
        txt.setName("txtCVV");
        txt.setBounds(x + 200, y, 100, 25);
        txt.setBorder(null);
        panel.add(txt);
        return txt;
    }

    /** Agrega el campo para nombre del titular */
    public JTextField agregarCampoNombreTitular(JPanel panel, int x, int y) {
        JLabel lbl = new JLabel("Nombre del titular*");
        lbl.setFont(fuenteLabel);
        lbl.setBounds(x, y, 160, 20);
        panel.add(lbl);

        JTextField txt = new JTextField();
        txt.setName("txtNombreTitular");
        txt.setBounds(x + 200, y, 205, 25);
        txt.setBorder(null);
        panel.add(txt);
        return txt;
    }

    /** Agrega el campo para dirección */
    public JTextField agregarCampoDireccion(JPanel panel, int x, int y) {
        JLabel lbl = new JLabel("Dirección de residencia*");
        lbl.setFont(fuenteLabel);
        lbl.setBounds(x, y, 180, 20);
        panel.add(lbl);

        JTextField txt = new JTextField();
        txt.setName("txtDireccion");
        txt.setBounds(x + 200, y, 205, 25);
        txt.setBorder(null);
        panel.add(txt);
        return txt;
    }

    public JTextField agregarCampoDocumento(JPanel panel, int x, int y) {
        JLabel lbl = new JLabel("Documento*");
        lbl.setFont(fuenteLabel);
        lbl.setBounds(x, y, 160, 20);
        panel.add(lbl);

        JTextField txt = new JTextField();
        txt.setName("txtDocumento");
        txt.setBounds(x + 200, y, 200, 25);
        txt.setBorder(null);
        txt.setBorder(null);
        panel.add(txt);
        return txt;
    }

    // COMBOBOXES

    // Agrega el combo de mes */
    public JComboBox<String> agregarComboMes(JPanel panel, int x, int y) {
        JLabel lbl = new JLabel("Fecha (AA)(MM)*");
        lbl.setFont(fuenteLabel);
        lbl.setBounds(x, y, 200, 20);
        panel.add(lbl);

        JComboBox<String> cbMes = new JComboBox<>();
        for (int i = 1; i <= 12; i++)
            cbMes.addItem(String.valueOf(i));
        cbMes.setName("cbMes");
        cbMes.setBounds(x + 200, y, 60, 25);
        panel.add(cbMes);
        return cbMes;
    }

    /** Agrega el combo de año */
    public JComboBox<String> agregarComboAnio(JPanel panel, int x, int y, int anioActual) {
        JComboBox<String> cbAnio = new JComboBox<>();
        for (int i = 0; i < 10; i++){
            cbAnio.addItem(String.valueOf(anioActual + i));
        }
        cbAnio.setName("cbAnio");
        cbAnio.setBounds(x, y, 80, 25);
        panel.add(cbAnio);
        return cbAnio;
    }

    /** Agrega un combo de tipo de documento a partir de un arreglo */
    public JComboBox<String> agregarComboTipoDocumento(JPanel panel, int x, int y, List<Map<Integer, String>> tipos) {
        JLabel lbl = new JLabel("Tipo de documento*");
        lbl.setFont(fuenteLabel);
        lbl.setBounds(x, y, 200, 20);
        panel.add(lbl);

        JComboBox<String> cbTipoDoc = new JComboBox<>();

        // 🔹 Solo recorremos los valores (String) del mapa
        for (Map<Integer, String> tipo : tipos) {
            for (String valor : tipo.values()) {
                cbTipoDoc.addItem(valor);
            }
        }

        cbTipoDoc.setName("cbTipoDocumento");
        cbTipoDoc.setBounds(x + 200, y, 150, 25);
        panel.add(cbTipoDoc);

        return cbTipoDoc;
    }


    /** Agrega un combo genérico (por ejemplo banco o tipo de cuenta) */
    public JComboBox<String> agregarComboGenerico(JPanel panel, int x, int y, String etiqueta, String[] opciones,
            String nombre) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(fuenteLabel);
        lbl.setBounds(x, y, 160, 20);
        panel.add(lbl);

        JComboBox<String> combo = new JComboBox<>();
        for (String opcion : opciones){
           combo.addItem(opcion); 
        }  
        combo.setName(nombre);
        combo.setBounds(x + 200, y, 150, 25);
        panel.add(combo);
        return combo;
    }
}
