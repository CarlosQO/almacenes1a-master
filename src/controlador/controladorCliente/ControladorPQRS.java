package controladorCliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import modelo.crudPQRS.DaoPQRS;
import vista.vistaCliente.PanelPrincipal;
import vista.vistaCliente.tarjetas.TarjetaPQRS;

public class ControladorPQRS implements ActionListener {
    private PanelPrincipal panelPrincipal;
    private static int idUsuario = 1002;
    private TarjetaPQRS tarjetaPqrsDialog;
    private JFrame frame;
    private DaoPQRS daoPQRS;

    public ControladorPQRS(PanelPrincipal panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
        panelPrincipal.btnPQRS.addActionListener(this);
        frame = new JFrame();
        daoPQRS = new DaoPQRS();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == panelPrincipal.btnPQRS) {
            panelPrincipal.menuPerfil.setVisible(false);
            // Crear el diálogo
            tarjetaPqrsDialog = new TarjetaPQRS(frame);

            // enviar la pqrs
            tarjetaPqrsDialog.btnEnviar.addActionListener(enviar -> {
                if (tarjetaPqrsDialog.validarCampos()) {
                    String asunto = tarjetaPqrsDialog.txtAsunto.getText();
                    String cuerpo = tarjetaPqrsDialog.txtPQRS.getText();
                    String mensaje = setEnviarPQRS(asunto, cuerpo);
                    JOptionPane.showMessageDialog(null, mensaje);
                }
            });
            // Mostrar el diálogo
            tarjetaPqrsDialog.mostrar();
        }
    }

    public String setEnviarPQRS(String asunto, String cuerpo) {
        boolean respuesta = daoPQRS.enviarPQRS(idUsuario, asunto, cuerpo);
        if (!respuesta) {
            return "Error PQRS enviada";
        }
        return "PQRS enviada";
    }

    public static void main(String[] args) throws IOException {
        PanelPrincipal menu = new PanelPrincipal();
        menu.setVisible(true);
        menu.setSize(1300, 700);
        ControladorCatalogo c = new ControladorCatalogo(menu);
        ControladorActividad ca = new ControladorActividad(menu);
        ControladorHistorial ch = new ControladorHistorial(menu);
        ControladorSeguimiento cs = new ControladorSeguimiento(menu);
        ControladorPQRS cpqrs = new ControladorPQRS(menu);
        CrontoladorManejarMenu ccerrar = new CrontoladorManejarMenu(menu);
    }
}
