package vista.componentes;

import org.jdesktop.swingx.JXPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Path2D;

public class RoundedJXPanel extends JXPanel {

    private int cornerRadius;
    private Color borderColor;
    private Color shadowColor = new Color(50, 50, 50, 80);
    private int shadowSize = 20;
    private int roundAllCorners = 1;

    // 🔹 NUEVAS VARIABLES PARA EL DEGRADADO
    private boolean usarDegradado = false;
    private Color colorDegradadoInicio = Color.WHITE;
    private Color colorDegradadoFin = new Color(200, 220, 255); // Azul claro por defecto

    // ---- CONSTRUCTOR ----
    public RoundedJXPanel(int radius, int borderHex) {
        this.cornerRadius = radius;
        this.borderColor = new Color(borderHex);
        setOpaque(false);
    }

    // ---- NUEVOS SETTERS PARA EL DEGRADADO ----
    public void setDegradado(boolean usar, Color inicio, Color fin) {
        this.usarDegradado = usar;
        if (inicio != null)
            this.colorDegradadoInicio = inicio;
        if (fin != null)
            this.colorDegradadoFin = fin;
        repaint();
    }

    // ---- SETTERS EXISTENTES ----
    public void setShadowColor(Color shadowColor) {
        this.shadowColor = shadowColor;
        repaint();
    }

    public void setShadowSize(int size) {
        this.shadowSize = size;
        repaint();
    }

    public void setRoundAllCorners(int roundAll) {
        this.roundAllCorners = roundAll;
        repaint();
    }

    // ---- PINTADO ----
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getWidth();
        int h = getHeight();

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // ---- SOMBRA ----
        // ---- SOMBRA MEJORADA ----
        for (int i = shadowSize; i > 0; i--) {
            // Crea una opacidad más suave y no lineal (más difusa hacia afuera)
            float alpha = (float) Math.pow((double) i / shadowSize, 2.2); // curva gamma suave

            // Menor opacidad base (para evitar sombras densas)
            int transparencia = (int) (alpha * 80); // antes era *100

            // Color de sombra ajustado
            g2.setColor(new Color(
                    shadowColor.getRed(),
                    shadowColor.getGreen(),
                    shadowColor.getBlue(),

                    Math.min(transparencia, 100)));

            int desplazamiento = i / 2; // leve desplazamiento para naturalidad

            switch (roundAllCorners) {
                case 1 -> g2.fillRoundRect(desplazamiento, desplazamiento, w - 2 * desplazamiento,
                        h - 2 * desplazamiento, cornerRadius, cornerRadius);
                case 2 -> g2.fill(createTopRoundedPath(desplazamiento, desplazamiento, w - 2 * desplazamiento,
                        h - 2 * desplazamiento, cornerRadius));
                case 3 -> g2.fill(createBottomLeftRoundedPath(desplazamiento, desplazamiento, w - 2 * desplazamiento,
                        h - 2 * desplazamiento, cornerRadius));
            }
        }

        // ---- FONDO (CON DEGRADADO O COLOR FIJO) ----
        if (usarDegradado) {
            GradientPaint gradiente = new GradientPaint(
                    0, 0,
                    colorDegradadoInicio,
                    w, 0,
                    colorDegradadoFin);
            g2.setPaint(gradiente);
        } else {
            g2.setColor(getBackground());
        }

        switch (roundAllCorners) {
            case 1 -> g2.fillRoundRect(shadowSize, shadowSize, w - shadowSize * 2, h - shadowSize * 2, cornerRadius,
                    cornerRadius);
            case 2 -> g2.fill(
                    createTopRoundedPath(shadowSize, shadowSize, w - shadowSize * 2, h - shadowSize * 2, cornerRadius));
            case 3 -> g2.fill(createBottomLeftRoundedPath(shadowSize, shadowSize, w - shadowSize * 2,
                    h - shadowSize * 2, cornerRadius));
        }

        // ---- BORDE ----
        g2.setStroke(new BasicStroke(2f));
        g2.setColor(borderColor);
        switch (roundAllCorners) {
            case 1 -> g2.drawRoundRect(shadowSize, shadowSize, w - shadowSize * 2, h - shadowSize * 2, cornerRadius,
                    cornerRadius);
            case 2 -> g2.draw(
                    createTopRoundedPath(shadowSize, shadowSize, w - shadowSize * 2, h - shadowSize * 2, cornerRadius));
            case 3 -> g2.draw(createBottomLeftRoundedPath(shadowSize, shadowSize, w - shadowSize * 2,
                    h - shadowSize * 2, cornerRadius));
        }

        g2.dispose();
    }

    // ---- MÉTODOS AUXILIARES ----
    private Path2D createTopRoundedPath(int x, int y, int width, int height, int radius) {
        Path2D path = new Path2D.Double();
        path.moveTo(x, y + radius);
        path.quadTo(x, y, x + radius, y);
        path.lineTo(x + width - radius, y);
        path.quadTo(x + width, y, x + width, y + radius);
        path.lineTo(x + width, y + height);
        path.lineTo(x, y + height);
        path.closePath();
        return path;
    }

    private Shape createBottomLeftRoundedPath(int x, int y, int w, int h, int r) {
        Path2D path = new Path2D.Double();
        path.moveTo(x, y);
        path.lineTo(x + w, y);
        path.lineTo(x + w, y + h);
        path.lineTo(x + r, y + h);
        path.quadTo(x, y + h, x, y + h - r);
        path.lineTo(x, y);
        path.closePath();
        return path;
    }
}
