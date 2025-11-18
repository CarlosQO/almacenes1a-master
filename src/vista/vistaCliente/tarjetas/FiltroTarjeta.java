package vista.vistaCliente.tarjetas;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.jdesktop.swingx.JXDatePicker;
import java.util.Date;

public class FiltroTarjeta extends JPanel {
    private String nombre, nombreBoton;
    private JPanel panelFiltroActividad;
    private JLabel nombreFiltro;
    private JLabel fechaInicioLabel, fechaFinLabel;
    public JButton buscar;
    private JXDatePicker fechaInicio, fechaFin;

    public FiltroTarjeta(String nombre, String nombreBoton) {
        this.nombre = nombre;
        this.nombreBoton = nombreBoton;
        this.setLayout(null);
        this.setBackground(new Color(220, 220, 220)); // fondo del panel

        panelFiltroActividad = new JPanel();
        panelFiltroActividad.setLayout(null);
        panelFiltroActividad.setBounds(0, 0, 680, 180);
        Color grisFondo = new Color(220, 220, 220);
        panelFiltroActividad.setBackground(grisFondo);

        nombreFiltro = new JLabel(nombre);
        nombreFiltro.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        nombreFiltro.setBounds(200, 10, 400, 30);
        panelFiltroActividad.add(nombreFiltro);

        fechaInicioLabel = new JLabel("Fecha de inicio");
        fechaFinLabel = new JLabel("Fecha de Fin");
        fechaInicioLabel.setBounds(30, 70, 100, 20);
        fechaFinLabel.setBounds(300, 70, 100, 20);
        panelFiltroActividad.add(fechaInicioLabel);
        panelFiltroActividad.add(fechaFinLabel);

        // Fecha Inicio
        fechaInicio = new JXDatePicker();
        fechaInicio.setFormats("dd/MM/yyyy");
        fechaInicio.setBounds(30, 100, 140, 40);
        // Evitar edicion manual
        ((JTextField) fechaInicio.getEditor()).setEditable(false);
        panelFiltroActividad.add(fechaInicio);

        // Fecha Fin
        fechaFin = new JXDatePicker();
        fechaFin.setFormats("dd/MM/yyyy");
        fechaFin.setBounds(300, 100, 140, 40);
        // Evitar edicion manual
        ((JTextField) fechaFin.getEditor()).setEditable(false);
        panelFiltroActividad.add(fechaFin);

        buscar = new JButton(nombreBoton);
        buscar.setBounds(500, 100, 150, 40);
        Color colorBoton = new Color(180, 230, 255);
        buscar.setBackground(colorBoton);
        buscar.setBorderPainted(false);
        buscar.setFocusPainted(false);
        buscar.setContentAreaFilled(false);
        buscar.setOpaque(true);
        panelFiltroActividad.add(buscar);

        this.add(panelFiltroActividad);
    }

    public String getFechaInicio() {
        if (fechaInicio.getDate() != null) {
            return new java.text.SimpleDateFormat("yyyy-MM-dd").format(fechaInicio.getDate());
        } else {
            return null;
        }
    }

    public String getFechaFin() {
        if (fechaFin.getDate() != null) {
            return new java.text.SimpleDateFormat("yyyy-MM-dd").format(fechaFin.getDate());
        } else {
            return null;
        }
    }

    public boolean validarFechas() {
        Date hoy = new Date(); // fecha actual

        if (fechaInicio.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Debe llenar el campo de la fecha de inicio");
            return false;
        }
        if (fechaFin.getDate() == null) {
            JOptionPane.showMessageDialog(null, "Debe llenar el campo de la fecha fin");
            return false;
        }
        // Validar que la fecha fin no sea anterior a la fecha inicio
        if (fechaFin.getDate().before(fechaInicio.getDate())) {
            JOptionPane.showMessageDialog(null, "La fecha fin no puede ser anterior a la fecha de inicio");
            return false;
        }
        // Validar que las fechas no sean superiores al día actual
        if (fechaInicio.getDate().after(hoy)) {
            JOptionPane.showMessageDialog(null, "La fecha de inicio no puede ser superior al día de hoy");
            return false;
        }
        if (fechaFin.getDate().after(hoy)) {
            JOptionPane.showMessageDialog(null, "La fecha fin no puede ser superior al día de hoy");
            return false;
        }

        return true;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombreFiltro) {
        this.nombre = nombreFiltro;
    }

    public String getNombreBoton() {
        return nombreBoton;
    }

    public void setNombreBoton(String nombreBoton) {
        this.nombreBoton = nombreBoton;
    }

}
