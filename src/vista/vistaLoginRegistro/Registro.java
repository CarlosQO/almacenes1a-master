package vista.vistaLoginRegistro;

import java.awt.Color;
import java.awt.Container;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import org.jdesktop.swingx.JXTextField;

import java.awt.Cursor;

import vista.componentes.CamposConLimite;
import vista.componentes.RoundedJXButton;
import vista.componentes.RoundedJXComboBox;
import vista.componentes.RoundedJXPanel;
import vista.fuenteLetra.Fuente;
import vista.vistaAdministrador.RoundedPanel;
import vista.componentes.Validaciones;

public class Registro extends JFrame {
        private Container container;
        private RoundedPanel contenedorInfo;
        private RoundedJXPanel numeroCedula, nombres, apellidos, correoElectronico, direccion, telefono, contrasena,
                        confirmarContrasena;
        public JXTextField inputNumeroDoc, inputNombres, inputApellido, inputCorreoElectronico, inputTelefono,
                        inputDireccion;
        public JPasswordField inputContrasena, inputConfirmarContrasena;
        public RoundedJXButton btnRegistrarse;
        private JLabel titleRegistro;
        public JLabel iniciarSesion;
        private RoundedJXPanel panelDesgradado;
        private Fuente fuente = new Fuente();
        public RoundedJXComboBox tipoCedula;

