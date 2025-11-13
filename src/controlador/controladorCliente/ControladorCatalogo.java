package controladorCliente;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import modelo.crudBancos.*;
import modelo.crudCarrito.*;
import modelo.crudDocumento.*;
import modelo.crudMetodoDePago.*;
import modelo.crudProducto.*;
import modelo.crudPromociones.*;
import modelo.pasarelaDePagosModelo.*;
import modelo.crudCategoriasProductos.*;
import vista.componentes.*;
import vista.vistaCliente.PanelPrincipal;
import vista.vistaCliente.pasarelaVista.BilleterElectronica;
import vista.vistaCliente.pasarelaVista.Consignacion;
import vista.vistaCliente.pasarelaVista.PasarelaPagosVista;
import vista.vistaCliente.pasarelaVista.Tarjetas;
import vista.vistaCliente.tarjetas.*;

public class ControladorCatalogo implements ActionListener {
    private JFrame frame;
    private PanelPrincipal panelPrincipal;
    private static int idUsuario = 1002;
    private DaoCarrito daoCarrito;
    private ProductoDao daoProducto;
    private ScrollPersonalizado scrollPersonalizado, scrollPromociones;
    private PasarelaPagosVista pasarela;
    private Tarjetas tarjeta;
    private Consignacion tarjetaConsignacion;
    private BilleterElectronica tarjetaBilletera;
    private Factory factory;
    private PromocionDao daoPromociones;
    private MetodoPagoDao daoMetodoPago;

    public ControladorCatalogo(PanelPrincipal panelPrincipal) {
        this.panelPrincipal = panelPrincipal;
        panelPrincipal.catalogo.addActionListener(this);
        panelPrincipal.carrito.addActionListener(this);
        panelPrincipal.btnLimpiarCarrito.addActionListener(this);
        panelPrincipal.btnComprarCarrito.addActionListener(this);
        panelPrincipal.contenedorTarjetasCorritas = new JPanel();
        panelPrincipal.lblValorTotal = new JLabel();
        factory = new Factory();
        daoProducto = new ProductoDao();
        daoCarrito = new DaoCarrito();
        daoPromociones = new PromocionDao();
        daoMetodoPago = new MetodoPagoDao();

        actualizarPromocionesSiExisten();
        cargarProductos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        panelPrincipal.panelCentroContenido.setBackground(Color.WHITE);
        panelPrincipal.setVisible(true);
        panelPrincipal.contenedorTarjetasCorritas.removeAll();

        if (e.getSource() == panelPrincipal.catalogo) {
            panelPrincipal.panelCentroContenido.removeAll();
            panelPrincipal.carritoContenedor.setVisible(false);
            actualizarPromocionesSiExisten();
            cargarProductos();
        }

        if (e.getSource() == panelPrincipal.carrito) {
            cargarProductosACarrito();
        }

        if (e.getSource() == panelPrincipal.btnLimpiarCarrito) {
            JOptionPane.showMessageDialog(null, limpiarCarrito(idUsuario));
            cargarProductosACarrito();
        }

        if (e.getSource() == panelPrincipal.btnComprarCarrito && obtenerCantidadCarrito(idUsuario) > 0) {
            // Cargar productos y promociones del carrito
            List<ProductosCarrito> listaProductos = daoCarrito.mostrarProductosCarrito(idUsuario);
            List<PromocionCarrito> listaPromociones = daoCarrito.mostarPromociones(idUsuario);
            boolean stockSuficiente = true;

            // Validar stock de productos individuales
            stockSuficiente = validarStockProductos(listaProductos, idUsuario);
            List<ProductosCarrito> listaCarritoExistente = daoCarrito.mostrarProductosCarrito(idUsuario);

            // Si el stock de productos es suficiente, validar también las promociones
            if (stockSuficiente && listaPromociones != null && !listaPromociones.isEmpty()) {
                stockSuficiente = validarStockPromociones(listaPromociones, idUsuario);
            }
            List<PromocionCarrito> listaPromocionesExistente = daoCarrito.mostarPromociones(idUsuario);

            // Si todo el stock (productos y promociones) es suficiente, abrir pasarela
            if (stockSuficiente) {
                frame = new JFrame();
                pasarela = new PasarelaPagosVista(frame);

                pasarela.btnTarjetaCredito.addActionListener(ev -> {
                    mostrarDialogoTarjeta("credito", getValorTotal(), listaCarritoExistente, listaPromocionesExistente);
                });

                pasarela.btnTarjetaDebito.addActionListener(ev -> {
                    mostrarDialogoTarjeta("debito", getValorTotal(), listaCarritoExistente, listaPromocionesExistente);
                });

                pasarela.btnConsignacion.addActionListener(evConsignacion -> {
                    mostrarDialogoConsignacion(getValorTotal(), listaCarritoExistente, listaPromocionesExistente);
                });

                pasarela.btnBilletera.addActionListener(evBilletera -> {
                    mostrarDiaologoBilleteraElectronica(getValorTotal(), listaCarritoExistente,
                            listaPromocionesExistente);
                });

                pasarela.getDialogo().setVisible(true);

            } else {
                JOptionPane.showMessageDialog(null, "Compra cancelada debido al stock insuficiente.");
            }
        }

        // si da clic a la pasarela y no hay productos disponibles
        if (e.getSource() == panelPrincipal.btnComprarCarrito && obtenerCantidadCarrito(idUsuario) < 1) {
            JOptionPane.showMessageDialog(null, "No tiene Productos en el carrito para comprar");
        }
    }

    // cantidad de tarjetas
    public int obtenerCantidadProductos() {
        try {
            List<Producto> listaProductos = daoProducto.mostrarProductos();
            return listaProductos.size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0; // En caso de error devolvemos 0
        }
    }

