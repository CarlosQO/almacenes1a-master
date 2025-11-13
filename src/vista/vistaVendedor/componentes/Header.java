package vista.vistaVendedor.componentes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.JPanel;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javax.swing.JOptionPane;

public class Header extends JPanel {

    private JPanel header;

    public Header() {
    }

    public JPanel headerCopiar(Color color, int width) {
        header = new JPanel();
        header.setBackground(color);
        header.setLayout(null);
        header.setBounds(0, 0, width, 80);

        // --- LOGO (JavaFX) ---
        JFXPanel fxLogoPanel = new JFXPanel(); // AQUI
        fxLogoPanel.setBounds(10, 5, 80, 80);
        fxLogoPanel.setOpaque(false); // Fondo Swing transparente
        header.add(fxLogoPanel);

        Platform.runLater(() -> {
            try {
                Image fxLogo = new Image(
                        new File("src/Iconos/LogoAlmacen1A.png").toURI().toString(),
                        80, 80, true, true);
                ImageView fxLogoView = new ImageView(fxLogo);
                fxLogoView.setPreserveRatio(true);
                fxLogoView.setSmooth(true);
                fxLogoView.setCache(true);

                // Clip redondeado
                javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle(80, 80);
                clip.setArcWidth(20);
                clip.setArcHeight(20);
                fxLogoView.setClip(clip);

                StackPane logoPane = new StackPane(fxLogoView);
                logoPane.setStyle("-fx-background-color: transparent;");

                // Usa el nombre completo para el color transparente
                Scene logoScene = new Scene(logoPane, javafx.scene.paint.Color.TRANSPARENT);
                fxLogoPanel.setScene(logoScene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // --- ICONO DE USUARIO (JavaFX) ---
        JFXPanel fxUserPanel = new JFXPanel();
        fxUserPanel.setBounds(header.getWidth() - 100, 10, 60, 60);
        fxUserPanel.setOpaque(false);
        fxUserPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(null, "Click desde icono user promociones");
            }
        });
        header.add(fxUserPanel);

        Platform.runLater(() -> {
            try {
                Image fxUser = new Image(
                        new File("src/Iconos/user.png").toURI().toString(),
                        60, 60, true, true);
                ImageView fxUserView = new ImageView(fxUser);
                fxUserView.setPreserveRatio(true);
                fxUserView.setSmooth(true);
                fxUserView.setCache(true);
                fxUserView.setCursor(javafx.scene.Cursor.HAND);
                // Clip redondeado
                javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle(60, 60);
                clip.setArcWidth(20);
                clip.setArcHeight(20);
                fxUserView.setClip(clip);

                StackPane userPane = new StackPane(fxUserView);
                userPane.setStyle("-fx-background-color: transparent;");

                Scene userScene = new Scene(userPane, javafx.scene.paint.Color.TRANSPARENT);
                fxUserPanel.setScene(userScene);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        fxUserPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return header;
    }

}
