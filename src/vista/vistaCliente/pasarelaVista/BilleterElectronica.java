package vista.vistaCliente.pasarelaVista;

import java.awt.Color;
import java.awt.Font;
import java.util.List;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import vista.componentes.Validaciones;

public class BilleterElectronica {
    public JDialog dialogoBilleteraElectronica;
    public JTextField txtDocumentoBilletera, txtDireccion;
    public JComboBox<String> cbBancoBilletera, cbTipoDocBilletera, cbDepartamento, cbCiudad;
    public JButton btnConsignarBilletera;
    public CiudadesDepartamentosColombia ciudadesDepartamentosColombia;
    public JLabel montoConsignacion, agregarMonto;
    public List<Map<Integer, String>> tiposDoc, bancos;

    public BilleterElectronica(JFrame frame, List<Map<Integer, String>> tiposDoc, List<Map<Integer, String>> bancos) {
        dialogoBilleteraElectronica = new JDialog(frame, "Billetera Electronica", true);
        dialogoBilleteraElectronica.setTitle("Billetera Electrónica");
        dialogoBilleteraElectronica.setModal(true);
        dialogoBilleteraElectronica.setLayout(null);
        dialogoBilleteraElectronica.setBounds(250, 250, 470, 380);
        dialogoBilleteraElectronica.getContentPane().setBackground(Color.WHITE);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 40, 470, 210);
        panel.setBackground(new Color(0xE4EFF9));
        dialogoBilleteraElectronica.add(panel);

        // título
        JLabel lblTitulo = new JLabel("Billetera Electrónica");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setBounds(20, 10, 300, 25);
        dialogoBilleteraElectronica.add(lblTitulo);

        // usamos la clase reutilizable
        CamposDeLasTarjetas campos = new CamposDeLasTarjetas();

        // Banco
        cbBancoBilletera = campos.agregarComboBanco(panel, 20, 10, "Elige un Banco*", bancos, "cbBancoBilletera");

        // Tipo de documento
        cbTipoDocBilletera = campos.agregarComboTipoDocumento(panel, 20, 50, tiposDoc);

        // Documento
        txtDocumentoBilletera = campos.agregarCampoDocumento(panel, 20, 90);

        // Departamentos y ciudades
        ciudadesDepartamentosColombia = new CiudadesDepartamentosColombia();

        JLabel lblDepartamento = new JLabel("Departamento, ciudad*");
        lblDepartamento.setFont(new Font("Arial", Font.BOLD, 13));
        lblDepartamento.setBounds(20, 130, 200, 25);
        panel.add(lblDepartamento);

        cbDepartamento = new JComboBox<>();
        ciudadesDepartamentosColombia.cargarDepartamentosEnCombo(cbDepartamento);
        cbDepartamento.setBounds(220, 130, 100, 25);
        panel.add(cbDepartamento);

        cbCiudad = new JComboBox<>();
        cbCiudad.setBounds(325, 130, 100, 25);
        panel.add(cbCiudad);

        // evento para cargar ciudades
        cbDepartamento.addActionListener(e -> {
            cbCiudad.removeAllItems();
            String depSeleccionado = (String) cbDepartamento.getSelectedItem();
            if (depSeleccionado != null && !depSeleccionado.isEmpty()) {
                for (String ciudad : ciudadesDepartamentosColombia.obtenerCiudadesPorDepartamento(depSeleccionado)) {
                    cbCiudad.addItem(ciudad);
                }
            } else {
                cbCiudad.addItem("Seleccione un departamento");
            }
        });

        // Dirección
        txtDireccion = campos.agregarCampoDireccion(panel, 20, 170);

        // Botón
        btnConsignarBilletera = new JButton("Consignar");
        btnConsignarBilletera.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        btnConsignarBilletera.setBackground(new Color(0xE4EFF9));
        btnConsignarBilletera.setForeground(Color.BLACK);
        btnConsignarBilletera.setFont(new Font("Arial", Font.PLAIN, 15));
        btnConsignarBilletera.setBounds(150, 270, 150, 30);
        dialogoBilleteraElectronica.add(btnConsignarBilletera);
    }

    public boolean validarCamposBilletera() {
        String errores = "";

        // Documento
        String tipoDoc = (String) cbTipoDocBilletera.getSelectedItem();
        String documento = txtDocumentoBilletera.getText().trim();

        if (tipoDoc == null || tipoDoc.equals("Seleccione tipo de documento")) {
            errores += "- Debe seleccionar un tipo de documento.\n";
        } else {
            if (documento.isEmpty()) {
                errores += "- El número de documento no puede estar vacío.\n";
            } else {
                if (tipoDoc.equals("CC") || tipoDoc.equals("TI")) {
                    if (!Validaciones.validarCedula(documento)) {
                        errores += "- Cédula o tarjeta de identidad inválida (debe tener entre 9 y 11 dígitos).\n";
                    }
                } else if (tipoDoc.equals("NIT")) {
                    if (!Validaciones.validarNIT(documento)) {
                        errores += "- NIT inválido (Ej: 900123456 o 900123456-7).\n";
                    }
                }
            }
        }

        // Banco
        String banco = (String) cbBancoBilletera.getSelectedItem();
        if (banco == null || banco.equals("Seleccione un banco")) {
            errores += "- Debe seleccionar un banco.\n";
        }

        // Departamento
        String departamento = (String) cbDepartamento.getSelectedItem();
        if (departamento == null || departamento.equals("Seleccione un departamento")) {
            errores += "- Debe seleccionar un departamento.\n";
        }

        // Ciudad
        String ciudad = (String) cbCiudad.getSelectedItem();
        if (ciudad == null || ciudad.equals("Seleccione una ciudad")) {
            errores += "- Debe seleccionar una ciudad.\n";
        }

        // Dirección
        String direccion = txtDireccion.getText().trim();
        if (direccion.isEmpty()) {
            errores += "- La dirección no puede estar vacía.\n";
        } else if (!Validaciones.validarDireccion(direccion)) {
            errores += "- Dirección inválida. Debe contener letras y números (Ej: Calle 45 #12-34).\n";
        }

        // Mostrar todos los errores juntos si existen
        if (!errores.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Se encontraron los siguientes errores:\n\n" + errores,
                    "Errores en el formulario", JOptionPane.WARNING_MESSAGE);
            return false;
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

    public List<Map<Integer, String>> getTiposDoc() {
        return tiposDoc;
    }

    public void setTiposDoc(List<Map<Integer, String>> tiposDoc) {
        this.tiposDoc = tiposDoc;
    }
}
