package vista.vistaCliente;

import controladorCliente.*;
import static vista.componentes.RoundedPanel.*;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import vista.componentes.RoundedButton;

public class PanelPrincipal extends JFrame {
    public JPanel panel, barra;
    public JButton catalogo, actividad, pedidos;
    public JButton carrito, cerrarCarrito;
    public JButton seguimientoOpcion, historialDeComprasOpcion;
    public DefaultTableModel modeloProductos;
    public JPanel panelCentroContenido, panelActividadCentrado, panelHistorialCentrado, panelSeguimientoCentrado;
    public JPanel panelTarjetasProductos, panelTarjetasPromociones;
    private JLabel lbllogo;
    public JPanel carritoContenedor, contenedorTarjetasCorritas, panelCuandoAyPromociones;
    public RoundedButton btnLimpiarCarrito, btnComprarCarrito;
    public RoundedButton btnGenerarReporteDeactividad;
    public JLabel lblValorTotal;
    public JLayeredPane layeredPane;
    public JPanel panelMenu, menuPerfil;
    public JButton btnPerfil, btnActualizarInfo, btnPQRS, btnCerrarSesion;

    public PanelPrincipal() throws IOException {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        barra = new JPanel();
        barra.setLayout(null);
        barra.setBounds(0, 0, 1300, 60);
        barra.setBackground(new Color(238, 238, 238));
        panel.add(barra);

        // para que se uetsre encima
        layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 1300, 700);

        // colocar logo y perfil a la izquierda
        BufferedImage logoOriginal = ImageIO.read(new File("src/Iconos/LogoAlmacen1A.png"));
        ImageIcon logo = resizeImage(logoOriginal, 60, 60);
        lbllogo = new JLabel(logo);
        lbllogo.setLayout(null);
        lbllogo.setBounds(5, 5, 60, 60);
        barra.add(lbllogo);
        // icon
        BufferedImage userImagenOriginal = ImageIO.read(new File("src/Iconos/user.png"));
        ImageIcon userImagen = resizeImage(userImagenOriginal, 50, 50);

        btnPerfil = new JButton(userImagen);// boton para actualizar perfil, cerrarcesion y pqrs
        btnPerfil.setBorderPainted(false);
        btnPerfil.setFocusPainted(false);
        btnPerfil.setContentAreaFilled(false);
        btnPerfil.setBounds(75, 5, 100, 50);
        barra.add(btnPerfil);

        menuPerfil = new JPanel();
        menuPerfil.setLayout(null);
        menuPerfil.setBackground(Color.WHITE);
        menuPerfil.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        menuPerfil.setBounds(75, 50, 150, 240);
        menuPerfil.setVisible(false);
        // Foto de usuario
        ImageIcon userImagenFoto = resizeImage(userImagenOriginal, 60, 60);
        JLabel lbFotoUser = new JLabel(userImagenFoto, SwingConstants.CENTER);
        lbFotoUser.setBounds(45, 15, 60, 60);
        lbFotoUser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        menuPerfil.add(lbFotoUser);
        // Botn Actualizar
        btnActualizarInfo = new JButton("Actualizar");
        btnActualizarInfo.setBounds(15, 90, 120, 30);
        estilizarBotonTransparente(btnActualizarInfo);
        menuPerfil.add(btnActualizarInfo);
        // Boton PQRS
        btnPQRS = new JButton("PQRS");
        btnPQRS.setBounds(15, 130, 120, 30);
        estilizarBotonTransparente(btnPQRS);
        menuPerfil.add(btnPQRS);
        // Cerrar Sesion
        btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setBounds(15, 170, 120, 30);
        estilizarBotonTransparente(btnCerrarSesion);
        menuPerfil.add(btnCerrarSesion);
        layeredPane.add(menuPerfil, JLayeredPane.POPUP_LAYER);

        // menu Central
        catalogo = new JButton("Catalogo");
        catalogo.setLayout(null);
        catalogo.setBounds(350, 15, 200, 30);
        catalogo.setBorderPainted(false);
        catalogo.setContentAreaFilled(false);
        catalogo.setFocusPainted(false);
        catalogo.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        barra.add(catalogo);

        actividad = new JButton("Actividad");
        actividad.setLayout(null);
        actividad.setBounds(550, 15, 200, 30);
        actividad.setBorderPainted(false);
        actividad.setContentAreaFilled(false);
        actividad.setFocusPainted(false);
        actividad.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        barra.add(actividad);

        Color grisClaroBotones = new Color(207, 207, 207);
        // pedidos
        pedidos = new JButton("Mis Pedidos");
        pedidos.setLayout(null);
        pedidos.setBounds(750, 5, 210, 50);
        pedidos.setBackground(new Color(238, 238, 238));
        pedidos.setBorderPainted(false);
        // pedidos.setContentAreaFilled(false);
        pedidos.setFocusPainted(false);
        pedidos.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        barra.add(pedidos);

