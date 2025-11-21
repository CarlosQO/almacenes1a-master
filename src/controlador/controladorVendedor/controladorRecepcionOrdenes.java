package controladorVendedor;

import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import modelo.crudPedidos.EstadoPedido;
import modelo.crudPedidos.Pedido;
import modelo.crudHistorialVenta.HistorialVentaDao;
import modelo.crudPedidos.DaoPedido;
import vista.vistaVendedor.RecepcionDeOrdenes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class controladorRecepcionOrdenes {
    private RecepcionDeOrdenes vista;
    private DaoPedido modelo;
    private HistorialVentaDao historialVentaDao;
    private String idVendedor;

    public controladorRecepcionOrdenes(RecepcionDeOrdenes vista, String idVendedor) {
        this.vista = vista;
        this.idVendedor = idVendedor;
        this.modelo = new DaoPedido();
        this.historialVentaDao = new HistorialVentaDao();
        asignarVendedorAOrdenes();
        cargarEstadosEnComboBox();
        configurarEventos();

        // Configurar la visibilidad inicial de la columna Acciones según el estado
        // inicial
        String estadoInicial = (String) vista.getCBEO().getSelectedItem();
        if (estadoInicial != null) {
            vista.setColumnAccionesVisible(estadoInicial.equalsIgnoreCase("Pendiente"));
        }
        actualizarTablaPedidos();
        vista.getTablaOrdenes().setDefaultEditor(Object.class, null);
    }

    private void configurarEventos() {
        JComboBox<String> comboBox = vista.getCBEO();
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarTablaPedidos();
            }
        });
    }

    private void actualizarTablaPedidos() {
        String estadoSeleccionado = (String) vista.getCBEO().getSelectedItem();
        if (estadoSeleccionado != null && !estadoSeleccionado.isEmpty()) {
            List<Pedido> pedidos = modelo.getPedidosPorEstado(estadoSeleccionado);
            DefaultTableModel modeloTabla = vista.getModeloTabla();

            // Configurar columnas según el estado seleccionado
            boolean esPendiente = estadoSeleccionado.equalsIgnoreCase("Pendiente");
            int columnasEsperadas = esPendiente ? 4 : 3;
            // Ajustar columnas si es necesario
            while (modeloTabla.getColumnCount() > columnasEsperadas) {
                modeloTabla.setColumnCount(modeloTabla.getColumnCount() - 1);
            }
            while (modeloTabla.getColumnCount() < columnasEsperadas) {
                modeloTabla.addColumn(esPendiente ? "Acciones" : "");
            }

            // Configurar el editor de la columna de acciones si el estado es Pendiente
            if (esPendiente && vista.getTablaOrdenes().getColumnCount() > 3) {
                vista.getTablaOrdenes().getColumnModel().getColumn(3).setCellEditor(
                        new javax.swing.DefaultCellEditor(new javax.swing.JComboBox<>()) {
                            private javax.swing.JButton btnCancelar;
                            private javax.swing.JButton btnEntregar;
                            private String clickedButton = null;

                            {
                                btnCancelar = new javax.swing.JButton("Cancelar");
                                btnEntregar = new javax.swing.JButton("Despachar");

                                btnCancelar.addActionListener(evt -> {
                                    clickedButton = "Cancelar";
                                    fireEditingStopped();
                                });

                                btnEntregar.addActionListener(evt -> {
                                    clickedButton = "Despachar";
                                    fireEditingStopped();
                                });
                            }

                            @Override
                            public java.awt.Component getTableCellEditorComponent(
                                    javax.swing.JTable table, Object value,
                                    boolean isSelected, int row, int column) {
                                if (row == 0)
                                    return new javax.swing.JPanel(); // Primera fila sin botones

                                javax.swing.JPanel panel = new javax.swing.JPanel();
                                panel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0));
                                panel.add(btnCancelar);
                                panel.add(btnEntregar);
                                return panel;
                            }

                            @Override
                            public Object getCellEditorValue() {
                                if (clickedButton != null) {
                                    int row = vista.getTablaOrdenes().getSelectedRow();
                                    // Verificar que la fila sea válida y exista en el modelo
                                    if (row > 0 && row < vista.getTablaOrdenes().getRowCount()) {
                                        int idPedido = (Integer) vista.getTablaOrdenes().getValueAt(row, 0);
                                        int idEstado;
                                        if (clickedButton.equals("Cancelar")) {
                                            idEstado = 5; // ID para estado Cancelado
                                        } else {
                                            idEstado = 4; // ID para estado Despachado
                                        }
                                        if (controladorRecepcionOrdenes.this.modelo.cambiarEstadoPedido(idPedido,
                                                idEstado)) {
                                            javax.swing.SwingUtilities.invokeLater(() -> {
                                                actualizarTablaPedidos();
                                            });
                                            historialVentaDao.registrarVenta(idVendedor, idPedido);

                                        }
                                    }
                                }
                                clickedButton = null;
                                return "";
                            }
                        });
            }

            // Limpiar la tabla
            while (vista.getTablaOrdenes().getRowCount() > 0) {
                vista.getModeloTabla().removeRow(0);
            }

            // Configurar visibilidad de la columna Acciones
            vista.setColumnAccionesVisible(esPendiente);

            // Agregar la fila vacía inicial
            vista.getModeloTabla().addRow(new Object[modeloTabla.getColumnCount()]);

            // Llenar la tabla con los pedidos
            for (Pedido pedido : pedidos) {
                Object[] fila;
                if (esPendiente) {
                    fila = new Object[] {
                            pedido.getIdPedido(),
                            pedido.getCliente(),
                            pedido.getEstado(),
                            ""
                    };
                } else {
                    fila = new Object[] {
                            pedido.getIdPedido(),
                            pedido.getCliente(),
                            pedido.getEstado()
                    };
                }
                vista.getModeloTabla().addRow(fila);
            }
        }
    }

    private void cargarEstadosEnComboBox() {
        JComboBox<String> comboBox = vista.getCBEO();
        comboBox.removeAllItems(); // Limpiamos el combo box

        // Obtenemos los estados desde la base de datos
        List<EstadoPedido> estados = modelo.getEstadosPedidos();

        // Agregamos los estados al combo box
        for (EstadoPedido estado : estados) {
            comboBox.addItem(estado.getEstado());
        }
    }

    private void asignarVendedorAOrdenes() {

    }
}
