package vista.componentes;

import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.jdesktop.swingx.JXDatePicker;

import vista.fuenteLetra.Fuente;
import vista.vistaAdministrador.RoundedPanel;

public class FechasComponenteV2 extends RoundedPanel {

    public JXDatePicker fechaInicio;
    public JXDatePicker fechaFin;
    private JLabel titulo;
    public JButton generar, descargar;
    private Fuente fuente = new Fuente();

    public FechasComponenteV2(int radius, int borderHex, String tipo, String nombreInforme) {
        super(radius, borderHex);

        // Configuración base del panel
        this.setShadowSize(1);
        this.setBackground(new Color(0xCFCFCF));
        this.setLayout(null);

        // ----- Título -----
        titulo = new JLabel(nombreInforme);
        titulo.setFont(fuente.fuente(3, true));
        titulo.setForeground(Color.black);
        titulo.setBounds(30, 10, 580, 30);
        this.add(titulo);

        // ----- Fecha Inicio -----
        fechaInicio = new JXDatePicker();
        fechaInicio.setFormats("dd/MM/yyyy");
        fechaInicio.setFont(fuente.fuente(5, false));
        fechaInicio.setBounds(titulo.getX(), titulo.getY() + titulo.getHeight() + 10, 180, 50);
        fechaInicio.setBorder(Promociones.crearTituloSinLinea("Fecha Inicio", new Color(0x000000)));
        agregarPlaceholderFecha(fechaInicio, "DD/MM/AAAA");

        // Evitar edición manual
        ((JTextField) fechaInicio.getEditor()).setEditable(false);

        this.add(fechaInicio);

        // Fecha Fin
        fechaFin = new JXDatePicker();
        fechaFin.setFormats("dd/MM/yyyy");
        fechaFin.setFont(fuente.fuente(5, false));
        fechaFin.setBounds(fechaInicio.getX() + fechaInicio.getWidth() + 20, fechaInicio.getY(), 180, 50);
        fechaFin.setBorder(Promociones.crearTituloSinLinea("Fecha Fin", new Color(0x000000)));
        agregarPlaceholderFecha(fechaFin, "DD/MM/AAAA");

        // Evitar edición manual
        ((JTextField) fechaFin.getEditor()).setEditable(false);

        this.add(fechaFin);

        // Botón
        generar = new JButton(tipo);
        generar.setBackground(new Color(0xDFF6FD));
        generar.setForeground(Color.black);
        generar.setFont(fuente.fuente(5, false));
        generar.setFocusable(false);
        generar.setBounds(fechaFin.getX() + fechaFin.getWidth() + 20, fechaFin.getY() + 10, 180, 35);
        generar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(generar);
    }

    private void agregarPlaceholderFecha(JXDatePicker datePicker, String placeholder) {
        JTextField editor = (JTextField) datePicker.getEditor();
        datePicker.setCursor(new Cursor(Cursor.HAND_CURSOR));
        editor.setForeground(Color.GRAY);
        editor.setText(placeholder);

        editor.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (editor.getText().equals(placeholder)) {
                    editor.setText("");
                    editor.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (editor.getText().isEmpty()) {
                    editor.setForeground(Color.GRAY);
                    editor.setText(placeholder);
                }
            }
        });
    }

    // Getter opcional si lo usas desde el controlador
    public JButton getGenerarReporte() {
        return generar;
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

    public void agregarBtnDescargarInforme(String tipo) {
        descargar = new JButton(tipo);
        descargar.setBackground(new Color(0xDFF6FD));
        descargar.setForeground(Color.black);
        descargar.setFont(fuente.fuente(5, false));
        descargar.setFocusable(false);
        descargar.setBounds(generar.getX() + generar.getWidth() + 20, generar.getY(), 180, generar.getHeight());
        descargar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        this.add(descargar);
    }

    public JButton getDescarga() {
        return descargar;
    }
}