    public int obtenerCantidadCarrito(int idUsuario) {
        try {
            // Obtener lista de productos del carrito
            List<ProductosCarrito> listaProductos = daoCarrito.mostrarProductosCarrito(idUsuario);
            // Obtener lista de promociones del carrito
            List<PromocionCarrito> listaPromociones = daoCarrito.mostarPromociones(idUsuario);

            // Calcular el total sumando ambas listas
            int totalItems = 0;
            if (listaProductos != null) {
                totalItems += listaProductos.size();
            }
            if (listaPromociones != null) {
                totalItems += listaPromociones.size();
            }

            return totalItems;

        } catch (Exception e) {
            e.printStackTrace();
            return 0; // En caso de error devolvemos 0
        }
    }

    public int obtenerCantidadPromociones() { return daoPromociones.numeroPromociones(); }

    // buscar categorias
    public String buscarCategoria(int idCategoria) {
        DaoCategoriaProductos dao = new DaoCategoriaProductos();
        String nombreCategoria = dao.buscarNombreCategoria(idCategoria);
         
        if (nombreCategoria == null || nombreCategoria.isEmpty()) {
            return "Sin categoría";
        }
        return nombreCategoria;
    }

    // listar
    public void getListarProductos(JPanel panelTarjetasProductos) throws IOException {
        panelTarjetasProductos.removeAll();

        SwingWorker<Void, JComponent> worker = new SwingWorker<Void, JComponent>() {

            @Override
            protected Void doInBackground() throws Exception {
                List<Producto> listaProductos = daoProducto.mostrarProductos();
                int xInicial = 125;
                int yInicial = 10;
                int anchoTarjeta = 220, altoTarjeta = 330;
                int separacionX = 50, separacionY = 50;
                int columnas = 3;
                int contador = 0;
                int categoriaActual = -1;
                int yInicioCategoria = yInicial;

                for (Producto p : listaProductos) {
                    // Cambio de categoría
                    if (p.getIdCategoria() != categoriaActual) {
                        categoriaActual = p.getIdCategoria();
                        String nombreCategoria = buscarCategoria(categoriaActual);

                        // Antes de agregar nueva categoría, ajustar Y si hay tarjetas incompletas
                        // arriba
                        if (contador % columnas != 0) {
                            yInicioCategoria += ((contador / columnas) + 1) * (altoTarjeta + separacionY);
                        } else if (contador > 0) {
                            yInicioCategoria += (contador / columnas) * (altoTarjeta + separacionY);
                        }

                        JLabel lblCategoria = new JLabel(nombreCategoria);
                        lblCategoria.setFont(new Font("Times New Roman", Font.BOLD, 24));
                        lblCategoria.setForeground(Color.BLACK);
                        lblCategoria.setBounds(xInicial, yInicioCategoria, 300, 30);
                        publish(lblCategoria);

                        // Reiniciar contador y bajar para las tarjetas
                        contador = 0;
                        yInicioCategoria += 40;
                    }

                    // Crear la tarjeta (sin tocar tu lógica)
                    TarjetaProducto tarjeta = new TarjetaProducto(
                            p.getId(), p.getImagen(), p.getNombre(), p.getTalla(),
                            p.getDescripcion(), p.getPrecio());

                    tarjeta.agregarAlCarrito.addActionListener(e -> {
                        agregarACarritoProductosDeCompras(
                                tarjeta.getIdentificadorTarjeta(), idUsuario, tarjeta.getImagen(), 1,
                                tarjeta.getPrecio());
                        cargarProductosACarrito();
                    });

                    tarjeta.compraInstanatnea.addActionListener(eCompraInstantanea -> {
                        frame = new JFrame();
                        pasarela = new PasarelaPagosVista(frame);
                        factory = new Factory();
                        ProductosCarrito productoConvertidoACarrito = new ProductosCarrito(p.getNombre(), p.getImagen(),
                                0, idUsuario, p.getId(), 1, p.getPrecio(), p.getPrecio());
                        List<ProductosCarrito> productosCompraInstantanea = new ArrayList<>();
                        productosCompraInstantanea.add(productoConvertidoACarrito);

                        pasarela.btnTarjetaCredito.addActionListener(ev -> {
                            mostrarDialogoTarjeta("credito", p.getPrecio(), productosCompraInstantanea,
                                    new ArrayList<PromocionCarrito>());
                        });

                        pasarela.btnTarjetaDebito.addActionListener(ev -> {
                            mostrarDialogoTarjeta("debito", p.getPrecio(), productosCompraInstantanea,
                                    new ArrayList<PromocionCarrito>());
                        });

                        pasarela.btnConsignacion.addActionListener(evConsignacion -> {
                            mostrarDialogoConsignacion(p.getPrecio(), productosCompraInstantanea,
                                    new ArrayList<PromocionCarrito>());
                        });

                        pasarela.btnBilletera.addActionListener(evBilletera -> {
                            mostrarDiaologoBilleteraElectronica(p.getPrecio(), productosCompraInstantanea,
                                    new ArrayList<PromocionCarrito>());
                        });

                        pasarela.getDialogo().setVisible(true);
                    });

                    // Calcular posición
                    int posX = xInicial + (contador % columnas) * (anchoTarjeta + separacionX);
                    int posY = yInicioCategoria + (contador / columnas) * (altoTarjeta + separacionY);
                    tarjeta.setBounds(posX, posY, anchoTarjeta, altoTarjeta);

                    publish(tarjeta);
                    contador++;
                }
                return null;
            }

            @Override
            protected void process(List<JComponent> chunks) {
                for (JComponent comp : chunks) {
                    panelTarjetasProductos.add(comp);
                }
                panelTarjetasProductos.revalidate();
                panelTarjetasProductos.repaint();
            }

            @Override
            protected void done() {
                panelTarjetasProductos.revalidate();
                panelTarjetasProductos.repaint();
            }
        };
        worker.execute();
    }

