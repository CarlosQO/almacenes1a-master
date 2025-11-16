package controladorSupervisor;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import modelo.crudMetodoDePago.MetodoPago;
import modelo.crudMetodoDePago.MetodoPagoDao;
import modelo.crudProducto.Producto;
import modelo.crudProducto.ProductoDao;
import modelo.crudProveedor.Proveedor;
import modelo.crudProveedor.ProveedorDao;
import modelo.crudProveedorEmpresa.ProveedorEmpresa;
import javax.swing.JList;
import modelo.crudProveedorEmpresa.ProveedorEmpresaDao;
import vista.componentes.Validaciones;
import vista.vistaSupervisor.PreRegistroProveedor;

public class ControladorProve implements ActionListener {
    public ProveedorDao pdao = new ProveedorDao();
    public Proveedor p = new Proveedor();
    public PreRegistroProveedor vista = new PreRegistroProveedor();
    private ProveedorEmpresaDao empresaDao = new ProveedorEmpresaDao();
    private int currentProviderId;
    private Producto productoSeleccionado;

    public ControladorProve(PreRegistroProveedor vista) {
        this.vista = vista;
        this.currentProviderId = 0;
        this.productoSeleccionado = null;

        // === Registrar listeners ===
        vista.getTipoEntidad().addActionListener(this);
        vista.getBtnEnviarPersona().addActionListener(this);
        vista.getBtnEnviarEmpresa().addActionListener(this);
        try {
            if (vista.getBtnAgregarProPersona() != null)
                vista.getBtnAgregarProPersona().addActionListener(this);
        } catch (Exception ex) {
            // ignore if not present
        }
        try {
            if (vista.getBtnAgregarProEmpresa() != null)
                vista.getBtnAgregarProEmpresa().addActionListener(this);
        } catch (Exception ex) {
        }

        inicializarCombos();
    }

    public ControladorProve() {
    }

    private void inicializarCombos() {
        try {
            cargarProductosSinProveedor(vista.getCboProductoPersona());
            configurarRendererProducto(vista.getCboProductoPersona());

            cargarProductosSinProveedor(vista.getCboProductoEmpresa());
            configurarRendererProducto(vista.getCboProductoEmpresa());

            cargarMetodosPago(vista.getCboPersonaMedioPago());
            cargarMetodosPago(vista.getCboMedioPago());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al cargar datos iniciales: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarProductosSinProveedor(JComboBox<Producto> comboBox) {
        ProductoDao productoDao = new ProductoDao();
        comboBox.removeAllItems();
        List<Producto> productos = productoDao.listarProductosSinProveedor();
        for (Producto producto : productos) {
            comboBox.addItem(producto);
        }
        if (comboBox.getItemCount() > 0)
            comboBox.setSelectedIndex(0);
    }

    private void cargarMetodosPago(JComboBox<MetodoPago> comboBox) {
        MetodoPagoDao metodoDao = new MetodoPagoDao();
        comboBox.removeAllItems();
        List<MetodoPago> metodos = metodoDao.listarMetodosPago();
        for (MetodoPago metodo : metodos) {
            comboBox.addItem(metodo);
        }
        if (comboBox.getItemCount() > 0)
            comboBox.setSelectedIndex(0);
    }

    private void configurarRendererProducto(JComboBox<Producto> comboBox) {
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Producto) {
                    Producto producto = (Producto) value;
                    setText(producto.getNombre());
                }
                return this;
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getTipoEntidad()) {
            CardLayout cl = (CardLayout) vista.getPanelCards().getLayout();
            String seleccion = vista.getTipoEntidad().getSelectedItem().toString();
            cl.show(vista.getPanelCards(), seleccion);

            // Ajustar tamaño dinámicamente según el formulario
            if (seleccion.equals("Persona")) {
                vista.getPanelCards().setBounds(vista.getWidth() - 500, 170, 350, 450);
            } else {
                vista.getPanelCards().setBounds(vista.getWidth() - 550, 170, 450, 450);
            }

            vista.revalidate();
            vista.repaint();
        } else if (e.getSource() == vista.getBtnEnviarPersona()) {
            insertarProveedorPersona();
        } else if (e.getSource() == vista.getBtnEnviarEmpresa()) {
            insertarProveedorEmpresa();
        } else if (e.getSource() == vista.getBtnAgregarProPersona()
                || e.getSource() == vista.getBtnAgregarProEmpresa()) {
            guardarProductoSeleccionado();
        }
    }