        // Panel que simula el menú de opciones
        panelMenu = new JPanel();
        panelMenu.setLayout(null);
        panelMenu.setBackground(new Color(220, 220, 220));
        panelMenu.setBounds(750, 60, 210, 100);
        panelMenu.setVisible(false);

        // Botones del "menú"
        seguimientoOpcion = new JButton("Seguimiento");
        seguimientoOpcion.setBackground(grisClaroBotones);
        seguimientoOpcion.setBorderPainted(false);
        seguimientoOpcion.setBorderPainted(false);
        seguimientoOpcion.setFont(new Font("Times New Roman", Font.PLAIN, 19));
        seguimientoOpcion.setBounds(0, 0, 210, 47);

        historialDeComprasOpcion = new JButton("Historial de Compras");
        historialDeComprasOpcion.setBackground(grisClaroBotones);
        historialDeComprasOpcion.setBorderPainted(false);
        historialDeComprasOpcion.setBorderPainted(false);
        historialDeComprasOpcion.setFont(new Font("Times New Roman", Font.PLAIN, 19));
        historialDeComprasOpcion.setBounds(0, 50, 210, 58);

        // Agregar botones al panelMenu
        panelMenu.add(seguimientoOpcion);
        panelMenu.add(historialDeComprasOpcion);
        panelMenu.setVisible(false);

        // Agregar panelMenu a la barra
        panel.add(panelMenu);
        // carrito
        BufferedImage carritoImagenOriginal = ImageIO.read(new File("src/Iconos/carrito.png"));
        ImageIcon carritoImagen = resizeImage(carritoImagenOriginal, 40, 40);
        carrito = new JButton(carritoImagen);
        carrito.setLayout(null);
        carrito.setBounds(1220, 5, 50, 50);
        estilizarBotonTransparente(carrito);
        barra.add(carrito);

        // panel central Catalogo
        panelCentroContenido = new JPanel();
        panelCentroContenido.setLayout(null);
        panelCentroContenido.setBounds(150, 60, 1000, 600);
        panelCentroContenido.setBackground(Color.WHITE);
        panel.add(panelCentroContenido);

        // boton generar reporte
        btnGenerarReporteDeactividad = new RoundedButton("Generar Reporte pdf", grisClaroBotones);
        btnGenerarReporteDeactividad.setLayout(null);
        btnGenerarReporteDeactividad.setVisible(false);
        btnGenerarReporteDeactividad.setBounds(800, 270, 150, 30);

        // carrito: en el controlador se ve visible
        carritoContenedor = new JPanel();
        carritoContenedor.setBackground(Color.WHITE);
        carritoContenedor.setLayout(null);
        carritoContenedor.setBounds(900, 65, 370, 500);
        carritoContenedor.setPreferredSize(new Dimension(400, 500));
        // cerrar
        cerrarCarrito = new JButton("X");
        cerrarCarrito.setBorderPainted(false);
        cerrarCarrito.setContentAreaFilled(false);
        cerrarCarrito.setLayout(null);
        cerrarCarrito.setBounds(0, 0, 70, 70);
        carritoContenedor.add(cerrarCarrito);
        // evento para cerrar

        cerrarCarrito.addActionListener(e -> {
            carritoContenedor.setVisible(false);
            panelCentroContenido.revalidate();
            panelCentroContenido.repaint();
        });

        // Título "Mi Carrito"
        JLabel lblTitulo = new JLabel("Mi Carrito");
        lblTitulo.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lblTitulo.setBounds(140, 20, 200, 30);
        carritoContenedor.add(lblTitulo);
        // Etiqueta "Precio Total"
        JLabel lblPrecioTotal = new JLabel("Precio Total:");
        lblPrecioTotal.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblPrecioTotal.setBounds(40, 420, 150, 30);
        carritoContenedor.add(lblPrecioTotal);

        // Botones personalizados
        btnComprarCarrito = new RoundedButton("Finalizar compra", new Color(76, 175, 80));
        btnComprarCarrito.setFont(new Font("Times New Roman", Font.BOLD, 14));
        btnComprarCarrito.setBounds(20, 360, 150, 40);
        btnComprarCarrito.setForeground(Color.WHITE);
        btnComprarCarrito.setFocusable(false);

        btnLimpiarCarrito = new RoundedButton("Limpiar carrito", new Color(244, 67, 54));
        btnLimpiarCarrito.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        btnLimpiarCarrito.setBounds(200, 360, 150, 40);
        btnLimpiarCarrito.setForeground(Color.WHITE);
        btnLimpiarCarrito.setFocusable(false);

        carritoContenedor.add(btnComprarCarrito);
        carritoContenedor.add(btnLimpiarCarrito);
        carritoContenedor.setVisible(false);

        panel.add(layeredPane);
        layeredPane.add(panelCentroContenido, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(carritoContenedor, JLayeredPane.POPUP_LAYER);

        this.add(panel);
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // Mtodo para quitar fondo y borde de botones
    private void estilizarBotonTransparente(JButton boton) {
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setContentAreaFilled(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

}