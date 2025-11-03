package vista.componentes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

public class ImagenRoundedPanel extends JPanel {
    private final int cornerRadius;
    private final Color backgroundColor;
    private final Image imagen;

    public ImagenRoundedPanel(int cornerRadius, Color backgroundColor, Image imagen) {
        this.cornerRadius = cornerRadius;
        this.backgroundColor = backgroundColor;
        this.imagen = imagen;
        setOpaque(false); // importante para que no se pinte el fondo por defecto
    }

    @Override
    protected void paintComponent(Graphics g) {
        // no llamar a super.paintComponent(g) porque queremos controlar el pintado y el
        // clip
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // forma redondeada que usaremos como clip
            RoundRectangle2D.Float clip = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius,
                    cornerRadius);
            g2.setClip(clip);

            // fondo (opcional)
            if (backgroundColor != null) {
                g2.setColor(backgroundColor);
                g2.fill(clip);
            }

            // dibujar imagen escalada al tamaño del panel (si existe)
            if (imagen != null) {
                g2.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
            }
        } finally {
            g2.dispose();
        }
    }
}