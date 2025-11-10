package vista.vistaCliente.pasarelaVista;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import vista.componentes.Validaciones;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Consignacion {
    public JTextField txtNombreConsignacion, txtDocumentoConsignacion, txtNumCuenta, txtDireccion;
    public JComboBox<String> cbTipoDocConsignacion, cbBancoConsignacion, cbTipoCuenta, cbDepartamento, cbCiudad;
    public JLabel montoConsignacion, agregarMonto;
    public JButton btnConsignar;
    public JDialog dialogoConsignacion;
    public double total;
    public CiudadesDepartamentosColombia ciudadesDepartamentosColombia;

    public Consignacion(JFrame frame, double total, List<Map<Integer, String>> tiposDoc,
            List<Map<Integer, String>> bancos) {
        this.total = total;

        // Crear diálogo
        dialogoConsignacion = new JDialog(frame, "Consignación Bancaria", true);
        dialogoConsignacion.setSize(490, 520);
        dialogoConsignacion.setLayout(null);
        dialogoConsignacion.setLocationRelativeTo(frame);
        dialogoConsignacion.getContentPane().setBackground(Color.WHITE);

        // Título
        JLabel lblTitulo = new JLabel("Datos de Consignación");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBounds(20, 10, 300, 25);
        dialogoConsignacion.add(lblTitulo);

        // Panel principal
        JPanel panel = new JPanel(null);
        panel.setBounds(0, 40, 470, 370);
        panel.setBackground(new Color(0xE4EFF9));
        dialogoConsignacion.add(panel);

        // Instancia de CamposDeLasTarjetas
        CamposDeLasTarjetas campos = new CamposDeLasTarjetas();

        // Campos
        txtNombreConsignacion = campos.agregarCampoNombreTitular(panel, 20, 10);

        cbTipoDocConsignacion = campos.agregarComboTipoDocumento(panel, 20, 50, tiposDoc);

        txtDocumentoConsignacion = new JTextField();
        txtDocumentoConsignacion = campos.agregarCampoDocumento(panel, 20, 90);
        panel.add(txtDocumentoConsignacion);

        cbBancoConsignacion = campos.agregarComboBanco(panel, 20, 130, "Elige un banco*", bancos,
                "cbBancoConsignacion");

        String[] tiposCuenta = { "Ahorros", "Corriente" };
        cbTipoCuenta = campos.agregarComboGenerico(panel, 20, 170, "Tipo de cuenta*", tiposCuenta, "cbTipoCuenta");

        txtNumCuenta = new JTextField();
        txtNumCuenta = campos.agregarCampoNumeroTarjeta(panel, 20, 210);
        panel.add(txtNumCuenta);

        // Departamentos y ciudades
        ciudadesDepartamentosColombia = new CiudadesDepartamentosColombia();
        JLabel lblDep = new JLabel("Departamento - Ciudad*");
        lblDep.setFont(new Font("Arial", Font.BOLD, 13));
        lblDep.setBounds(20, 250, 180, 20);
        panel.add(lblDep);

        cbDepartamento = new JComboBox<>();
        ciudadesDepartamentosColombia.cargarDepartamentosEnCombo(cbDepartamento);
        cbDepartamento.setBounds(220, 250, 100, 25);
        panel.add(cbDepartamento);

        cbCiudad = new JComboBox<>();
        cbCiudad.setBounds(325, 250, 100, 25);
        panel.add(cbCiudad);

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

        // direccion
        txtDireccion = campos.agregarCampoDireccion(panel, 20, 290);

        // Monto
        JLabel lblMonto = new JLabel("Monto a pagar*");
        lblMonto.setFont(new Font("Arial", Font.BOLD, 13));
        lblMonto.setBounds(20, 330, 160, 20);
        panel.add(lblMonto);

        agregarMonto = new JLabel(" " + total);
        agregarMonto.setBounds(220, 330, 220, 25);
        panel.add(agregarMonto);

        // Botón consignar
        btnConsignar = new JButton("Consignar");
        btnConsignar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        btnConsignar.setBackground(new Color(0xE4EFF9));
        btnConsignar.setFont(new Font("Arial", Font.PLAIN, 15));
        btnConsignar.setBounds(160, 425, 150, 40);
        dialogoConsignacion.add(btnConsignar);
    }

    public boolean validarCamposConsignacion() {
        String errores = "";

        String nombre = txtNombreConsignacion.getText().trim();
        String documento = txtDocumentoConsignacion.getText().trim();
        String numeroCuenta = txtNumCuenta.getText().trim();
        String direccion = txtDireccion.getText().trim();

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

        String departamento = "";
        if (cbDepartamento.getSelectedItem() != null) {
            departamento = cbDepartamento.getSelectedItem().toString();
        }

        String ciudad = "";
        if (cbCiudad.getSelectedItem() != null) {
            ciudad = cbCiudad.getSelectedItem().toString();
        }

        // ===== VALIDACIONES =====

        // Nombre
        if (nombre.isEmpty()) {
            errores += "- El nombre no puede estar vacío.\n";
        } else if (!Validaciones.validarLetras(nombre)) {
            errores += "- El nombre solo debe contener letras.\n";
        }

        // Tipo y número de documento
        if (tipoDoc.isEmpty()) {
            errores += "- Debe seleccionar un tipo de documento.\n";
        } else {
            if (tipoDoc.equals("CC") || tipoDoc.equals("TI")) {
                if (!Validaciones.validarCedula(documento)) {
                    errores += "- Cédula / TI inválida (9 a 11 dígitos).\n";
                }
            } else if (tipoDoc.equals("NIT")) {
                if (!Validaciones.validarNIT(documento)) {
                    errores += "- NIT inválido (ej: 900123456 o 900123456-7).\n";
                }
            } else {
                // Si tipoDoc tiene otro formato y documento vacío
                if (documento.isEmpty()) {
                    errores += "- Debe ingresar el número de documento.\n";
                }
            }
        }

        // Banco
        if (banco.isEmpty()) {
            errores += "- Debe seleccionar un banco.\n";
        }

        // Tipo de cuenta
        if (tipoCuenta.isEmpty()) {
            errores += "- Debe seleccionar un tipo de cuenta.\n";
        }

        // Número de cuenta
        if (numeroCuenta.isEmpty()) {
            errores += "- El número de cuenta no puede estar vacío.\n";
        } else if (!Validaciones.validarNumeroCuenta(numeroCuenta)) {
            errores += "- Número de cuenta inválido (debe iniciar en 4 o 5 y tener 13 a 16 dígitos).\n";
        }

        // Departamento y ciudad
        if (departamento.isEmpty()) {
            errores += "- Debe seleccionar un departamento.\n";
        }
        if (ciudad.isEmpty()) {
            errores += "- Debe seleccionar una ciudad.\n";
        }

        // Dirección (usando tu método)
        if (direccion.isEmpty()) {
            errores += "- La dirección no puede estar vacía.\n";
        } else if (!Validaciones.validarDireccion(direccion)) {
            errores += "- Dirección inválida. Debe contener letras y números (Ej: Calle 45 #12-34).\n";
        }

        // Mostrar todos los errores juntos, si hay
        if (!errores.isEmpty()) {
            JOptionPane.showMessageDialog(dialogoConsignacion, errores,
                    "Errores en el formulario",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
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

    public static void main(String[] args) {
        // Crear un JFrame base (padre del diálogo)
        JFrame frame = new JFrame("Prueba Consignación");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Crear instancia de la ventana de consignación
        double total = 150000; // ejemplo de monto total
        List<Map<Integer, String>> tiposDoc = new ArrayList<>();

        Map<Integer, String> tipo1 = new HashMap<>();
        tipo1.put(1, "Cédula de ciudadanía");

        Map<Integer, String> tipo2 = new HashMap<>();
        tipo2.put(2, "Tarjeta de identidad");

        Map<Integer, String> tipo3 = new HashMap<>();
        tipo3.put(3, "Pasaporte");

        tiposDoc.add(tipo1);
        tiposDoc.add(tipo2);
        tiposDoc.add(tipo3);
        Consignacion consignacion = new Consignacion(frame, total, tiposDoc, tiposDoc);

        // Mostrar el diálogo
        consignacion.dialogoConsignacion.setVisible(true);

        // Ejemplo: Validar los campos al cerrar o presionar botón
        consignacion.btnConsignar.addActionListener(e -> {
            if (consignacion.validarCamposConsignacion()) {
                System.out.println("✅ Validaciones correctas. Se puede procesar la consignación.");
                consignacion.dialogoConsignacion.dispose();
            } else {
                System.out.println("❌ Hay errores en los campos de consignación.");
            }
        });
    }
}
