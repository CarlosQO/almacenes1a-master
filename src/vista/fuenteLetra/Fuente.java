package vista.fuenteLetra;

import java.awt.Font;
import java.io.File;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Fuente {

    public Fuente() {
    }

    public Font fuente(int tamañoFuente, boolean tipoFuente) {
        try {
            if (tamañoFuente == 1) {
                if (tipoFuente) {
                    Font customFont = Font
                            .createFont(Font.TRUETYPE_FONT, new File("src/fonts/newCentury.ttf"))
                            .deriveFont(Font.BOLD, 50);
                    return customFont;
                } else {
                    Font customFont = Font
                            .createFont(Font.TRUETYPE_FONT, new File("src/fonts/newCentury.ttf"))
                            .deriveFont(Font.PLAIN, 50);
                    return customFont;
                }
            } else if (tamañoFuente == 2) {
                if (tipoFuente) {
                    Font customFont = Font
                            .createFont(Font.TRUETYPE_FONT, new File("src/fonts/newCentury.ttf"))
                            .deriveFont(Font.BOLD, 40);
                    return customFont;
                } else {
                    Font customFont = Font
                            .createFont(Font.TRUETYPE_FONT, new File("src/fonts/newCentury.ttf"))
                            .deriveFont(Font.PLAIN, 40);
                    return customFont;
                }
            } else if (tamañoFuente == 3) {
                if (tipoFuente) {
                    Font customFont = Font
                            .createFont(Font.TRUETYPE_FONT, new File("src/fonts/newCentury.ttf"))
                            .deriveFont(Font.BOLD, 30);
                    return customFont;
                } else {
                    Font customFont = Font
                            .createFont(Font.TRUETYPE_FONT, new File("src/fonts/newCentury.ttf"))
                            .deriveFont(Font.PLAIN, 30);
                    return customFont;
                }
            } else if (tamañoFuente == 4) {
                if (tipoFuente) {
                    Font customFont = Font
                            .createFont(Font.TRUETYPE_FONT, new File("src/fonts/newCentury.ttf"))
                            .deriveFont(Font.BOLD, 20);
                    return customFont;
                } else {
                    Font customFont = Font
                            .createFont(Font.TRUETYPE_FONT, new File("src/fonts/newCentury.ttf"))
                            .deriveFont(Font.PLAIN, 20);
                    return customFont;
                }
            } else if (tamañoFuente == 5) {
                if (tipoFuente) {
                    Font customFont = Font
                            .createFont(Font.TRUETYPE_FONT, new File("src/fonts/newCentury.ttf"))
                            .deriveFont(Font.BOLD, 15);
                    return customFont;
                } else {
                    Font customFont = Font
                            .createFont(Font.TRUETYPE_FONT, new File("src/fonts/newCentury.ttf"))
                            .deriveFont(Font.PLAIN, 15);
                    return customFont;
                }
            } else if (tamañoFuente == 6) {
                if (tipoFuente) {
                    Font customFont = Font
                            .createFont(Font.TRUETYPE_FONT, new File("src/fonts/newCentury.ttf"))
                            .deriveFont(Font.BOLD, 12);
                    return customFont;
                } else {
                    Font customFont = Font
                            .createFont(Font.TRUETYPE_FONT, new File("src/fonts/newCentury.ttf"))
                            .deriveFont(Font.PLAIN, 12);
                    return customFont;
                }
            } else {
                JOptionPane.showMessageDialog(null, "valor invalido, ingrese un tamaño del 1 al 5");
                return UIManager.getFont("Label.font");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se logro cargar la fuente de las letras");
            return UIManager.getFont("Label.font");
        }
    }
}
