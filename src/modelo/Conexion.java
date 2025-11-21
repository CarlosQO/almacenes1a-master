package modelo;

import java.sql.DriverManager;
import java.sql.Connection;

public class Conexion {
    private static Conexion instance;
    private final String url = "jdbc:mysql://localhost:3306/bd_almacenes1anorbi";
    private final String user = "root";
    private final String pass = "";

    public Conexion() {
    }

    public static Conexion getInstance() {
        if (instance == null) {
            instance = new Conexion();
        }
        return instance;
    }

    public Connection getConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(url, user, pass);
    }
}