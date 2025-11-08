package vista.vistaCliente.pasarelaVista;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.time.Year;
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

public class Tarjetas{
    public JDialog dialogoTarjeta;
    public JTextField txtTarjeta, txtCVV, txtNombreTarjeta, txtDireccion;
    public JComboBox<String> cbMes, cbAnio, cbDepartamento, cbCiudad;
    public JButton btnFinalizar;
    public String tipoTarjeta;//debito o credito
    public CiudadesDepartamentosColombia ciudadesDepartamentosColombia;
    ColorAzulClaro colorAzulClaro = new ColorAzulClaro();
    Color colorFondoAzulClaro= colorAzulClaro.colorFondoAzulClaro;
    CamposDeLasTarjetas campos = new CamposDeLasTarjetas();


    public Tarjetas(JFrame frame, String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
        
        dialogoTarjeta = new JDialog(frame, "Tarjeta"+tipoTarjeta, true);
        dialogoTarjeta.setLayout(null);
        dialogoTarjeta.setBounds(250, 250, 470, 400);
        dialogoTarjeta.getContentPane().setBackground(Color.WHITE);
        
        // Panel principal donde va todo
        JPanel panelTarjeta = new JPanel();
        panelTarjeta.setLayout(null);
        panelTarjeta.setBounds(0, 40, 470, 250);
        panelTarjeta.setBackground(colorFondoAzulClaro);
        dialogoTarjeta.add(panelTarjeta);
        //titulo
        JLabel lblTitulo = new JLabel("Tarjeta "+tipoTarjeta);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBounds(20, 10, 300, 25);
        dialogoTarjeta.add(lblTitulo);
       
        txtTarjeta = campos.agregarCampoNumeroTarjeta(panelTarjeta, 20, 10);

        // Fecha de vencimiento
        cbMes = campos.agregarComboMes(panelTarjeta, 20, 50);
        cbAnio = campos.agregarComboAnio(panelTarjeta, 300, 50, Year.now().getValue());

        // CVV
        txtCVV = campos.agregarCampoCVV(panelTarjeta, 20, 90);
        txtCVV.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || txtCVV.getText().length() >= 4) e.consume();
            }
        });

        // Nombre del titular
        txtNombreTarjeta = campos.agregarCampoNombreTitular(panelTarjeta, 20, 130);

        // Departamento y ciudad
        ciudadesDepartamentosColombia = new CiudadesDepartamentosColombia();
        JLabel lblDepCiudad = new JLabel("Departamento, Ciudad");
        lblDepCiudad.setBounds(20, 170, 180, 20);
        panelTarjeta.add(lblDepCiudad);

        cbDepartamento = new JComboBox<>();
        ciudadesDepartamentosColombia.cargarDepartamentosEnCombo(cbDepartamento);
        cbDepartamento.setBounds(220, 170, 100, 25);
        panelTarjeta.add(cbDepartamento);

        cbCiudad = new JComboBox<>();
        cbCiudad.setBounds(325, 170, 100, 25);
        panelTarjeta.add(cbCiudad);

        // Evento de cambio de departamento
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
        txtDireccion = campos.agregarCampoDireccion(panelTarjeta, 20, 210);
        
        //finalizar boton
        btnFinalizar = new JButton("Finalizar Compra");
        btnFinalizar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        btnFinalizar.setBackground(colorFondoAzulClaro);
        btnFinalizar.setForeground(Color.BLACK);
        btnFinalizar.setFont(new Font("Arial", Font.PLAIN, 15));
        btnFinalizar.setBounds(155, 300, 150, 40);
        dialogoTarjeta.add(btnFinalizar);
    }

    public String getTipoTarjeta() {return tipoTarjeta;}

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }
    
    public boolean validarCamposTarjeta(){
        boolean resultado = true;
        String numeroTarjeta = getTxtTarjeta().getText().trim();
        String cvv = getTxtCVV().getText().trim();
        String nombreTitular = getTxtNombreTarjeta().getText().trim();
        String direccion = getTxtDireccion().getText().trim();

        String departamento = "";
        if (getCbDepartamento().getSelectedItem() != null) {
            departamento = getCbDepartamento().getSelectedItem().toString();
        }

        String ciudad = "";
        if (getCbCiudad().getSelectedItem() != null) {
            ciudad = getCbCiudad().getSelectedItem().toString();
        }

        String mes = "";
        if (getCbMes().getSelectedItem() != null) {
            mes = getCbMes().getSelectedItem().toString();
        }

        String anio = "";
        if (getCbAnio().getSelectedItem() != null) {
            anio = getCbAnio().getSelectedItem().toString();
        }

        if (!Validaciones.validarNumeroCuenta(numeroTarjeta)) {
            JOptionPane.showMessageDialog(null, "Número de tarjeta inválido");
            resultado = false;
        }

        if (!Validaciones.validarCVV(cvv)) {
            JOptionPane.showMessageDialog(null, "CVV inválido (3 o 4 dígitos)");
            resultado = false;
        }

        if (!Validaciones.validarLetras(nombreTitular)) {
            JOptionPane.showMessageDialog(null, "El nombre solo debe contener letras");
            resultado = false;
        }

        if (departamento.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un departamento");
            resultado = false;
        }

        if (ciudad.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar una ciudad");
            resultado = false;
        }

        if (direccion.isEmpty()) {
            JOptionPane.showMessageDialog(null, "La dirección no puede estar vacía");
            resultado = false;
        }

        if (mes.isEmpty() || anio.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar mes y año");
            resultado = false;
        }
        return resultado;
    }
    
    public JDialog getDialogotarjeta() { return dialogoTarjeta; }

    public JTextField getTxtTarjeta() {
        return txtTarjeta;
    }

    public void setTxtTarjeta(JTextField txtTarjeta) {
        this.txtTarjeta = txtTarjeta;
    }

    public JTextField getTxtCVV() {
        return txtCVV;
    }

    public void setTxtCVV(JTextField txtCVV) {
        this.txtCVV = txtCVV;
    }

    public JTextField getTxtNombreTarjeta() {
        return txtNombreTarjeta;
    }

    public void setTxtNombreTarjeta(JTextField txtNombreTarjeta) {
        this.txtNombreTarjeta = txtNombreTarjeta;
    }

    public JTextField getTxtDireccion() {
        return txtDireccion;
    }

    public void setTxtDireccion(JTextField txtDireccion) {
        this.txtDireccion = txtDireccion;
    }

    public JComboBox<String> getCbMes() {
        return cbMes;
    }

    public void setCbMes(JComboBox<String> cbMes) {
        this.cbMes = cbMes;
    }

    public JComboBox<String> getCbAnio() {
        return cbAnio;
    }

    public void setCbAnio(JComboBox<String> cbAnio) {
        this.cbAnio = cbAnio;
    }

    public JComboBox<String> getCbDepartamento() {
        return cbDepartamento;
    }

    public void setCbDepartamento(JComboBox<String> cbDepartamento) {
        this.cbDepartamento = cbDepartamento;
    }

    public JComboBox<String> getCbCiudad() {
        return cbCiudad;
    }

    public void setCbCiudad(JComboBox<String> cbCiudad) {
        this.cbCiudad = cbCiudad;
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

        // Acción del botón
        btnAbrirTarjeta.addActionListener(e -> {
            Tarjetas tarjeta = new Tarjetas(frame,"Crédito");
            tarjeta.dialogoTarjeta.setVisible(true);
        });

        // Mostrar el frame
        frame.setLocationRelativeTo(null); // Centrar en pantalla
        frame.setVisible(true);
    
    }
}