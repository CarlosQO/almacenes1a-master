package controladorCliente;

import java.io.IOException;

import controladorLogin.PaginaLogin;
import controladorMisAjuste.MiAjustesControlador;
import vista.vistaCliente.PanelPrincipal;
import vista.vistaMiAjustes.MiAjustes;

public class ControladorPrincipalCliente {
    private ControladorActividad controladorModuloActividad;
    private ControladorCatalogo controladorModuloCatalogo;
    private ControladorHistorial controladorModuloHistorialDeCompras;
    private ControladorPQRS controladorOpcionPQRS;
    private ControladorSeguimiento controladorSeguimiento;
    private CrontoladorManejarMenu controladorManejarMenu;
    private PanelPrincipal panelPrincipal;
    private MiAjustes ajustes;
    PaginaLogin paginaLogin = new PaginaLogin();

    public ControladorPrincipalCliente(PanelPrincipal p, String id) throws IOException {
        this.panelPrincipal = p;
        int idUsuario = Integer.parseInt(id);
        controladorManejarMenu = new CrontoladorManejarMenu(panelPrincipal);
        controladorModuloActividad = new ControladorActividad(panelPrincipal, idUsuario);
        controladorModuloCatalogo = new ControladorCatalogo(panelPrincipal, idUsuario);
        controladorModuloHistorialDeCompras = new ControladorHistorial(panelPrincipal, idUsuario);
        controladorSeguimiento = new ControladorSeguimiento(panelPrincipal, idUsuario);
        controladorOpcionPQRS = new ControladorPQRS(panelPrincipal, id);

        ajustes = new MiAjustes();
        new MiAjustesControlador(ajustes, PaginaLogin.usuario, PaginaLogin.documento, PaginaLogin.rol, panelPrincipal);

        panelPrincipal.setLocationRelativeTo(null);
        panelPrincipal.setVisible(true);
    }
}