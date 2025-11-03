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

import vista.vistaCliente.Validaciones;

public class Tarjetas {
    public JDialog dialogoTarjeta;
    public JTextField txtTarjeta, txtCVV, txtNombreTarjeta, txtDireccion;
    public JComboBox<String> cbMes, cbAnio, cbDepartamento, cbCiudad;
    public JButton btnFinalizar;
    public String tipoTarjeta;// debito o credito
    ColorAzulClaro colorAzulClaro = new ColorAzulClaro();
    Color colorFondoAzulClaro = colorAzulClaro.colorFondoAzulClaro;

    public Tarjetas(JFrame frame, String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
        dialogoTarjeta = new JDialog(frame, "Tarjeta" + tipoTarjeta, true);
        dialogoTarjeta.setLayout(null);
        dialogoTarjeta.setBounds(250, 250, 470, 400);
        dialogoTarjeta.getContentPane().setBackground(Color.WHITE);

        // Panel principal donde va todo
        JPanel panelTarjeta = new JPanel();
        panelTarjeta.setLayout(null);
        panelTarjeta.setBounds(0, 40, 470, 250);
        panelTarjeta.setBackground(colorFondoAzulClaro);
        dialogoTarjeta.add(panelTarjeta);
        // titulo
        JLabel lblTitulo = new JLabel("Tarjeta " + tipoTarjeta);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBounds(20, 10, 300, 25);
        dialogoTarjeta.add(lblTitulo);
        // documento
        JLabel lblTarjeta = new JLabel("Número de tarjeta*");
        lblTarjeta.setBounds(20, 10, 160, 20);
        panelTarjeta.add(lblTarjeta);//
        txtTarjeta = new JTextField();
        txtTarjeta.setBounds(190, 10, 160, 25);
        txtTarjeta.setBorder(null);
        panelTarjeta.add(txtTarjeta);
        // fecha
        JLabel lblFecha = new JLabel("Fecha Vencimiento (MM/AA)*");
        lblFecha.setBounds(20, 50, 170, 20);
        panelTarjeta.add(lblFecha);
        // mes
        cbMes = new JComboBox<>();
        for (int i = 1; i <= 12; i++) {
            cbMes.addItem(i + "");
        }
        cbMes.setBounds(190, 50, 60, 25);
        panelTarjeta.add(cbMes);
        // año
        cbAnio = new JComboBox<>();
        int anioActual = Year.now().getValue();
        for (int i = 0; i < 10; i++) {
            cbAnio.addItem(String.valueOf(anioActual + i));
        }
        ;
        cbAnio.setBounds(250, 50, 90, 25);
        panelTarjeta.add(cbAnio);
        // cvv
        JLabel lblCVV = new JLabel("Código de Seguridad*");
        lblCVV.setBounds(20, 90, 160, 20);
        panelTarjeta.add(lblCVV);//
        txtCVV = new JTextField();
        txtCVV.setBounds(190, 90, 60, 25);
        txtCVV.setBorder(null);
        panelTarjeta.add(txtCVV);
        // e
        txtCVV.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || txtCVV.getText().length() >= 4)
                    e.consume();
            }
        });
        // titular
        JLabel lblNombre = new JLabel("Nombre del titular*");
        lblNombre.setBounds(20, 130, 160, 20);
        panelTarjeta.add(lblNombre);//
        txtNombreTarjeta = new JTextField();
        txtNombreTarjeta.setBounds(190, 130, 160, 25);
        txtNombreTarjeta.setBorder(null);
        panelTarjeta.add(txtNombreTarjeta);
        // departamento
        JLabel lblDepartamento = new JLabel("Departamento");
        lblDepartamento.setBounds(20, 170, 120, 20);
        panelTarjeta.add(lblDepartamento);//
        cbDepartamento = new JComboBox<>();
        cbDepartamento.addItem("");
        cbDepartamento.addItem("Amazonas");
        cbDepartamento.addItem("Antioquia");
        cbDepartamento.addItem("Arauca");
        cbDepartamento.addItem("Atlántico");
        cbDepartamento.addItem("Bolívar");
        cbDepartamento.addItem("Boyacá");
        cbDepartamento.addItem("Caldas");
        cbDepartamento.addItem("Caquetá");
        cbDepartamento.addItem("Casanare");
        cbDepartamento.addItem("Cauca");
        cbDepartamento.addItem("Cesar");
        cbDepartamento.addItem("Chocó");
        cbDepartamento.addItem("Córdoba");
        cbDepartamento.addItem("Cundinamarca");
        cbDepartamento.addItem("Guainía");
        cbDepartamento.addItem("Guaviare");
        cbDepartamento.addItem("Huila");
        cbDepartamento.addItem("La Guajira");
        cbDepartamento.addItem("Magdalena");
        cbDepartamento.addItem("Meta");
        cbDepartamento.addItem("Nariño");
        cbDepartamento.addItem("Norte de Santander");
        cbDepartamento.addItem("Putumayo");
        cbDepartamento.addItem("Quindío");
        cbDepartamento.addItem("Risaralda");
        cbDepartamento.addItem("San Andrés y Providencia");
        cbDepartamento.addItem("Santander");
        cbDepartamento.addItem("Sucre");
        cbDepartamento.addItem("Tolima");
        cbDepartamento.addItem("Valle del Cauca");
        cbDepartamento.addItem("Vaupés");
        cbDepartamento.addItem("Vichada");
        cbDepartamento.setBounds(190, 170, 100, 25);
        panelTarjeta.add(cbDepartamento);
        // ciudad
        JLabel lblCiudad = new JLabel("Ciudad");
        lblCiudad.setBounds(300, 170, 50, 20);
        panelTarjeta.add(lblCiudad);//
        cbCiudad = new JComboBox<>();
        cbDepartamento.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String depSeleccionado = cbDepartamento.getSelectedItem().toString();
                cargarCiudadesPorDepartamento(depSeleccionado);
            }
        });
        cbCiudad.setBounds(350, 170, 90, 25);
        panelTarjeta.add(cbCiudad);
        // direccion
        JLabel lblDireccion = new JLabel("Dirección de Residencia*");
        lblDireccion.setBounds(20, 210, 180, 20);
        panelTarjeta.add(lblDireccion);//
        txtDireccion = new JTextField();
        txtDireccion.setBorder(null);
        txtDireccion.setBounds(190, 210, 210, 25);
        panelTarjeta.add(txtDireccion);
        // finalizar boton
        btnFinalizar = new JButton("Finalizar Compra");
        btnFinalizar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        btnFinalizar.setBackground(colorFondoAzulClaro);
        btnFinalizar.setForeground(Color.BLACK);
        btnFinalizar.setFont(new Font("Arial", Font.PLAIN, 15));
        btnFinalizar.setBounds(155, 300, 150, 40);
        dialogoTarjeta.add(btnFinalizar);
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public void cargarCiudadesPorDepartamento(String departamento) {
        cbCiudad.removeAllItems(); // Limpia el combo antes de agregar

        switch (departamento) {
            case "Amazonas":
                cbCiudad.addItem("Leticia");
                cbCiudad.addItem("Puerto Nariño");
                break;

            case "Antioquia":
                cbCiudad.addItem("Medellín");
                cbCiudad.addItem("Bello");
                cbCiudad.addItem("Itagüí");
                cbCiudad.addItem("Envigado");
                cbCiudad.addItem("Rionegro");
                break;

            case "Arauca":
                cbCiudad.addItem("Arauca");
                cbCiudad.addItem("Saravena");
                cbCiudad.addItem("Tame");
                cbCiudad.addItem("Arauquita");
                break;

            case "Atlántico":
                cbCiudad.addItem("Barranquilla");
                cbCiudad.addItem("Soledad");
                cbCiudad.addItem("Malambo");
                cbCiudad.addItem("Sabanalarga");
                cbCiudad.addItem("Puerto Colombia");
                break;

            case "Bolívar":
                cbCiudad.addItem("Cartagena");
                cbCiudad.addItem("Magangué");
                cbCiudad.addItem("Turbaco");
                cbCiudad.addItem("Arjona");
                cbCiudad.addItem("El Carmen de Bolívar");
                break;

            case "Boyacá":
                cbCiudad.addItem("Tunja");
                cbCiudad.addItem("Duitama");
                cbCiudad.addItem("Sogamoso");
                cbCiudad.addItem("Chiquinquirá");
                cbCiudad.addItem("Paipa");
                break;
            case "Caldas":
                cbCiudad.addItem("Manizales");
                cbCiudad.addItem("Villamaría");
                cbCiudad.addItem("Chinchiná");
                cbCiudad.addItem("La Dorada");
                cbCiudad.addItem("Neira");
                break;

            case "Caquetá":
                cbCiudad.addItem("Florencia");
                cbCiudad.addItem("Belén de los Andaquíes");
                cbCiudad.addItem("San Vicente del Caguán");
                cbCiudad.addItem("Puerto Rico");
                cbCiudad.addItem("Cartagena del Chairá");
                break;

            case "Casanare":
                cbCiudad.addItem("Yopal");
                cbCiudad.addItem("Aguazul");
                cbCiudad.addItem("Villanueva");
                cbCiudad.addItem("Tauramena");
                cbCiudad.addItem("Monterrey");
                break;

            case "Cauca":
                cbCiudad.addItem("Popayán");
                cbCiudad.addItem("Santander de Quilichao");
                cbCiudad.addItem("Puerto Tejada");
                cbCiudad.addItem("El Tambo");
                cbCiudad.addItem("Patía");
                break;

            case "Cesar":
                cbCiudad.addItem("Valledupar");
                cbCiudad.addItem("Aguachica");
                cbCiudad.addItem("La Jagua de Ibirico");
                cbCiudad.addItem("Bosconia");
                cbCiudad.addItem("Curumaní");
                break;

            case "Chocó":
                cbCiudad.addItem("Quibdó");
                cbCiudad.addItem("Istmina");
                cbCiudad.addItem("Tadó");
                cbCiudad.addItem("Condoto");
                cbCiudad.addItem("Bahía Solano");
                break;
            case "Córdoba":
                cbCiudad.addItem("Montería");
                cbCiudad.addItem("Lorica");
                cbCiudad.addItem("Sahagún");
                cbCiudad.addItem("Cereté");
                cbCiudad.addItem("Tierralta");
                break;

            case "Cundinamarca":
                cbCiudad.addItem("Bogotá D.C.");
                cbCiudad.addItem("Soacha");
                cbCiudad.addItem("Zipaquirá");
                cbCiudad.addItem("Facatativá");
                cbCiudad.addItem("Chía");
                break;

            case "Guainía":
                cbCiudad.addItem("Inírida");
                cbCiudad.addItem("Barranco Minas");
                cbCiudad.addItem("Mapiripana");
                cbCiudad.addItem("Cacahual");
                cbCiudad.addItem("Pana Pana");
                break;

            case "Guaviare":
                cbCiudad.addItem("San José del Guaviare");
                cbCiudad.addItem("Calamar");
                cbCiudad.addItem("El Retorno");
                cbCiudad.addItem("Miraflores");
                break;

            case "Huila":
                cbCiudad.addItem("Neiva");
                cbCiudad.addItem("Pitalito");
                cbCiudad.addItem("Garzón");
                cbCiudad.addItem("La Plata");
                cbCiudad.addItem("Campoalegre");
                break;

            case "La Guajira":
                cbCiudad.addItem("Riohacha");
                cbCiudad.addItem("Maicao");
                cbCiudad.addItem("Uribia");
                cbCiudad.addItem("Fonseca");
                cbCiudad.addItem("San Juan del Cesar");
                break;
            case "Magdalena":
                cbCiudad.addItem("Santa Marta");
                cbCiudad.addItem("Ciénaga");
                cbCiudad.addItem("Fundación");
                cbCiudad.addItem("Aracataca");
                cbCiudad.addItem("El Banco");
                break;

            case "Meta":
                cbCiudad.addItem("Villavicencio");
                cbCiudad.addItem("Acacías");
                cbCiudad.addItem("Granada");
                cbCiudad.addItem("Puerto López");
                cbCiudad.addItem("Restrepo");
                break;

            case "Nariño":
                cbCiudad.addItem("Pasto");
                cbCiudad.addItem("Tumaco");
                cbCiudad.addItem("Ipiales");
                cbCiudad.addItem("Túquerres");
                cbCiudad.addItem("La Unión");
                break;

            case "Norte de Santander":
                cbCiudad.addItem("Cúcuta");
                cbCiudad.addItem("Ocaña");
                cbCiudad.addItem("Pamplona");
                cbCiudad.addItem("Villa del Rosario");
                cbCiudad.addItem("Los Patios");
                break;

            case "Putumayo":
                cbCiudad.addItem("Mocoa");
                cbCiudad.addItem("Puerto Asís");
                cbCiudad.addItem("Villagarzón");
                cbCiudad.addItem("Orito");
                cbCiudad.addItem("Sibundoy");
                break;

            case "Quindío":
                cbCiudad.addItem("Armenia");
                cbCiudad.addItem("Calarcá");
                cbCiudad.addItem("Montenegro");
                cbCiudad.addItem("Quimbaya");
                cbCiudad.addItem("La Tebaida");
                break;
            case "Risaralda":
                cbCiudad.addItem("Pereira");
                cbCiudad.addItem("Dosquebradas");
                cbCiudad.addItem("Santa Rosa de Cabal");
                cbCiudad.addItem("La Virginia");
                cbCiudad.addItem("Belén de Umbría");
                break;

            case "San Andrés y Providencia":
                cbCiudad.addItem("San Andrés");
                cbCiudad.addItem("Providencia");
                cbCiudad.addItem("Santa Catalina");
                break;

            case "Santander":
                cbCiudad.addItem("Bucaramanga");
                cbCiudad.addItem("Floridablanca");
                cbCiudad.addItem("Giron");
                cbCiudad.addItem("Piedecuesta");
                cbCiudad.addItem("Barrancabermeja");
                break;

            case "Sucre":
                cbCiudad.addItem("Sincelejo");
                cbCiudad.addItem("Corozal");
                cbCiudad.addItem("Sampués");
                cbCiudad.addItem("San Marcos");
                cbCiudad.addItem("Tolú");
                break;

            case "Tolima":
                cbCiudad.addItem("Ibagué");
                cbCiudad.addItem("Espinal");
                cbCiudad.addItem("Melgar");
                cbCiudad.addItem("Honda");
                cbCiudad.addItem("Lérida");
                break;

            case "Valle del Cauca":
                cbCiudad.addItem("Cali");
                cbCiudad.addItem("Palmira");
                cbCiudad.addItem("Buenaventura");
                cbCiudad.addItem("Tuluá");
                cbCiudad.addItem("Buga");
                break;

            case "Vaupés":
                cbCiudad.addItem("Mitú");
                cbCiudad.addItem("Carurú");
                cbCiudad.addItem("Taraira");
                cbCiudad.addItem("Pacoa");
                cbCiudad.addItem("Papunaua");
                break;

            case "Vichada":
                cbCiudad.addItem("Puerto Carreño");
                cbCiudad.addItem("La Primavera");
                cbCiudad.addItem("Santa Rosalía");
                cbCiudad.addItem("Cumaribo");
                break;

            default:
                cbCiudad.addItem("Seleccione un departamento válido");
                break;
        }
    }

    public boolean validarCamposTarjeta() {
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

    public JDialog getDialogotarjeta() {
        return dialogoTarjeta;
    }

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
            Tarjetas tarjeta = new Tarjetas(frame, "Crédito");
            tarjeta.dialogoTarjeta.setVisible(true);
        });

        // Mostrar el frame
        frame.setLocationRelativeTo(null); // Centrar en pantalla
        frame.setVisible(true);

    }
}