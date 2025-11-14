package vista.vistaSupervisor;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.io.InputStream;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import modelo.crudMetodoDePago.MetodoPago;
import modelo.crudProducto.Producto;
import vista.componentes.Header;
import vista.vistaSupervisor.componentes.RoundedPanel;

public class PreRegistroProveedor extends JFrame {

    private JComboBox<Producto> cboProductoPersona;
    private JComboBox<Producto> cboProductoEmpresa;
    private JComboBox<MetodoPago> cboPersonaMedioPago;
    private JComboBox<MetodoPago> cboMedioPago;
    private JComboBox<String> tipoEntidad;

    private JButton btnAgregarProPersona, btnAgregarProEmpresa, btnEnviarPersona, btnEnviarEmpresa;

    private JPanel panelTipoEntidad, panelFormu, panelAuxFormu, panelPer, panelCards, panelAuxEmp, panelReEmp;
    private JTextField txtPersonaNumeroDoc, txtPersonaNombre, txtPersonaDireccion,
            txtPersonaTelefono, txtPersonaCorreo,
            txtNumeroDoc, txtNombre, txtDireccion, txtTelefono, txtCorreo,
            txtNit, txtNombreEntidad, txtCorreoEntidad;
    private JLabel lTipoE, lPreRegis;
    // Paneles de formularios
    private JPanel panelPersona;
    private JPanel panelEmpresa;
    private Font customFont;
    private Header header;

