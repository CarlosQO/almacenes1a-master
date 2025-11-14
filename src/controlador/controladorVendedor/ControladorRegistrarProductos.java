package controlador.controladorVendedor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;

import modelo.crudCategoriasProductos.*;
import modelo.crudProducto.*;
import vista.vistaVendedor.ModuloRegistrarModulos;
import vista.vistaVendedor.*;

public class ControladorRegistrarProductos implements ActionListener {
    private JFrame frame;
    private int idVendedor;
    private ModuloRegistrarModulos moduloRegistroProductos;
    private DaoCategoriaProductos daoCategoria;
    private ProductoDao daoProducto;

    public ControladorRegistrarProductos(JFrame frame, int idVendedor) {
        this.frame = frame;
        this.idVendedor = idVendedor;
        daoCategoria = new DaoCategoriaProductos();
        daoProducto = new ProductoDao();
        cargarModuloProductos();
        moduloRegistroProductos.formularioRegistro.btnGuardar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == moduloRegistroProductos.formularioRegistro.btnGuardar) {
            boolean validarCampos = moduloRegistroProductos.validarCampos();
            if (validarCampos) {
                registrarProductos();
            }
        }
    }

    public void cargarModuloProductos() {
        frame.setSize(1300, 700);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().removeAll(); // limpia el contenido anterior

        moduloRegistroProductos = new ModuloRegistrarModulos(frame, getListarCatgorias());
        moduloRegistroProductos.setLayout(null);
        moduloRegistroProductos.setBounds(0, 0, 1300, 700);

        frame.add(moduloRegistroProductos);
        frame.revalidate(); // actualiza la interfaz
        frame.repaint(); // repinta la ventana
    }

    public boolean registrarProductos() {
        // obtiene los datos desde los campos de texto
        int id = Integer.parseInt(moduloRegistroProductos.formularioRegistro.getTxtIdProducto().getText().trim());
        String nombre = moduloRegistroProductos.formularioRegistro.getTxtNombre().getText().trim();
        String descripcion = moduloRegistroProductos.formularioRegistro.getTxtDescripcion().getText().trim();
        int cantidad = Integer.parseInt(moduloRegistroProductos.formularioRegistro.getTxtCantidad().getText().trim());
        double precio = Double
                .parseDouble(moduloRegistroProductos.formularioRegistro.getTxtPrecioVenta().getText().trim());

        // imagen pendiente
        String imagen = moduloRegistroProductos.formularioRegistro.getUrlImagen();

        //

        String talla = "M";
        String categoriaSeleccionada = (String) moduloRegistroProductos.formularioRegistro.getComboCategoria()
                .getSelectedItem();
        int idCategoria = -1;

        for (Map.Entry<Integer, String> entry : getListarCatgorias().entrySet()) {
            if (entry.getValue().equals(categoriaSeleccionada)) {
                idCategoria = entry.getKey();
                break;
            }
        }
        int idEstado = 1;
        boolean resultadoRegistro = daoProducto.registrarProducto(id, nombre, cantidad, precio, descripcion, talla,
                imagen, idCategoria, idEstado);
        if (resultadoRegistro) {
            moduloRegistroProductos.formularioRegistro.limpiarFormulario();
            return true;
        } else {
            return false;
        }

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

    public static void main(String[] args) {
        // Crear el frame principal
        JFrame frame = new JFrame("Registro de Productos");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1300, 700);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);

        // ID del vendedor de prueba
        int idVendedor = 1002;

        // Crear el controlador (esto también carga el módulo de registro)
        ControladorRegistrarProductos controlador = new ControladorRegistrarProductos(frame, idVendedor);

        // Mostrar la ventana
        frame.setVisible(true);
    }
}
