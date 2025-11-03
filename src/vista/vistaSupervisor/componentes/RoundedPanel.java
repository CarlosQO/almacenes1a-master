package vista.vistaSupervisor.componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;

public class RoundedPanel extends JPanel {
    private int cornerRadius;
    private Color borderColor;
    private Color shadowColor = new Color(50, 50, 50, 80);
    private int shadowSize = 20; // difuminado
    private int roundAllCorners = 1; // 1 para todas las curvas, 2 curvas superiores, 3 solo la curva inferior
                                     // izquierda

    public RoundedPanel(int radius, Color color) {
        this.cornerRadius = radius;
        this.borderColor = color;
        setOpaque(false);
    }

    // ---- SETTERS ----
    public void setShadowColor(Color shadowColor) {
        this.shadowColor = shadowColor;
        repaint();
    }

    public void setShadowSize(int size) {
        this.shadowSize = size;
        repaint();
    }

    // Cambiar tipo de esquinas
    public void setRoundAllCorners(int roundAll) {
        this.roundAllCorners = roundAll;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth();
        int h = getHeight();

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // ---- SOMBRA ----
        for (int i = shadowSize; i > 0; i--) {
            float alpha = (float) i / (shadowSize * 15f);
            g2.setColor(new Color(
                    shadowColor.getRed(),
                    shadowColor.getGreen(),
                    shadowColor.getBlue(),
                    (int) (alpha * 100)));

            if (roundAllCorners == 1) {
                g2.fillRoundRect(i, i, w - 2 * i, h - 2 * i, cornerRadius, cornerRadius);
            } else if (roundAllCorners == 2) {
                g2.fill(createTopRoundedPath(i, i, w - 2 * i, h - 2 * i, cornerRadius));
            } else if (roundAllCorners == 3) {
                g2.fill(createBottomLeftRoundedPath(i, i, w - 2 * i, h - 2 * i, cornerRadius));
            } else {
                System.out.println("Los bordes que se pueden usar es del 1 hasta el 3 sombra");
            }
        }

        // ---- FONDO ----
        g2.setColor(getBackground());
        if (roundAllCorners == 1) {
            g2.fillRoundRect(shadowSize, shadowSize, w - shadowSize * 2, h - shadowSize * 2, cornerRadius,
                    cornerRadius);
        } else if (roundAllCorners == 2) {
            g2.fill(createTopRoundedPath(shadowSize, shadowSize, w - shadowSize * 2, h - shadowSize * 2, cornerRadius));
        } else if (roundAllCorners == 3) {
            g2.fill(createBottomLeftRoundedPath(shadowSize, shadowSize, w - shadowSize * 2, h - shadowSize * 2,
                    cornerRadius));
        } else {
            System.out.println("Los bordes que se pueden usar es del 1 hasta el 3 fondo");
        }

        // ---- BORDE ----
        g2.setStroke(new BasicStroke(2f));
        g2.setColor(borderColor);
        if (roundAllCorners == 1) {
            // todas las esquinas
            g2.drawRoundRect(shadowSize, shadowSize, w - shadowSize * 2, h - shadowSize * 2, cornerRadius,
                    cornerRadius);
        } else if (roundAllCorners == 2) {
            // solo esquinas superiores
            g2.draw(createTopRoundedPath(shadowSize, shadowSize, w - shadowSize * 2, h - shadowSize * 2, cornerRadius));
        } else if (roundAllCorners == 3) {
            // solo esquina inferior izquierda
            g2.draw(createBottomLeftRoundedPath(shadowSize, shadowSize, w - shadowSize * 2, h - shadowSize * 2,
                    cornerRadius));
        } else {
            System.out.println("Los bordes que se pueden usar es del 1 hasta el 3 borde");
        }

        g2.dispose();
    }

    // ---- Método para crear Path con solo esquinas superiores redondeadas ----
    private Path2D createTopRoundedPath(int x, int y, int width, int height, int radius) {
        Path2D path = new Path2D.Double();
        path.moveTo(x, y + radius); // lado izquierdo
        path.quadTo(x, y, x + radius, y); // esquina superior izquierda
        path.lineTo(x + width - radius, y); // lado superior
        path.quadTo(x + width, y, x + width, y + radius);// esquina superior derecha
        path.lineTo(x + width, y + height); // lado derecho
        path.lineTo(x, y + height); // lado inferior
        path.closePath();
        return path;
    }

    private Shape createBottomLeftRoundedPath(int x, int y, int w, int h, int r) {
        Path2D path = new Path2D.Double();
        path.moveTo(x, y);// Comenzamos en la esquina superior izquierda (sin redondear)
        path.lineTo(x + w, y);// Línea recta hacia arriba derecha
        path.lineTo(x + w, y + h);// Línea recta hacia abajo derecha
        path.lineTo(x + r, y + h);// Línea recta hasta el inicio de la curva inferior izquierda
        path.quadTo(x, y + h, x, y + h - r);// Curva cuadrática para redondear la esquina inferior izquierda
        path.lineTo(x, y);// Subir por el borde izquierda
        path.closePath();
        return path;
    }

    public static ImageIcon resizeImage(BufferedImage originalImage, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.drawImage(originalImage, 0, 0, width, height, null);
        g2.dispose();

        return new ImageIcon(resizedImage);
    }
}