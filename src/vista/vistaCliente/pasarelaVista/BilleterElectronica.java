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
import vista.componentes.Validaciones;

public class BilleterElectronica {
    public JDialog dialogoBilleteraElectronica;
    public JTextField txtDocumentoBilletera, txtDireccion;
    public JComboBox<String> cbBancoBilletera, cbTipoDocBilletera, cbDepartamento, cbCiudad;
    public JButton btnConsignarBilletera;
    public CiudadesDepartamentosColombia ciudadesDepartamentosColombia;
    public JLabel montoConsignacion, agregarMonto;
    public String[] tiposDoc;

    public BilleterElectronica(JFrame frame, String[] tiposDoc) {
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
        String[] bancos = {"Bancolombia", "Davivienda", "BBVA", "Banco de Bogotá"};
        cbBancoBilletera = campos.agregarComboGenerico(panel, 20, 10, "Elige un Banco*", bancos, "cbBancoBilletera");

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

    public String[] getTiposDoc() {
        return tiposDoc;
    }

    public void setTiposDoc(String[] tiposDoc) {
        this.tiposDoc = tiposDoc;
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Prueba Pasarela de Pagos");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null); // Para posicionamiento manual

        // Botón para abrir el diálogo de Tarjetas
        JButton btnAbrirTarjeta = new JButton("Pagar con Tarjeta");
        btnAbrirTarjeta.setBounds(200, 150, 200, 50);
        frame.add(btnAbrirTarjeta);
        
        String[] tiposDoc = {"CC", "NIT"};

        // Acción del botón
        btnAbrirTarjeta.addActionListener(e -> {
            BilleterElectronica tarjeta = new BilleterElectronica(frame, tiposDoc);
            tarjeta.dialogoBilleteraElectronica.setVisible(true);
        });

        // Mostrar el frame
        frame.setLocationRelativeTo(null); // Centrar en pantalla
        frame.setVisible(true);
    }

}
