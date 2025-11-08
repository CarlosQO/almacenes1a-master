package vista.componentes;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ScrollPersonalizado extends JPanel {
    private JPanel contenido;
    private Point clickInicial;
    private boolean scrollHorizontal;
    private boolean scrollVertical;
    private int velocidadScroll = 30;

    public ScrollPersonalizado(JPanel contenido, String tipoScroll, int anchoVisible, int altoVisible) {
        this.contenido = contenido;
        setLayout(null);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(anchoVisible, altoVisible));

        // Detectar tipo de scroll
        switch (tipoScroll.toLowerCase()) {
            case "horizontal":
                scrollHorizontal = true;
                scrollVertical = false;
                break;
            case "vertical":
                scrollHorizontal = false;
                scrollVertical = true;
                break;
            default:
                scrollHorizontal = true;
                scrollVertical = true;
                break;
        }

        // Ajustar tamaño del contenido
        contenido.setSize(contenido.getPreferredSize());
        contenido.setLocation(0, 0);
        add(contenido);

        // Eventos del mouse (arrastre manual)
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                clickInicial = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int dx = e.getX() - clickInicial.x;
                int dy = e.getY() - clickInicial.y;
                mover(dx, dy);
                clickInicial = e.getPoint();
            }
        });

        // Evento de rueda del mouse
        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int notches = e.getWheelRotation();

                // Si solo scroll vertical
                if (scrollVertical && !scrollHorizontal) {
                    mover(0, -notches * velocidadScroll);
                }
                // Si solo scroll horizontal (mantener Shift para moverse horizontalmente)
                else if (scrollHorizontal && !scrollVertical) {
                    mover(-notches * velocidadScroll, 0);
                }
                // Si ambos, detectar si se mantiene Shift
                else if (scrollHorizontal && scrollVertical) {
                    if (e.isShiftDown()) {
                        mover(-notches * velocidadScroll, 0); // scroll horizontal con Shift
                    } else {
                        mover(0, -notches * velocidadScroll); // scroll vertical normal
                    }
                }
            }
        });
    }

    private void mover(int dx, int dy) {
        int nuevoX = contenido.getX();
        int nuevoY = contenido.getY();

        // Desplazamiento horizontal
        if (scrollHorizontal) {
            nuevoX += dx;
            if (nuevoX > 0)
                nuevoX = 0;
            if (nuevoX < getWidth() - contenido.getWidth())
                nuevoX = getWidth() - contenido.getWidth();
        }

        // Desplazamiento vertical
        if (scrollVertical) {
            nuevoY += dy;
            if (nuevoY > 0)
                nuevoY = 0;
            if (nuevoY < getHeight() - contenido.getHeight())
                nuevoY = getHeight() - contenido.getHeight();
        }

        contenido.setLocation(nuevoX, nuevoY);
        repaint();
    }

    public void habilitarArrastreEnHijos(Component componente) {
        if (componente instanceof Container contenedor) {
            for (Component hijo : contenedor.getComponents()) {
                habilitarArrastreEnHijos(hijo);
            }
        }

        if (componente != this) {
            componente.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    dispatchEvent(SwingUtilities.convertMouseEvent(e.getComponent(), e, ScrollPersonalizado.this));
                }
            });

            componente.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    dispatchEvent(SwingUtilities.convertMouseEvent(e.getComponent(), e, ScrollPersonalizado.this));
                }
            });
        }
    }
}