    public void getListarPromociones(JPanel panelPromociones) {
        List<Promocion> listaPromociones = daoPromociones.mostrarPromociones();
        panelPromociones.removeAll();

        SwingWorker<Void, TarjetaPromocion> worker = new SwingWorker<Void, TarjetaPromocion>() {
            @Override
            protected Void doInBackground() throws Exception {
                int x = 10;
                for (Promocion prm : listaPromociones) {
                    TarjetaPromocion tarjetaPromocion = new TarjetaPromocion(
                            prm.getIdPromomocion(), prm.getNombrePromocion(),
                            prm.getDescripcionPromocion(), prm.getPorcentajeDescuento(),
                            prm.getRutaImagenPrimera(), prm.getRutaImagenSegunda(),
                            prm.getTotal());

                    tarjetaPromocion.panelPromocion.setLayout(null);
                    tarjetaPromocion.panelPromocion.setBounds(x, 50, 650, 400);
                    x += 680;

                    tarjetaPromocion.btnAgregarCarrito.addActionListener(e -> {
                        agregarACarritoPromociones(prm.getIdPromomocion(), idUsuario, prm.getRutaImagenPrimera(), 1,
                                prm.getTotal());
                        cargarProductosACarrito();
                    });

                    // comprar ahora
                    tarjetaPromocion.btnComprarAhora.addActionListener(e -> {
                        JFrame frame = new JFrame();
                        PasarelaPagosVista pasarela = new PasarelaPagosVista(frame);

                        // Crear la promoción convertida a formato de carrito
                        PromocionCarrito promoCarrito = new PromocionCarrito(
                                prm.getNombrePromocion(),
                                prm.getRutaImagenPrimera(),
                                0, // idCarrito (no aplica aquí)
                                idUsuario,
                                prm.getIdPromomocion(),
                                1, // cantidad
                                prm.getTotal(), // precio unitario
                                prm.getTotal(), // subtotal (1 * total)
                                prm.getTotal() // total
                        );

                        List<PromocionCarrito> listaPromocionesInstantaneas = new ArrayList<>();
                        listaPromocionesInstantaneas.add(promoCarrito);

                        // Pasarela de pago para promoción (sin productos)
                        pasarela.btnTarjetaCredito.addActionListener(ev -> {
                            mostrarDialogoTarjeta(
                                    "credito", prm.getTotal(), new ArrayList<ProductosCarrito>(),
                                    listaPromocionesInstantaneas);
                        });

                        pasarela.btnTarjetaDebito.addActionListener(ev -> {
                            mostrarDialogoTarjeta(
                                    "debito", prm.getTotal(), new ArrayList<ProductosCarrito>(),
                                    listaPromocionesInstantaneas);
                        });

                        pasarela.btnConsignacion.addActionListener(ev -> {
                            mostrarDialogoConsignacion(
                                    prm.getTotal(), new ArrayList<ProductosCarrito>(), listaPromocionesInstantaneas);
                        });

                        pasarela.btnBilletera.addActionListener(ev -> {
                            mostrarDiaologoBilleteraElectronica(
                                    prm.getTotal(), new ArrayList<ProductosCarrito>(), listaPromocionesInstantaneas);
                        });

                        pasarela.getDialogo().setVisible(true);
                    });

                    publish(tarjetaPromocion);
                }
                return null;
            }

            @Override
            protected void process(List<TarjetaPromocion> chunks) {
                for (TarjetaPromocion tarjeta : chunks) {
                    panelPromociones.add(tarjeta.panelPromocion);
                    panelPromociones.revalidate();
                    panelPromociones.repaint();
                }
            }

            @Override
            protected void done() {
                if (listaPromociones.isEmpty()) {
                    JLabel mensaje = new JLabel("No hay promociones disponibles en este momento");
                    mensaje.setBounds(10, 50, 250, 50);
                    panelPromociones.add(mensaje);
                }
                panelPromociones.revalidate();
                panelPromociones.repaint();
            }
        };
        worker.execute();
    }

    private void actualizarPromocionesSiExisten() {
        if (obtenerCantidadPromociones() > 0) {
            cargarPromociones();
        }
    }

