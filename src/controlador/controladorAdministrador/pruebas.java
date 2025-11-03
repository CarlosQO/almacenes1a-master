package controladorAdministrador;

import java.io.IOException;
import javax.swing.JFrame;

import vista.vistaAdministrador.PrincipalAdministradorVista;

public class pruebas {

    public static void main(String[] args) throws IOException {
        PrincipalAdministradorVista v = new PrincipalAdministradorVista();
        PaginaPrincipal c = new PaginaPrincipal(v);
        v.setResizable(false);
        v.setVisible(true);
        v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
