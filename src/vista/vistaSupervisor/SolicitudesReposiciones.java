package vista.vistaSupervisor;

import javax.swing.*;

import vista.componentes.Header;
import vista.vistaSupervisor.componentes.RoundedPanel;

import java.awt.*;
import java.io.InputStream;

public class SolicitudesReposiciones extends JFrame {
    private JPanel panelP, panelSoliContent;
    private RoundedPanel panelSoliRepo;
    private JLabel lSoliRepo;
    private Font customFont;
    private Header header;

    public SolicitudesReposiciones() {
        setTitle("Solicitud de Reposicion");
        setSize(1300, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        panelP = new JPanel();
        panelP.setBackground(Color.white);
        panelP.setLayout(null);
        panelP.setBounds(0, 0, 1300, 700);

        try {
            InputStream is = SolicitudesReposiciones.class.getResourceAsStream("/fonts/newCentury.ttf");
            customFont = Font.createFont(Font.TRUETYPE_FONT, is);
            is.close();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (Exception e) {
            e.printStackTrace();
        }

        header = new Header();
        panelP.add(header);

        lSoliRepo = new JLabel("Solicitud de Reposicion");
        if (customFont != null) {
            lSoliRepo.setFont(customFont.deriveFont(Font.PLAIN, 56f));
        } else {
            lSoliRepo.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 56));
        }
        lSoliRepo.setBounds(200, 100, 800, 80);

        panelP.add(lSoliRepo);

        // Crear el contenedor interior que alojará múltiples panelSoli en columna
        panelSoliContent = new JPanel();
        panelSoliContent.setLayout(new BoxLayout(panelSoliContent, BoxLayout.Y_AXIS));
        panelSoliContent.setOpaque(false); // dejamos que el RoundedPanel exterior pinte el fondo

        // Envolver en un JScrollPane
        JScrollPane scroll = new JScrollPane(panelSoliContent);
        scroll.setBounds(20, 30, 940, 380);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        // Hacer transparente el viewport para que se vea el fondo redondeado
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);

        // Panel exterior con esquinas redondeadas que contendrá al JScrollPane
        panelSoliRepo = new RoundedPanel(20, Color.LIGHT_GRAY);
        panelSoliRepo.setShadowSize(1);
        panelSoliRepo.setLayout(null);
        panelSoliRepo.setBounds(200, 200, 980, 430);
        panelSoliRepo.add(scroll, BorderLayout.CENTER);

        panelP.add(panelSoliRepo);
        add(panelP);
    }

    public JPanel getPanelSoliContent() {
        return panelSoliContent;
    }

    public Font getCustomFont() {
        return customFont;
    }
}