        public Registro() {
                super("Registro");
                container = getContentPane();
                setSize(1300, 700);
                setLocationRelativeTo(null);
                setResizable(false);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLayout(null);
                container.setBackground(Color.WHITE);
                // agregar el componente que va a tener la sombra y los demas componentes
                contenedorInfo = new vista.vistaAdministrador.RoundedPanel(20, 0xFFFFFF);
                contenedorInfo.setShadowColor(new Color(0, 210, 255, 50));
                contenedorInfo.setShadowSize(24);
                contenedorInfo.setLayout(null);
                contenedorInfo.setBounds(150, 55, 1000, 550);
                contenedorInfo.setBackground(Color.WHITE);

                // agregar el panel degradado
                panelDesgradado = new RoundedJXPanel(20, 0xFFFFFF);
                panelDesgradado.setBounds(500, 24, 476, 502);
                panelDesgradado.setLayout(null);
                panelDesgradado.setShadowSize(0);
                // panelDesgradado.setShadowColor(getForeground());
                panelDesgradado.setBackground(new Color(1, 179, 255));
                panelDesgradado.setDegradado(true, new java.awt.Color(255, 255, 255), new Color(0, 210, 255, 40));

                // Agregar el icono
                try {
                        // Cargar la imagen desde la carpeta Iconos (debe estar dentro de src)
                        ImageIcon imagenIcon = new ImageIcon(getClass().getResource("/Iconos/LogoAlmacen1A.png"));

                        // Escalar la imagen suavemente al tamaño deseado
                        Image imagenEscalada = imagenIcon.getImage().getScaledInstance(400, 400, Image.SCALE_SMOOTH);

                        // Crear el JLabel con la imagen escalada
                        JLabel iconoImg = new JLabel(new ImageIcon(imagenEscalada));
                        iconoImg.setBounds(50, 100, 400, 400);

                        // Agregar al contenedor
                        contenedorInfo.add(iconoImg);

                } catch (Exception e) {
                        System.out.println("Error al cargar la imagen del login: " + e.toString());
                }

                // Agregar el titulo
                titleRegistro = new JLabel("R e g i s t r o  d e  U s u a r i o");
                titleRegistro.setBounds(20, 24, 476, 100);
                titleRegistro.setHorizontalAlignment(SwingConstants.CENTER);
                titleRegistro.setFont(fuente.fuente(4, true));
                panelDesgradado.add(titleRegistro);

                // Agregar el comboBox para elegir el tipo de cedula
                tipoCedula = new RoundedJXComboBox<String>();
                // tipoCedula.setBackground(Color.GRAY);
                tipoCedula.setBounds(50, titleRegistro.getY() + titleRegistro.getHeight() + 20, 160, 40);
                tipoCedula.setFont(fuente.fuente(4, false));
                JLabel txtCedula = new JLabel("Tipo de Documento*");
                txtCedula.setBounds(50, tipoCedula.getY() - 20, 160, 20);
                txtCedula.setFont(fuente.fuente(5, false));
                txtCedula.setForeground(Color.GRAY);
                panelDesgradado.add(txtCedula);
                panelDesgradado.add(tipoCedula);

                // Agregar el campo para ingresar la cedula
                numeroCedula = new RoundedJXPanel(20, 0xFFFFFF);
                numeroCedula.setShadowSize(0);
                numeroCedula.setLayout(null);
                numeroCedula.setBounds(tipoCedula.getX() + tipoCedula.getWidth() + 20,
                                titleRegistro.getY() + titleRegistro.getHeight() + 20, 220, 40);
                numeroCedula.setFont(fuente.fuente(4, false));
                JLabel txtNumeCedu = new JLabel("Numero de Cedula*");
                txtNumeCedu.setBounds(numeroCedula.getX(), numeroCedula.getY() - 20, 160, 20);
                txtNumeCedu.setFont(fuente.fuente(5, false));
                txtNumeCedu.setForeground(Color.GRAY);
                // Agregar el TextField para ingresar la cedula
                inputNumeroDoc = new JXTextField();
                inputNumeroDoc.setPrompt("12345678901");
                inputNumeroDoc.setPromptForeground(new Color(128, 128, 128));
                inputNumeroDoc.setFont(fuente.fuente(4, false));
                inputNumeroDoc.setForeground(Color.BLACK);
                inputNumeroDoc.setBounds(5, 1, numeroCedula.getWidth() - 10, 40);
                inputNumeroDoc.setBackground(null);
                inputNumeroDoc.setBorder(null);
                CamposConLimite.limitarCaracteres(inputNumeroDoc, 13);
                numeroCedula.add(inputNumeroDoc);
                panelDesgradado.add(txtNumeCedu);
                panelDesgradado.add(numeroCedula);

                // Agregar el campo para ingresar la cedula
                nombres = new RoundedJXPanel(20, 0xFFFFFF);
                nombres.setShadowSize(0);
                nombres.setLayout(null);
                nombres.setBounds(tipoCedula.getX(),
                                tipoCedula.getY() + tipoCedula.getHeight() + 25, 190, 40);
                nombres.setFont(fuente.fuente(4, false));
                JLabel txtNombres = new JLabel("Nombres*");
                txtNombres.setBounds(nombres.getX(), nombres.getY() - 20, 160, 20);
                txtNombres.setFont(fuente.fuente(5, false));
                txtNombres.setForeground(Color.GRAY);
                // Agregar el TextField para ingresar la cedula
                inputNombres = new JXTextField();
                inputNombres.setPrompt("Juan Pepito");
                inputNombres.setPromptForeground(new Color(128, 128, 128));
                inputNombres.setFont(fuente.fuente(4, false));
                inputNombres.setForeground(Color.BLACK);
                inputNombres.setBounds(5, 1, nombres.getWidth() - 10, 40);
                inputNombres.setBackground(null);
                inputNombres.setBorder(null);
                CamposConLimite.limitarCaracteres(inputNombres, 30);
                nombres.add(inputNombres);
                panelDesgradado.add(txtNombres);
                panelDesgradado.add(nombres);

                // Agregar el campo para ingresar la cedula
                apellidos = new RoundedJXPanel(20, 0xFFFFFF);
                apellidos.setShadowSize(0);
                apellidos.setLayout(null);
                apellidos.setBounds(numeroCedula.getX() + 20,
                                numeroCedula.getY() + numeroCedula.getHeight() + 25, 200, 40);
                apellidos.setFont(fuente.fuente(4, false));
                JLabel txtApelli = new JLabel("Apellidos*");
                txtApelli.setBounds(apellidos.getX(), apellidos.getY() - 20, 160, 20);
                txtApelli.setFont(fuente.fuente(5, false));
                txtApelli.setForeground(Color.GRAY);
                // Agregar el TextField para ingresar la cedula
                inputApellido = new JXTextField();
                inputApellido.setPrompt("Pepito Perez");
                inputApellido.setPromptForeground(new Color(128, 128, 128));
                inputApellido.setFont(fuente.fuente(4, false));
                inputApellido.setForeground(Color.BLACK);
                inputApellido.setBounds(5, 1, apellidos.getWidth() - 10, 40);
                inputApellido.setBackground(null);
                inputApellido.setBorder(null);
                CamposConLimite.limitarCaracteres(inputApellido, 30);
                apellidos.add(inputApellido);
                panelDesgradado.add(txtApelli);
                panelDesgradado.add(apellidos);

                // Agregar el campo para ingresar la cedula
                correoElectronico = new RoundedJXPanel(20, 0xFFFFFF);
                correoElectronico.setShadowSize(0);
                correoElectronico.setLayout(null);
                correoElectronico.setBounds(tipoCedula.getX(),
                                nombres.getY() + nombres.getHeight() + 25, 190, 40);
                correoElectronico.setFont(fuente.fuente(4, false));
                JLabel txtCorreo = new JLabel("Correo Electronico*");
                txtCorreo.setBounds(correoElectronico.getX(), correoElectronico.getY() - 20, 160, 20);
                txtCorreo.setFont(fuente.fuente(5, false));
                txtCorreo.setForeground(Color.GRAY);
                // Agregar el TextField para ingresar la cedula
                inputCorreoElectronico = new JXTextField();
                inputCorreoElectronico.setPrompt("PepitoP@gmail.com");
                inputCorreoElectronico.setPromptForeground(new Color(128, 128, 128));
                inputCorreoElectronico.setFont(fuente.fuente(4, false));
                inputCorreoElectronico.setForeground(Color.BLACK);
                inputCorreoElectronico.setBounds(5, 1, nombres.getWidth() - 10, 40);
                inputCorreoElectronico.setBackground(null);
                inputCorreoElectronico.setBorder(null);
                CamposConLimite.limitarCaracteres(inputCorreoElectronico, 100);
                correoElectronico.add(inputCorreoElectronico);
                panelDesgradado.add(txtCorreo);
                panelDesgradado.add(correoElectronico);

                // Agregar el campo para ingresar la cedula
                telefono = new RoundedJXPanel(20, 0xFFFFFF);
                telefono.setShadowSize(0);
                telefono.setLayout(null);
                telefono.setBounds(numeroCedula.getX() + 20,
                                apellidos.getY() + apellidos.getHeight() + 25, 200, 40);
                telefono.setFont(fuente.fuente(4, false));
                JLabel txtTelefono = new JLabel("Telefono*");
                txtTelefono.setBounds(telefono.getX(), telefono.getY() - 20, 160, 20);
                txtTelefono.setFont(fuente.fuente(5, false));
                txtTelefono.setForeground(Color.GRAY);
                // Agregar el TextField para ingresar la cedula
                inputTelefono = new JXTextField();
                inputTelefono.setPrompt("3213213213");
                inputTelefono.setPromptForeground(new Color(128, 128, 128));
                inputTelefono.setFont(fuente.fuente(4, false));
                inputTelefono.setForeground(Color.BLACK);
                inputTelefono.setBounds(5, 1, apellidos.getWidth() - 10, 40);
                inputTelefono.setBackground(null);
                inputTelefono.setBorder(null);
                CamposConLimite.limitarCaracteres(inputTelefono, 30);
                telefono.add(inputTelefono);
                panelDesgradado.add(txtTelefono);
                panelDesgradado.add(telefono);

                // Agregar el campo para ingresar la contraseña
                contrasena = new RoundedJXPanel(20, 0xFFFFFF);
                contrasena.setShadowSize(0);
                contrasena.setLayout(null);
                contrasena.setBounds(tipoCedula.getX(),
                                correoElectronico.getY() + correoElectronico.getHeight() + 25, 190, 40);
                contrasena.setFont(fuente.fuente(4, false));
                JLabel txtContra = new JLabel("Contraseña*");
                txtContra.setBounds(contrasena.getX(), contrasena.getY() - 20, 160, 20);
                txtContra.setFont(fuente.fuente(5, false));
                txtContra.setForeground(Color.GRAY);
                // Agregar el TextField para ingresar la cedula
                inputContrasena = new JPasswordField();
                inputContrasena.setFont(fuente.fuente(4, false));
                inputContrasena.setForeground(Color.BLACK);
                inputContrasena.setBounds(5, 1, nombres.getWidth() - 10, 40);
                inputContrasena.setBackground(null);
                inputContrasena.setBorder(null);
                CamposConLimite.agregarPlaceholder(inputContrasena, "•••••••••");
                contrasena.add(inputContrasena);
                panelDesgradado.add(txtContra);
                panelDesgradado.add(contrasena);

                // Agregar el campo para ingresar la contraseña
                confirmarContrasena = new RoundedJXPanel(20, 0xFFFFFF);
                confirmarContrasena.setShadowSize(0);
                confirmarContrasena.setLayout(null);
                confirmarContrasena.setBounds(telefono.getX(),
                                telefono.getY() + telefono.getHeight() + 25, 200, 40);
                confirmarContrasena.setFont(fuente.fuente(4, false));
                JLabel txtConfiContra = new JLabel("Confirmar Contraseña*");
                txtConfiContra.setBounds(confirmarContrasena.getX(), confirmarContrasena.getY() - 20, 200, 20);
                txtConfiContra.setFont(fuente.fuente(5, false));
                txtConfiContra.setForeground(Color.GRAY);
                // Agregar el TextField para ingresar la cedula
                inputConfirmarContrasena = new JPasswordField();
                inputConfirmarContrasena.setFont(fuente.fuente(4, false));
                inputConfirmarContrasena.setForeground(Color.BLACK);
                inputConfirmarContrasena.setBounds(5, 1, nombres.getWidth() - 10, 40);
                inputConfirmarContrasena.setBackground(null);
                inputConfirmarContrasena.setBorder(null);
                CamposConLimite.agregarPlaceholder(inputConfirmarContrasena, "•••••••••");
                confirmarContrasena.add(inputConfirmarContrasena);
                panelDesgradado.add(txtConfiContra);
                panelDesgradado.add(confirmarContrasena);

                // agregar el boton de Registro
                btnRegistrarse = new RoundedJXButton("Crear Cuenta");
                btnRegistrarse.setCornerRadius(20);
                btnRegistrarse.setFont(fuente.fuente(4, true));
                btnRegistrarse.setBackground(new Color(86, 179, 255));
                btnRegistrarse.setHoverColor(new Color(0, 140, 255));
                btnRegistrarse.setBounds(confirmarContrasena.getX(), contrasena.getY() + contrasena.getHeight() + 25,
                                confirmarContrasena.getWidth(), 40);
                btnRegistrarse.setForeground(Color.white);
                getRootPane().setDefaultButton(btnRegistrarse);
                panelDesgradado.add(btnRegistrarse);

                // Agregar el campo para ingresar la direccion
                direccion = new RoundedJXPanel(20, 0xFFFFFF);
                direccion.setShadowSize(0);
                direccion.setLayout(null);
                direccion.setBounds(contrasena.getX(),
                                btnRegistrarse.getY(), contrasena.getWidth(), 40);
                direccion.setFont(fuente.fuente(4, false));
                JLabel txtDireccion = new JLabel("Dirección*");
                txtDireccion.setBounds(direccion.getX(), direccion.getY() - 20, 200, 20);
                txtDireccion.setFont(fuente.fuente(5, false));
                txtDireccion.setForeground(Color.GRAY);
                // Agregar el TextField para ingresar la cedula
                inputDireccion = new JXTextField();
                inputDireccion.setPrompt("Calle 124 # 12 - 12");
                inputDireccion.setPromptForeground(new Color(128, 128, 128));
                inputDireccion.setFont(fuente.fuente(4, false));
                inputDireccion.setForeground(Color.BLACK);
                inputDireccion.setBounds(5, 1, nombres.getWidth() - 10, 40);
                inputDireccion.setBackground(null);
                inputDireccion.setBorder(null);
                CamposConLimite.limitarCaracteres(inputDireccion, 70);
                direccion.add(inputDireccion);
                panelDesgradado.add(txtDireccion);
                panelDesgradado.add(direccion);

                // agregar texto para el registro de usuario
                JLabel txt = new JLabel("¿TIENES CUENTA? ");
                txt.setFont(fuente.fuente(6, true));
                txt.setBounds(130, panelDesgradado.getWidth() - 20, 150, 40);
                iniciarSesion = new JLabel("Iniciar Sesion");
                iniciarSesion.setFont(fuente.fuente(6, true));
                iniciarSesion.setForeground(Color.BLUE);
                iniciarSesion.setBounds(txt.getX() + 125, txt.getY(), 150, 40);
                iniciarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
                panelDesgradado.add(txt);
                panelDesgradado.add(iniciarSesion);

                contenedorInfo.add(panelDesgradado);
                container.add(contenedorInfo);
        }

