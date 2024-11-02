import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Change "FitnessAppDB" to your actual database name if different
    private static final String URL = "jdbc:mysql://localhost:3306/fitness_tracker";

    // Change "root" if you're using a different MySQL username
    private static final String USER = "root";

    // Replace "******" with your actual MySQL password
    private static final String PASSWORD = "343540";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Optional: You might want to add this line for older versions of JDBC
            // Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

}