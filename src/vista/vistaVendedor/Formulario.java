package vista.vistaVendedor;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.swing.*;
import vista.componentes.*;
import java.util.Map;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Formulario extends JPanel {
    private RoundedPanel panelFormulario;
    public JTextField txtIdProducto;
    public JTextField txtNombre;
    public JComboBox<String> comboCategoria, comboEstado, comboTalla;
    public JTextField txtDescripcion;
    public JTextField txtCantidad;
    public JTextField txtPrecioVenta;
    public JButton btnSeleccionarImagen;
    public RoundedButton btnGuardar;
    public JLabel lblImagenSeleccionada;
    public JLabel lblidProducto;
    public String titulo, urlImagen;
    public int idProducto;

    // constructor que recibe tambien las categorias
    public Formulario(String titulo, int idProducto, Map<Integer, String> categorias, Map<Integer, String> estados, int x, int y) {
        this.titulo = titulo;
        this.idProducto = idProducto;

        panelFormulario = new RoundedPanel(30, 0xC7D9E8);
        panelFormulario.setShadowSize(1);
        panelFormulario.setBackground(new Color(240, 248, 255));
        panelFormulario.setBounds(x, y, 520, 520);
        panelFormulario.setLayout(null);
        add(panelFormulario);

        JLabel lblTitulo = new JLabel(titulo + " de producto", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Times New Roman", Font.BOLD, 25));
        lblTitulo.setForeground(Color.BLACK);
        lblTitulo.setBounds(0, 20, 520, 30);
        panelFormulario.add(lblTitulo);

        JLabel lblId = new JLabel("Id de producto* :");
        aplicarFuenteLabels(lblId);
        lblId.setBounds(60, 70, 230, 25);
        panelFormulario.add(lblId);

        JLabel lblTalla = new JLabel("Talla* :");
        aplicarFuenteLabels(lblTalla);
        panelFormulario.add(lblTalla);

        comboTalla = new JComboBox<>();

        comboTalla.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        comboTalla.setBackground(Color.WHITE);
        comboTalla.addItem("-- seleccione --");
        comboTalla.addItem("XS");
        comboTalla.addItem("S");
        comboTalla.addItem("M");
        comboTalla.addItem("L");
        comboTalla.addItem("XL");
        comboTalla.addItem("XXL");
        panelFormulario.add(comboTalla);

        if (titulo.equals("Registro")) {
            lblTalla.setBounds(350, 70, 100, 25);
            comboTalla.setBounds(350, 100, 110, 25);

            txtIdProducto = crearCampoTexto(60, 100, 250, 25);
            panelFormulario.add(txtIdProducto);

            JLabel lblCantidad = new JLabel("Cantidad* :");
            aplicarFuenteLabels(lblCantidad);
            lblCantidad.setBounds(300, 370, 110, 25);
            panelFormulario.add(lblCantidad);

            txtCantidad = crearCampoTexto(300, 400, 165, 25);
            panelFormulario.add(txtCantidad);
        } else {
            lblTalla.setBounds(300, 370, 200, 25);
            comboTalla.setBounds(300, 400, 110, 25);
            JLabel lblIdProducto = new JLabel("" + idProducto);
            lblIdProducto.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            lblIdProducto.setBounds(60, 100, 230, 25);
            panelFormulario.add(lblIdProducto);

            JLabel lblEstado = new JLabel("Estado* :");
            aplicarFuenteLabels(lblEstado);
            lblEstado.setBounds(305, 70, 100, 25);
            panelFormulario.add(lblEstado);

            comboEstado = new JComboBox<>();
            comboEstado.setBounds(305, 100, 165, 25);
            comboEstado.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            comboEstado.setBackground(Color.WHITE);
            comboEstado.addItem("-- seleccione --");
            for (String estado : estados.values()) {
                comboEstado.addItem(estado);
            }
            panelFormulario.add(comboEstado);
        }

        JLabel lblNombre = new JLabel("Nombre* :");
        aplicarFuenteLabels(lblNombre);
        lblNombre.setBounds(60, 140, 200, 25);
        panelFormulario.add(lblNombre);

        txtNombre = crearCampoTexto(60, 170, 230, 25);
        panelFormulario.add(txtNombre);

        JLabel lblCategoria = new JLabel("Categoria* :");
        aplicarFuenteLabels(lblCategoria);
        lblCategoria.setBounds(305, 140, 100, 25);
        panelFormulario.add(lblCategoria);

        // combo box de categoria
        comboCategoria = new JComboBox<>();
        comboCategoria.setBounds(305, 170, 165, 25);
        comboCategoria.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        comboCategoria.setBackground(Color.WHITE);
        comboCategoria.addItem("-- seleccione --");
        for (String nombreCategoria : categorias.values()) {
            comboCategoria.addItem(nombreCategoria);
        }
        panelFormulario.add(comboCategoria);

        JLabel lblImagen = new JLabel("Imagen* :");
        aplicarFuenteLabels(lblImagen);
        lblImagen.setBounds(60, 210, 100, 25);
        panelFormulario.add(lblImagen);

        btnSeleccionarImagen = new JButton("seleccionar imagen");
        btnSeleccionarImagen.setBounds(200, 210, 250, 25);
        btnSeleccionarImagen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!comboCategoria.getSelectedItem().toString().equals("-- seleccione --")) {
                    agregarImagen();
                } else {
                    JOptionPane.showMessageDialog(null, "Debe seleccionar una categoria");
                }
            }
        });
        panelFormulario.add(btnSeleccionarImagen);

        lblImagenSeleccionada = new JLabel("", SwingConstants.CENTER);
        lblImagenSeleccionada.setBounds(60, 240, 410, 40);
        lblImagenSeleccionada.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        panelFormulario.add(lblImagenSeleccionada);

        JLabel lblDescripcion = new JLabel("Descripcion* :");
        aplicarFuenteLabels(lblDescripcion);
        lblDescripcion.setBounds(60, 300, 130, 25);
        panelFormulario.add(lblDescripcion);

        txtDescripcion = crearCampoTexto(60, 330, 410, 25);
        panelFormulario.add(txtDescripcion);

        JLabel lblPrecio = new JLabel("Precio de venta* :");
        aplicarFuenteLabels(lblPrecio);
        lblPrecio.setBounds(60, 370, 140, 25);
        panelFormulario.add(lblPrecio);

        txtPrecioVenta = crearCampoTexto(60, 400, 200, 25);
        panelFormulario.add(txtPrecioVenta);

        btnGuardar = new RoundedButton("Guardar", new Color(51, 122, 255));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setFont(new Font("Times New Roman", Font.BOLD, 16));
        btnGuardar.setBounds(180, 450, 160, 40);
        panelFormulario.add(btnGuardar);

        if (titulo.equals("Registro")) {
            agregarPlaceholder(txtIdProducto, "Ej: 1023");
            agregarPlaceholder(txtNombre, "Ej: Camisa deportiva");
            agregarPlaceholder(txtDescripcion, "Ej: Camisa de algodón manga corta");
            agregarPlaceholder(txtCantidad, "Ej: 25");
            agregarPlaceholder(txtPrecioVenta, "Ej: 49.900");
        }
    }

    private void agregarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona una imagen");

        // Filtro para mostrar solo imágenes
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Imágenes (JPG, PNG, GIF)", "jpg", "png", "gif"));

        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();

            // Carpeta destino dentro del proyecto
            String carpetaDestino = "src/productos/"
                    + comboCategoria.getSelectedItem().toString().replaceAll("\\s+", "");

            // quitar espacios en blanco entre las palabras del comboCategoria
            urlImagen = carpetaDestino.replaceAll("\\s+", "") + "/" + archivoSeleccionado.getName();

            lblImagenSeleccionada.setText(urlImagen);

            // Creamos la carpeta si no existe
            File carpeta = new File(carpetaDestino);
            if (!carpeta.exists()) {
                carpeta.mkdir();
            }

            // 📄 Archivo destino con mismo nombre que el original
            File destino = new File(carpeta, archivoSeleccionado.getName());

            try {
                // Copiar el archivo seleccionado a la carpeta destino
                copiarArchivo(archivoSeleccionado, destino);
                // JOptionPane.showMessageDialog(null, "Imagen guardada en: " +
                // destino.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al guardar la imagen");
            }
        }
    }

    // Método para copiar archivos
    private static void copiarArchivo(File origen, File destino) throws IOException {
        try (InputStream in = new FileInputStream(origen);
                OutputStream out = new FileOutputStream(destino)) {

            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }

    private void aplicarFuenteLabels(JLabel label) {
        label.setFont(new Font("Times New Roman", Font.BOLD, 18));
        label.setForeground(Color.BLACK);
    }

    private JTextField crearCampoTexto(int x, int y, int width, int height) {
        JTextField campo = new JTextField();
        campo.setBounds(x, y, width, height);
        campo.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        campo.setForeground(Color.BLACK);
        campo.setBackground(Color.WHITE);
        campo.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(0xB0E0E6)));
        return campo;
    }

    public boolean validarCampos() {
        String errores = "";
        String id = (txtIdProducto != null) ? txtIdProducto.getText().trim() : "";
        String nombre = txtNombre.getText().trim();
        String categoria = (String) comboCategoria.getSelectedItem();
        String descripcion = txtDescripcion.getText().trim();
        String precio = txtPrecioVenta.getText().trim();

        if (titulo.equalsIgnoreCase("Registro")) {
            if (id.isEmpty()) {
                errores += "- el id del producto no puede estar vacio.\n";
            } else if (!Validaciones.validarNumeros(id)) {
                errores += "- el id debe contener solo numeros.\n";
            }

            String cantidad = txtCantidad.getText().trim();
            if (cantidad.isEmpty()) {
                errores += "- La cantidad no puede estar vacía.\n";
            } else if (!Validaciones.validarCantidad(cantidad)) {
                errores += "- La cantidad debe ser un número entero positivo.\n";
            }
        } else {
            String estados = (String) comboEstado.getSelectedItem();
            if (estados.equals("-- seleccione --")) {
                errores += "- debe seleccionar un estado.\n";
            }
        }

        String talla = (String) comboTalla.getSelectedItem();
        if (talla.equals("-- seleccione --")) {
            errores += "- debe seleccionar una talla.\n";
        }

        if (nombre.isEmpty()) {
            errores += "- el nombre no puede estar vacio.\n";
        } else if (!Validaciones.validarNombreProducto(nombre)) {
            errores += "- El nombre solo puede contener letras, números y espacios, y debe tener al menos una letra.\n";
        }

        if (categoria.equals("-- seleccione --")) {
            errores += "- debe seleccionar una categoria.\n";
        }

        if (descripcion.isEmpty()) {
            errores += "- la descripcion no puede estar vacia.\n";
        } else if (!Validaciones.validarDescripcionEntre25y55Caracteres(descripcion)) {
            errores += "- La descripción debe tener entre 25 y 55 caracteres.\n";
        }

        boolean confirmarPrecio = true;
        if (precio.isEmpty()) {
            errores += "- El precio no puede estar vacío.\n";
            confirmarPrecio = false;
        } else if (!Validaciones.validarPrecio(precio)) {
            errores += "- El precio debe tener un formato válido.\n";
            confirmarPrecio = false;
        }

        if (confirmarPrecio) {
            double precioDouble = Double.parseDouble(precio);

            if (precioDouble < 5000.00) {
                int opcion = JOptionPane.showConfirmDialog(
                        null,
                        "¿Seguro que desea que el producto cueste " + precioDouble + "?",
                        "Confirmar precio bajo",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                // Si el usuario elige "No, se puede manejar el caso según lo necesites
                if (opcion != JOptionPane.YES_OPTION) {
                    errores += "- Se canceló la confirmación del precio.\n";
                }
            }
        }

        if (!errores.isEmpty()) {
            JOptionPane.showMessageDialog(null, errores, "errores de validacion de campos", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public JTextField getTxtIdProducto() {
        return txtIdProducto;
    }

    public void setTxtIdProducto(JTextField txtIdProducto) {
        this.txtIdProducto = txtIdProducto;
    }

    public JTextField getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(JTextField txtNombre) {
        this.txtNombre = txtNombre;
    }

    public JTextField getTxtDescripcion() {
        return txtDescripcion;
    }

    public void setTxtDescripcion(JTextField txtDescripcion) {
        this.txtDescripcion = txtDescripcion;
    }

    public JTextField getTxtCantidad() {
        return txtCantidad;
    }

    public void setTxtCantidad(JTextField txtCantidad) {
        this.txtCantidad = txtCantidad;
    }

    public JTextField getTxtPrecioVenta() {
        return txtPrecioVenta;
    }

    public void setTxtPrecioVenta(JTextField txtPrecioVenta) {
        this.txtPrecioVenta = txtPrecioVenta;
    }

    public JComboBox<String> getComboCategoria() {
        return comboCategoria;
    }

    public void setComboCategoria(JComboBox<String> comboCategoria) {
        this.comboCategoria = comboCategoria;
    }

    public RoundedPanel getPanelFormulario() {
        return panelFormulario;
    }

    public JLabel getLblIdProducto() {
        return lblidProducto;
    }

    public JComboBox<String> getComboEstado() {
        return comboEstado;
    }

    public void setComboEstado(JComboBox<String> comboEstado) {
        this.comboEstado = comboEstado;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public JComboBox<String> getComboTalla() {
        return comboTalla;
    }

    public void limpiarFormulario() {
        // Limpiar campos de texto
        if (txtIdProducto != null) {
            txtIdProducto.setText("");
        }
        txtNombre.setText("");
        txtDescripcion.setText("");
        txtPrecioVenta.setText("");

        if (txtCantidad != null) {
            txtCantidad.setText("");
        }

        // Reiniciar combo boxes
        if (comboCategoria != null && comboCategoria.getItemCount() > 0) {
            comboCategoria.setSelectedIndex(0); // "-- seleccione --"
        }
        if (comboEstado != null && comboEstado.getItemCount() > 0) {
            comboEstado.setSelectedIndex(0); // "-- seleccione --"
        }
        if (comboTalla != null && comboTalla.getItemCount() > 0) {
            comboTalla.setSelectedIndex(0); // "-- seleccione --"
        }

        // Limpiar imagen seleccionada
        lblImagenSeleccionada.setText("");
        lblImagenSeleccionada.setIcon(null);
    }

    public void agregarPlaceholder(JTextField txt, String placeholder) {
        // Colocar placeholder inicial
        txt.setText(placeholder);
        txt.setForeground(Color.GRAY);

        txt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txt.getText().equals(placeholder)) {
                    txt.setText("");
                    txt.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txt.getText().isEmpty()) {
                    txt.setText(placeholder);
                    txt.setForeground(Color.GRAY);
                }
            }
        });
    }

}