package vista.vistaCliente.pasarelaVista;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import vista.vistaCliente.Validaciones;

public class BilleterElectronica {
    public JDialog dialogoBilleteraElectronica;
    public JTextField txtDocumentoBilletera;
    public JComboBox<String> cbBancoBilletera, cbTipoDocBilletera;
    public JButton btnConsignarBilletera;

    public BilleterElectronica(JFrame frame) {
        dialogoBilleteraElectronica = new JDialog(frame, "Billetera Electronica", true);
        dialogoBilleteraElectronica.setTitle("Billetera Electrónica");
        dialogoBilleteraElectronica.setModal(true);
        dialogoBilleteraElectronica.setLayout(null);
        dialogoBilleteraElectronica.setBounds(250, 250, 420, 280);
        dialogoBilleteraElectronica.getContentPane().setBackground(Color.WHITE);
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 40, 420, 130);
        panel.setBackground(new Color(0xE4EFF9));
        dialogoBilleteraElectronica.add(panel);
        // titulo
        JLabel lblTitulo = new JLabel("Billetera Electrónica");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setBounds(20, 10, 300, 25);
        dialogoBilleteraElectronica.add(lblTitulo);
        // banco
        JLabel lblBanco = new JLabel("Elige un Banco*");
        lblBanco.setBounds(20, 10, 200, 25);
        panel.add(lblBanco);//
        cbBancoBilletera = new JComboBox<>();
        cbBancoBilletera.addItem("Bancolombia");
        cbBancoBilletera.addItem("Davivienda");
        cbBancoBilletera.addItem("BBVA");
        cbBancoBilletera.addItem("Banco de Bogotá");
        cbBancoBilletera.setBounds(180, 10, 200, 30);
        panel.add(cbBancoBilletera);
        // tipo doc
        JLabel lblTipoDoc = new JLabel("Tipo de documento*");
        lblTipoDoc.setBounds(20, 50, 200, 25);
        panel.add(lblTipoDoc);
        cbTipoDocBilletera = new JComboBox<>();
        cbTipoDocBilletera.addItem("CC");
        cbTipoDocBilletera.addItem("NIT");
        cbTipoDocBilletera.setBounds(180, 50, 200, 30);
        panel.add(cbTipoDocBilletera);
        // numero documento
        JLabel lblDocumento = new JLabel("Número de Documento*");
        lblDocumento.setBounds(20, 90, 200, 25);
        panel.add(lblDocumento);
        txtDocumentoBilletera = new JTextField();
        txtDocumentoBilletera.setBounds(180, 90, 200, 30);
        txtDocumentoBilletera.setBorder(null);
        panel.add(txtDocumentoBilletera);
        // boton
        btnConsignarBilletera = new JButton("Consignar");
        btnConsignarBilletera.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        btnConsignarBilletera.setBackground(new Color(0xE4EFF9));
        btnConsignarBilletera.setForeground(Color.BLACK);
        btnConsignarBilletera.setFont(new Font("Arial", Font.PLAIN, 15));
        btnConsignarBilletera.setBounds(120, 185, 150, 30);
        dialogoBilleteraElectronica.add(btnConsignarBilletera);
    }

    public boolean validarCamposBilletera() {

        if (cbBancoBilletera.getSelectedItem() == null
                || cbBancoBilletera.getSelectedItem().toString() == null
                || cbBancoBilletera.getSelectedItem().toString().trim().isEmpty()) {

            JOptionPane.showMessageDialog(dialogoBilleteraElectronica, "Debe seleccionar un banco");
            return false;
        }

        if (cbTipoDocBilletera.getSelectedItem() == null
                || cbTipoDocBilletera.getSelectedItem().toString() == null
                || cbTipoDocBilletera.getSelectedItem().toString().trim().isEmpty()) {

            JOptionPane.showMessageDialog(dialogoBilleteraElectronica, "Debe seleccionar un tipo de documento");
            return false;
        }

        String tipoDoc = cbTipoDocBilletera.getSelectedItem().toString().trim();
        String documento = txtDocumentoBilletera.getText().trim();

        if (documento == null || documento.trim().isEmpty()) {
            JOptionPane.showMessageDialog(dialogoBilleteraElectronica, "El número de documento no puede estar vacío");
            return false;
        }

        if (tipoDoc.equals("CC")) {
            if (!Validaciones.validarCedula(documento)) {
                JOptionPane.showMessageDialog(dialogoBilleteraElectronica, "Cédula inválida (9 a 11 dígitos)");
                return false;
            }
        } else if (tipoDoc.equals("NIT")) {
            if (!Validaciones.validarNumeros(documento)) {
                JOptionPane.showMessageDialog(dialogoBilleteraElectronica, "El NIT solo debe contener números");
                return false;
            }
        }
        return true;
    }

    public JDialog getDialogoBilleteraElectronica() {
        return dialogoBilleteraElectronica;
    }

    public JTextField getTxtDocumentoBilletera() {
        return txtDocumentoBilletera;
    }

    public void setTxtDocumentoBilletera(JTextField txtDocumentoBilletera) {
        this.txtDocumentoBilletera = txtDocumentoBilletera;
    }

    public JComboBox<String> getCbBancoBilletera() {
        return cbBancoBilletera;
    }

    public void setCbBancoBilletera(JComboBox<String> cbBancoBilletera) {
        this.cbBancoBilletera = cbBancoBilletera;
    }

    public JComboBox<String> getCbTipoDocBilletera() {
        return cbTipoDocBilletera;
    }

    public void setCbTipoDocBilletera(JComboBox<String> cbTipoDocBilletera) {
        this.cbTipoDocBilletera = cbTipoDocBilletera;
    }

}
