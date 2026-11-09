import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author ManuelPerez
 * @version 1.0
 */

public class EjemploJDBC {

    private static final String URL = "jdbc:mysql://localhost:3306/mi_bbddd_demo";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        String sqlDropUsuarios = "DROP TABLE IF EXISTS usuarios";
        String sqlCreateUsuarios = "CREATE TABLE usuarios (" +
                "id INT AUTO_INCREMENT PRIMARY KEY" +
                "nombre VARCHAR(100) NOT NULL" +
                "email VARCHAR(100) NOT NULL UNIQUE" +
                ")";
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {


            statement.executeUpdate(sqlDropUsuarios);
            statement.executeUpdate(sqlCreateUsuarios);
            System.out.println("Usuarios creados");
        } catch (SQLException e) {
            System.err.println("Error durante el DDl " + e.getMessage());
            e.printStackTrace();
        }
    }
}