    public void getListarCarritoProductos(JPanel panelCarritoTarjetas) throws IOException {
        List<ProductosCarrito> listaCarrito = daoCarrito.mostrarProductosCarrito(idUsuario);
        panelCarritoTarjetas.removeAll(); // Limpiar panel antes de cargar

        SwingWorker<Void, TarjetasProductoCarrito> worker = new SwingWorker<Void, TarjetasProductoCarrito>() {

            @Override
            protected Void doInBackground() throws Exception {
                int y = 10;
                if (obtenerCantidadPromociones() > 0) {
                    List<PromocionCarrito> listaPromocion = daoCarrito.mostarPromociones(idUsuario);
                    for (PromocionCarrito p : listaPromocion) {
                        TarjetasProductoCarrito tarjetaCarritoPromocion = new TarjetasProductoCarrito(
                                p.getIdPromocion(),
                                p.getImagen(),
                                p.getNombreProducto(),
                                p.getCantidadPromocion(),
                                p.getPrecioUnitarioPromocion(),
                                p.getSubtotalPorPromocion());

                        tarjetaCarritoPromocion.setLayout(null);
                        tarjetaCarritoPromocion.setBounds(7, y, 280, 100);
                        y += 105;

                        // Listener para aumentar cantidad
                        tarjetaCarritoPromocion.setAumentarCantidadListener(e -> {
                            aumentarCantidadPromocion(p.getIdPromocion(), p.getPrecioUnitarioPromocion());
                        });

                        // Listener para disminuir cantidad
                        tarjetaCarritoPromocion.setDisminuirCantidadListener(e -> {
                            disminuirCantidadPromocion(p.getIdPromocion(), p.getPrecioUnitarioPromocion());
                        });
                        publish(tarjetaCarritoPromocion);
                    }
                }

                for (ProductosCarrito c : listaCarrito) {
                    TarjetasProductoCarrito tarjetaCarrito = new TarjetasProductoCarrito(
                            c.getIdProducto(),
                            c.getImagen(),
                            c.getNombreProducto(),
                            c.getCantidadProducto(),
                            c.getPrecioUnitarioProducto(),
                            c.getSubtotalPorProducto());

                    tarjetaCarrito.setLayout(null);
                    tarjetaCarrito.setBounds(7, y, 280, 100);
                    y += 105;

                    // Listener para aumentar cantidad
                    tarjetaCarrito.setAumentarCantidadListener(e -> {
                        aumentarCantidadProductos(tarjetaCarrito.getIdProducto(), tarjetaCarrito.getValorUnitario());
                    });

                    // Listener para disminuir cantidad
                    tarjetaCarrito.setDisminuirCantidadListener(e -> {
                        disminuirCantidadProductos(tarjetaCarrito.getIdProducto(), tarjetaCarrito.getValorUnitario());
                        // JOptionPane.showMessageDialog(null, "id"+tarjetaCarrito.getIdProducto());
                    });
                    publish(tarjetaCarrito);
                }

                return null;
            }

            @Override
            protected void process(List<TarjetasProductoCarrito> chunks) {
                for (TarjetasProductoCarrito tarjeta : chunks) {
                    panelCarritoTarjetas.add(tarjeta);
                    // panelCarritoTarjetas.revalidate();
                    // panelCarritoTarjetas.repaint();
                }
            }

            @Override
            protected void done() {
                panelCarritoTarjetas.revalidate();
                panelCarritoTarjetas.repaint();
            }
        };
        worker.execute();
    }

    // cargar
    public JPanel cargarPromociones() {
        panelPrincipal.panelTarjetasPromociones = new JPanel();
        panelPrincipal.panelTarjetasPromociones.removeAll();
        // panelPrincipal.panelTarjetasPromociones.setBackground(new Color(0x93E6FF));
        panelPrincipal.panelTarjetasPromociones.setBackground(Color.red);
        panelPrincipal.panelTarjetasPromociones.setLayout(null);
        // panelPrincipal.panelTarjetasPromociones.setOpaque(true);

        getListarPromociones(panelPrincipal.panelTarjetasPromociones);

        JLabel titulo = new JLabel("Promociones");
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titulo.setBounds(10, 5, 200, 30);
        panelPrincipal.panelTarjetasPromociones.add(titulo);

        int numeroPromociones = obtenerCantidadPromociones();
        if (numeroPromociones > 0) {
            int ancho = 680 * numeroPromociones;
            panelPrincipal.panelTarjetasPromociones.setBounds(0, 0, ancho, 450);
            panelPrincipal.panelTarjetasPromociones.setPreferredSize(new Dimension(ancho, 450));

            scrollPromociones = new ScrollPersonalizado(panelPrincipal.panelTarjetasPromociones, "horizontal", 980,
                    460);
            scrollPromociones.setBounds(10, 10, 980, 460);
            if (obtenerCantidadPromociones() > 0) {
                // panelPrincipal.panelCentroContenido.add(scrollPromociones);
            }
            return scrollPromociones;

        } else {
            panelPrincipal.panelTarjetasPromociones.setBounds(40, 60, 980, 410);
            // panelPrincipal.panelCentroContenido.add(panelPrincipal.panelTarjetasPromociones);
            // JOptionPane.showMessageDialog(null, "Funciona sin scrol");
            return panelPrincipal.panelTarjetasPromociones;
        }
    }

