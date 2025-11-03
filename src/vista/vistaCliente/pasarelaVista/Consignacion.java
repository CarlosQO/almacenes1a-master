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

public class Consignacion {
    public JTextField txtNombreConsignacion, txtDocumentoConsignacion, txtNumCuenta;
    public JComboBox<String> cbTipoDocConsignacion, cbBancoConsignacion, cbTipoCuenta;
    public JLabel montoConsignacion, agregarMonto;
    public JButton btnConsignar;
    public JDialog dialogoConsignacion;
    public double total;

    public Consignacion(JFrame frame, double total) {
        dialogoConsignacion = new JDialog(frame, "Consignacion", true);
        dialogoConsignacion.getContentPane().setBackground(Color.WHITE);
        dialogoConsignacion.setTitle("Consignación Bancaria");
        dialogoConsignacion.setModal(true);
        dialogoConsignacion.setLayout(null);
        dialogoConsignacion.setBounds(250, 250, 450, 445);
        // Panel principal
        JPanel panelConsignacion = new JPanel();
        panelConsignacion.setLayout(null);
        panelConsignacion.setBounds(0, 40, 450, 300);
        panelConsignacion.setBackground(new Color(0xE4EFF9));
        dialogoConsignacion.add(panelConsignacion);
        // Título
        JLabel lblTitulo = new JLabel("Datos de Consignación");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBounds(20, 10, 300, 25);
        dialogoConsignacion.add(lblTitulo);
        // Nombre de quien recibe
        JLabel lblNombre = new JLabel("Nombre de quien recibe*");
        lblNombre.setBounds(20, 10, 160, 20);
        panelConsignacion.add(lblNombre);
        txtNombreConsignacion = new JTextField();
        txtNombreConsignacion.setBounds(180, 10, 220, 25);
        txtNombreConsignacion.setBorder(null);
        panelConsignacion.add(txtNombreConsignacion);
        // Tipo de documento
        JLabel lblTipoDoc = new JLabel("Tipo de documento*");
        lblTipoDoc.setBounds(20, 50, 160, 20);
        panelConsignacion.add(lblTipoDoc);
        cbTipoDocConsignacion = new JComboBox<>();
        cbTipoDocConsignacion.addItem("CC");
        cbTipoDocConsignacion.addItem("NIT");
        cbTipoDocConsignacion.setBounds(180, 50, 100, 25);
        panelConsignacion.add(cbTipoDocConsignacion);
        // Documento
        JLabel lblDocumento = new JLabel("Documento CC/NIT*");
        lblDocumento.setBounds(20, 90, 160, 20);
        panelConsignacion.add(lblDocumento);
        txtDocumentoConsignacion = new JTextField();
        txtDocumentoConsignacion.setBounds(180, 90, 220, 25);
        txtDocumentoConsignacion.setBorder(null);
        panelConsignacion.add(txtDocumentoConsignacion);
        // Banco
        JLabel lblBanco = new JLabel("Elige un Banco*");
        lblBanco.setBounds(20, 130, 160, 20);
        panelConsignacion.add(lblBanco);
        cbBancoConsignacion = new JComboBox<>();
        cbBancoConsignacion.addItem("Bancolombia");
        cbBancoConsignacion.addItem("Davivienda");
        cbBancoConsignacion.addItem("BBVA");
        cbBancoConsignacion.addItem("Banco de Bogotá");
        cbBancoConsignacion.addItem("Nequi");
        cbBancoConsignacion.setBounds(180, 130, 150, 25);
        panelConsignacion.add(cbBancoConsignacion);
        // Tipo de cuenta
        JLabel lblTipoCuenta = new JLabel("Tipo de Cuenta*");
        lblTipoCuenta.setBounds(20, 170, 160, 20);
        panelConsignacion.add(lblTipoCuenta);
        cbTipoCuenta = new JComboBox<>();
        cbTipoCuenta.addItem("Ahorros");
        cbTipoCuenta.addItem("Corriente");
        cbTipoCuenta.setBounds(180, 170, 150, 25);
        panelConsignacion.add(cbTipoCuenta);
        // Número de cuenta
        JLabel lblNumCuenta = new JLabel("Número de cuenta*");
        lblNumCuenta.setBounds(20, 210, 160, 20);
        panelConsignacion.add(lblNumCuenta);
        txtNumCuenta = new JTextField();
        txtNumCuenta.setBounds(180, 210, 220, 25);
        txtNumCuenta.setBorder(null);
        panelConsignacion.add(txtNumCuenta);
        // Monto a pagar
        JLabel lblMonto = new JLabel("Monto a Pagar*");
        lblMonto.setBounds(20, 250, 160, 20);
        panelConsignacion.add(lblMonto);
        agregarMonto = new JLabel(" " + total);
        agregarMonto.setBounds(180, 250, 220, 25);
        panelConsignacion.add(agregarMonto);
        // Botón consignar
        btnConsignar = new JButton("Consignar");
        btnConsignar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        btnConsignar.setBackground(new Color(0xE4EFF9));
        btnConsignar.setForeground(Color.BLACK);
        btnConsignar.setFont(new Font("Arial", Font.PLAIN, 15));
        btnConsignar.setBounds(140, 350, 150, 40);
        dialogoConsignacion.add(btnConsignar);
    }

