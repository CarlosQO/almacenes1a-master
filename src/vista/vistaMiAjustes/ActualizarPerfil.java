package vista.vistaMiAjustes;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import vista.componentes.Validaciones;

import vista.fuenteLetra.Fuente;

public class ActualizarPerfil extends JFrame {
        public JTextField numeCedula;
        public JButton guardarCambios;
        private Container container;
        private JPanel confiRolContainer, headerRol;
        private FlowLayout miflow;
        public JTextField numeId, numeNombres, numeApellidos, numeTelefono;
        private Fuente fuente = new Fuente();

        public ActualizarPerfil() {
                super("Actualizar Perfil");
                setSize(350, 400);
                setLocationRelativeTo(null);
                setResizable(false);
                container = getContentPane();
                miflow = new FlowLayout();
                container.setLayout(miflow);
                confiRolContainer = new JPanel();
                confiRolContainer.setLayout(null);
                confiRolContainer.setPreferredSize(new Dimension(320, 330));
                confiRolContainer.setBackground(Color.WHITE);
                confiRolContainer.setBounds(10, 0, 320, 330);
                // añadir la cabecera con el titulo de configurar Rol
                headerRol = new JPanel();
                headerRol.setLayout(null);
                headerRol.setBounds(0, 0, (int) confiRolContainer.getPreferredSize().getWidth(), 40);
                JLabel titleActualizar = new JLabel("Actualizar Perfil");
                titleActualizar.setFont(fuente.fuente(3, true));
                titleActualizar.setHorizontalAlignment(titleActualizar.CENTER);
                titleActualizar.setBounds(0, 10, 320,
                                (int) titleActualizar.getPreferredSize().getHeight());
                headerRol.add(titleActualizar);
                confiRolContainer.add(headerRol);

                JLabel id = new JLabel("Documento:");
                id.setFont(fuente.fuente(5, false));
                id.setBounds(10, 80, (int) id.getPreferredSize().getWidth() + 10,
                                (int) id.getPreferredSize().getHeight());
                confiRolContainer.add(id);
                numeId = new JTextField();
                numeId.setBounds(120, 80, 190, 30);
                numeId.setFont(fuente.fuente(5, false));
                numeId.setEditable(false);
                confiRolContainer.add(numeId);

                JLabel nombres = new JLabel("Nombres:");
                nombres.setFont(fuente.fuente(5, false));
                nombres.setBounds(10, id.getY() + id.getHeight() + 30, (int) nombres.getPreferredSize().getWidth() + 10,
                                (int) nombres.getPreferredSize().getHeight());
                confiRolContainer.add(nombres);
                numeNombres = new JTextField();
                numeNombres.setBounds(120, id.getY() + id.getHeight() + 30, 190, 30);
                numeNombres.setFont(fuente.fuente(5, false));
                confiRolContainer.add(numeNombres);

                JLabel apellidos = new JLabel("Apellidos:");
                apellidos.setFont(fuente.fuente(5, false));
                apellidos.setBounds(10, nombres.getY() + nombres.getHeight() + 30,
                                (int) apellidos.getPreferredSize().getWidth() + 10,
                                (int) apellidos.getPreferredSize().getHeight());
                confiRolContainer.add(apellidos);
                numeApellidos = new JTextField();
                numeApellidos.setBounds(120, nombres.getY() + nombres.getHeight() + 30, 190, 30);
                numeApellidos.setFont(fuente.fuente(5, false));
                confiRolContainer.add(numeApellidos);

                JLabel telefono = new JLabel("Teléfono:");
                telefono.setFont(fuente.fuente(5, false));
                telefono.setBounds(10, apellidos.getY() + apellidos.getHeight() + 30,
                                (int) telefono.getPreferredSize().getWidth() + 10,
                                (int) telefono.getPreferredSize().getHeight());
                confiRolContainer.add(telefono);
                numeTelefono = new JTextField();
                numeTelefono.setBounds(120, apellidos.getY() + apellidos.getHeight() + 30, 190, 30);
                numeTelefono.setFont(fuente.fuente(5, false));
                confiRolContainer.add(numeTelefono);

                guardarCambios = new JButton("Guardar Cambios");
                guardarCambios.setFont(fuente.fuente(5, false));
                guardarCambios.setBackground(Color.WHITE);
                guardarCambios.setFocusable(false);
                guardarCambios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                guardarCambios.setBounds(70, numeTelefono.getY() + numeTelefono.getHeight() + 30, 200, 30);
                confiRolContainer.add(guardarCambios);

                container.add(confiRolContainer);
        }

        public void mostrarComoModal(JFrame parent, String m) {
                JDialog dialog = new JDialog(parent, m, true);
                dialog.setContentPane(this.getContentPane());
                dialog.setSize(this.getSize());
                dialog.setLocationRelativeTo(null);
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.setVisible(true);
        }

        public void llenarCampos(String documento, String nombres, String apellidos, String telefono) {
                numeId.setText(documento);
                numeNombres.setText(nombres);
                numeApellidos.setText(apellidos);
                numeTelefono.setText(telefono);
                revalidate();
                repaint();
        }

        public boolean validarCampos() {
                String cadena = "<html><h1 style='color:red';>Se encontran los siguientes errores:</h1></html>";
                boolean pasaOno = true;
                if (!Validaciones.validarSoloLetras(numeNombres.getText())) {
                        cadena += "El nombre no es valido, solo debe tener letras\n";
                        pasaOno = false;
                }
                if (!Validaciones.validarSoloLetras(numeApellidos.getText())) {
                        cadena += "El apellido no es valido, solo debe tener letras\n";
                        pasaOno = false;
                }
                if (!Validaciones.validarTelefono(numeTelefono.getText())) {
                        cadena += "El telefono no es valido, solo debe tener numeros y tener 10 o 11 digitos\n";
                        pasaOno = false;
                }
                if (!pasaOno) {
                        JOptionPane.showMessageDialog(null, cadena, "Error en los campos", JOptionPane.ERROR_MESSAGE);
                }
                return pasaOno;
        }
}