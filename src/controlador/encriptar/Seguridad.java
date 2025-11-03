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
}
