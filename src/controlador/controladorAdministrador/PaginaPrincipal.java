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
import vista.vistaAdministrador.PaginaProductoMasVendidos;
import vista.vistaAdministrador.PaginaProductoMenosVendidos;
import vista.vistaAdministrador.PanelesModal;
import vista.vistaAdministrador.PrincipalAdministradorVista;

public class PaginaPrincipal implements ActionListener {

    public PrincipalAdministradorVista principal;
    public PanelesModal r = new PanelesModal();
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

    public PaginaPrincipal(PrincipalAdministradorVista p) throws IOException {
        this.principal = p;
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
    }

    private List<Rol> roles = new ArrayList<>();

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == r.buscar) {

            if (r.numeCedula.getText().length() > 0 && r.numeCedula.getText().length() <= 10) {
                String documento = r.numeCedula.getText();
                Usuario user = uDao.obtenerUsuario(documento);

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
                    JOptionPane.showMessageDialog(null, "No se encontró ningún usuario con esa cédula.");
                }

            } else {
                // Si el campo está vacío o no cumple con la longitud
                r.limpiarCampos();
                JOptionPane.showMessageDialog(null, "El campo cédula no debe estar vacío.");
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
