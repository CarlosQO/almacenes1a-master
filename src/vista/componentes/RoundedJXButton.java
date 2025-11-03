package vista.componentes;

import org.jdesktop.swingx.JXButton;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundedJXButton extends JXButton {

    private int cornerRadius = 20; // Radio de esquinas
    private Color borderColor = new Color(0x007BFF); // Color del borde
    private float borderWidth = 2f; // Grosor del borde

    private Color baseColor = new Color(0x007BFF); // Color de fondo normal
    private Color hoverColor = new Color(0x339CFF); // Color al pasar el mouse
    private Color pressedColor = new Color(0x005FCC); // Color al presionar
    private Color currentBackground; // Fondo actual dinámico

    public RoundedJXButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setForeground(Color.WHITE);
        setFont(getFont().deriveFont(Font.BOLD, 14f));

        currentBackground = baseColor;
        setBackground(baseColor);

        // Listener de interacción
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                currentBackground = hoverColor;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                currentBackground = baseColor;
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                currentBackground = pressedColor;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                currentBackground = hoverColor;
                repaint();
            }
        });
    }

    // --- SETTERS PERSONALIZABLES ---
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

    public void setBaseColor(Color baseColor) {
        this.baseColor = baseColor;
        this.currentBackground = baseColor;
        repaint();
    }

    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    public void setPressedColor(Color pressedColor) {
        this.pressedColor = pressedColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // --- Fondo redondeado ---
        g2.setColor(currentBackground);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));

        // --- Texto centrado ---
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