    public void cargarProductos() {
        panelPrincipal.panelTarjetasProductos = new JPanel();
        panelPrincipal.panelTarjetasProductos.removeAll();
        panelPrincipal.panelTarjetasProductos.setBackground(new Color(0x93E6FF));
        panelPrincipal.panelTarjetasProductos.setLayout(null);
        int posicionY = 0;

        try {
            if (obtenerCantidadPromociones() > 0) {
                JPanel panelPromociones = new JPanel();
                panelPromociones.setLayout(null);
                panelPromociones.setBackground(new Color(0x93E6FF));
                getListarPromociones(panelPromociones);

                // Calculamos el ancho total (cada tarjeta mide 650 + 30 de margen)
                int cantidadPromos = obtenerCantidadPromociones();
                int anchoTotal = cantidadPromos * 680;
                if (anchoTotal < 1000) {anchoTotal = 1000;}

                panelPromociones.setPreferredSize(new Dimension(anchoTotal, 400));

                // Scroll horizontal SOLO para promociones
                JScrollPane scrollPromociones = new JScrollPane(panelPromociones,
                        JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                        JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                scrollPromociones.setBounds(0, posicionY, 990, 470);
                scrollPromociones.setBorder(null);
                scrollPromociones.getHorizontalScrollBar().setUnitIncrement(20); // velocidad del scroll

                panelPrincipal.panelTarjetasProductos.add(scrollPromociones);//agregar al panel principal del catalogo

                posicionY += 480; // Dejamos espacio debajo para productos
            }

            JPanel panelProductos = new JPanel();
            panelProductos.setLayout(null);
            panelProductos.setBackground(new Color(0x93E6FF));

            getListarProductos(panelProductos);

            int numeroCategorias = daoProducto.obtenerCantidadCategoriasConProductos();
            numeroCategorias *= 2;

            // Calcular el alto necesario para el panel de productos
            int numeroTarjetas = obtenerCantidadProductos();
            CalcularTamañoPanel calc = new CalcularTamañoPanel();
            int altoCalculadoProductos = calc.calcularAltoPanel(numeroTarjetas, 3, 330, 50, 20);
            altoCalculadoProductos += 100;

            altoCalculadoProductos += numeroCategorias * 70; // espacio extra por categorías

            panelProductos.setBounds(0, posicionY, 1000, altoCalculadoProductos);
            panelPrincipal.panelTarjetasProductos.add(panelProductos);

            int altoTotal = posicionY + altoCalculadoProductos;
            panelPrincipal.panelTarjetasProductos.setPreferredSize(new Dimension(1000, altoTotal));

            scrollPersonalizado = new ScrollPersonalizado(panelPrincipal.panelTarjetasProductos, "vertical", 1000, 500);
            scrollPersonalizado.setBounds(0, 0, 1000, 600);

            panelPrincipal.panelCentroContenido.removeAll();
            panelPrincipal.panelCentroContenido.add(scrollPersonalizado);
            panelPrincipal.panelCentroContenido.revalidate();
            panelPrincipal.panelCentroContenido.repaint();

        } catch (IOException ex) {
            Logger.getLogger(ControladorCatalogo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public void cargarProductosACarrito() {
        // daoCarrito.validarStockItemsCarrito(idUsuario);
        panelPrincipal.carritoContenedor.setVisible(true);
        panelPrincipal.contenedorTarjetasCorritas.removeAll();
        if (scrollPersonalizado != null) {
            panelPrincipal.carritoContenedor.remove(scrollPersonalizado); // quitar el scroll anterior
            scrollPersonalizado = null;
        }
        panelPrincipal.contenedorTarjetasCorritas.setBackground(new Color(0x93E6FF));
        panelPrincipal.contenedorTarjetasCorritas.setLayout(null);

        try {
            getListarCarritoProductos(panelPrincipal.contenedorTarjetasCorritas);
        } catch (IOException ex) {
            Logger.getLogger(ControladorCatalogo.class.getName()).log(Level.SEVERE, null, ex);
        }

        int numeroTarjetasC = obtenerCantidadCarrito(idUsuario);
        if (numeroTarjetasC > 0 && numeroTarjetasC > 2) {
            CalcularTamañoPanel calcCarrito = new CalcularTamañoPanel();
            int altoCalculadoCarrito = calcCarrito.calcularAltoPanel(
                    numeroTarjetasC,
                    1, // 1 columna
                    100, // alto de cada tarjeta
                    10, // separación vertical
                    10 // padding final
            );
            panelPrincipal.contenedorTarjetasCorritas.setPreferredSize(new Dimension(300, altoCalculadoCarrito));
            scrollPersonalizado = new ScrollPersonalizado(panelPrincipal.contenedorTarjetasCorritas, "vertical", 300,
                    290);
            scrollPersonalizado.setBounds(40, 60, 300, 290);
            panelPrincipal.carritoContenedor.add(scrollPersonalizado);
        } else if (numeroTarjetasC == 0) {
            panelPrincipal.contenedorTarjetasCorritas.setBounds(40, 60, 300, 290);
            panelPrincipal.carritoContenedor.add(panelPrincipal.contenedorTarjetasCorritas);
            // Agregar mensaje cuando el carrito está vacío
            JLabel mensajeVacio = new JLabel("No hay productos en el carrito");
            mensajeVacio.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            mensajeVacio.setHorizontalAlignment(SwingConstants.CENTER);
            mensajeVacio.setBounds(50, 120, 200, 30);
            panelPrincipal.contenedorTarjetasCorritas.add(mensajeVacio);
        }

        // Mostrar el total
        panelPrincipal.lblValorTotal.setText(String.format("$ %.2f", getValorTotal()));
        panelPrincipal.lblValorTotal.setFont(new Font("Arial", Font.PLAIN, 20));
        panelPrincipal.lblValorTotal.setBounds(200, 420, 150, 30);
        panelPrincipal.carritoContenedor.add(panelPrincipal.lblValorTotal);
    }

    // carrito acciones
    public void agregarACarritoProductosDeCompras(int idProducto, int idUsuario, String imagen, int cantidad, double precioUnitario) {
        daoCarrito.agregarProductosAlCarrito(idProducto, idUsuario, imagen, cantidad, precioUnitario);
        if (panelPrincipal.carritoContenedor.isVisible()) {
            cargarProductosACarrito();
        }
    }

    public void agregarACarritoPromociones(int idPromocion, int idUsuario, String imagen, int cantidad, double precioUnitario) {
        daoCarrito.agregarPromocionAlCarrito(idPromocion, idUsuario, imagen, cantidad, precioUnitario);
        if (panelPrincipal.carritoContenedor.isVisible()) {
            cargarProductosACarrito();
        }
    }

    public double getValorTotal() {
        double valorTotal = 0;
        List<ProductosCarrito> listaProductos = daoCarrito.mostrarProductosCarrito(idUsuario);
        List<PromocionCarrito> listaPromociones = daoCarrito.mostarPromociones(idUsuario);
        if (listaProductos != null) {
            for (ProductosCarrito p : listaProductos)
                valorTotal += p.getSubtotalPorProducto();
        }
        if (listaPromociones != null) {
            for (PromocionCarrito p : listaPromociones)
                valorTotal += p.getSubtotalPorPromocion();
        }
        return valorTotal;
    }

    public String limpiarCarrito(int idUsuario) {
        String limpiarCarrito = daoCarrito.limpiarCarrito(idUsuario);
        cargarProductosACarrito();
        return limpiarCarrito;
    }

    public void aumentarCantidadProductos(int idProducto, double precioUnitario) {
        daoCarrito.aumentarCantidadProductoDelCarrito(idProducto, idUsuario, precioUnitario);
        cargarProductosACarrito();
    }

    public void disminuirCantidadProductos(int idProducto, double precioUnitario) {
        daoCarrito.dismunirCantidadProductoDelCarrito(idProducto, idUsuario, precioUnitario);
        cargarProductosACarrito();
    }

    public void aumentarCantidadPromocion(int idPromocion, double precioUnitario) {
        daoCarrito.aumentarCantidadPromocionDelCarrito(idPromocion, idUsuario, precioUnitario);
        cargarProductosACarrito();
    }

    public void disminuirCantidadPromocion(int idProducto, double precioUnitario) {
        daoCarrito.disminuirCantidadPromocionDelCarrito(idProducto, idUsuario, precioUnitario);
        cargarProductosACarrito();
    }

    // validaciones para permitir hacer la compra y disminucion de stock
    private boolean validarStockProductos(List<ProductosCarrito> listaCarrito, int idUsuario) {
        boolean stockSuficiente = true;

        for (ProductosCarrito c : listaCarrito) {
            boolean hayStock = getValidadStockProductos(c.getIdProducto(), c.getCantidadProducto());
            if (!hayStock) {
                int disponible = getCantidadDisponible(c.getIdProducto());
                int opcion = JOptionPane.showConfirmDialog(null,
                        "El producto: " + c.getNombreProducto() +
                                " solo tiene disponible " + disponible + " unidades.\n¿Deseas continuar con la compra?",
                        "Stock insuficiente", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (opcion == JOptionPane.NO_OPTION) {
                    stockSuficiente = false;
                    break;
                }

                if (opcion == JOptionPane.YES_OPTION) {
                    actualizarProductoConBajoStock(idUsuario, c.getIdProducto(), disponible);
                }
            }
        }
        return stockSuficiente;
    }

    private boolean validarStockPromociones(List<PromocionCarrito> listaPromocion, int idUsuario) {
        boolean stockSuficiente = true;

        for (PromocionCarrito promo : listaPromocion) {
            boolean hayStockPromo = getValidarStockPromocion(promo.getIdPromocion(), promo.getCantidadPromocion());
            if (!hayStockPromo) {
                int disponiblePromo = getCantidadDisponiblePromocion(promo.getIdPromocion());
                int opcion = JOptionPane.showConfirmDialog(null,
                        "La promoción: " + promo.getNombreProducto() +
                                " solo tiene disponible " + disponiblePromo
                                + " unidades.\n¿Deseas continuar con la compra?",
                        "Stock insuficiente en promoción", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (opcion == JOptionPane.NO_OPTION) {
                    stockSuficiente = false;
                    break;
                }
                if (opcion == JOptionPane.YES_OPTION) {
                    actualizarPromocionConBajoStock(idUsuario, promo.getIdPromocion(), disponiblePromo);
                }
            }
        }
        return stockSuficiente;
    }

    public void disminuirStockProductosReal(int idProducto, int cantidad) { daoProducto.setDismuirStock(idProducto, cantidad);    }

    public void disminuirStockPromocionReal(int idProducto, int cantidad) { daoPromociones.disminuirStockPromocion(idProducto, cantidad);}

    public boolean getValidadStockProductos(int idProducto, int cantidad) { return daoProducto.getValidarStockProducto(idProducto, cantidad);}

    public boolean getValidarStockPromocion(int idPromocion, int cantidadPromocion) { return daoPromociones.validarStockPromocion(idPromocion, cantidadPromocion);}

    public int getCantidadDisponible(int idProducto) { return daoProducto.stockProducto(idProducto); }

    public int getCantidadDisponiblePromocion(int idPromocion) { return daoPromociones.cantidadEnStockPromociones(idPromocion); }

    // disminuir cantridad en las tablas
    public List<Integer> getIdsProductosPromociones(int idPromocion) { return daoPromociones.obtenerIdsProductosPromocion(idPromocion); }

    public void actualizarProductoConBajoStock(int idUsuario, int idProducto, int cantidad) { daoCarrito.actualizarCantidadProducto(idUsuario, idProducto, cantidad); }

    public void actualizarPromocionConBajoStock(int idUsuario, int idPromocion, int cantidadDisponible) {
        daoCarrito.actualizarCantidadPromocion(idUsuario, idPromocion, cantidadDisponible);
    }

    // metodos de pago
    public void mostrarDialogoTarjeta(String tipoTrajeta, double valor, List<ProductosCarrito> productos,
            List<PromocionCarrito> promociones) {
        tarjeta = new Tarjetas(frame, tipoTrajeta);
        tarjeta.btnFinalizar.addActionListener(e -> {

            if (tarjeta.validarCamposTarjeta()) {
                // Obtener datos
                String numeroTarjeta = tarjeta.getTxtTarjeta().getText().trim();
                String cvv = tarjeta.getTxtCVV().getText().trim();
                String nombreTitular = tarjeta.getTxtNombreTarjeta().getText().trim();
                String fecha = tarjeta.getCbAnio().getSelectedItem().toString() + "-"
                        + tarjeta.getCbMes().getSelectedItem().toString();

                // Asignar a factory
                factory.setNumeroTarjeta(numeroTarjeta);
                factory.setCvvTarjeta(cvv);
                factory.setFechaTarjeta(fecha);
                factory.setNombreDelTitularTarjeta(nombreTitular);
                factory.setSaldo(getSaldo());

                // Procesar pago
                TipoDePago tipoPagoEnum;
                int idMetodo = 0;
                if (tipoTrajeta.equalsIgnoreCase("credito")) {
                    tipoPagoEnum = TipoDePago.TARJETA_CREDITO;
                    idMetodo = daoMetodoPago.buscarMetodoDePagoPorId("Tarjeta de Credito");
                } else if (tipoTrajeta.equalsIgnoreCase("debito")) {
                    tipoPagoEnum = TipoDePago.TARJETA_DEBITO;
                    idMetodo = daoMetodoPago.buscarMetodoDePagoPorId("Tarjeta Debito");
                } else {
                    JOptionPane.showMessageDialog(null, "Tipo de tarjeta no válido.");
                    return;
                }
                // Procesar pago con el tipo correcto
                if (idMetodo > 0) {
                    ProcesoDePago pago = factory.obtenerPago(tipoPagoEnum);

                    if (pago.pagar(valor) == 1) {
                        procesarFactura(idUsuario, idMetodo, valor, productos, promociones);
                        // guardarventa
                        for (ProductosCarrito p : productos) {
                            guardarVenta(p.getIdProducto(), p.getCantidadProducto());
                            disminuirStockProductosReal(p.getIdProducto(), p.getCantidadProducto());
                        }

                        for (PromocionCarrito promo : promociones) {
                            // Obtener los IDs de los productos que pertenecen a esa promoción
                            List<Integer> idsProductos = getIdsProductosPromociones(promo.getIdPromocion());
                            // Registrar la venta de cada producto real
                            for (int idProducto : idsProductos) {
                                guardarVenta(idProducto, promo.getCantidadPromocion());// guardar la venta
                                disminuirStockProductosReal(idProducto, promo.getCantidadPromocion()); // disminuirStock
                            }
                            // Disminuir el stock de la promoción
                            disminuirStockPromocionReal(promo.getIdPromocion(), promo.getCantidadPromocion());
                        }

                        actualizarPromocionesSiExisten();
                        cargarProductos();
                        limpiarCarrito(idUsuario);
                        tarjeta.dialogoTarjeta.dispose();
                        pasarela.getDialogo().setVisible(false);

                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Se produjo un error al conectar con la pasarela de pago.", "Error de conexión",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        tarjeta.dialogoTarjeta.setVisible(true);
    }

    public void mostrarDialogoConsignacion(double valor, List<ProductosCarrito> productos,
            List<PromocionCarrito> promociones) {

        tarjetaConsignacion = new Consignacion(frame, valor, cargarTiposDocumento(), listarBancos());

        tarjetaConsignacion.btnConsignar.addActionListener(eConsignar -> {
            boolean validacionesConsignacion = tarjetaConsignacion.validarCamposConsignacion();
            if (validacionesConsignacion) {
                String nombreConsignacion = tarjetaConsignacion.getTxtNombreConsignacion().getText().trim();
                String documentoConsignacion = tarjetaConsignacion.getTxtDocumentoConsignacion().getText().trim();
                String numeroCuentaConsignacion = tarjetaConsignacion.getTxtNumCuenta().getText().trim();
                String tipoDocConsignacion = tarjetaConsignacion.getCbTipoDocConsignacion().getSelectedItem()
                        .toString();
                String bancoConsignacion = tarjetaConsignacion.getCbBancoConsignacion().getSelectedItem().toString();
                String tipoCuentaConsignacion = tarjetaConsignacion.getCbTipoCuenta().getSelectedItem().toString();

                factory.setNumeroCuentaConsignacion(numeroCuentaConsignacion);
                factory.setTipoDedocumentoConsignacion(tipoDocConsignacion);
                factory.setBancoConsignacion(bancoConsignacion);
                factory.setTipoCuentaConsignacion(tipoCuentaConsignacion);
                factory.setNombreRemitenteConsignacion(nombreConsignacion);
                factory.setNumeroDocumentoConsignacion(documentoConsignacion);
                factory.setSaldo(getSaldo());

                ProcesoDePago pagoConsignacion = factory.obtenerPago(TipoDePago.CONSIGNACION);
                if (pagoConsignacion.pagar(valor) == 1) {
                    int idMetodo = daoMetodoPago.buscarMetodoDePagoPorId("Consignacion");
                    if (idMetodo > 0) {
                        procesarFactura(idUsuario, idMetodo, valor, productos, promociones);
                        // guardarventa
                        for (ProductosCarrito p : productos) {
                            guardarVenta(p.getIdProducto(), p.getCantidadProducto());
                            disminuirStockProductosReal(p.getIdProducto(), p.getCantidadProducto());
                        }

                        for (PromocionCarrito promo : promociones) {
                            // Obtener los IDs de los productos que pertenecen a esa promoción
                            List<Integer> idsProductos = getIdsProductosPromociones(promo.getIdPromocion());
                            // Registrar la venta de cada producto real
                            for (int idProducto : idsProductos) {
                                guardarVenta(idProducto, promo.getCantidadPromocion());// guardar la venta
                                disminuirStockProductosReal(idProducto, promo.getCantidadPromocion()); // disminuirStock
                            }
                            // Disminuir el stock de la promoción
                            disminuirStockPromocionReal(promo.getIdPromocion(), promo.getCantidadPromocion());
                        }

                        actualizarPromocionesSiExisten();
                        cargarProductos();
                        limpiarCarrito(idUsuario);
                        tarjetaConsignacion.dialogoConsignacion.dispose();
                        pasarela.getDialogo().setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Se produjo un error al conectar con la pasarela de pago.", "Error de conexión",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        tarjetaConsignacion.dialogoConsignacion.setVisible(true);
    }

    public void mostrarDiaologoBilleteraElectronica(double valortotal, List<ProductosCarrito> productos,
            List<PromocionCarrito> promociones) {
        tarjetaBilletera = new BilleterElectronica(frame, cargarTiposDocumento(), listarBancos());

        tarjetaBilletera.btnConsignarBilletera.addActionListener(eBilletera -> {
            boolean validacionesBilleteraElectronica = tarjetaBilletera.validarCamposBilletera();
            JOptionPane.showMessageDialog(null, validacionesBilleteraElectronica);
            if (validacionesBilleteraElectronica) {
                String bancoBilletera = tarjetaBilletera.getCbBancoBilletera().getSelectedItem().toString();
                String tipoDocBilletera = tarjetaBilletera.getCbTipoDocBilletera().getSelectedItem().toString().trim();
                String documentoBilletera = tarjetaBilletera.getTxtDocumentoBilletera().getText().trim();

                factory.setBancoBilletera(bancoBilletera);
                factory.setTipoDocumentoBilletera(tipoDocBilletera);
                factory.setNumeroDocumentoBilletera(documentoBilletera);
                factory.setSaldo(getSaldo());

                ProcesoDePago pagoBilleteraElectronica = factory.obtenerPago(TipoDePago.BILLETERA_ELECTRONICA);
                int resultadoPago = pagoBilleteraElectronica.pagar(valortotal);
                if (resultadoPago == 1) {

                    // Validar y eliminar productos o promociones sin stock o inactivos
                    daoCarrito.validarStockItemsCarrito(idUsuario);

                    int idMetodo = daoMetodoPago.buscarMetodoDePagoPorId("Billetera Electronica");
                    if (idMetodo > 0) {
                        // Procesar factura
                        procesarFactura(idUsuario, idMetodo, valortotal, productos, promociones);

                        // Registrar ventas y disminuir stock real de productos
                        for (ProductosCarrito p : productos) {
                            guardarVenta(p.getIdProducto(), p.getCantidadProducto());
                            disminuirStockProductosReal(p.getIdProducto(), p.getCantidadProducto());
                        }

                        // Disminuir stock real de promociones y guardar venta productos de la promo
                        for (PromocionCarrito promo : promociones) {
                            // Obtener los IDs de los productos que pertenecen a esa promoción
                            List<Integer> idsProductos = getIdsProductosPromociones(promo.getIdPromocion());
                            // Registrar la venta de cada producto real
                            for (int idProducto : idsProductos) {
                                guardarVenta(idProducto, promo.getCantidadPromocion());// guardar la venta
                                disminuirStockProductosReal(idProducto, promo.getCantidadPromocion()); // disminuirStock
                            }
                            // Disminuir el stock de la promoción
                            disminuirStockPromocionReal(promo.getIdPromocion(), promo.getCantidadPromocion());
                        }

                        actualizarPromocionesSiExisten();
                        cargarProductos();
                        limpiarCarrito(idUsuario);// Limpiar carrito
                        tarjetaBilletera.dialogoBilleteraElectronica.dispose();
                        pasarela.getDialogo().setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Se produjo un error al conectar con la pasarela de pago.", "Error de conexión",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        tarjetaBilletera.dialogoBilleteraElectronica.setVisible(true);
    }

    public List<Map<Integer, String>> cargarTiposDocumento() {
        DocumentoDao daoDocumento = new DocumentoDao();
        List<Documento> documentos = daoDocumento.listar();
        List<Map<Integer, String>> tiposDoc = new ArrayList<>();

        try {
            for (Documento doc : documentos) {
                Map<Integer, String> mapa = new HashMap<>();
                mapa.put(doc.getId(), doc.getNombre());
                tiposDoc.add(mapa);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener la lista de tipos de documento: " + e.getMessage());
        }
        return tiposDoc;
    }

    public List<Map<Integer, String>> listarBancos() {
        DaoBancos daoBancos = new DaoBancos();
        List<Banco> bancos = daoBancos.listarBancos();
        List<Map<Integer, String>> listaBancos = new ArrayList<>();

        try {
            for (Banco banco : bancos) {
                Map<Integer, String> mapa = new HashMap<>();
                mapa.put(banco.getIdBanco(), banco.getNombreBanco());
                listaBancos.add(mapa);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener la lista de bancos: " + e.getMessage());
        }

        return listaBancos;
    }

    // procesos para la venta
    public void procesarFactura(int idUsuario, int idMetodoPago, double total, List<ProductosCarrito> productos,
            List<PromocionCarrito> promociones) {
        int idFactura = daoCarrito.facturaInsert(idUsuario, idMetodoPago, total);
        if (idFactura != -1) {
            for (ProductosCarrito p : productos) {
                daoCarrito.detFacturaInsertProdcuto(
                        idFactura,
                        p.getIdProducto(), // ID del producto
                        p.getCantidadProducto(), // Cantidad comprada
                        p.getPrecioUnitarioProducto(), // precioUnitario
                        p.getSubtotalPorProducto()// Subtotal del producto
                );
            }
            if (promociones.size() > 0) {
                for (PromocionCarrito pro : promociones) {
                    daoCarrito.detFacturaInsertPromocion(
                            idFactura, pro.getIdPromocion(),
                            pro.getCantidadPromocion(),
                            pro.getPrecioUnitarioPromocion(),
                            pro.getPrecioUnitarioPromocion());
                }
            }
            daoCarrito.registrarPedido(idFactura);
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo crear la factura");
        }
    }

    public void guardarVenta(int idProducto, int cantidad) {
        daoProducto.guardarVenta(idProducto, cantidad);
    }

    // Salgo
    public double getSaldo() {
        return 1000000000.00;
    }

    public static void main(String[] args) throws IOException {
        PanelPrincipal menu = new PanelPrincipal();
        menu.setVisible(true);
        menu.setSize(1300, 700);
        ControladorCatalogo c = new ControladorCatalogo(menu);
        ControladorActividad ca = new ControladorActividad(menu);
        ControladorSeguimiento cs = new ControladorSeguimiento(menu);
        ControladorHistorial ch = new ControladorHistorial(menu);
        ControladorPQRS cpqrs = new ControladorPQRS(menu);
        CrontoladorManejarMenu ccerrar = new CrontoladorManejarMenu(menu);
    }
    // src/productos/CamisasFormalesHombre/camisa MangaLarga Blanca.jpg
}