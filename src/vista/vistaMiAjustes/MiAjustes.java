package vista.vistaMiAjustes;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class MiAjustes extends JFrame {
    private JLabel icono;
    private Container contain;

    public MiAjustes() {
        setTitle("Mis Ajustes");
        setSize(200, 250);
        setLocationRelativeTo(null);
        setResizable(false);
        setBackground(Color.white);
        contain = getContentPane();
        contain.setLayout(null);

    }
    /*
     * // carrito de compra
     * carritoContain = new RoundedPanel(40, 0x9E9C9C);
     * carritoContain.setBackground(Color.white);
     * carritoContain.setRoundAllCorners(3);
     * carritoContain.setLayout(null);
     * carritoContain.setShadowSize(1);
     * carritoContain.setBounds(principalContainer.getWidth() - 230, header.getY() +
     * header.getHeight(), 200,
     * 250);
     * 
     * JLabel miPerfil = new JLabel("Mi Perfil");
     * miPerfil.setFont(fuente(false));
     * miPerfil.setBounds(70, 10, (int) miPerfil.getPreferredSize().getWidth(),
     * (int) miPerfil.getPreferredSize().getHeight());
     * carritoContain.add(miPerfil);
     * 
     * JLabel equis = new JLabel("<html><p style='font-Size: 20px;'>X</p></html>");
     * equis.setFont(fuente(false));
     * equis.setBounds(carritoContain.getWidth() - (int)
     * equis.getPreferredSize().getWidth() - 10, 0,
     * (int) equis.getPreferredSize().getWidth(), (int)
     * equis.getPreferredSize().getHeight());
     * equis.setCursor(new Cursor(Cursor.HAND_CURSOR));
     * equis.addMouseListener(new MouseAdapter() {
     * 
     * @Override
     * public void mouseClicked(MouseEvent e) {
     * carritoContain.setVisible(false);
     * carritoBooleano = false;
     * repaint();
     * revalidate();
     * }
     * });
     * carritoContain.add(equis);
     */
}
