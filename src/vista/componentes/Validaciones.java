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

     public static boolean validarNumeros(String texto) {
        return Pattern.matches("^\\d+$", texto);
    }

     //productos
    public static boolean validarPrecio(String texto) {
        // Acepta decimales con hasta 2 cifras después del punto
        return Pattern.matches("^\\d+(\\.\\d{1,2})?$", texto);
    }

    public static boolean validarTalla(String texto) {
        // Tallas típicas: S, M, L, XL, XS, XXL
        return Pattern.matches("^(XS|S|M|L|XL|XXL)$", texto.toUpperCase());
    }

    public static boolean validarImagen(String texto) {
        // Acepta nombres de archivo o URLs con extensiones comunes
        return Pattern.matches(".*\\.(jpg|jpeg|png|gif|bmp)$", texto.toLowerCase());
    }

    public static boolean validarNombreProducto(String texto) {
        // Acepta letras (obligatorias), números (opcionales), espacios y tildes
        String regex = "^(?=.*[a-zA-ZáéíóúÁÉÍÓÚñÑ])[a-zA-ZáéíóúÁÉÍÓÚñÑ0-9\\s]*$";
        return Pattern.matches(regex, texto.trim());
    }
    
    //tamño descripcionProducto
    public static boolean validarDescripcionEntre25y55Caracteres(String texto) {
        int longitud = texto.trim().length();
        return longitud >= 25 && longitud <= 55;
    }
    
    //validar cantidad
    public static boolean validarCantidad(String texto) {
        // Debe ser un número entero positivo (sin ceros a la izquierda)
        String regex = "^[1-9]\\d*$";
        return Pattern.matches(regex, texto.trim());
    }

}