    public PreRegistroProveedor() {
        setTitle("Pre Registro Proveedor");
        setSize(1300, 700);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        try {
            InputStream is = PreRegistroProveedor.class.getResourceAsStream("/fonts/newCentury.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, is);
            is.close();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (Exception e) {
            e.printStackTrace();
        }

        header = new Header();
        add(header);

        lPreRegis = new JLabel("PreRegistro de Proveedores");
        lPreRegis.setFont(customFont.deriveFont(Font.PLAIN, 32));
        lPreRegis.setBounds(100, 100, 600, 50);
        add(lPreRegis);
        lTipoE = new JLabel("Tipo de entidad");
        lTipoE.setFont(new Font("Roboto", Font.PLAIN, 24));
        lTipoE.setBounds(30, 40, 300, 30);

        // ComboBox
        tipoEntidad = new JComboBox<>(new String[] { "Persona", "Empresa" });
        tipoEntidad.setFont(customFont.deriveFont(Font.PLAIN, 24));

        tipoEntidad.setBounds(35, 100, 160, 50);

        panelTipoEntidad = new RoundedPanel(20, Color.LIGHT_GRAY);
        panelTipoEntidad.setLayout(null);
        panelTipoEntidad.setBounds(100, 170, 230, 240);
        panelTipoEntidad.setBackground(Color.lightGray);
        panelTipoEntidad.add(lTipoE);
        panelTipoEntidad.add(tipoEntidad);

        if (panelTipoEntidad instanceof RoundedPanel) {
            ((RoundedPanel) panelTipoEntidad).setShadowSize(3);
            ((RoundedPanel) panelTipoEntidad).setShadowColor(new Color(0, 0, 0));
            ((RoundedPanel) panelTipoEntidad).setRoundAllCorners(1);
        }

        add(panelTipoEntidad);

        // Panel contenedor con CardLayout
        panelCards = new JPanel(new CardLayout());
        panelCards.setBounds(getWidth() - 500, 170, 350, 450); // tamaño inicial (persona)

        // Crear formularios
        panelPersona = crearFormularioPersona();
        panelEmpresa = crearFormularioEmpresa();

        // Agregar los paneles al CardLayout
        panelCards.add(panelPersona, "Persona");
        panelCards.add(panelEmpresa, "Empresa");

        add(panelCards);
    }

    // --- Formulario Persona Natural ---
    private JPanel crearFormularioPersona() {
        // Usamos RoundedPanel para bordes redondeados y sombra
        panelFormu = new RoundedPanel(20, Color.lightGray);
        panelFormu.setLayout(null);
        panelFormu.setBackground(Color.LIGHT_GRAY);
        panelFormu.setPreferredSize(new Dimension(350, 450));
        // configuraciones opcionales de sombra y tipo de esquinas
        if (panelFormu instanceof RoundedPanel) {
            ((RoundedPanel) panelFormu).setShadowSize(3);
            ((RoundedPanel) panelFormu).setShadowColor(new Color(0, 0, 0, 90));
            ((RoundedPanel) panelFormu).setRoundAllCorners(1);
        }

        panelPer = new JPanel();
        panelPer.setLayout(new GridLayout(8, 1, 10, 10));

        txtPersonaNumeroDoc = new JTextField(25);
        txtPersonaNumeroDoc.setBorder(new TitledBorder("Número de Documento"));

        txtPersonaNombre = new JTextField(25);
        txtPersonaNombre.setBorder(new TitledBorder("Nombre Completo"));

        cboProductoPersona = new JComboBox<>();
        cboProductoPersona.setBorder(new TitledBorder("Productos"));

        btnAgregarProPersona = new JButton("Agregar");
        btnAgregarProPersona.setPreferredSize(new Dimension(80, 38));
        panelAuxFormu = new JPanel(new GridLayout(1, 2, 10, 0));
        panelAuxFormu.add(cboProductoPersona);
        panelAuxFormu.add(btnAgregarProPersona);
        panelAuxFormu.setOpaque(false);
        panelPer.setOpaque(false);

        cboPersonaMedioPago = new JComboBox<>();
        cboPersonaMedioPago.setBorder(new TitledBorder("Medio de Pago"));

        txtPersonaDireccion = new JTextField(25);
        txtPersonaDireccion.setBorder(new TitledBorder("Direccion"));

        txtPersonaTelefono = new JTextField(25);
        txtPersonaTelefono.setBorder(new TitledBorder("Teléfono"));

        txtPersonaCorreo = new JTextField(25);
        txtPersonaCorreo.setBorder(new TitledBorder("Correo Electronico"));

        btnEnviarPersona = new JButton("Enviar");

        panelPer.add(txtPersonaNumeroDoc);
        panelPer.add(txtPersonaNombre);
        panelPer.add(panelAuxFormu);
        panelPer.add(cboPersonaMedioPago);
        panelPer.add(txtPersonaDireccion);
        panelPer.add(txtPersonaTelefono);
        panelPer.add(txtPersonaCorreo);

        // Configurar los bounds y agregar componentes
        panelPer.setBounds(25, 25, 300, 400);
        panelFormu.add(panelPer);

        btnEnviarPersona.setBounds(100, 390, 150, 35);
        panelFormu.add(btnEnviarPersona);

        return panelFormu;
    }

    // --- Formulario Empresa ---
    private JPanel crearFormularioEmpresa() {

        panelFormu = new RoundedPanel(20, Color.lightGray);
        panelFormu.setLayout(null);
        panelFormu.setBackground(Color.LIGHT_GRAY);
        panelFormu.setPreferredSize(new Dimension(400, 450));
        // configuraciones opcionales de sombra y tipo de esquinas
        if (panelFormu instanceof RoundedPanel) {
            ((RoundedPanel) panelFormu).setShadowSize(3);
            ((RoundedPanel) panelFormu).setShadowColor(new Color(0, 0, 0, 90));
            ((RoundedPanel) panelFormu).setRoundAllCorners(1);
        }

        btnEnviarEmpresa = new JButton("Enviar");

        panelAuxEmp = new JPanel();
        panelAuxEmp.setLayout(new GridLayout(4, 2, 10, 10));
        panelAuxEmp.setOpaque(false);

        // Campos de la entidad
        txtNit = new JTextField(15);
        txtNit.setBorder(new TitledBorder("NIT"));
        txtNombreEntidad = new JTextField(15);
        txtNombreEntidad.setBorder(new TitledBorder("Nombre Entidad"));
        cboProductoEmpresa = new JComboBox<>();
        cboProductoEmpresa.setBorder(new TitledBorder("Productos"));

        btnAgregarProEmpresa = new JButton("Agregar");
        btnAgregarProEmpresa.setPreferredSize(new Dimension(120, 38));
        cboMedioPago = new JComboBox<>();
        cboMedioPago.setBorder(new TitledBorder("Medio de Pago"));
        txtDireccion = new JTextField(15);
        txtDireccion.setBorder(new TitledBorder("Dirección Entidad"));
        txtCorreoEntidad = new JTextField(15);
        txtCorreoEntidad.setBorder(new TitledBorder("Correo Entidad"));

        panelAuxEmp.add(txtNit);
        panelAuxEmp.add(txtNombreEntidad);
        panelAuxEmp.add(cboProductoEmpresa);
        panelAuxEmp.add(btnAgregarProEmpresa);
        panelAuxEmp.add(cboMedioPago);
        panelAuxEmp.add(txtDireccion);
        panelAuxEmp.add(txtCorreoEntidad);

        panelAuxEmp.setBounds(25, 30, 400, 200);

        panelReEmp = new JPanel();
        panelReEmp.setLayout(new GridLayout(2, 2, 10, 10));
        panelReEmp.setOpaque(false);
        // datos representante
        JLabel lDatosRepre = new JLabel("Datos del representante:");
        lDatosRepre.setFont(customFont.deriveFont(Font.PLAIN, 20));
        lDatosRepre.setBounds(25, 240, 400, 30);

        txtNumeroDoc = new JTextField(15);
        txtNumeroDoc.setBorder(new TitledBorder("Número de Documento"));
        txtNombre = new JTextField(15);
        txtNombre.setBorder(new TitledBorder("Nombre Completo"));
        txtTelefono = new JTextField(15);
        txtTelefono.setBorder(new TitledBorder("Teléfono"));
        txtCorreo = new JTextField(15);
        txtCorreo.setBorder(new TitledBorder("Correo Electronico"));

        panelFormu.add(panelAuxEmp);

        panelFormu.add(lDatosRepre);
        panelReEmp.add(txtNumeroDoc);
        panelReEmp.add(txtNombre);
        panelReEmp.add(txtTelefono);
        panelReEmp.add(txtCorreo);

        panelReEmp.setBounds(25, 275, 400, 90);

        panelFormu.add(panelReEmp);
        btnEnviarEmpresa.setBounds(150, 385, 150, 35);
        panelFormu.add(btnEnviarEmpresa);

        return panelFormu;
    }

    // Getters para Controlador

    public JPanel getPanelCards() {
        return panelCards;
    }

    public JPanel getPanelPersona() {
        return panelPersona;
    }

    public JPanel getPanelEmpresa() {
        return panelEmpresa;
    }

    // Getters para campos del formulario Persona (usados por el controlador)
    public String getPersonaNumeroDoc() {
        return txtPersonaNumeroDoc != null ? txtPersonaNumeroDoc.getText().trim() : "";
    }

    public String getPersonaNombre() {
        return txtPersonaNombre != null ? txtPersonaNombre.getText().trim() : "";
    }

    public int getPersonaMedioDePago() {
        MetodoPago selectedMetodo = (MetodoPago) cboPersonaMedioPago.getSelectedItem();
        return selectedMetodo != null ? selectedMetodo.getId() : 0;
    }

    public String getPersonaDireccion() {
        return txtPersonaDireccion != null ? txtPersonaDireccion.getText().trim() : "";
    }

    public String getPersonaTelefono() {
        return txtPersonaTelefono != null ? txtPersonaTelefono.getText().trim() : "";
    }

    public String getPersonaCorreo() {
        return txtPersonaCorreo != null ? txtPersonaCorreo.getText().trim() : "";
    }

    // Getters para formulario Empresa
    public String getEmpresaNit() {
        return txtNit != null ? txtNit.getText().trim() : "";
    }

    public String getEmpresaNombreEntidad() {
        return txtNombreEntidad != null ? txtNombreEntidad.getText().trim() : "";
    }

    public int getEmpresaMedioDePago() {
        MetodoPago selectedMetodo = (MetodoPago) cboMedioPago.getSelectedItem();
        return selectedMetodo != null ? selectedMetodo.getId() : 0;
    }

    public String getEmpresaDireccion() {
        return txtDireccion != null ? txtDireccion.getText().trim() : "";
    }

    public String getEmpresaTelefono() {
        return txtTelefono != null ? txtTelefono.getText().trim() : "";
    }

    public String getEmpresaCorreo() {
        return txtCorreoEntidad != null ? txtCorreoEntidad.getText().trim() : "";
    }

    // Representante
    public String getRepresentanteNumeroDoc() {
        return txtNumeroDoc != null ? txtNumeroDoc.getText().trim() : "";
    }

    public String getRepresentanteNombre() {
        return txtNombre != null ? txtNombre.getText().trim() : "";
    }

    public String getRepresentanteTelefono() {
        return txtTelefono != null ? txtTelefono.getText().trim() : "";
    }

    public String getRepresentanteCorreo() {
        return txtCorreo != null ? txtCorreo.getText().trim() : "";
    }

    public Producto getProductoSeleccionado() {
        // Si el panel persona está visible, tomar de su combo; si no, tomar del de
        // empresa
        try {
            if (panelPersona != null && panelPersona.isShowing() && cboProductoPersona != null) {
                return (Producto) cboProductoPersona.getSelectedItem();
            }
        } catch (Exception ex) {
            // ignore and try empresa
        }

        try {
            if (panelEmpresa != null && panelEmpresa.isShowing() && cboProductoEmpresa != null) {
                return (Producto) cboProductoEmpresa.getSelectedItem();
            }
        } catch (Exception ex) {
            // ignore
        }

        if (cboProductoPersona != null && cboProductoPersona.getItemCount() > 0) {
            return (Producto) cboProductoPersona.getSelectedItem();
        }
        if (cboProductoEmpresa != null && cboProductoEmpresa.getItemCount() > 0) {
            return (Producto) cboProductoEmpresa.getSelectedItem();
        }
        return null;
    }

    public JComboBox<Producto> getCboProductoPersona() {
        return cboProductoPersona;
    }

    public JComboBox<Producto> getCboProductoEmpresa() {
        return cboProductoEmpresa;
    }

    public JComboBox<MetodoPago> getCboPersonaMedioPago() {
        return cboPersonaMedioPago;
    }

    public JComboBox<MetodoPago> getCboMedioPago() {
        return cboMedioPago;
    }

    public JComboBox<String> getTipoEntidad() {
        return tipoEntidad;
    }

    public JButton getBtnAgregarProPersona() {
        return btnAgregarProPersona;
    }

    public JButton getBtnAgregarProEmpresa() {
        return btnAgregarProEmpresa;
    }

    public JButton getBtnEnviarPersona() {
        return btnEnviarPersona;
    }

    public JButton getBtnEnviarEmpresa() {
        return btnEnviarEmpresa;
    }
}
