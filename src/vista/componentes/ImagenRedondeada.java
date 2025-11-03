package vista.componentes;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class ImagenRedondeada {

    public ImagenRedondeada() {
    }

    public static ImageIcon imagenRedonda(BufferedImage originalImage, int width, int height, int cornerRadius) {
        int type = BufferedImage.TYPE_INT_ARGB;
        BufferedImage resized = new BufferedImage(width, height, type);
        Graphics2D g2 = resized.createGraphics();

        // ---- Calidad de renderizado ----
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // ---- Clip redondeado ----
        Shape clip = new java.awt.geom.RoundRectangle2D.Double(0, 0, width, height, cornerRadius, cornerRadius);
        g2.setClip(clip);

        // ---- Dibujar imagen escalada ----
        g2.drawImage(originalImage, 0, 0, width, height, null);

        g2.dispose();

        return new ImageIcon(resized);
    }
}
