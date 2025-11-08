package vista.componentes;

import java.util.regex.Pattern;

public class Validaciones {

    public static boolean validarSoloLetras(String texto) {
        String patron = "^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]{1,40}$";
        // Si el texto es nulo o vacío, retornamos false directamente
        if (texto == null || texto.trim().isEmpty()) {
            return false;
        }
        return texto.matches(patron);
    }

    public static boolean validarTelefono(String texto) {
        String patron = "^[0-9]{10,11}$";
        if (texto == null || texto.trim().isEmpty()) {
            return false;
        }
        return texto.matches(patron); // el numero debe tener entre 10 y 11 digitos
    }

    public static boolean validarCorreo(String correo) {
        String patron = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        if (correo == null || correo.trim().isEmpty()) {
            return false;
        }
        if (correo.length() > 40) {
            return false;
        }
        return correo.matches(patron);
    }

    public static boolean validarCedula(String cedula) {
        String patron = "^[0-9]{10,11}$";
        if (cedula == null || cedula.trim().isEmpty()) {
            return false;
        }
        return cedula.matches(patron);
    }

    public static boolean validarContrasena(String contrasena) {
        String patron = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@$!%*?&.,_-]{8,}$";
        if (contrasena == null || contrasena.trim().isEmpty()) {
            return false;
        }
        return contrasena.matches(patron);
    }

    public static boolean validarDireccion(String direccion) {
        if (direccion == null)
            return false;

        direccion = direccion.trim();

        // Longitud
        if (direccion.length() < 5 || direccion.length() > 100)
            return false;

        // Formato general (letras, números, #, -, . y espacios)
        if (!direccion.matches("^[A-Za-zÀ-ÿ0-9\\s#\\-.]+$"))
            return false;

        // Debe tener al menos una letra y un número
        if (!direccion.matches(".*[A-Za-z].*") || !direccion.matches(".*[0-9].*"))
            return false;

        return true;
    }

    public static boolean validarNIT(String nit) {
        return Pattern.matches("^\\d{5,12}(-\\d)?$", nit);
    }

    public static boolean validarLetras(String texto) {
        return Pattern.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", texto);
    }

    public static boolean validarNumeroCuenta(String texto) {
        return Pattern.matches("^[45]\\d{12,15}$", texto);
    }

    public static boolean validarNumeros(String texto) {
        return Pattern.matches("^\\d+$", texto);
    }

    public static boolean validarCVV(String texto) {
        return Pattern.matches("^\\d{3,4}", texto);
    }
}