        public void limpiarCampos() {
                inputNombres.setText("");
                inputApellido.setText("");
                inputCorreoElectronico.setText("");
                inputTelefono.setText("");
                inputContrasena.setText("");
                inputConfirmarContrasena.setText("");
        }

        public boolean ValidarCampos() {
                String contrasena = new String(inputContrasena.getPassword());
                String confirmar = new String(inputConfirmarContrasena.getPassword());
                boolean pasaOno = true;
                String cadena = "<html><h1 style='color:red';>Se encontran los siguientes errores:</h1></html>";
                if (!Validaciones.validarCedula(inputNumeroDoc.getText())) {

                        cadena += "La cedula no es valida, debe ser numerica y tener 9 o 10 digitos\n";
                        pasaOno = false;
                }
                if (!Validaciones.validarSoloLetras(inputNombres.getText())) {

                        cadena += "El nombre no es valido, solo debe tener letras\n";
                        pasaOno = false;
                }
                if (!Validaciones.validarSoloLetras(inputApellido.getText())) {
                        cadena += "El apellido no es valido, solo debe tener letras\n";
                        pasaOno = false;
                }
                if (!Validaciones.validarCorreo(inputCorreoElectronico.getText())) {
                        cadena += "El correo no es valido, debe tener un formato valido( @ | . )\n";
                        pasaOno = false;
                }
                if (!Validaciones.validarTelefono(inputTelefono.getText())) {
                        cadena += "El telefono no es valido, debe tener 10 o 11 digitos y ser numerico\n";
                        pasaOno = false;
                }

                if (!Validaciones.validarDireccion(inputDireccion.getText())) {
                        cadena += "La direccion no es valida, debe tener al menos 5 caracteres, una letra y un numero\n";
                        pasaOno = false;
                }
                if (!Validaciones.validarContrasena(contrasena)) {
                        cadena += "La contrasena no es valida, debe tener 8 o mas caracteres, una mayuscula, una minuscula y un numero\n";
                        pasaOno = false;
                }

                if (!contrasena.equals(confirmar)) {
                        cadena += "Las contrasenas no coinciden\n";
                        pasaOno = false;
                }
                if (!pasaOno) {
                        JOptionPane.showMessageDialog(null, cadena, "Error de validacion",
                                        JOptionPane.WARNING_MESSAGE);
                }
                return pasaOno;
        }
}
