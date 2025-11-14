package controladorVendedor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.crudProducto.*;
import modelo.crudCategoriasProductos.*;
import modelo.crudEstadosProdcutos.*;
import vista.vistaVendedor.Formulario;
import vista.vistaVendedor.ModuloActualizarDatosProductos;

public class ControladorActualizarProductos implements ActionListener {

    private JFrame frame;
    private int idVendedor;
    private ModuloActualizarDatosProductos moduloActualizarDatosProductos;
    private DaoCategoriaProductos daoCategoria;
    private ProductoDao daoProducto;
    private DaoEstadosProductos daoEstadoProductos;

    public ControladorActualizarProductos(JFrame frame, int idVendedor) {
        this.frame = frame;
        this.idVendedor = idVendedor;
        daoCategoria = new DaoCategoriaProductos();
        daoProducto = new ProductoDao();
        daoEstadoProductos = new DaoEstadosProductos();
        cargarModuloActualizarDatos();
        moduloActualizarDatosProductos.btnBuscar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == moduloActualizarDatosProductos.btnBuscar) {
            cargarFormularioActualizar();
        }
    }

    public void cargarModuloActualizarDatos() {
        frame.setSize(1300, 700);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().removeAll(); // limpia el contenido anterior

        moduloActualizarDatosProductos = new ModuloActualizarDatosProductos(frame, getListarCatgorias(),
                getListarEstado());
        moduloActualizarDatosProductos.setLayout(null);
        moduloActualizarDatosProductos.setBounds(0, 0, 1300, 700);

        frame.add(moduloActualizarDatosProductos);
        frame.revalidate(); // actualiza la interfaz
        frame.repaint(); // repinta la ventana
    }

    public void cargarFormularioActualizar() {
        int idBuscado = Integer.parseInt(moduloActualizarDatosProductos.txtBuscar.getText().trim());
        boolean productoExiste = daoProducto.productoExiste(idBuscado);

        if (productoExiste) {
            List<Producto> infoProducto = daoProducto.informacionDeUnProducto(idBuscado);
            if (infoProducto != null && !infoProducto.isEmpty()) {
                Producto p = infoProducto.get(0);

                if (moduloActualizarDatosProductos.formularioActualizar != null) {
                    moduloActualizarDatosProductos
                            .remove(moduloActualizarDatosProductos.formularioActualizar.getPanelFormulario());
                    moduloActualizarDatosProductos.formularioActualizar = null;
                    moduloActualizarDatosProductos.revalidate();
                    moduloActualizarDatosProductos.repaint();
                }

                // Crear el formulario con categorías y estados
                moduloActualizarDatosProductos.formularioActualizar = new Formulario(
                        "Actualizacion",
                        idBuscado,
                        getListarCatgorias(),
                        getListarEstado(),
                        600, 100);

                // Llenar los campos con la info del producto
                moduloActualizarDatosProductos.formularioActualizar.getTxtNombre().setText(p.getNombre());
                moduloActualizarDatosProductos.formularioActualizar.getTxtDescripcion().setText(p.getDescripcion());
                moduloActualizarDatosProductos.formularioActualizar.getTxtPrecioVenta()
                        .setText(String.valueOf(p.getPrecio()));
                // cantidad e imagen se pueden llenar si los tienes en Producto
                if (moduloActualizarDatosProductos.formularioActualizar.getTxtCantidad() != null) {
                    moduloActualizarDatosProductos.formularioActualizar.getTxtCantidad()
                            .setText(String.valueOf(p.getCantidad()));
                }
                moduloActualizarDatosProductos.formularioActualizar.lblImagenSeleccionada.setText(p.getImagen());

                // Seleccionar la categoría correspondiente en el combo
                for (int i = 0; i < moduloActualizarDatosProductos.formularioActualizar.getComboCategoria()
                        .getItemCount(); i++) {
                    if (moduloActualizarDatosProductos.formularioActualizar.getComboCategoria().getItemAt(i)
                            .equals(getListarCatgorias().get(p.getIdCategoria()))) {
                        moduloActualizarDatosProductos.formularioActualizar.getComboCategoria().setSelectedIndex(i);
                        break;
                    }

                }

                // Seleccionar el estado correspondiente en el combo
                for (int i = 0; i < moduloActualizarDatosProductos.formularioActualizar.getComboEstado()
                        .getItemCount(); i++) {
                    if (moduloActualizarDatosProductos.formularioActualizar.getComboEstado().getItemAt(i)
                            .equals(getListarEstado().get(p.getIdEstado()))) {
                        moduloActualizarDatosProductos.formularioActualizar.getComboEstado().setSelectedIndex(i);
                        break;
                    }
                }

                // Hacer visible el panel del formulario
                moduloActualizarDatosProductos.formularioActualizar.getPanelFormulario().setVisible(true);
                moduloActualizarDatosProductos
                        .add(moduloActualizarDatosProductos.formularioActualizar.getPanelFormulario());
                moduloActualizarDatosProductos.revalidate();
                moduloActualizarDatosProductos.repaint();

                // Acción del botón Guardar
                moduloActualizarDatosProductos.formularioActualizar.btnGuardar.addActionListener(eve -> {
                    boolean validar = moduloActualizarDatosProductos.formularioActualizar.validarCampos();
                    if (validar) {
                        actualizarInformacionProductos(idBuscado);
                    }
                });
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean actualizarInformacionProductos(int idProducto) {
        // obtiene los datos desde los campos de texto
        String nombre = moduloActualizarDatosProductos.formularioActualizar.getTxtNombre().getText().trim();
        String descripcion = moduloActualizarDatosProductos.formularioActualizar.getTxtDescripcion().getText().trim();
        double precio = Double
                .parseDouble(moduloActualizarDatosProductos.formularioActualizar.getTxtPrecioVenta().getText().trim());

        // imagen pendiente
        String imagen = "src/productos/CamisasFormalesHombre/camisa MangaLarga Blanca.jpg";
        //

        String talla = "M";
        String categoriaSeleccionada = (String) moduloActualizarDatosProductos.formularioActualizar.getComboCategoria()
                .getSelectedItem();
        int idCategoria = -1;

        for (Map.Entry<Integer, String> entry : getListarCatgorias().entrySet()) {
            if (entry.getValue().equals(categoriaSeleccionada)) {
                idCategoria = entry.getKey();
                break;
            }
        }

        String estadoSeleccionada = (String) moduloActualizarDatosProductos.formularioActualizar.getComboEstado()
                .getSelectedItem();
        int idEstado = -1;

        for (Map.Entry<Integer, String> entry : getListarEstado().entrySet()) {
            if (entry.getValue().equals(estadoSeleccionada)) {
                idEstado = entry.getKey();
                break;
            }
        }
        daoProducto.actualizarDatosProducto(idProducto, nombre, precio, descripcion, talla, imagen, idCategoria,
                idEstado);
        return true;
    }

    public Map<Integer, String> getListarCatgorias() {
        Map<Integer, String> categoriasMap = new HashMap<>();
        List<Categoria> lista = daoCategoria.cargarCategorias();

        if (lista != null) {
            for (Categoria c : lista) {
                categoriasMap.put(c.getIdCategoria(), c.getNombreCategoria());
            }
        }
        return categoriasMap;
    }

    public Map<Integer, String> getListarEstado() {
        Map<Integer, String> estadosMap = new HashMap<>();
        List<EstadosProductos> lista = daoEstadoProductos.mostrarEstados();

        if (lista != null) {
            for (EstadosProductos e : lista) {
                estadosMap.put(e.getIdEstado(), e.getNombreEstado());
            }
        }
        return estadosMap;
    }
}
