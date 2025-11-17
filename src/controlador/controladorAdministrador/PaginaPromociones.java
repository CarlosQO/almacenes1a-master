package controladorAdministrador;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

import modelo.crudProducto.Producto;
import modelo.crudProducto.ProductoDao;
import modelo.crudPromociones.PromocionDao;
import vista.componentes.Promociones;
import vista.componentes.Tarjetas;
import vista.vistaAdministrador.PaginaListarConfigRolModal;

public class PaginaPromociones implements ActionListener {

    public vista.vistaAdministrador.PaginaPromociones promo;
    public Producto producto = new Producto();
    public ProductoDao prDao = new ProductoDao();
    private List<Producto> prod = new ArrayList<>();
    public Promociones promoModal = new Promociones();
    public PaginaListarConfigRolModal modal = new PaginaListarConfigRolModal();
    public PromocionDao promoDao = new PromocionDao();

    // Variables para manejar los precios base y segundo producto
    private double precioBase = 0;
    private double precioSegundo = 0;

    public PaginaPromociones() {
    }

    public PaginaPromociones(vista.vistaAdministrador.PaginaPromociones pr) throws IOException {
        this.promo = pr;
        promo.setSize(new Dimension(1300, 700));
        promo.setLocationRelativeTo(null);
        promo.setResizable(false);
        promo.setVisible(true);
        promoModal.descuento.addActionListener(this);

        // Carga los productos en un hilo aparte
        new SwingWorker<Void, Tarjetas>() {
            @Override
            protected Void doInBackground() throws Exception {
                prod = prDao.listarProductoMenosVendidos(); // consulta SQL en segundo plano

                int ejeX = 10, ejeY = 50;
                for (Producto p : prod) {
                    // Crear tarjeta con imagen + datos
                    Tarjetas tarjeta = new Tarjetas(
                            20,
                            0xDCD6D6,
                            p.getId(),
                            p.getImagen(),
                            p.getNombre(),
                            p.getTalla(),
                            String.valueOf(p.getCantidad()),
                            "$" + String.valueOf(p.getPrecio()));

                    tarjeta.setLayout(null);
                    tarjeta.setShadowSize(1);
                    tarjeta.setBackground(Color.white);
                    tarjeta.setBounds(ejeX, ejeY, 180, 250);
                    tarjeta.add(tarjeta.containDetalle);
                    tarjeta.setProducto(p);

                    ejeX += 185;
                    if (ejeX > 925) {
                        ejeX = 10;
                        ejeY += 255;
                    }
                    publish(tarjeta);
                }
                return null;
            }

            @Override
            protected void process(List<Tarjetas> tarjetas) {
                for (Tarjetas t : tarjetas) {
                    t.promocionar.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent evt) {

                            Producto productoSeleccionado = t.getProducto();
                            int idProducto = productoSeleccionado.getId();
                            String urlImagen = productoSeleccionado.getImagen();

                            // Reiniciar precios cada vez que se abre el modal
                            precioBase = productoSeleccionado.getPrecio();
                            precioSegundo = 0;

                            if (promoModal == null) {
                                promoModal = new Promociones();
                            }

                            promoModal.limpiarCampos();
                            promoModal.PrecioTotal.setText(String.format("%.2f", precioBase));
                            promoModal.idProductoSeleccionado = idProducto;
                            promoModal.urlImagenSeleccionada = urlImagen;

                            promoModal.setSize(515, 280);
                            promoModal.validate();
                            modal.setSize(promoModal.getSize());
                            modal.setContentPane(promoModal.getContentPane());
                            llenarComboBoxProductoMasVendidos();

                            for (MouseListener ml : promoModal.btnPublicar.getMouseListeners()) {
                                promoModal.btnPublicar.removeMouseListener(ml);
                            }
                            promoModal.btnPublicar.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent evt) {
                                    guardarPromociones(promoModal.idProductoSeleccionado,
                                            promoModal.urlImagenSeleccionada);
                                }
                            });

                            modal.mostrarComoModal(modal, "Promoción");
                        }
                    });
                    promo.contenedorImagen.add(t);
                }
                promo.revalidate();
                promo.repaint();
            }

        }.execute();
        promo.repaint();
        promo.contenedorImagen.repaint();
        promo.contenedorImagen.revalidate();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        // Cuando se selecciona el segundo producto
        if (source == promoModal.productos) {
            int index = promoModal.productos.getSelectedIndex();

            if (index > 0) {
                Producto seleccionado = prodMasVendi.get(index - 1);

                promoModal.urlImagen.setText(seleccionado.getImagen());
                promoModal.idSegundoProductoSeleccionado = seleccionado.getId();
                precioSegundo = seleccionado.getPrecio(); // guardar precio del segundo producto
            } else {
                promoModal.PrecioTotal.setText(String.format("%.2f", precioBase));
                promoModal.urlImagen.setText("");
                promoModal.idSegundoProductoSeleccionado = 0;
                precioSegundo = 0;
            }
            actualizarPrecioTotal();
        }

        // Cuando cambia el descuento
        if (source == promoModal.descuento) {
            actualizarPrecioTotal();
        }
    }

    public int obtenerUnProducto(int id) {
        producto = prDao.obtenerProducto(id);
        return producto.getCantidad();
    }

    private List<Producto> prodMasVendi = new ArrayList<>();

    public void llenarComboBoxProductoMasVendidos() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Productos Mas Vendidos");

        prodMasVendi = prDao.listarProductoMasVendidos();
        for (Producto p : prodMasVendi) {
            model.addElement(p.getNombre());
        }
        promoModal.productos.setModel(model);

        for (ActionListener al : promoModal.productos.getActionListeners()) {
            promoModal.productos.removeActionListener(al);
        }
        promoModal.productos.addActionListener(this);
    }

    public void guardarPromociones(int IdPrimerPro, String imgPrimerPro) {
        if (!promoModal.validarCampos()) {
            System.out.println("Por favor, complete todos los campos.");
            return;
        }

        if (!validarCamposPromocion(IdPrimerPro)) {
            return; // Detiene la ejecución si hay errores
        }

        int cantidadIngresada = Integer.parseInt(promoModal.cantidad.getText().trim());

        modelo.crudPromociones.Promocion p = new modelo.crudPromociones.Promocion();
        p.setIdPrimerProducto(IdPrimerPro);
        if (promoModal.idSegundoProductoSeleccionado != 0) {
            p.setIdSegundoProducto(promoModal.idSegundoProductoSeleccionado);
        } else {
            p.setIdSegundoProducto(0);
        }
        p.setDescripcionPromocion(promoModal.descripcionProm.getText().trim());
        p.setCantidad(cantidadIngresada);
        p.setNombrePromocion(promoModal.nombreProm.getText().trim());
        p.setPorcentajeDescuento((int) promoModal.descuento.getSelectedItem());
        try {
            String textoPrecio = promoModal.PrecioTotal.getText().trim()
                    .replace("$", "")
                    .replace(",", "")
                    .replace(" ", "");
            int precioTotal = Integer.parseInt(textoPrecio.substring(0, textoPrecio.length() - 2));
            p.setTotal(precioTotal);

        } catch (NumberFormatException e) {
            System.out.println("Error al parsear el precio: " + e.getMessage());
            return;
        }

        p.setRutaImagenPrimera(imgPrimerPro);
        if (promoModal.urlImagen.getText().trim().isEmpty()) {
            p.setRutaImagenSegunda("");
        } else {
            p.setRutaImagenSegunda(promoModal.urlImagen.getText().trim());
        }

        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Desea registrar esta promoción?",
                "Confirmar registro", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (promoDao.RegistrarPromocion(p) == 1) {
                JOptionPane.showMessageDialog(null, "Promoción registrada con éxito");
                promoModal.limpiarCampos();
                modal.dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Error al registrar la promoción.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Recalcular correctamente el total con descuento
    private void actualizarPrecioTotal() {
        try {
            double totalSinDescuento = precioBase + precioSegundo;

            // forma directa y sencilla
            int descuentoSeleccionado = (Integer) promoModal.descuento.getSelectedItem();

            double factor = 1.0 - (descuentoSeleccionado / 100.0);
            double totalConDescuento = totalSinDescuento * factor;

            promoModal.PrecioTotal.setText(String.format("%.2f", totalConDescuento));
        } catch (Exception ex) {
            promoModal.PrecioTotal.setText(String.format("%.2f", precioBase + precioSegundo));
        }
    }

    private boolean validarCamposPromocion(int IdPrimerPro) {
        // Validar nombre
        if (promoModal.nombreProm.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El nombre de la promoción es obligatorio.", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Validar descripción
        if (promoModal.descripcionProm.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "La descripción de la promoción no puede estar vacía.", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Validar descuento
        if (promoModal.descuento.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un porcentaje de descuento.", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Validar precio total
        if (promoModal.PrecioTotal.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El precio total no puede estar vacío.", "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // Validar cantidad
        if (promoModal.cantidad.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "La cantidad no debe estar vacía");
            return false;
        }

        int cantidadIngresada;
        try {
            cantidadIngresada = Integer.parseInt(promoModal.cantidad.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor ingresa un número válido en la cantidad");
            return false;
        }

        if (cantidadIngresada < 1) {
            JOptionPane.showMessageDialog(null, "La cantidad es inválida, ingresa una cantidad mayor a 0");
            return false;
        }

        try {
            int cantidadPrimerPro = obtenerUnProducto(IdPrimerPro);
            int cantidadSegundoPro = 0;
            boolean tieneSegundoProducto = promoModal.idSegundoProductoSeleccionado > 0;

            if (tieneSegundoProducto) {
                try {
                    cantidadSegundoPro = obtenerUnProducto(promoModal.idSegundoProductoSeleccionado);
                } catch (Exception ex) {
                    cantidadSegundoPro = 0;
                }
            }

            int stockDisponible = (tieneSegundoProducto && cantidadSegundoPro > 0)
                    ? Math.min(cantidadPrimerPro, cantidadSegundoPro)
                    : cantidadPrimerPro;

            if (cantidadIngresada > stockDisponible) {
                JOptionPane.showMessageDialog(null,
                        "La cantidad ingresada supera el stock disponible.\n"
                                + "Stock máximo permitido: " + stockDisponible);
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al verificar el stock: " + e.getMessage());
            return false;
        }

        return true; // Si todo pasa correctamente
    }
}
