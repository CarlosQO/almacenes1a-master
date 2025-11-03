package vista.componentes;

import java.awt.*;
import javax.swing.*;

public class RoundedButton extends JButton {
    private Color backgroundColor;

    // sin img
    public RoundedButton(String text, Color backgroundColor) {
        super(text);
        this.backgroundColor = backgroundColor;
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        // setHorizontalAlignment(SwingConstants.CENTER);
        // setHorizontalTextPosition(SwingConstants.CENTER);
        // setVerticalTextPosition(SwingConstants.CENTER);
        setMargin(new Insets(0, 10, 0, 10));
    }

    // con img
    public RoundedButton(String text, Icon icon, Color backgroundColor) {
        super(text, icon);
        this.backgroundColor = backgroundColor;
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setHorizontalAlignment(SwingConstants.CENTER);
        setHorizontalTextPosition(SwingConstants.LEFT); // Texto a la izquierda del icono
        setVerticalTextPosition(SwingConstants.CENTER);
        setIconTextGap(8);
        setMargin(new Insets(0, 10, 0, 10));

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // fondo redondeado
        g2.setColor(backgroundColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        // texto o imagen originales
        super.paintComponent(g);
        g2.dispose();
    }
}
