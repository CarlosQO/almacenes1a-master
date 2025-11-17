package controladorMisAjuste;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import vista.vistaMiAjustes.MiAjustes;

public class MiAjustesControlador implements ActionListener {
    private String usuario;
    private MiAjustes ajusteV;

    public MiAjustesControlador(MiAjustes ajusteV, String usuario, String documento) {
        this.ajusteV = ajusteV;
        this.usuario = usuario;
        ajusteV.setVisible(true);
        ajusteV.setUsuario(usuario);
        ajusteV.repintar();
        ajusteV.actualizarDatos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Actualizar datos de: " + usuario + " con documento: " + documento);
            }
        });
        ajusteV.cerrarSesion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Cerrar sesion de: " + usuario + " con documento: " + documento);
            }
        });
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {

    }
}