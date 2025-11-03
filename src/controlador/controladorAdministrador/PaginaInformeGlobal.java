package controladorAdministrador;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;

import controladorAdministrador.PDF_Administrador.InformeGlobalPDF;
import modelo.crudProducto.Producto;
import vista.componentes.TarjetaFiltro;

public class PaginaInformeGlobal implements ActionListener {

    public vista.vistaAdministrador.PaginaInformeGlobal informeGlobal;
    public modelo.crudInformeGlobal.InformeGlobalDao infoDao = new modelo.crudInformeGlobal.InformeGlobalDao();
    public modelo.crudUsuario.UsuarioDao usuDao = new modelo.crudUsuario.UsuarioDao();
    public modelo.crudProducto.ProductoDao proDao = new modelo.crudProducto.ProductoDao();
    public vista.fuenteLetra.Fuente fuente = new vista.fuenteLetra.Fuente();
    // public
    // controlador.controladorAdministrador.PDF_Administrador.InformeGlobalPDF
    // globalPDF;
    public InformeGlobalPDF globalPDF;
    public Producto pMasVen, pMenVen;
    public String tendencia, metodoDePago;
    public int cantidadPedido;

    public PaginaInformeGlobal(vista.vistaAdministrador.PaginaInformeGlobal p) {
        this.informeGlobal = p;

        // Configurar ventana
        informeGlobal.setSize(1300, 700);
        informeGlobal.setLocationRelativeTo(null);
        informeGlobal.setResizable(false);
        informeGlobal.setVisible(true);
        informeGlobal.containFechas.generar.addActionListener(this);
        informeGlobal.containProdMasVen.setLayout(null);
        informeGlobal.containProdMenosVen.setLayout(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == informeGlobal.containFechas.generar) {
            String fechaInicio = informeGlobal.containFechas.getFechaInicio();
            String fechaFin = informeGlobal.containFechas.getFechaFin();
            if (validarFechas(fechaInicio, fechaFin)) {
                prodMasVendi = proDao.listarProductoMasVendidosPorFecha(fechaInicio, fechaFin);
                prodMenosVendi = proDao.listarProductoMenosVendidosPorFecha(fechaInicio, fechaFin);
                prodStock = proDao.listarStockActualPorProducto();
                ventaYingresosGenerados = infoDao.totalVentaYingresosGenerados(fechaInicio, fechaFin);
                int numeroCliente = numeroDeCliente();
                tendencia = tendenciaCompra(fechaInicio, fechaFin);
                cantidadPedido = cantidadDePedido(fechaInicio, fechaFin);
                metodoDePago = metodoDePagoMasUtilizado(fechaInicio, fechaFin);
                if (!prodMasVendi.isEmpty() && !prodMenosVendi.isEmpty() && !prodStock.isEmpty()) {
                    informeGlobal.revalidate();
                    informeGlobal.repaint();
                    informeGlobal.contenedorInfo.setVisible(true);
                    // JOptionPane.showMessageDialog(null, metodoDePago);
                    // Agregar la cantidad, metodo de pago, numero cliente
                    if (cantidadPedido != 0) {
                        informeGlobal.textCantiPedi.setText(Integer.toString(cantidadPedido));
                    } else {
                        informeGlobal.textCantiPedi.setText("No se hicieron pedidos");
                    }
                    if (!metodoDePago.isEmpty()) {
                        informeGlobal.textMetoPago.setText(metodoDePago);
                    } else {
                        informeGlobal.textMetoPago.setText("No se hicieron pagos");
                    }
                    if (!tendencia.isEmpty()) {
                        informeGlobal.textTendeCom.setText(tendencia);
                    } else {
                        informeGlobal.textTendeCom.setText("No hay tendencia de compra");
                    }

                    informeGlobal.textClienteRegis.setText(Integer.toString(numeroCliente));

                    // agregar el producto mas vendido y menos vendido
                    pMasVen = prodMasVendi.get(0);
                    informeGlobal.textProdMasVen.setText(pMasVen.getNombre());
                    pMenVen = prodMenosVendi.get(0);
                    informeGlobal.textProdMenVen.setText(pMenVen.getNombre());
                    // Agregar el total de venta y ingresos generados
                    informeGlobal.textTotalVenta.setText(ventaYingresosGenerados.get(0));
                    informeGlobal.textTotalIngreso.setText(ventaYingresosGenerados.get(1));
                    // Agregar las tarjetas de los menos vendidos y mas vendidos
                    mostrarProductosEnPanel(prodMasVendi, informeGlobal.containProdMasVen);
                    mostrarProductosEnPanel(prodMenosVendi, informeGlobal.containProdMenosVen);
                    // Agregar las tarjetas con el stock del producto
                    mostrarStockEnPanel(prodStock, informeGlobal.containStockCategoria);
                    // Actualizar tamaño de contain fecha
                    informeGlobal.containFechas.agregarBtnDescargarInforme("Descargar Informe");
                    for (ActionListener al : informeGlobal.containFechas.descargar.getActionListeners()) {
                        informeGlobal.containFechas.descargar.removeActionListener(al);
                    }
                    informeGlobal.containFechas.descargar.addActionListener(this);
                    informeGlobal.modificarTamañoFecha(260);
                    informeGlobal.containFechas.revalidate();
                    informeGlobal.containFechas.repaint();
                } else {
                    informeGlobal.contenedorInfo.setVisible(false);
                    informeGlobal.modificarTamañoFecha(210);
                    JOptionPane.showMessageDialog(null, "Elija otro rango de fechas por favor");
                }
            } else {
                informeGlobal.contenedorInfo.setVisible(false);
                informeGlobal.modificarTamañoFecha(210);
            }
        }
        if (e.getSource() == informeGlobal.containFechas.descargar) {
            globalPDF = new InformeGlobalPDF();
            // Generar PDF con toda la información del informe
            globalPDF.generar(
                    informeGlobal.containFechas.getFechaInicio(), // Fecha inicio
                    informeGlobal.containFechas.getFechaFin(), // Fecha fin
                    prodMasVendi, // Lista productos más vendidos
                    prodMenosVendi, // Lista productos menos vendidos
                    prodStock, // Lista de stock por producto
                    ventaYingresosGenerados, // Total ventas e ingresos
                    numeroDeCliente(), // Total clientes
                    cantidadPedido, // Total pedidos
                    metodoDePago, // Método de pago más usado
                    tendencia // Tendencia de compra
            );

            JOptionPane.showMessageDialog(null,
                    " Informe PDF generado correctamente.\nSe guardó en la carpeta Descargas.");
        }

    }

