package controladorAdministrador;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import modelo.crudRol.ConfigRolDao;
import modelo.crudRol.Rol;
import modelo.crudUsuario.Usuario;
import modelo.crudUsuario.UsuarioDao;
import modelo.crudVendedor.Vendedor;
import modelo.crudVendedor.VendedorDao;
import vista.vistaAdministrador.PaginaGenerarContrato;
import vista.vistaAdministrador.PaginaHistoricoTendenciaCompra;
import vista.vistaAdministrador.PaginaHistoricoVentas;
import vista.vistaAdministrador.PaginaProductoMasVendidos;
import vista.vistaAdministrador.PaginaProductoMenosVendidos;
import vista.vistaAdministrador.PaginaListarConfigRolModal;
import vista.vistaAdministrador.PrincipalAdministradorVista;

public class PaginaPrincipal implements ActionListener {

    public PrincipalAdministradorVista principal;
    public PaginaListarConfigRolModal r = new PaginaListarConfigRolModal();
    public Usuario usuario = new Usuario();
    public Vendedor vendedor = new Vendedor();
    public UsuarioDao uDao = new UsuarioDao();
    public ConfigRolDao rolDao = new ConfigRolDao();
    public VendedorDao venDao = new VendedorDao();
    // public static controlador.controladorAdministrador.PaginaPromociones promo =
    // new controlador.controladorAdministrador.PaginaPromociones();
    public static PaginaPromociones promo = new PaginaPromociones();
    public static vista.vistaAdministrador.PaginaPromociones promoPagi;
    public PaginaProductoMasVendidos paginaMasVendidos;
    public PaginaProductoMenosVendidos paginaMenosVendidos;
    public PaginaProducMasVendidos controPaginaMasVendido;
    public PaginaProducMenosVendidos controPaginaMenosVendido;
    // public controlador.controladorAdministrador.PaginaInformeGlobal
    // controPaginaInfoGlobal;
    public PaginaInformeGlobal controPaginaInfoGlobal;
    public vista.vistaAdministrador.PaginaInformeGlobal paginaInformeGlobal;
    public vista.vistaAdministrador.PaginaHistorialVenta paginaHistoVenta;
    // public controlador.controladorAdministrador.PaginaHistorialVenta
    // controHistoVenta;
    public PaginaHistorialVenta controHistoVenta;
    public Rol rol = new Rol();
    // Historico de ventas
    public PaginaHistoricoVenta controhistoVenta;
    public PaginaHistoricoVentas pHistoricoVentas;

    // Tendencia de compra
    public PaginaHistoricoTendenciaCompra pTendenciaCompra;
    public PaginaTendenciaCompra controTendenciaCompra;

    // Aprobar proveedor
    public PaginaAprobarProveedor controAproPro;
    public vista.vistaAdministrador.PaginaAprobarProveedor paginaAprobarProveedor;

    // Habilitar proveedor
    public PaginaHabilitarProveedor controHabPro;
    public vista.vistaAdministrador.PaginaHabilitarProveedor paginaHabilitarProveedor;

    // Deshabilitar proveedor
    public controladorAdministrador.PaginaDeshabilitarProveedor controDesPro;
    public vista.vistaAdministrador.PaginaDeshabilitarProveedor paginaDeshabilitarProveedor;

    // listar proveedores
    public PaginaListarProveedor controListarProveedor;
    public vista.vistaAdministrador.PaginaListarProveedores paginaListarProveedores;

    private String idAdministrador;

    // contrato de proveedores
    public PaginaContratoProveeControlador PContratoProvee;
    public PaginaGenerarContrato paginaGenerarContrato;

