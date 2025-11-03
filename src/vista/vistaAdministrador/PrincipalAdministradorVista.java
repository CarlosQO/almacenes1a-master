package vista.vistaAdministrador;

import static vista.vistaAdministrador.RoundedPanel.resizeImage;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import vista.componentes.ScrollPersonalizado;

public class PrincipalAdministradorVista extends JFrame {

        private Container container;
        private JPanel principal, histoContain, proveeContain;
        private RoundedPanel principalContainer, header, cuerpo;
        private RoundedPanel historiVentaPeri, historiTendeCompra, carritoContain;
        private JLabel icono, user, titulo, permisos, promociones, reportes, historicos, proveedores, cerrarSesion;
        private RoundedPanel listaProvee, aprobarProvee, contratoProvee, modificarInfo, deshabiliProvee,
                        habilitarProvee;
        public RoundedPanel confiRol, promoContain, producMasVendi, producMenVendi,
                        informeGlobal, histoVentas;
        public JLabel actualizarPerfil;
        private static boolean carritoBooleano = false;

        public PrincipalAdministradorVista() throws IOException {
                super("Almacenes 1A");

                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setSize(1300, 700);
                setLocationRelativeTo(null);

                container = getContentPane();
                // Panel principal
                principal = new JPanel();
                principal.setPreferredSize(new Dimension(1300, 700));
                principal.setLayout(null);
                principal.setBackground(Color.WHITE);
                // Definir el tamaño del panel hijo
                int ancho = 1220;
                int alto = 620;

                principalContainer = new RoundedPanel(20, 0xCEC6C6);
                principalContainer.setLayout(null);
                principalContainer.setBackground(new Color(0xF8FAFE));
                // principalContainer.setRoundAllCorners(false); //para border superiores
                principalContainer.setShadowColor(new Color(150, 150, 150, 10));
                // Márgenes alrededor
                int margenHorizontal = (principal.getPreferredSize().width - ancho) / 2;
                int margenVertical = (principal.getPreferredSize().height - alto) / 2;

                // Ubicarlo centrado
                principalContainer.setBounds(margenHorizontal, margenVertical - 20, ancho, alto);

                // Agregar el header de la principalContainer
                header = new RoundedPanel(20, 0xFFFFFF);
                header.setLayout(null);
                header.setBackground(Color.WHITE);
                header.setShadowSize(0);
                header.setRoundAllCorners(2);
                header.setBounds(22, 22, (int) principalContainer.getPreferredSize().getWidth() - 43, 80);

                // carrito de compra
                carritoContain = new RoundedPanel(40, 0x9E9C9C);
                carritoContain.setBackground(Color.white);
                carritoContain.setRoundAllCorners(3);
                carritoContain.setLayout(null);
                carritoContain.setShadowSize(1);
                carritoContain.setBounds(principalContainer.getWidth() - 230, header.getY() + header.getHeight(), 200,
                                250);

                JLabel miPerfil = new JLabel("Mi Perfil");
                miPerfil.setFont(fuente(false));
                miPerfil.setBounds(70, 10, (int) miPerfil.getPreferredSize().getWidth(),
                                (int) miPerfil.getPreferredSize().getHeight());
                carritoContain.add(miPerfil);

                JLabel equis = new JLabel("<html><p style='font-Size: 20px;'>X</p></html>");
                equis.setFont(fuente(false));
                equis.setBounds(carritoContain.getWidth() - (int) equis.getPreferredSize().getWidth() - 10, 0,
                                (int) equis.getPreferredSize().getWidth(), (int) equis.getPreferredSize().getHeight());
                equis.setCursor(new Cursor(Cursor.HAND_CURSOR));
                equis.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                                carritoContain.setVisible(false);
                                carritoBooleano = false;
                                repaint();
                                revalidate();
                        }
                });
                carritoContain.add(equis);

                // agregar el icono user a mi perfil
                BufferedImage originalUserCarr = ImageIO.read(new File("src/Iconos/user.png"));
                ImageIcon imagenUserCarr = resizeImage(originalUserCarr, 70, 70);
                JLabel userCarrito = new JLabel(imagenUserCarr);
                userCarrito.setBounds(70, 30, 80, 80);
                carritoContain.add(userCarrito);

                // agregar el actualizar mi perfil}
                actualizarPerfil = new JLabel("Actualizar mi Perfil");
                actualizarPerfil.setFont(fuente(false));
                actualizarPerfil.setCursor(new Cursor(Cursor.HAND_CURSOR));
                actualizarPerfil.setBounds(20, 120, (int) actualizarPerfil.getPreferredSize().getWidth(),
                                (int) actualizarPerfil.getPreferredSize().getHeight());
                carritoContain.add(actualizarPerfil);

                // agregar cerrar sesion
                cerrarSesion = new JLabel("Cerrar Sesión");
                cerrarSesion.setFont(fuente(false));
                cerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
                cerrarSesion.setBounds(20, (int) (carritoContain.getHeight() * 0.8),
                                (int) cerrarSesion.getPreferredSize().getWidth(),
                                (int) cerrarSesion.getPreferredSize().getHeight());
                carritoContain.add(cerrarSesion);

                carritoContain.setVisible(false);
                principalContainer.add(carritoContain);

                // añadir el icono del almacen de la imagen
                BufferedImage originalIcon = ImageIO.read(new File("src/Iconos/LogoMejorado.png"));
                ImageIcon imagen = resizeImage(originalIcon, 70, 70);
                icono = new JLabel(imagen);
                icono.setBounds(20, 15, 70, 70);
                header.add(icono);

                // añadir el icono de usuario
                BufferedImage originalUser = ImageIO.read(new File("src/Iconos/user.png"));
                ImageIcon imagenUser = resizeImage(originalUser, 60, 60);
                user = new JLabel(imagenUser);
                user.setCursor(new Cursor(Cursor.HAND_CURSOR));
                user.setBounds((int) header.getPreferredSize().getWidth() - 90, 15, 60, 60);
                header.add(user);
                user.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                                if (!carritoBooleano) {
                                        carritoContain.setVisible(true);
                                        carritoBooleano = true;
                                } else {
                                        carritoContain.setVisible(false);
                                        carritoBooleano = false;
                                }
                                repaint();
                                revalidate();
                        }
                });
                // añadir el header
                principalContainer.add(header);

                // añadir el titulo
                titulo = new JLabel("Almacenes 1A");
                try {
                        Font customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/fonts/newCentury.ttf"))
                                        .deriveFont(Font.BOLD, 40);
                        titulo.setFont(customFont);
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "No se logro cargar la fuente de la letras");
                }

                titulo.setBounds(60, 150, (int) titulo.getPreferredSize().getWidth() + 40,
                                (int) titulo.getPreferredSize().getHeight());
                principalContainer.add(titulo);

                // añadir el cuerpo que va a contener los modulos del administrador
                cuerpo = new RoundedPanel(20, 0x9E9C9C);
                cuerpo.setShadowSize(1);
                cuerpo.setLayout(null);
                cuerpo.setBackground(Color.WHITE);
                cuerpo.setBounds(60, 210, ancho - 120, 350);

                // añadir el titulo del modulos de permisos
                permisos = new JLabel("Permisos");
                permisos.setFont(fuente(true));
                permisos.setBounds(20, 10, (int) titulo.getPreferredSize().getWidth() + 40,
                                (int) titulo.getPreferredSize().getHeight());
                cuerpo.add(permisos);

                // añadir el modulo de permisos y el nombre Jlabel Configurar Rol
                confiRol = new RoundedPanel(20, 0x9E9C9C);
                confiRol.setShadowSize(1);
                confiRol.setLayout(null);
                confiRol.setBackground(Color.WHITE);
                JLabel nomConfigRol = new JLabel(
                                "<html><span style='letter-spacing: 5px;'>Configurar Rol</span></html>");
                nomConfigRol.setFont(fuente(false));
                confiRol.setBounds(20, (int) (10 + permisos.getPreferredSize().getHeight() + 10),
                                (int) permisos.getPreferredSize().getWidth() + 80, 100);
                nomConfigRol.setBounds(50, (int) confiRol.getPreferredSize().getHeight() - 35,
                                (int) nomConfigRol.getPreferredSize().getWidth() + 20,
                                (int) nomConfigRol.getPreferredSize().getHeight());
                confiRol.add(nomConfigRol);
                confiRol.setCursor(new Cursor(Cursor.HAND_CURSOR));
                cuerpo.add(confiRol);

                // añadir el titulo del modulo de Promociones
                promociones = new JLabel("Promociones");
                promociones.setFont(fuente(true));
                promociones.setBounds((int) confiRol.getPreferredSize().getWidth() + 40, 13,
                                (int) promociones.getPreferredSize().getWidth() + 40,
                                (int) promociones.getPreferredSize().getHeight());
                cuerpo.add(promociones);

                // añadir el modulo de promociones y el nombre Jlabel Promoción
                promoContain = new RoundedPanel(20, 0x9E9C9C);
                promoContain.setCursor(new Cursor(Cursor.HAND_CURSOR));
                promoContain.setShadowSize(1);
                promoContain.setLayout(null);
                promoContain.setBackground(Color.WHITE);
                JLabel nomPromo = new JLabel("<html><span style='letter-spacing: 5px;'>Promoción</span></html>");
                nomPromo.setFont(fuente(false));
                promoContain.setBounds((int) confiRol.getPreferredSize().getWidth() + 40,
                                (int) (10 + permisos.getPreferredSize().getHeight() + 10),
                                (int) confiRol.getPreferredSize().getWidth() + 20, 100);
                nomPromo.setBounds(75, (int) promoContain.getPreferredSize().getHeight() - 35,
                                (int) nomPromo.getPreferredSize().getWidth() + 20,
                                (int) nomPromo.getPreferredSize().getHeight());
                promoContain.add(nomPromo);
                cuerpo.add(promoContain);

                // añadir el titulo del modulo de Reportes
                reportes = new JLabel("Reportes");
                reportes.setFont(fuente(true));
                reportes.setBounds((int) (cuerpo.getPreferredSize().getWidth() / 2) - 40, 13,
                                (int) promociones.getPreferredSize().getWidth() + 40,
                                (int) promociones.getPreferredSize().getHeight());
                cuerpo.add(reportes);

                // añadir los modulos de reportes - Producto mas vendido
                producMasVendi = new RoundedPanel(20, 0x9E9C9C);
                producMasVendi.setShadowSize(1);
                producMasVendi.setLayout(null);
                producMasVendi.setBackground(Color.WHITE);
                producMasVendi.setCursor(new Cursor(Cursor.HAND_CURSOR));
                JLabel nomProdMasVen = new JLabel(
                                "<html><p style='letter-spacing: 5px; text-align: center;'>Producto mas vendido</p></html>");
                nomProdMasVen.setFont(fuente(false));
                // JOptionPane.showMessageDialog(null, cuerpo.getWidth()+" |
                // "+(promoContain.getX()+promoContain.getWidth())+" | "+reportes.getX());
                producMasVendi.setBounds((int) reportes.getX(),
                                (int) (10 + permisos.getPreferredSize().getHeight() + 10), 180,
                                100);
                nomProdMasVen.setBounds(35, (int) producMasVendi.getHeight() - 55,
                                (int) nomProdMasVen.getPreferredSize().getWidth() - 50,
                                (int) nomProdMasVen.getPreferredSize().getHeight() + 40);
                producMasVendi.add(nomProdMasVen);
                cuerpo.add(producMasVendi);

                // añadir los modulos de reportes - producto menos Vendido
                producMenVendi = new RoundedPanel(20, 0x9E9C9C);
                producMenVendi.setCursor(new Cursor(Cursor.HAND_CURSOR));
                producMenVendi.setShadowSize(1);
                producMenVendi.setLayout(null);
                producMenVendi.setBackground(Color.WHITE);
                JLabel nomProdMenVen = new JLabel(
                                "<html><p style='letter-spacing: 5px; text-align: center;'>Producto menos vendido</p></html>");
                nomProdMenVen.setFont(fuente(false));
                producMenVendi.setBounds((int) (reportes.getX() + producMasVendi.getWidth() + 20),
                                (int) (10 + permisos.getPreferredSize().getHeight() + 10), 180, 100);
                nomProdMenVen.setBounds(25, (int) producMasVendi.getHeight() - 55,
                                (int) nomProdMenVen.getPreferredSize().getWidth() - 50,
                                (int) nomProdMenVen.getPreferredSize().getHeight() + 40);
                producMenVendi.add(nomProdMenVen);
                cuerpo.add(producMenVendi);

                // añadir los modulos de reportes - Informe Globales
                informeGlobal = new RoundedPanel(20, 0x9E9C9C);
                informeGlobal.setShadowSize(1);
                informeGlobal.setLayout(null);
                informeGlobal.setCursor(new Cursor(Cursor.HAND_CURSOR));
                informeGlobal.setBackground(Color.WHITE);
                JLabel nomInfoGlobal = new JLabel(
                                "<html><p style='letter-spacing: 5px; text-align: center;'>Informes Globales</p></html>");
                nomInfoGlobal.setFont(fuente(false));
                informeGlobal.setBounds((int) (producMenVendi.getX() + producMenVendi.getWidth() + 20),
                                (int) (10 + permisos.getPreferredSize().getHeight() + 10), 170, 100);
                nomInfoGlobal.setBounds(50, (int) producMasVendi.getHeight() - 55,
                                (int) nomInfoGlobal.getPreferredSize().getWidth() - 50,
                                (int) nomInfoGlobal.getPreferredSize().getHeight() + 40);
                informeGlobal.add(nomInfoGlobal);
                cuerpo.add(informeGlobal);

                // añadir el titulo del modulo de Históricos - histoVentas, historiVentaPeri,
                // historiTendeCompra;
                historicos = new JLabel("Históricos                 >");
                historicos.setFont(fuente(true));
                historicos.setBounds(20, (int) (confiRol.getY() + confiRol.getHeight() + 20),
                                (int) titulo.getPreferredSize().getWidth() + 40,
                                (int) titulo.getPreferredSize().getHeight());
                cuerpo.add(historicos);

                // añadir el container para los modulos de historicos con el scroll
                histoContain = new JPanel();
                histoContain.setLayout(null);
                histoContain.setBackground(Color.WHITE);
                histoContain.setPreferredSize(new Dimension(600, 100));

                // 385 ancho de histoContain 180
                // añadir los modulos a histoContain - histoVentas, historiVentaPeri,
                // historiTendeCompra;
                // histoVentas
                histoVentas = new RoundedPanel(20, 0x9E9C9C);
                histoVentas.setShadowSize(1);
                histoVentas.setLayout(null);
                histoVentas.setBackground(Color.WHITE);
                histoVentas.setCursor(new Cursor(Cursor.HAND_CURSOR));
                JLabel nomHistoVen = new JLabel(
                                "<html><p style='letter-spacing: 5px; text-align: center;'>Historial de ventas</p></html>");
                nomHistoVen.setFont(fuente(false));
                histoVentas.setBounds(0, 0, 180, 100);
                nomHistoVen.setBounds(55, (int) producMasVendi.getHeight() - 55,
                                (int) nomHistoVen.getPreferredSize().getWidth() - 50,
                                (int) nomHistoVen.getPreferredSize().getHeight() + 40);
                histoVentas.add(nomHistoVen);
                histoContain.add(histoVentas);

                // historiVentaPeri
                historiVentaPeri = new RoundedPanel(20, 0x9E9C9C);
                historiVentaPeri.setShadowSize(1);
                historiVentaPeri.setLayout(null);
                historiVentaPeri.setBackground(Color.WHITE);
                JLabel nomHistoriVenPer = new JLabel(
                                "<html><p style='letter-spacing: 5px; text-align: center;'>Históricos Ventas por Periodo</p></html>");
                nomHistoriVenPer.setFont(fuente(false));
                historiVentaPeri.setBounds((int) (histoVentas.getX() + histoVentas.getWidth() + 20), 0, 180, 100);
                nomHistoriVenPer.setBounds(10, (int) producMasVendi.getHeight() - 55,
                                (int) nomHistoriVenPer.getPreferredSize().getWidth() - 50,
                                (int) nomHistoriVenPer.getPreferredSize().getHeight() + 40);
                historiVentaPeri.add(nomHistoriVenPer);
                histoContain.add(historiVentaPeri);

                // historiTendeCompra
                historiTendeCompra = new RoundedPanel(20, 0x9E9C9C);
                historiTendeCompra.setShadowSize(1);
                historiTendeCompra.setLayout(null);
                historiTendeCompra.setBackground(Color.WHITE);
                JLabel nomHistoriTenPer = new JLabel(
                                "<html><p style='letter-spacing: 5px; text-align: center;'>Históricos de tendencia de compras</p></html>");
                nomHistoriTenPer.setFont(fuente(false));
                historiTendeCompra.setBounds((int) (historiVentaPeri.getX() + historiVentaPeri.getWidth() + 20), 0, 180,
                                100);
                nomHistoriTenPer.setBounds(5, (int) producMasVendi.getHeight() - 55,
                                (int) nomHistoriTenPer.getPreferredSize().getWidth() - 80,
                                (int) nomHistoriTenPer.getPreferredSize().getHeight() + 40);
                historiTendeCompra.add(nomHistoriTenPer);
                histoContain.add(historiTendeCompra);

                // Crear scroll personalizado para historicos
                ScrollPersonalizado scrollHist = new ScrollPersonalizado(histoContain, "horizontal",
                                (int) (cuerpo.getWidth() * 0.35),
                                100);
                scrollHist.setBounds(20, historicos.getY() + historicos.getHeight() + 10,
                                (int) (cuerpo.getWidth() * 0.35),
                                100);

                cuerpo.add(scrollHist);
                cuerpo.revalidate();
                cuerpo.repaint();

                // añadir el titulo del modulo de proveedores
                proveedores = new JLabel("Proveedores                                                   >");
                proveedores.setFont(fuente(true));
                proveedores.setBounds((int) (scrollHist.getX() + scrollHist.getWidth() + 20), historicos.getY() + 8,
                                655,
                                (int) proveedores.getPreferredSize().getHeight());
                cuerpo.add(proveedores);

                // añadir el container para los modulos de historicos con el scroll
                proveeContain = new JPanel();
                proveeContain.setLayout(null);
                proveeContain.setBackground(Color.white);
                proveeContain.setPreferredSize(new Dimension(950, 100));

                // añadir los modulo para con contenedor proveeContain
                // listaProvee, aprobarProvee, contratoProvee, modificarInfo, deshabiliProvee,
                // habilitarProvee
                listaProvee = new RoundedPanel(20, 0x9E9C9C);
                listaProvee.setShadowSize(1);
                listaProvee.setLayout(null);
                listaProvee.setBackground(Color.white);
                JLabel nomListProvee = new JLabel(
                                "<html><p style='letter-spacing: 5px; text-align: center;'>Lista de proveedores</p></html>");
                nomListProvee.setFont(fuente(false));
                listaProvee.setBounds(0, 0, 150, 100);
                nomListProvee.setBounds(20, listaProvee.getHeight() - 55,
                                (int) nomListProvee.getPreferredSize().getWidth() - 40,
                                (int) nomListProvee.getPreferredSize().getHeight() + 40);
                listaProvee.add(nomListProvee);
                proveeContain.add(listaProvee);

                // aprobarProvee
                aprobarProvee = new RoundedPanel(20, 0x9E9C9C);
                aprobarProvee.setShadowSize(1);
                aprobarProvee.setLayout(null);
                aprobarProvee.setBackground(Color.white);
                JLabel nomAproProvee = new JLabel(
                                "<html><p style='letter-spacing: 5px; text-align: center;'>Aprobar proveedores</p></html>");
                nomAproProvee.setFont(fuente(false));
                aprobarProvee.setBounds((int) (listaProvee.getX() + listaProvee.getWidth() + 10), 0, 150, 100);
                nomAproProvee.setBounds(20, aprobarProvee.getHeight() - 55,
                                (int) nomAproProvee.getPreferredSize().getWidth() - 40,
                                (int) nomAproProvee.getPreferredSize().getHeight() + 40);
                aprobarProvee.add(nomAproProvee);
                proveeContain.add(aprobarProvee);

                // contratoProvee
                contratoProvee = new RoundedPanel(20, 0x9E9C9C);
                contratoProvee.setShadowSize(1);
                contratoProvee.setLayout(null);
                contratoProvee.setBackground(Color.white);
                JLabel nomContraProvee = new JLabel(
                                "<html><p style='letter-spacing: 5px; text-align: center;'>Contrato de proveedores</p></html>");
                nomContraProvee.setFont(fuente(false));
                contratoProvee.setBounds((int) (aprobarProvee.getX() + aprobarProvee.getWidth() + 10), 0, 150, 100);
                nomContraProvee.setBounds(10, contratoProvee.getHeight() - 55,
                                (int) nomContraProvee.getPreferredSize().getWidth() - 40,
                                (int) nomContraProvee.getPreferredSize().getHeight() + 40);
                contratoProvee.add(nomContraProvee);
                proveeContain.add(contratoProvee);

                // modificarInfo
                modificarInfo = new RoundedPanel(20, 0x9E9C9C);
                modificarInfo.setShadowSize(1);
                modificarInfo.setLayout(null);
                modificarInfo.setBackground(Color.white);
                JLabel nomModiInfo = new JLabel(
                                "<html><p style='letter-spacing: 5px; text-align: center;'>Modificar Información</p></html>");
                nomModiInfo.setFont(fuente(false));
                modificarInfo.setBounds((int) (contratoProvee.getX() + contratoProvee.getWidth() + 10), 0, 150, 100);
                nomModiInfo.setBounds(20, modificarInfo.getHeight() - 55,
                                (int) nomModiInfo.getPreferredSize().getWidth() - 40,
                                (int) nomModiInfo.getPreferredSize().getHeight() + 40);
                modificarInfo.add(nomModiInfo);
                proveeContain.add(modificarInfo);

                // deshabiliProvee
                deshabiliProvee = new RoundedPanel(20, 0x9E9C9C);
                deshabiliProvee.setShadowSize(1);
                deshabiliProvee.setLayout(null);
                deshabiliProvee.setBackground(Color.white);
                JLabel nomDeshabiProvee = new JLabel(
                                "<html><p style='letter-spacing: 5px; text-align: center;'>Deshabilitar proveedor</p></html>");
                nomDeshabiProvee.setFont(fuente(false));
                deshabiliProvee.setBounds((int) (modificarInfo.getX() + modificarInfo.getWidth() + 10), 0, 150, 100);
                nomDeshabiProvee.setBounds(20, deshabiliProvee.getHeight() - 55,
                                (int) nomDeshabiProvee.getPreferredSize().getWidth() - 40,
                                (int) nomDeshabiProvee.getPreferredSize().getHeight() + 40);
                deshabiliProvee.add(nomDeshabiProvee);
                proveeContain.add(deshabiliProvee);

                // habilitarProvee
                habilitarProvee = new RoundedPanel(20, 0x9E9C9C);
                habilitarProvee.setShadowSize(1);
                habilitarProvee.setLayout(null);
                habilitarProvee.setBackground(Color.white);
                JLabel nomHabiProvee = new JLabel(
                                "<html><p style='letter-spacing: 5px; text-align: center;'>Habilitar proveedor</p></html>");
                nomHabiProvee.setFont(fuente(false));
                habilitarProvee.setBounds((int) (deshabiliProvee.getX() + deshabiliProvee.getWidth() + 10), 0, 150,
                                100);
                nomHabiProvee.setBounds(30, habilitarProvee.getHeight() - 55,
                                (int) nomHabiProvee.getPreferredSize().getWidth() - 40,
                                (int) nomHabiProvee.getPreferredSize().getHeight() + 40);
                habilitarProvee.add(nomHabiProvee);
                proveeContain.add(habilitarProvee);

                // Crear scroll personalizado para Proveedores
                ScrollPersonalizado scrollProvee = new ScrollPersonalizado(proveeContain, "horizontal", 655, 100);
                scrollProvee.setBounds((int) (scrollHist.getX() + scrollHist.getWidth() + 20),
                                historicos.getY() + historicos.getHeight() + 10, 655, 100);
                cuerpo.add(scrollProvee);

                principalContainer.add(cuerpo);
                // Añadir al principal
                principal.add(principalContainer);
                container.add(principal);
        }

        // true para Titulos false PARA Nombre pequeños
        public Font fuente(boolean b) {
                try {
                        if (b) {
                                Font customFont = Font
                                                .createFont(Font.TRUETYPE_FONT, new File("src/fonts/newCentury.ttf"))
                                                .deriveFont(Font.BOLD, 30);
                                return customFont;
                        } else {
                                Font customFont = Font
                                                .createFont(Font.TRUETYPE_FONT, new File("src/fonts/newCentury.ttf"))
                                                .deriveFont(Font.BOLD, 15);
                                return customFont;
                        }
                } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "No se logro cargar la fuente de las letras");
                        return UIManager.getFont("Label.font");
                }
        }
}
