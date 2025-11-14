package vista.vistaSupervisor.componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PlaceholderSupport {
    public static void setPlaceholder(JTextField textField, String placeholder) {
        textField.setForeground(Color.GRAY);
        textField.setText(placeholder);
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }

    public static boolean isPlaceholderVisible(JTextField textField, String placeholder) {
        return textField.getText().equals(placeholder) && textField.getForeground().equals(Color.GRAY);
    }
}
