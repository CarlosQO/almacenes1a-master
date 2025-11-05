package vista.componentes;

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

    public static boolean validarDescripcion(String texto) {
        if (texto == null)
            return false;
        texto = texto.trim();

        if (texto.length() < 20 || texto.length() > 55) {
            return false;
        }

        int letras = 0;
        int numeros = 0;
        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c) || "áéíóúÁÉÍÓÚñÑ".indexOf(c) >= 0) {
                letras++;
            } else if (Character.isDigit(c)) {
                numeros++;
            }
        }

        // Calcular porcentaje de letras
        double porcentajeLetras = (double) letras / texto.length();

        // Debe tener al menos el 50% de letras
        return porcentajeLetras >= 0.5;
    }

    public static boolean validarNombrePromocion(String texto) {
        if (texto == null)
            return false;

        texto = texto.trim();

        // Validar longitud
        if (texto.length() < 10 || texto.length() > 15) {
            return false;
        }

        // Contar letras y números
        int letras = 0;
        int numeros = 0;
        for (char c : texto.toCharArray()) {
            if (Character.isLetter(c) || "áéíóúÁÉÍÓÚñÑ".indexOf(c) >= 0) {
                letras++;
            } else if (Character.isDigit(c)) {
                numeros++;
            }
        }

        // Calcular porcentaje de letras
        double porcentajeLetras = (double) letras / texto.length();

        return porcentajeLetras >= 0.5;
    }

    public static boolean validarCantidadPromocionar(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return false;
        }

        if (!texto.matches("\\d+")) {
            return false;
        }

        int cantidad = Integer.parseInt(texto);
        return cantidad > 0;
    }
}
