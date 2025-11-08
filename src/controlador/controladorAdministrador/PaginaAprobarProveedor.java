package controladorAdministrador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import modelo.crudProveedor.Proveedor;

public class PaginaAprobarProveedor implements ActionListener {
    private vista.vistaAdministrador.PaginaAprobarProveedor paginaAprobarProveedor;

    public PaginaAprobarProveedor(vista.vistaAdministrador.PaginaAprobarProveedor paginaAprobarProveedor) {
        this.paginaAprobarProveedor = paginaAprobarProveedor;
        paginaAprobarProveedor.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    private List<Proveedor> proveedores = new ArrayList<>();

    private void cargarProveedoresInactivos() {

    }
}