    private void guardarProductoSeleccionado() {
        try {
            Object selected = vista.getProductoSeleccionado();
            if (selected != null && selected instanceof Producto) {
                this.productoSeleccionado = (Producto) selected;
                JOptionPane.showMessageDialog(vista,
                        "Producto '" + productoSeleccionado.getNombre() + "' seleccionado.\n" +
                                "Se asignará automáticamente al registrar el proveedor.",
                        "Producto Seleccionado",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(vista,
                        "Por favor seleccione un producto de la lista",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista,
                    "Error al seleccionar el producto: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validarYRegistrarProveedor(Proveedor nuevo) {
        if (productoSeleccionado != null) {
            nuevo.setIdProducto(productoSeleccionado.getId());
        }

        if (nuevo.getDocumento().isEmpty() || nuevo.getNombre().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe completar el número de documento y el nombre.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (nuevo.getMetodoDePago() == 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un método de pago.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (pdao.existeProveedorPorNit(nuevo.getDocumento())) {
            JOptionPane.showMessageDialog(null, "Ya existe un proveedor registrado con este número de documento.",
                    "Error de Duplicidad", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idInsert = pdao.setAgregar(nuevo);

        if (idInsert > 0) {
            this.currentProviderId = idInsert;
            JOptionPane.showMessageDialog(null, "Proveedor agregado correctamente.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            if (productoSeleccionado != null) {
                cargarProductosSinProveedor(vista.getCboProductoEmpresa());
                cargarProductosSinProveedor(vista.getCboProductoPersona());
            }
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo agregar el proveedor. Revise la conexión o los datos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void insertarProveedorEmpresa() {
        if (vista == null) {
            JOptionPane.showMessageDialog(null, "Vista no inicializada", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nit = vista.getEmpresaNit();
        String nombreEntidad = vista.getEmpresaNombreEntidad();
        int medioPago = vista.getEmpresaMedioDePago();
        String direccion = vista.getEmpresaDireccion();
        String telefonoEntidad = vista.getEmpresaTelefono();
        String correoEntidad = vista.getEmpresaCorreo();

        String repDocumento = vista.getRepresentanteNumeroDoc();
        String repNombre = vista.getRepresentanteNombre();
        String repTelefono = vista.getRepresentanteTelefono();
        String repCorreo = vista.getRepresentanteCorreo();

        if (!Validaciones.validarCedula(nit)) {
            JOptionPane.showMessageDialog(null, "El NIT no es válido. Debe contener entre 10 y 11 dígitos numéricos.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!Validaciones.validarTelefono(telefonoEntidad)) {
            JOptionPane.showMessageDialog(null, "El teléfono de la entidad no es válido.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!Validaciones.validarCorreo(correoEntidad)) {
            JOptionPane.showMessageDialog(null, "El correo de la entidad no es válido.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!Validaciones.validarSoloLetras(repNombre)) {
            JOptionPane.showMessageDialog(null, "El nombre del representante debe contener solo letras y espacios.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!Validaciones.validarCedula(repDocumento)) {
            JOptionPane.showMessageDialog(null,
                    "El documento del representante no es válido. Debe ser numérico (10-11 dígitos).",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!Validaciones.validarTelefono(repTelefono)) {
            JOptionPane.showMessageDialog(null, "El teléfono del representante no es válido.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!Validaciones.validarCorreo(repCorreo)) {
            JOptionPane.showMessageDialog(null, "El correo del representante no es válido.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (repDocumento.isEmpty() || repNombre.isEmpty() || repTelefono.isEmpty() || repCorreo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe completar todos los datos del representante.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Proveedor nuevo = new Proveedor();
        nuevo.setTipo("EMPRESA");
        nuevo.setDocumento(nit);
        nuevo.setNombre(nombreEntidad);
        nuevo.setMetodoDePago(medioPago);
        nuevo.setDireccion(direccion);
        nuevo.setTelefono(telefonoEntidad);
        nuevo.setCorreo(correoEntidad);
        nuevo.setIdProducto(vista.getProductoSeleccionado() != null ? vista.getProductoSeleccionado().getId() : 0);
        nuevo.setEstado(3);

        validarYRegistrarProveedor(nuevo);

        if (this.currentProviderId > 0) {
            ProveedorEmpresa pe = new ProveedorEmpresa();
            pe.setIdProveedor(this.currentProviderId);
            pe.setRepresentanteNombre(repNombre);
            pe.setRepresentanteDocumento(repDocumento);
            pe.setRepresentanteTelefono(repTelefono);
            pe.setRepresentanteCorreo(repCorreo);

            int res = empresaDao.setAgregar(pe);
            if (res <= 0) {
                JOptionPane.showMessageDialog(null,
                        "El proveedor se registró, pero falló al guardar los datos del representante.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                limpiarCampos();
            }
        }
    }

    private void insertarProveedorPersona() {
        if (vista == null) {
            JOptionPane.showMessageDialog(null, "Vista no inicializada", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String documento = vista.getPersonaNumeroDoc();
        String nombre = vista.getPersonaNombre();
        int medioPago = vista.getPersonaMedioDePago();
        String direccion = vista.getPersonaDireccion();
        String telefono = vista.getPersonaTelefono();
        String correo = vista.getPersonaCorreo();

        if (!Validaciones.validarCedula(documento)) {
            JOptionPane.showMessageDialog(null,
                    "El número de documento no es válido. Debe contener entre 10 y 11 dígitos numéricos.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!Validaciones.validarSoloLetras(nombre)) {
            JOptionPane.showMessageDialog(null, "El nombre debe contener solo letras y espacios.",
                    "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!Validaciones.validarTelefono(telefono)) {
            JOptionPane.showMessageDialog(null, "El número de teléfono no es válido.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!Validaciones.validarCorreo(correo)) {
            JOptionPane.showMessageDialog(null, "El formato del correo electrónico no es válido.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Proveedor nuevo = new Proveedor();
        nuevo.setTipo("NATURAL");
        nuevo.setDocumento(documento);
        nuevo.setNombre(nombre);
        nuevo.setMetodoDePago(medioPago);
        nuevo.setDireccion(direccion);
        nuevo.setTelefono(telefono);
        nuevo.setCorreo(correo);
        nuevo.setIdProducto(vista.getProductoSeleccionado() != null ? vista.getProductoSeleccionado().getId() : 0);
        nuevo.setEstado(3);

        validarYRegistrarProveedor(nuevo);
    }

    public void limpiarCampos() {
        // Limpiar formulario Persona
        vista.setPersonaNumeroDoc("");
        vista.setPersonaNombre("");
        vista.setPersonaDireccion("");
        vista.setPersonaTelefono("");
        vista.setPersonaCorreo("");
        vista.setPersonaMedioDePago(0);

        // Limpiar formulario Empresa
        vista.setEmpresaNit("");
        vista.setEmpresaNombreEntidad("");
        vista.setEmpresaDireccion("");
        vista.setEmpresaCorreo("");
        vista.setEmpresaMedioDePago(0);

        // Limpiar campos del representante
        vista.setRepresentanteNumeroDoc("");
        vista.setRepresentanteNombre("");
        vista.setRepresentanteTelefono("");
        vista.setRepresentanteCorreo("");
    }
}
