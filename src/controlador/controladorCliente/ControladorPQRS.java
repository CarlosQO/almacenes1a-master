package controladorCliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import modelo.crudPQRS.DaoPQRS;
import vista.vistaCliente.PanelPrincipal;
import vista.vistaCliente.tarjetas.TarjetaPQRS;

public class ControladorPQRS implements ActionListener {
    private PanelPrincipal panelPrincipal;
    private String idUsuario;
    private TarjetaPQRS tarjetaPqrsDialog;
    private JFrame frame;
    private DaoPQRS daoPQRS;

    public ControladorPQRS(PanelPrincipal panelPrincipal, String idUsuario) {
        this.panelPrincipal = panelPrincipal;
        this.idUsuario = idUsuario;
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
        } else {
            tarjetaPqrsDialog.dialogo.dispose();
            return "PQRS enviada";
        }

    }

    public String getIdUsuario() {
        return idUsuario;
    }

    // Setter
    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

}
