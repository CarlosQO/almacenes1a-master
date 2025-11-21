package vista.vistaLoginRegistro;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import org.jdesktop.swingx.JXTextField;

import vista.componentes.CamposConLimite;
import vista.componentes.OjoLabel;
import vista.componentes.RoundedJXButton;
import vista.componentes.RoundedJXPanel;
import vista.fuenteLetra.Fuente;
import vista.vistaAdministrador.RoundedPanel;
import vista.componentes.Validaciones;

public class Login extends JFrame {
    private Container container;
    private JLabel bienvenido, titleInicioSesion;
    private vista.vistaAdministrador.RoundedPanel contenedorInfo, campoNumeCedu, campoContra;
    public JXTextField inputNumero;
    public JPasswordField inputContra;
    public RoundedJXButton btnIniciarSesion;
    private RoundedJXPanel panelDesgradado;
    private Fuente fuente = new Fuente();
    public JLabel registrarse;
    private OjoLabel ojo;

    public Login() {
        super("LOGIN");
        container = getContentPane();
        setSize(1300, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        container.setBackground(Color.white);
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
        panelDesgradado.setDegradado(true, new Color(255, 255, 255), new Color(0, 210, 255, 40));
        contenedorInfo.add(panelDesgradado);

        // Agregar el titulo de bienvenido
        bienvenido = new JLabel("<html><p style='letter-spacing: 30px;'>¡ B I E N V E N I D O !</p></html>");
        bienvenido.setHorizontalAlignment(SwingConstants.CENTER);
        bienvenido.setFont(fuente.fuente(2, true));
        bienvenido.setBounds(250, 30, 500, 40);
        contenedorInfo.add(bienvenido);
        contenedorInfo.setComponentZOrder(bienvenido, 0);
        contenedorInfo.setComponentZOrder(panelDesgradado, 1);

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
        // titulo de inicio de sesion
        titleInicioSesion = new JLabel("I n i c i o  D e  S e s i ó n");
        titleInicioSesion.setBounds(170, 120, 400, 40);
        titleInicioSesion.setFont(fuente.fuente(4, false));
        panelDesgradado.add(titleInicioSesion);

        // Agregar los componentes para los JtextField
        campoNumeCedu = new RoundedPanel(20, 0xFFFFFF);
        campoNumeCedu.setLayout(null);
        campoNumeCedu.setBackground(new Color(227, 227, 227, 255));
        campoNumeCedu.setShadowSize(0);
        campoNumeCedu.setBounds(130, titleInicioSesion.getY() + 50, 300, 50);
        panelDesgradado.add(campoNumeCedu);
        // agregar el JTextField para llenar los campos
        inputNumero = new JXTextField();
        inputNumero.setPrompt("Numero de documento");
        inputNumero.setPromptForeground(new Color(128, 128, 128));
        inputNumero.setFont(fuente.fuente(4, false));
        inputNumero.setForeground(Color.BLACK);
        inputNumero.setBounds(40, 5, 245, 40);
        inputNumero.setBackground(new Color(227, 227, 227, 255));
        inputNumero.setBorder(null);
        CamposConLimite.limitarCaracteres(inputNumero, 13);
        campoNumeCedu.add(inputNumero);
        // ------------------------------- //
        campoContra = new RoundedPanel(20, 0xFFFFFF);
        campoContra.setLayout(null);
        campoContra.setBackground(new Color(227, 227, 227, 255));
        campoContra.setShadowSize(0);
        campoContra.setBounds(130, campoNumeCedu.getY() + 80, 300, 50);
        panelDesgradado.add(campoContra);
        // agregar el JTextField para llenar los campos
        inputContra = new JPasswordField();
        inputContra.setFont(fuente.fuente(4, false));
        inputContra.setForeground(Color.BLACK);
        inputContra.setBounds(40, 5, 200, 40);
        inputContra.setBackground(new Color(227, 227, 227, 255));
        inputContra.setBorder(null);
        inputContra.setColumns(14);
        CamposConLimite.agregarPlaceholder(inputContra, "contraseña");
        CamposConLimite.limitarCaracteres(inputContra, 25);
        campoContra.add(inputContra);

        ojo = new OjoLabel(inputContra);
        ojo.setBounds(inputContra.getWidth() + inputContra.getX() + 20, 15, 40, 20);
        campoContra.add(ojo);

        // agregar el boton de iniciar sesion
        btnIniciarSesion = new RoundedJXButton("Iniciar Sesión");
        btnIniciarSesion.setCornerRadius(20);
        btnIniciarSesion.setFont(fuente.fuente(4, true));
        btnIniciarSesion.setBackground(new Color(86, 179, 255));
        btnIniciarSesion.setHoverColor(new Color(0, 140, 255));
        btnIniciarSesion.setBounds(160, campoContra.getY() + 80, 240, 40);
        btnIniciarSesion.setForeground(Color.white);
        getRootPane().setDefaultButton(btnIniciarSesion);
        panelDesgradado.add(btnIniciarSesion);

        // agregar texto para el registro de usuario
        JLabel txt = new JLabel("¿NO TIENES CUENTA? ");
        txt.setFont(fuente.fuente(6, true));
        txt.setBounds(160, panelDesgradado.getWidth() - 20, 150, 40);
        registrarse = new JLabel("Registrarse");
        registrarse.setFont(fuente.fuente(6, true));
        registrarse.setForeground(Color.BLUE);
        registrarse.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registrarse.setBounds(txt.getX() + 150, txt.getY(), 150, 40);
        panelDesgradado.add(txt);
        panelDesgradado.add(registrarse);

        container.add(contenedorInfo);
    }

    public boolean validarCampos() {
        String contrasena = new String(inputContra.getPassword());
        if (!Validaciones.validarCedula(inputNumero.getText())) {
            JOptionPane.showMessageDialog(null,
                    "La cedula no es valida, debe ser numerica y tener 9 o 10 digitos", "Cédula inválida",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (!Validaciones.validarContrasena(contrasena)) {
            JOptionPane.showMessageDialog(null,
                    "La contrasena no es valida, debe tener 8 o mas caracteres, una mayuscula, una minuscula y un numero",
                    "",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public void limpiarCampos() {
        inputNumero.setText("");
        inputContra.setText("");
    }
}
