package controlador.controladorSupervisor;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import modelo.crudProducto.Producto;
import modelo.crudProducto.ProductoDao;
import modelo.crudProveedor.Proveedor;
import modelo.crudProveedor.ProveedorDao;
import modelo.crudProveedorEmpresa.ProveedorEmpresa;
import modelo.crudProveedorEmpresa.ProveedorEmpresaDao;
import vista.componentes.Validaciones;
import vista.vistaSupervisor.PreRegistroProveedor;

public class ControladorProve implements ActionListener {
    public ProveedorDao pdao = new ProveedorDao();
    public Proveedor p = new Proveedor();
    public PreRegistroProveedor vista = new PreRegistroProveedor();
    private ProveedorEmpresaDao empresaDao = new ProveedorEmpresaDao();
    private ProductoDao productoDao;
    private int currentProviderId;
    private Producto productoSeleccionado;

    public ControladorProve(PreRegistroProveedor vista) {
        this.vista = vista;
        this.productoDao = new ProductoDao();
        this.currentProviderId = 0;
        this.productoSeleccionado = null;

        // Configurar action listeners
        vista.getTipoEntidad().addActionListener(this);
        vista.getBtnEnviarPersona().addActionListener(this);
        vista.getBtnEnviarEmpresa().addActionListener(this);
        // Registrar ambos botones "Agregar" (persona y empresa)
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
            // ignore if not present
        }
    }

    public ControladorProve() {
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

            // Forzar refresco de la vista
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

    private void asignarProductoGuardado(int idProveedor) {
        if (productoSeleccionado != null) {
            if (productoDao.asignarProveedor(productoSeleccionado.getId(), idProveedor)) {
                productoSeleccionado = null; // Limpiar después de asignar
                vista.actualizarListaProductos(); // Actualizar la lista de productos
            } else {
                JOptionPane.showMessageDialog(null, "Error al asignar el producto al proveedor", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void setCurrentProviderId(int id) {
        this.currentProviderId = id;
    }

    private void validarYRegistrarProveedor(Proveedor nuevo) {
        // 1. Validaciones genéricas
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

        // 2. Validar si el documento ya existe en la base de datos
        if (pdao.existeProveedorPorNit(nuevo.getDocumento())) {
            JOptionPane.showMessageDialog(null, "Ya existe un proveedor registrado con este número de documento.",
                    "Error de Duplicidad", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. Si todo es válido, proceder con la inserción
        int idInsert = pdao.setAgregar(nuevo);

        if (idInsert > 0) {
            setCurrentProviderId(idInsert);
            asignarProductoGuardado(idInsert); // Asigna el producto si se seleccionó uno
            JOptionPane.showMessageDialog(null, "Proveedor agregado correctamente.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
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

        // leer campos de la vista
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

        // Validaciones de tipo de dato para Empresa
        if (!Validaciones.validarCedula(nit)) {
            JOptionPane.showMessageDialog(null, "El NIT no es válido. Debe contener entre 10 y 11 dígitos numéricos.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE);
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
                    "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!Validaciones.validarCedula(repDocumento)) {
            JOptionPane.showMessageDialog(null,
                    "El documento del representante no es válido. Debe ser numérico (10-11 dígitos).", "Validación",
                    JOptionPane.WARNING_MESSAGE);
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
        // Validar que los datos del representante no estén vacíos
        if (repDocumento.isEmpty() || repNombre.isEmpty() || repTelefono.isEmpty() || repCorreo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe completar todos los datos del representante.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 1) Insertar en tabla proveedor
        Proveedor nuevo = new Proveedor();
        nuevo.setTipo("EMPRESA");
        nuevo.setDocumento(nit);
        nuevo.setNombre(nombreEntidad);
        nuevo.setMetodoDePago(medioPago);
        nuevo.setDireccion(direccion);
        nuevo.setTelefono(telefonoEntidad);
        nuevo.setCorreo(correoEntidad);
        nuevo.setEstado(3);

        // Validar y registrar el proveedor base
        validarYRegistrarProveedor(nuevo);

        // Si el proveedor se insertó (currentProviderId > 0), registrar datos de la
        // empresa
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
                        "El proveedor se registró, pero falló al guardar los datos del representante.", "Aviso",
                        JOptionPane.WARNING_MESSAGE);
            }
        } else {
            // El mensaje de error ya se mostró en validarYRegistrarProveedor
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

        // Validaciones de tipo de dato para Persona Natural
        if (!Validaciones.validarCedula(documento)) {
            JOptionPane.showMessageDialog(null,
                    "El número de documento no es válido. Debe contener entre 10 y 11 dígitos numéricos.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!Validaciones.validarSoloLetras(nombre)) {
            JOptionPane.showMessageDialog(null, "El nombre debe contener solo letras y espacios.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
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
        nuevo.setEstado(3); // activo por defecto

        // Usar el nuevo método centralizado para validar y registrar
        validarYRegistrarProveedor(nuevo);
    }
}