    private List<Producto> prodMasVendi = new ArrayList<>();
    private List<Producto> prodMenosVendi = new ArrayList<>();
    private List<Producto> prodStock = new ArrayList<>();
    private List<String> ventaYingresosGenerados = new ArrayList<>();

    public boolean validarFechas(String fechaInicioStr, String fechaFinStr) {
        // Validar que se haya llenado la fecha de inicio
        if (fechaInicioStr == null || fechaInicioStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe llenar el campo de la fecha de inicio");
            return false;
        }

        // Validar que se haya llenado la fecha de fin
        if (fechaFinStr == null || fechaFinStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe llenar el campo de la fecha fin");
            return false;
        }

        try {
            // Formato esperado de las fechas (por ejemplo: 2025-10-15)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false); // evita aceptar fechas inválidas como 2025-02-30

            // Parsear las fechas
            Date fechaInicio = sdf.parse(fechaInicioStr);
            Date fechaFin = sdf.parse(fechaFinStr);
            Date hoy = new Date(); // fecha actual

            // Validar que ninguna de las fechas sea posterior al día actual
            if (fechaInicio.after(hoy)) {
                JOptionPane.showMessageDialog(null, "La fecha de inicio no puede ser superior al día de hoy");
                return false;
            }
            if (fechaFin.after(hoy)) {
                JOptionPane.showMessageDialog(null, "La fecha fin no puede ser superior al día de hoy");
                return false;
            }

            // Validar que la fecha fin no sea anterior a la fecha inicio
            if (fechaFin.before(fechaInicio)) {
                JOptionPane.showMessageDialog(null, "La fecha fin no puede ser anterior a la fecha de inicio");
                return false;
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Formato de fecha inválido. Use el formato yyyy-MM-dd");
            return false;
        }

        return true;
    }

    public int numeroDeCliente() {
        int cantidad = usuDao.obtenerCantidadUsuario();
        return cantidad;
    }

    public int cantidadDePedido(String fechaInicio, String fechaFin) {
        int cantidadPedido = infoDao.totalPedidosFechas(fechaInicio, fechaFin);
        return cantidadPedido;
    }

    public String metodoDePagoMasUtilizado(String fechaInicio, String fechaFin) {
        String metodoPago = infoDao.metodoDePagoFecha(fechaInicio, fechaFin);
        return metodoPago;
    }

    public String tendenciaCompra(String fechaInicio, String fechaFin) {
        String t = infoDao.tendenciaDeCompraFecha(fechaInicio, fechaFin);
        return t;
    }

    private void mostrarProductosEnPanel(
            List<Producto> listaProductos,
            JPanel panelDestino) {
        panelDestino.removeAll();
        new SwingWorker<Void, TarjetaFiltro>() {
            @Override
            protected Void doInBackground() throws Exception {
                int ejeX = 10, ejeY = 10;
                int ranking = 1;

                for (Producto p : listaProductos) {
                    String ranString = Integer.toString(ranking);

                    TarjetaFiltro tarjeta = new TarjetaFiltro(
                            20,
                            0xDCD6D6,
                            p.getImagen(),
                            p.getNombre(),
                            p.getTalla(),
                            Integer.toString(p.getCantidad()),
                            ranString);

                    tarjeta.setLayout(null);
                    tarjeta.setShadowSize(1);
                    tarjeta.setBackground(Color.white);
                    tarjeta.setBounds(ejeX, ejeY, 180, 240);
                    tarjeta.add(tarjeta.containDetalle);
                    tarjeta.setProducto(p);

                    ranking++;
                    ejeX += 185;
                    if (ejeX > 925) {
                        ejeX = 10;
                        ejeY += 245;
                    }

                    publish(tarjeta);
                }
                return null;
            }

            @Override
            protected void process(List<TarjetaFiltro> tarjetas) {
                for (TarjetaFiltro t : tarjetas) {
                    panelDestino.add(t);
                }
                panelDestino.revalidate();
                panelDestino.repaint();
            }

        }.execute();
    }

    private void mostrarStockEnPanel(List<Producto> listaStock, JPanel panelDestino) {

        panelDestino.removeAll();
        panelDestino.setLayout(null);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (listaStock == null || listaStock.isEmpty()) {
            System.out.println("️ Lista vacía o nula.");
            return;
        }

        // Agrupar productos por categoría
        Map<String, List<Producto>> productosPorCategoria = new LinkedHashMap<>();
        for (Producto p : listaStock) {
            String categoria = (p.getCategoria() == null) ? "Sin categoría" : p.getCategoria();
            productosPorCategoria.computeIfAbsent(categoria, k -> new ArrayList<>()).add(p);
        }

        int ejeX = 10, ejeY = 10;
        int ancho = 280, alto = 230;
        int espacioX = 30, espacioY = 25;
        int tarjetasPorFila = 3;
        int contador = 0;

        for (Map.Entry<String, List<Producto>> entry : productosPorCategoria.entrySet()) {
            String categoria = entry.getKey();
            List<Producto> productos = entry.getValue();

            // Crear panel interno con layout nulo
            JPanel contenido = new JPanel(null);
            contenido.setBackground(Color.WHITE);
            contenido.setPreferredSize(new Dimension(ancho - 20, Math.max(220, productos.size() * 25 + 40)));

            // Título de categoría
            JLabel lblCategoria = new JLabel(categoria);
            lblCategoria.setFont(fuente.fuente(4, true));
            lblCategoria.setBounds(10, 5, ancho - 30, 25);
            contenido.add(lblCategoria);

            // Agregar productos a la tarjeta
            int y = 35;
            for (Producto p : productos) {
                JLabel lblNombre = new JLabel(p.getNombre());
                lblNombre.setFont(fuente.fuente(6, false));
                lblNombre.setBounds(10, y, 180, 20);

                JLabel lblCantidad = new JLabel(p.getCantidad() + " und");
                lblCantidad.setFont(fuente.fuente(6, false));
                lblCantidad.setBounds(200, y, 80, 20);

                contenido.add(lblNombre);
                contenido.add(lblCantidad);
                y += 22;
            }

            // Scroll interno para la tarjeta
            JScrollPane scroll = new JScrollPane(contenido);
            scroll.setBounds(ejeX, ejeY, ancho, alto);
            scroll.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scroll.getVerticalScrollBar().setUnitIncrement(10);

            // Efecto visual tipo tarjeta
            scroll.getViewport().setBackground(Color.WHITE);
            scroll.setBackground(Color.WHITE);

            panelDestino.add(scroll);

            contador++;
            if (contador % tarjetasPorFila == 0) {
                ejeX = 10;
                ejeY += alto + espacioY;
            } else {
                ejeX += ancho + espacioX;
            }
        }

        int filas = (int) Math.ceil(contador / 3.0);
        int totalHeight = filas * (alto + espacioY) + 20;
        panelDestino.setPreferredSize(new Dimension(1000, totalHeight));

        panelDestino.revalidate();
        panelDestino.repaint();

    }
}
