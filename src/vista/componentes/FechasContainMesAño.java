package vista.componentes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import vista.fuenteLetra.Fuente;
import vista.vistaAdministrador.RoundedPanel;

public class FechasContainMesAño extends RoundedPanel {

    private JLabel lblTitulo, lblMes, lblAnio;
    public JComboBox<String> comboMes, comboAnio;
    public JButton generar, descargar;
    private Fuente fuente = new Fuente();

    public FechasContainMesAño() {
        super(20, 0xFFFFFF); // Bordes redondeados, color blanco
        setShadowSize(1);
        setLayout(null);
        setBackground(new Color(0xCFCFCF));
        setBounds(10, 120, 200, 180);

        Font fontLabel = fuente.fuente(6, true);
        Font fontTitulo = fuente.fuente(5, true);

        // ===== TÍTULO =====
        lblTitulo = new JLabel("Histórico de Compras", SwingConstants.CENTER);
        lblTitulo.setFont(fontTitulo);
        lblTitulo.setBounds(10, 10, 180, 30);
        add(lblTitulo);

        // ===== MES =====
        lblMes = new JLabel("Mes:");
        lblMes.setFont(fontLabel);
        lblMes.setBounds(20, 50, 80, 25);
        add(lblMes);

        comboMes = new JComboBox<>(new String[] {
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        });
        comboMes.setFont(fuente.fuente(6, true));
        comboMes.setBounds(80, 50, 100, 25);
        add(comboMes);

        // ===== AÑO =====
        lblAnio = new JLabel("Año:");
        lblAnio.setFont(fontLabel);
        lblAnio.setBounds(20, 90, 80, 25);
        add(lblAnio);

        comboAnio = new JComboBox<>();
        comboAnio.setBounds(80, 90, 100, 25);
        comboAnio.setFont(fuente.fuente(6, true));
        add(comboAnio);

        // Llenar los años (por ejemplo, desde 2020 hasta el actual)
        int anioActual = LocalDate.now().getYear();
        for (int i = 2025; i <= anioActual; i++) {
            comboAnio.addItem(String.valueOf(i));
        }

        // Restringir meses futuros si el año es el actual
        comboAnio.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                restringirMeses();
            }
        });

        // ===== BOTÓN =====
        generar = new JButton("Generar Histórico");
        generar.setFocusPainted(false);
        generar.setFocusable(false);
        generar.setBackground(new Color(0xDFF6FD));
        generar.setForeground(Color.BLACK);
        generar.setFont(fuente.fuente(6, true));
        generar.setBounds(20, 140, 160, 30);
        generar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(generar);

        restringirMeses();
    }

    private void restringirMeses() {
        int mesActual = LocalDate.now().getMonthValue();
        int anioSeleccionado = Integer.parseInt(comboAnio.getSelectedItem().toString());
        int anioActual = LocalDate.now().getYear();

        comboMes.removeAllItems();
        String[] meses = {
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        };

        for (int i = 0; i < (anioSeleccionado == anioActual ? mesActual : 12); i++) {
            comboMes.addItem(meses[i]);
        }
    }

    public void agregarBtnDescargarInforme(String tipo) {
        descargar = new JButton(tipo);
        descargar.setBackground(new Color(0xDFF6FD));
        descargar.setForeground(Color.black);
        descargar.setFont(fuente.fuente(6, true));
        descargar.setFocusable(false);
        descargar.setBounds(generar.getX(), generar.getY() + 35, 160, 30);
        descargar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(descargar);
    }

    public JButton getDescarga() {
        return descargar;
    }

    public JButton getGenerarReporte() {
        return generar;
    }

    public String getMesSeleccionado() {
        return comboMes.getSelectedItem().toString();
    }

    public String getAñoSeleccionado() {
        return comboAnio.getSelectedItem().toString();
    }

    public int obtenerNumeroMes() {
        return comboMes.getSelectedIndex() + 1;
    }
}