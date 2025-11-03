package controladorCliente;

import java.io.IOException;

import vista.vistaCliente.PanelPrincipal;

public class ControladorPrincipalCliente {
    private ControladorActividad controladorModuloActividad;
    private ControladorCatalogo controladorModuloCatalogo;
    private ControladorHistorial controladorModuloHistorialDeCompras;
    private ControladorPQRS controladorOpcionPQRS;
    private ControladorSeguimiento controladorSeguimiento;
    private CrontoladorManejarMenu controladorManejarMenu;
    private PanelPrincipal panelPrincipal;

    public ControladorPrincipalCliente(PanelPrincipal p, String id) throws IOException {
        this.panelPrincipal = p;

        controladorManejarMenu = new CrontoladorManejarMenu(panelPrincipal);
        controladorModuloActividad = new ControladorActividad(panelPrincipal);
        controladorModuloCatalogo = new ControladorCatalogo(panelPrincipal);
        controladorModuloHistorialDeCompras = new ControladorHistorial(panelPrincipal);
        controladorSeguimiento = new ControladorSeguimiento(panelPrincipal);
        controladorOpcionPQRS = new ControladorPQRS(panelPrincipal);

        panelPrincipal.setLocationRelativeTo(null);
        panelPrincipal.setVisible(true);
    }
}