    public PaginaPrincipal(PrincipalAdministradorVista p, String id) throws IOException {
        this.principal = p;
        this.idAdministrador = id;
        r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        p.confiRol.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                r.setSize(new Dimension(340, 420));
                r.listarConfiRol();
                r.buscar.addActionListener(PaginaPrincipal.this);
                r.guardarCambios.addActionListener(PaginaPrincipal.this);
                r.mostrarComoModal(r, "Configurar Rol");
            }
        });
        p.promoContain.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    promoPagi = new vista.vistaAdministrador.PaginaPromociones();
                    promo = new PaginaPromociones(promoPagi);
                    configurarCierreVentana(promoPagi);
                } catch (IOException ex) {
                    System.out.println("Error al abrir la página de promociones");
                }
            }
        });
        p.producMasVendi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                paginaMasVendidos = new PaginaProductoMasVendidos();
                controPaginaMasVendido = new PaginaProducMasVendidos(paginaMasVendidos);
                configurarCierreVentana(paginaMasVendidos);
            }
        });
        p.producMenVendi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                paginaMenosVendidos = new PaginaProductoMenosVendidos();
                controPaginaMenosVendido = new PaginaProducMenosVendidos(paginaMenosVendidos);
                configurarCierreVentana(paginaMenosVendidos);
            }
        });
        p.informeGlobal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                paginaInformeGlobal = new vista.vistaAdministrador.PaginaInformeGlobal();
                controPaginaInfoGlobal = new PaginaInformeGlobal(paginaInformeGlobal);
                configurarCierreVentana(paginaInformeGlobal);
            }
        });
        p.histoVentas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                paginaHistoVenta = new vista.vistaAdministrador.PaginaHistorialVenta();
                controHistoVenta = new PaginaHistorialVenta(paginaHistoVenta);
                configurarCierreVentana(paginaHistoVenta);
            }
        });
        p.historiVentaPeri.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pHistoricoVentas = new PaginaHistoricoVentas();
                controhistoVenta = new PaginaHistoricoVenta(pHistoricoVentas);
                configurarCierreVentana(pHistoricoVentas);
            }
        });
        p.historiTendeCompra.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                pTendenciaCompra = new PaginaHistoricoTendenciaCompra();
                controTendenciaCompra = new PaginaTendenciaCompra(pTendenciaCompra);
                configurarCierreVentana(pTendenciaCompra);
            }
        });
        p.aprobarProvee.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                paginaAprobarProveedor = new vista.vistaAdministrador.PaginaAprobarProveedor();
                controAproPro = new PaginaAprobarProveedor(paginaAprobarProveedor);
                configurarCierreVentana(paginaAprobarProveedor);
            }
        });
        p.habilitarProvee.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                paginaHabilitarProveedor = new vista.vistaAdministrador.PaginaHabilitarProveedor();
                controHabPro = new PaginaHabilitarProveedor(paginaHabilitarProveedor);
                configurarCierreVentana(paginaHabilitarProveedor);
            }
        });
        p.deshabiliProvee.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                paginaDeshabilitarProveedor = new vista.vistaAdministrador.PaginaDeshabilitarProveedor();
                controDesPro = new controladorAdministrador.PaginaDeshabilitarProveedor(paginaDeshabilitarProveedor);
                configurarCierreVentana(paginaDeshabilitarProveedor);
            }
        });
        p.listaProvee.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                paginaListarProveedores = new vista.vistaAdministrador.PaginaListarProveedores();
                controListarProveedor = new PaginaListarProveedor(paginaListarProveedores);
                configurarCierreVentana(paginaListarProveedores);
            }
        });
        p.contratoProvee.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                paginaGenerarContrato = new PaginaGenerarContrato();
                PContratoProvee = new PaginaContratoProveeControlador(paginaGenerarContrato);
                configurarCierreVentana(paginaGenerarContrato);
            }
        });
    }

    private List<Rol> roles = new ArrayList<>();

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == r.buscar) {
            if (!r.validaciones()) {
                r.limpiarCampos();
                return;
            }
            if (r.numeCedula.getText().equals(idAdministrador)) {
                JOptionPane.showMessageDialog(null, "No puede cambiar el rol del administrador", "",
                        JOptionPane.INFORMATION_MESSAGE);
                r.limpiarCampos();
                return;
            }

            if (r.numeCedula.getText().length() > 0 && r.numeCedula.getText().length() <= 10) {
                String documento = r.numeCedula.getText();
                Usuario user = uDao.obtenerUsuario(documento);
                // validacion que no cambie el rol a ningun administrador
                if (user != null && user.getIdRol() == 1) {
                    JOptionPane.showMessageDialog(null, "No puede cambiar el rol del administrador", "",
                            JOptionPane.INFORMATION_MESSAGE);
                    r.limpiarCampos();
                    return;
                }

                if (user != null) {
                    r.numeId.setText(user.getDocumento());
                    r.numeNombres.setText(user.getNombre());
                    r.numeApellidos.setText(user.getApellido());

                    roles = rolDao.listar();

                    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
                    int indexSeleccionado = -1;
                    int contador = 0;

                    for (Rol rol : roles) {
                        model.addElement(rol.getRol());

                        if (rol.getId_rol() == user.getIdRol()) {
                            indexSeleccionado = contador;
                        }
                        contador++;
                    }

                    r.tipoDeRol.setModel(model);

                    if (indexSeleccionado != -1) {
                        r.tipoDeRol.setSelectedIndex(indexSeleccionado);
                    }

                } else {
                    // Si no se encuentra el usuario, limpiar todo
                    r.limpiarCampos();
                    System.out.println("No se encontró usuario con cédula: " + documento);
                }

            } else {
                // Si el campo está vacío o no cumple con la longitud
                r.limpiarCampos();
                System.out.println("Por favor ingrese una cédula válida.");
            }
        }

        if (e.getSource() == r.guardarCambios) {
            if (r.tipoDeRol.getItemCount() > 0) {
                int selectedIndex = r.tipoDeRol.getSelectedIndex();

                if (selectedIndex >= 0 && selectedIndex < roles.size()) {
                    Rol rolSeleccionado = roles.get(selectedIndex);
                    int idRol = rolSeleccionado.getId_rol();
                    String nombreRol = rolSeleccionado.getRol();
                    String documento = r.numeId.getText();

                    vendedor = venDao.buscarVendedor(documento);
                    int columnasAfectadas = uDao.ActualizarRol(idRol, documento);

                    if (columnasAfectadas > 0) {
                        JOptionPane.showMessageDialog(null, "Rol actualizado correctamente");
                        if (vendedor == null && nombreRol.equalsIgnoreCase("Vendedor")) {
                            agregarVendedor(documento, "Activo", 0);
                        } else {
                            if (!nombreRol.equalsIgnoreCase("Vendedor")) {
                                actualizarEstado(documento, "Inactivo");
                            } else {
                                actualizarEstado(documento, "Activo");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pudo actualizar el rol");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor primero busca un usuario");
            }

        }
    }

    private void configurarCierreVentana(JFrame ventanaSecundaria) {
        principal.setVisible(false);

        javax.swing.SwingUtilities.invokeLater(() -> {
            ventanaSecundaria.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    principal.setVisible(true);
                }
            });
        });
    }

    private int agregarVendedor(String documento, String estado, int cantiVenta) {
        modelo.crudVendedor.Vendedor vende = new modelo.crudVendedor.Vendedor();
        vende.setIdVendedor(documento);
        vende.setIdUsuario(documento);
        vende.setNumeroDeVenta(cantiVenta);
        vende.setEstado("Activo");
        int filaInsertada = venDao.agregarVendedor(vende);
        return filaInsertada;
    }

    private void actualizarEstado(String idVendedor, String estado) {
        venDao.actualizarEstado(idVendedor, estado);
    }
}
