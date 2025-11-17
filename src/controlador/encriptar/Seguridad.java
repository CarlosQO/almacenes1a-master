package encriptar;

import org.mindrot.jbcrypt.BCrypt;

public class Seguridad {

    // Genera el hash de la contraseña
    public static String encriptar(String contrasena) {
        return BCrypt.hashpw(contrasena, BCrypt.gensalt());
    }

    // Verifica si una contraseña ingresada coincide con el hash almacenado
    public static boolean verificar(String contrasena, String hash) {
        return BCrypt.checkpw(contrasena, hash);
    }

    public static void main(String[] args) {
        String contrasena = "mi_contrasena_segura";
        String hash = encriptar(contrasena);

        System.out.println("Contraseña original: " + contrasena);
        System.out.println("Hash generado: " + hash);

        // Verificación
        boolean esValida = verificar(contrasena, hash);
        System.out.println("¿La contraseña es válida? " + esValida);
    }
    
}
