package vista.componentes;

import org.jdesktop.swingx.JXComboBox;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

public class RoundedJXComboBox<E> extends JXComboBox {

    private int cornerRadius = 20;
    private Color borderColor = new Color(0xFFFFFF);
    private float borderWidth = 2f;
    private Color backgroundColor = new Color(227, 227, 227, 255);
    private Color textColor = Color.BLACK;

    public RoundedJXComboBox() {
        super();
        setOpaque(false);
        setEditable(true);
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setForeground(textColor);
        setBackground(backgroundColor);
        setFocusable(false);
        AutoCompleteDecorator.decorate(this);
        setBorder(BorderFactory.createEmptyBorder());

        // Fondo del campo de texto
        if (getEditor().getEditorComponent() instanceof JTextField editor) {
            editor.setOpaque(false);
            editor.setBackground(backgroundColor);
            editor.setForeground(textColor);
            editor.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        }

        // Fondo y alineación de la lista
        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list,
                    Object value,
                    int index,
                    boolean isSelected,
                    boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected,
                        cellHasFocus);
                label.setOpaque(true);
                label.setBackground(isSelected ? new Color(0xE3F2FD) : backgroundColor);
                label.setForeground(isSelected ? borderColor : textColor);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });

        // --- UI personalizada para quitar borde del botón ---
        setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton("▼");
                button.setBorder(BorderFactory.createEmptyBorder());
                button.setContentAreaFilled(false);
                button.setOpaque(false);
                button.setFocusPainted(false);
                button.setForeground(textColor);
                return button;
            }
        });
    }

    // --- SETTERS PERSONALIZADOS ---
    public void setCornerRadius(int cornerRadius) {
        this.cornerRadius = cornerRadius;
        repaint();
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        repaint();
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
        repaint();
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        setBackground(backgroundColor);
        if (getEditor().getEditorComponent() instanceof JTextField editor) {
            editor.setBackground(backgroundColor);
        }
        repaint();
    }

    // --- DIBUJO PERSONALIZADO ---
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        g2.setColor(backgroundColor);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(borderColor);
        g2.setStroke(new BasicStroke(borderWidth));

        int w = getWidth();
        int h = getHeight();
        g2.draw(new RoundRectangle2D.Float(borderWidth / 2, borderWidth / 2,
                w - borderWidth, h - borderWidth, cornerRadius, cornerRadius));

        g2.dispose();
    }
}
