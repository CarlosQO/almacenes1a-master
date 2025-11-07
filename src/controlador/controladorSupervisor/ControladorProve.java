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
                System.out.println("Producto asignado exitosamente al proveedor: " + idProveedor);
                productoSeleccionado = null; // Limpiar después de asignar
                vista.actualizarListaProductos(); // Actualizar la lista de productos
            } else {
                System.out.println("Error al asignar el producto al proveedor");
            }
        }
    }

    public void setCurrentProviderId(int id) {
        System.out.println("Estableciendo ID del proveedor actual: " + id);
        this.currentProviderId = id;
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

        if (nit.isEmpty() || nombreEntidad.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe completar NIT y nombre de la entidad.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (medioPago == 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un método de pago.", "Validación",
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
        nuevo.setEstado(1);

        int idInsert = pdao.setAgregar(nuevo);
        setCurrentProviderId(idInsert);
        if (idInsert <= 0) {
            JOptionPane.showMessageDialog(null, "No se pudo agregar la empresa en tabla proveedor.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2) Insertar datos específicos en proveedor_empresa
        ProveedorEmpresa pe = new ProveedorEmpresa();
        pe.setIdProveedor(idInsert);
        pe.setRepresentanteNombre(repNombre);
        pe.setRepresentanteDocumento(repDocumento);
        pe.setRepresentanteTelefono(repTelefono);
        pe.setRepresentanteCorreo(repCorreo);

        int res = empresaDao.setAgregar(pe);
        if (res > 0) {
            asignarProductoGuardado(idInsert);
            JOptionPane.showMessageDialog(null, "Empresa agregada correctamente.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "La empresa se insertó en proveedor pero falló proveedor_empresa.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void insertarProveedorPersona() {
        if (vista == null) {
            JOptionPane.showMessageDialog(null, "Vista no inicializada", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String documento = vista.getPersonaNumeroDoc();
        String nombre = vista.getPersonaNombre();

        System.out.println("Documento: [" + documento + "]");
        System.out.println("Nombre: [" + nombre + "]");
        int medioPago = vista.getPersonaMedioDePago();
        String direccion = vista.getPersonaDireccion();
        String telefono = vista.getPersonaTelefono();
        String correo = vista.getPersonaCorreo();

        // Validaciones simples
        if (documento.isEmpty() || nombre.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe completar el número de documento y el nombre.", "Validación",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (medioPago == 0) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un método de pago.", "Validación",
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
        nuevo.setEstado(1); // activo por defecto

        int idInsert = pdao.setAgregar(nuevo);
        if (idInsert > 0) {
            asignarProductoGuardado(idInsert);
            JOptionPane.showMessageDialog(null, "Proveedor agregado correctamente.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            setCurrentProviderId(idInsert);
            // opcional: limpiar campos
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo agregar el proveedor. Revise la conexión o los datos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
