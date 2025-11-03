package vista.vistaCliente;

import java.util.regex.Pattern;

public class Validaciones {

    public static boolean validarCorreo(String correo) {
        String regex = "^[\\w._%+-]+@{1}[\\w.-]+\\.[a-zA-Z]{2,6}$";
        return Pattern.matches(regex, correo);
    }

    // es para validar nombres, apellidos y campos que solo requieran letras
    public static boolean validarLetras(String texto) {
        return Pattern.matches("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+$", texto);
    }

    public static boolean validarCedula(String cedula) {
        return Pattern.matches("^\\d{9,11}$", cedula);
    }

    public static boolean validarNumeros(String texto) {
        return Pattern.matches("^\\d+$", texto);
    }

    public static boolean validarTelefonos(String texto) {
        return Pattern.matches("^\\d{10}", texto);
    }

    public static boolean validarCVV(String texto) {
        return Pattern.matches("^\\d{3,4}", texto);
    }

    public static boolean validarFecha(String texto) {
        return Pattern.matches("^(0[1-9]|1[0-2])/(\\d{2})$", texto);
    }

    public static boolean validarNumeroCuenta(String texto) {
        return Pattern.matches("^[45]\\d{12,15}$", texto);
    }

    public static boolean validarContraseña(String contrasena) {
        // Al menos 8 caracteres, una mayúscula, una minúscula, un número
        String regex = "^(?=.*[A-Z])(?=.*\\d).{8,}$";
        return Pattern.matches(regex, contrasena);
    }

    public static boolean validarNIT(String nit) {
        return Pattern.matches("^\\d{5,12}(-\\d)?$", nit);
    }

    public static boolean validarMinimo50Caracteres(String texto) {
        return texto != null && texto.trim().length() >= 50;
    }
}