    public boolean validarCamposConsignacion() {
        boolean resultado = true;

        String nombre = txtNombreConsignacion.getText().trim();
        String documento = txtDocumentoConsignacion.getText().trim();
        String numeroCuenta = txtNumCuenta.getText().trim();

        String tipoDoc = "";
        if (cbTipoDocConsignacion.getSelectedItem() != null) {
            tipoDoc = cbTipoDocConsignacion.getSelectedItem().toString();
        }

        String banco = "";
        if (cbBancoConsignacion.getSelectedItem() != null) {
            banco = cbBancoConsignacion.getSelectedItem().toString();
        }

        String tipoCuenta = "";
        if (cbTipoCuenta.getSelectedItem() != null) {
            tipoCuenta = cbTipoCuenta.getSelectedItem().toString();
        }

        // VALIDACIONES
        if (!Validaciones.validarLetras(nombre)) {
            JOptionPane.showMessageDialog(dialogoConsignacion, "El nombre solo debe contener letras");
            resultado = false;
        }

        if (tipoDoc.isEmpty()) {
            JOptionPane.showMessageDialog(dialogoConsignacion, "Debe seleccionar un tipo de documento");
            resultado = false;
        } else {
            if (tipoDoc.equals("CC")) {
                if (!Validaciones.validarCedula(documento)) {
                    JOptionPane.showMessageDialog(dialogoConsignacion, "Cédula inválida (9 a 11 dígitos)");
                    resultado = false;
                }
            } else if (tipoDoc.equals("NIT")) {
                if (!Validaciones.validarNIT(documento)) { // asegurate de haber agregado validarNIT en Validaciones
                    JOptionPane.showMessageDialog(dialogoConsignacion, "NIT inválido (ej: 900123456 o 900123456-7)");
                    resultado = false;
                }
            }
        }

        if (banco.isEmpty()) {
            JOptionPane.showMessageDialog(dialogoConsignacion, "Debe seleccionar un banco");
            resultado = false;
        }

        if (tipoCuenta.isEmpty()) {
            JOptionPane.showMessageDialog(dialogoConsignacion, "Debe seleccionar un tipo de cuenta");
            resultado = false;
        }

        if (!Validaciones.validarNumeroCuenta(numeroCuenta)) {
            JOptionPane.showMessageDialog(dialogoConsignacion,
                    "Número de cuenta inválido (debe iniciar en 4 o 5 y tener 13 a 16 dígitos)");
            resultado = false;
        }
        return resultado;
    }

    public JTextField getTxtNombreConsignacion() {
        return txtNombreConsignacion;
    }

    public void setTxtNombreConsignacion(JTextField txtNombreConsignacion) {
        this.txtNombreConsignacion = txtNombreConsignacion;
    }

    public JTextField getTxtDocumentoConsignacion() {
        return txtDocumentoConsignacion;
    }

    public void setTxtDocumentoConsignacion(JTextField txtDocumentoConsignacion) {
        this.txtDocumentoConsignacion = txtDocumentoConsignacion;
    }

    public JTextField getTxtNumCuenta() {
        return txtNumCuenta;
    }

    public void setTxtNumCuenta(JTextField txtNumCuenta) {
        this.txtNumCuenta = txtNumCuenta;
    }

    public JComboBox<String> getCbTipoDocConsignacion() {
        return cbTipoDocConsignacion;
    }

    public void setCbTipoDocConsignacion(JComboBox<String> cbTipoDocConsignacion) {
        this.cbTipoDocConsignacion = cbTipoDocConsignacion;
    }

    public JComboBox<String> getCbBancoConsignacion() {
        return cbBancoConsignacion;
    }

    public void setCbBancoConsignacion(JComboBox<String> cbBancoConsignacion) {
        this.cbBancoConsignacion = cbBancoConsignacion;
    }

    public JComboBox<String> getCbTipoCuenta() {
        return cbTipoCuenta;
    }

    public void setCbTipoCuenta(JComboBox<String> cbTipoCuenta) {
        this.cbTipoCuenta = cbTipoCuenta;
    }

    public JDialog getDialogoConsignacion() {
        return dialogoConsignacion;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
